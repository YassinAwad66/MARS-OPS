/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package missions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import core.*;
import common.*;
import facilities.Equipment;
import agents.Agent;
import resources.*;

public final class MissionManager implements Tickable {
    private Mission activeMission;
    private List<Mission> history = new ArrayList<>();
    private ResourcePool resourcePool;
    private Environment environment;

    public MissionManager(ResourcePool resourcePool, Environment environment) {
        if (resourcePool == null || environment == null)
            throw new IllegalArgumentException("dependencies are required");
        this.resourcePool = resourcePool;
        this.environment = environment;
    }

    public OperationResult submit(Mission mission, List<Agent> agents) {
        if (mission == null)
            return OperationResult.fail("Mission is required");
        if (mission.getState() != null)
            return OperationResult.fail("Mission was already submitted");

        String rejection = null;
        if (activeMission != null) {
            rejection = "Another mission is already active";
        } else if (mission.needsSurface() && environment.isDustStorm()) {
            rejection = "Surface operations are not allowed during a dust storm";
        } else if (mission.needsComms() && environment.isCommsDisrupted()) {
            rejection = "Communication-dependent operations are not allowed during a disruption";
        } else if (agents == null || !mission.covers(agents) || !allAvailableAndOperational(agents)) {
            rejection = "Agent does not possess the required capability";
        } else {
            OperationResult supply = resourcePool.canSupply(mission.getResourceCost());
            if (!supply.isSuccess())
                rejection = supply.getReason();
        }

        if (rejection != null) {
            mission.fail(rejection);
            history.add(mission);
            return OperationResult.fail(rejection);
        }

        OperationResult spent = resourcePool.consume(mission.getResourceCost());
        if (!spent.isSuccess()) {
            mission.fail(spent.getReason());
            history.add(mission);
            return spent;
        }
        mission.assign(agents);
        for (Agent agent : agents)
            agent.setAvailable(false);
        activeMission = mission;
        return OperationResult.ok();
    }

    @Override
    public void onTick(MarsColony colony) {
        if (activeMission == null)
            return;
        Mission mission = activeMission;

        if (mission.getState() == MissionState.ASSIGNED) {
            mission.start();
            // Running agents must still be usable; agent-specific capability checks happen
            // here too.
        } else if (mission.getState() == MissionState.IN_PROGRESS) {
            if (environment.isDustStorm() && mission.needsSurface()) {
                mission.fail("Surface mission failed because of a dust storm");
            } else if (environment.isCommsDisrupted() && mission.needsComms()) {
                mission.fail("Mission failed because communication was disrupted");
            } else if (!assignedAgentsUsable(mission)) {
                mission.fail("An assigned agent is no longer usable");
            } else if (!equipmentUsable(mission)) {
                mission.fail("Required equipment is damaged or unavailable");
            } else {
                mission.advance();
            }
        }

        if (mission.isFinished())
            finishActiveMission();
    }

    private boolean allAvailableAndOperational(List<Agent> agents) {
        if (agents.isEmpty())
            return false;
        for (Agent agent : agents) {
            if (agent == null || !agent.isAvailable() || !agent.isOperational())
                return false;
        }
        return true;
    }

    private boolean assignedAgentsUsable(Mission mission) {
        for (Agent agent : mission.getAssignedAgents()) {
            if (!agent.isOperational() || !agent.canPerform(mission))
                return false;
        }
        return true;
    }

    private boolean equipmentUsable(Mission mission) {
        for (Equipment equipment : mission.getRequiredEquipment()) {
            if (equipment == null || !equipment.isWorking())
                return false;
        }
        return true;
    }

    private void finishActiveMission() {
        Mission ended = activeMission;
        release(ended);
        history.add(ended);
        activeMission = null;
    }

    private void release(Mission mission) {
        for (Agent agent : mission.getAssignedAgents())
            agent.setAvailable(true);
    }

    public void failActiveMission(String reason) {
        if (activeMission == null)
            return;
        activeMission.fail(reason);
        finishActiveMission();
    }

    public Mission getActiveMission() {
        return activeMission;
    }

    public List<Mission> getHistory() {
        return Collections.unmodifiableList(history);
    }

}
