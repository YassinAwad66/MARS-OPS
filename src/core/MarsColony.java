package core;

import java.util.*;
import resources.*;
import facilities.*;
import missions.*;
import agents.*;
import emergencies.*;

public class MarsColony {
    private List<CrewMember> crew;
    private List<Robot> robots;
    private List<Facility> facilities;
    private Environment environment;
    private ResourcePool resourcePool;
    private MissionManager missionManager;
    private List<Emergency> emergencyLog;

    // Constructor to initialize the colony components
    public MarsColony(Environment environment, ResourcePool resourcePool, MissionManager missionManager) {
        this.crew = new ArrayList<>();
        this.robots = new ArrayList<>();
        this.facilities = new ArrayList<>();
        this.environment = environment;
        this.resourcePool = resourcePool;
        this.missionManager = missionManager;
        this.emergencyLog = new ArrayList<>();
    }

    // Add crew member to the colony
    public void addCrew(CrewMember c) {
        crew.add(c);
    }

    // Add robot to the colony
    public void addRobot(Robot r) {
        robots.add(r);
    }

    // Add facility to the colony
    public void addFacility(Facility f) {
        facilities.add(f);
    }

    // Get all agents (crew members and robots combined)
    public List<Agent> getAllAgents() {
        List<Agent> allAgents = new ArrayList<>();
        allAgents.addAll(crew);
        allAgents.addAll(robots);
        return allAgents;
    }

    // Get available agents for missions or emergencies
    public List<Agent> getAvailableAgents() {
        List<Agent> availableAgents = new ArrayList<>();
        for (Agent agent : getAllAgents()) {
            if (agent.isAvailable() && agent.isOperational()) {
                availableAgents.add(agent);
            }
        }
        return availableAgents;
    }

    // Submit a mission to the mission manager
    public OperationResult submitMission(Mission m, List<Agent> agents) {
        return missionManager.submit(m, agents);
    }

    // Trigger an emergency and add it to the emergency log
    public void triggerEmergency(Emergency e) {
        emergencyLog.add(e);
        e.handle(this);
    }

    public boolean hasCapabilities(Set<Capability> requiredCapabilities) {
        if (requiredCapabilities == null || requiredCapabilities.isEmpty()) {
            return true;
        }

        for (Capability required : requiredCapabilities) {
            boolean found = false;
            for (Agent agent : getAllAgents()) {
                if (agent != null && agent.isOperational() && agent.getCapabilities() != null && agent.getCapabilities().contains(required)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    // Get total quantity of a specific resource across all operational facilities
    public int getTotal(ResourceType t) {
        return resourcePool.getTotal(t);
    }

    // Getters for colony parts
    public List<CrewMember> getCrew() {
        return crew;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public ResourcePool getResourcePool() {
        return resourcePool;
    }

    public MissionManager getMissionManager() {
        return missionManager;
    }

    public List<Emergency> getEmergencyLog() {
        return emergencyLog;
    }

    // Generate colony status report
    public String generateReport() {
        return ColonyReport.build(this);
    }
}