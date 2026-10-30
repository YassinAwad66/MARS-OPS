package main;

import core.*;
import agents.*;
import emergencies.*;
import facilities.*;
import missions.*;
import resources.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== MARS-OPS Simulation Started ===\n");

        MarsColony colony = buildColony();

        scenario1Exploration(colony);
        scenario2Rejection(colony);
        scenario3ResourceShortage(colony);
        scenario4OxygenLeak(colony);
        scenario5DustStorm(colony);

        System.out.println("\n=== MARS-OPS Simulation Completed ===");
    }

    /**
     * Build and initialize a colony.
     */
    private static MarsColony buildColony() {

        Environment env = new Environment();

        // Create Habitat
        Habitat habitat = new Habitat("Habitat-A", 10);

        // Initialize resources
        habitat.addResource(ResourceType.OXYGEN, 500);
        habitat.addResource(ResourceType.WATER, 300);
        habitat.addResource(ResourceType.POWER, 1000);

        List<Facility> facilities = new ArrayList<>();
        facilities.add(habitat);

        ResourcePool pool = new ResourcePool(facilities);

        MissionManager manager = new MissionManager(pool, env);

        MarsColony colony = new MarsColony(env, pool, manager);

        colony.addFacility(habitat);

        return colony;
    }

    /**
     * Scenario 1: Successful Exploration Mission.
     */
    private static void scenario1Exploration(MarsColony colony) {

        System.out.println("--- Scenario 1: Successful Exploration ---");

        printState(colony);

        Scientist scientist = new Scientist("Dr. Alice");

        Robot rover = new Robot(
                "Rover-1",
                AgentCapability.EXPLORATION
        );

        ExplorationMission mission = new ExplorationMission(
                "Mapping Sector 7",
                scientist,
                rover
        );

        OperationResult result =
                colony.getMissionManager().submitAndExecute(mission);

        System.out.println("Mission Execution Result: " + result.getReason());

        System.out.println("Scenario 1 completed.\n");
    }

    /**
     * Scenario 2: Mission Rejection.
     */
    private static void scenario2Rejection(MarsColony colony) {

        System.out.println("--- Scenario 2: Mission Rejection ---");

        Engineer engineer = new Engineer("Bob");

        Robot rover = new Robot(
                "Rover-2",
                AgentCapability.EXPLORATION
        );

        ExplorationMission badMission = new ExplorationMission(
                "Bad Mission",
                engineer,
                rover
        );

        OperationResult result =
                colony.getMissionManager().submit(badMission);

        System.out.println("Expected Rejection Result: " + result.getReason());

        System.out.println("Scenario 2 completed.\n");
    }

    /**
     * Scenario 3: Resource Shortage Check.
     */
    private static void scenario3ResourceShortage(MarsColony colony) {

        System.out.println("--- Scenario 3: Resource Shortage ---");

        Map<ResourceType, Integer> hugeDemand = new HashMap<>();

        hugeDemand.put(ResourceType.OXYGEN, 9999);

        OperationResult result =
                colony.getResourcePool().canSupply(hugeDemand);

        System.out.println("Resource Check Result: " + result.getReason());

        System.out.println("Scenario 3 completed.\n");
    }

    /**
     * Scenario 4: Oxygen Leak Emergency.
     */
    private static void scenario4OxygenLeak(MarsColony colony) {

        System.out.println("--- Scenario 4: Oxygen Leak Emergency ---");

        Habitat habitat = (Habitat) colony.getFacilities().get(0);

        OxygenLeak oxygenLeak = new OxygenLeak(
                habitat,
                50,
                "Oxygen leak detected in Habitat-A"
        );

        System.out.println("Emergency Triggered: Oxygen Leak");

        System.out.println("Handling emergency...");

        colony.triggerEmergency(oxygenLeak);

        System.out.println("Emergency State: " + oxygenLeak.getState());

        System.out.println("Emergency Reason: " + oxygenLeak.getReason());

        System.out.println("Remaining Oxygen: " + habitat.getOxygen());

        System.out.println("Scenario 4 completed.\n");
    }

    /**
     * Scenario 5: Dust Storm Effect.
     */
    private static void scenario5DustStorm(MarsColony colony) {

        System.out.println("--- Scenario 5: Dust Storm Effect ---");

        colony.getEnvironment().setCondition(
                EnvironmentCondition.DUST_STORM
        );

        Scientist scientist = new Scientist("Dr. Charlie");

        Robot rover = new Robot(
                "Rover-3",
                AgentCapability.EXPLORATION
        );

        ExplorationMission stormMission = new ExplorationMission(
                "Storm Exploration",
                scientist,
                rover
        );

        OperationResult result =
                colony.getMissionManager().submitAndExecute(stormMission);

        System.out.println("Result during Dust Storm: " + result.getReason());

        System.out.println("Scenario 5 completed.\n");
    }

    /**
     * Print colony status report.
     */
    private static void printState(MarsColony colony) {

        System.out.println(colony.generateReport());

        System.out.println("----------------------------------------");
    }
}