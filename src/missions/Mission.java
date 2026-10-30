
package missions;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;
import java.util.Collections;
import java.util.EnumMap;

import facilities.*;
import resources.ResourceType;
import agents.Agent;
import agents.Capability;

public abstract class Mission {

    protected final int id;
    protected final String name;
    protected final int duration;
    protected int elapsed;
    private MissionState state;
    protected String failureReason;
    private final Set<Capability> requiredCapabilities = EnumSet.noneOf(Capability.class);
    protected String requiredSkill;
    protected int batteryNeeded;
    protected final Map<ResourceType, Integer> resourceCost = new EnumMap<>(ResourceType.class);
    private final List<Equipment> requiredEquipment = new ArrayList<>();
    private final List<Facility> targetFacilities = new ArrayList<>();
    protected final List<Agent> assignedAgents = new ArrayList<>();
    private final int minimumAgents;

    protected boolean needsSurface;
    protected boolean needsCommunication;

    public Mission(int id, String name, int duration, boolean needsSurface, boolean needsCommunication, int minimumAgents, Set<Capability> requiredCapabilities) {
        if (duration <= 0) {
            throw new IllegalArgumentException("duration must be positive");
        }
        if (minimumAgents < 1) {
            throw new IllegalArgumentException("At least one agent is required");
        }
        this.id = id;
        this.duration = duration;
        this.name = name;
        this.needsSurface = needsSurface;
        this.needsCommunication = needsCommunication;
        this.minimumAgents = minimumAgents;
        for (Capability requiredCapability : requiredCapabilities) {
            requireCapability(requiredCapability);
        }
    }

    protected void requireCapability(Capability c) {
        if (c == null) {
            throw new IllegalArgumentException("required Capability can not be null");
        }
        requiredCapabilities.add(c);
    }

    protected void requireSkill(String skill) {
        if (skill == null || skill.trim().isEmpty()) {
            throw new IllegalArgumentException("required skills can not be null");
        }
        this.requiredSkill = skill.trim();
    }

    protected void requireBattery(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("requireBattery cannot be negative");
        }
        this.batteryNeeded = amount;
    }

    protected void requireResource(ResourceType t, int qty) {
        if (t == null || qty <= 0) {
            throw new IllegalArgumentException("required Resources cannot be negative or null ");
        }
        resourceCost.merge(t, qty, Integer::sum);
    }

    protected void requireEquipment(Equipment item) {
        if (item == null) {
            throw new IllegalArgumentException("equipment cannot be null");
        }
        requiredEquipment.add(item);
    }

    protected void addTargetFacility(Facility facility) {
        if (facility == null) {
            throw new IllegalArgumentException("facility cannot be null");
        }
        targetFacilities.add(facility);
    }

    public void assign(List<Agent> agents) {
        if (state != null) {
            throw new IllegalStateException("Mission " + id + " was already submitted");
        }
        if (agents == null || agents.isEmpty()) {
            throw new IllegalArgumentException("At least one agent is required");
        }

        assignedAgents.clear();
        assignedAgents.addAll(agents);
        state = MissionState.ASSIGNED;
    }

    public void start() {
        if (state != MissionState.ASSIGNED) {
            throw new IllegalStateException("Cannot start from" + state);
        }
        state = MissionState.IN_PROGRESS;
    }

    public void advance() {
        if (state != MissionState.IN_PROGRESS) {
            throw new IllegalStateException("Cannot start from" + state);
        }
        elapsed++;
        if (elapsed >= duration) {
            state = MissionState.COMPLETED;
        }
    }


    public void fail(String reason) {
        if (state == MissionState.COMPLETED || state == MissionState.FAILED) {
            throw new IllegalStateException("Mission (" + id + ") was already finished");
        }

        if (reason == null || reason.trim().isEmpty()) {
            reason = "Mission failed";
        }
        state = MissionState.FAILED;
        failureReason = reason;

    }

    public boolean covers(List<Agent> agents) {
        if (agents == null || agents.size() < minimumAgents) {
            return false;
        }

        for (Agent agent : agents) {
            if (agent == null || !agent.canPerform(this)) {
                return false;
            }
        }
        return true;
    }

//_______________________________________________getters__________________________________________________//
    public boolean isFinished() {
        return state == MissionState.COMPLETED || state == MissionState.FAILED;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getElapsed() {
        return elapsed;
    }

    public MissionState getState() {
        return state;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public Set<Capability> getRequiredCapabilities() {
        return Collections.unmodifiableSet(requiredCapabilities);
    }

    public String getRequiredSkill() {
        return requiredSkill;
    }

    public int getBatteryNeeded() {
        return batteryNeeded;
    }

    public Map<ResourceType, Integer> getResourceCost() {
        return Collections.unmodifiableMap(resourceCost);
    }

    public List<Equipment> getRequiredEquipment() {
        return Collections.unmodifiableList(requiredEquipment);
    }

    public List<Facility> getTargetFacilities() {
        return Collections.unmodifiableList(targetFacilities);
    }

    public List<Agent> getAssignedAgents() {
        return Collections.unmodifiableList(assignedAgents);
    }

    public boolean needsSurface() {
        return needsSurface;
    }

    public boolean needsComms() {
        return needsCommunication;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + id + " '" + name + "' ["
                + (state == null ? "NOT_SUBMITTED" : state) + ", " + elapsed + "/" + duration + "]";
    }

}
