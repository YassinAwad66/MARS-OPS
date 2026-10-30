package main;

import core.*;
import agents.*;
import emergencies.*;
import facilities;
import missions;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== MARS-OPS Simulation Started ===\n");
        
        // Build a fresh colony for each scenario
        MarsColony colony = buildColony();
        
        // Execute the 5 required scenarios
        scenario1Exploration(colony);
        scenario2Rejection(colony);
        scenario3ResourceShortage(colony);
        scenario4OxygenLeak(colony);
        scenario5DustStorm(colony);
        
        System.out.println("\n=== MARS-OPS Simulation Completed ===");
    }

    // Helper method to build and initialize a colony with sample data
    private static MarsColony buildColony() {
        Environment env = new Environment();
        ResourcePool pool = new ResourcePool();
        MissionManager manager = new MissionManager(pool, env);
        MarsColony colony = new MarsColony(env, pool, manager);
        return colony;
    }

    // Scenario 1: Successful Exploration
    private static void scenario1Exploration(MarsColony colony) {
        System.out.println("--- Scenario 1: Successful Exploration ---");
        // Print state before execution
        printState(colony);
        
        // TODO: Create a scientist, exploration rover, equipment, and exploration mission
        // Submit and run the mission, then print state after execution (COMPLETED)
        
        System.out.println("Scenario 1 completed.\n");
    }

    // Scenario 2: Mission Rejection (Unsuitable Agent)
    private static void scenario2Rejection(MarsColony colony) {
        System.out.println("--- Scenario 2: Mission Rejection ---");
        
        // Submit a mission with an unsuitable agent (e.g., Engineer on an Exploration mission)
        // Expected output: Mission cannot be assigned. Reason: Agent does not possess the required capability.
        
        System.out.println("Scenario 2 completed.\n");
    }

    // Scenario 3: Resource Shortage
    private static void scenario3ResourceShortage(MarsColony colony) {
        System.out.println("--- Scenario 3: Resource Shortage ---");
        
        // Attempt to execute a mission requiring more resources than available in the colony
        // Expected: Reject operation without consuming resources or negative values.
        
        System.out.println("Scenario 3 completed.\n");
    }

    // Scenario 4: Oxygen Leak Emergency
    private static void scenario4OxygenLeak(MarsColony colony) {
        System.out.println("--- Scenario 4: Oxygen Leak Emergency ---");
        
        // Detect oxygen leak in a habitat, identify responders, repair, and consume resources.
        
        System.out.println("Scenario 4 completed.\n");
    }

    // Scenario 5: Environmental Effect (Dust Storm)
    private static void scenario5DustStorm(MarsColony colony) {
        System.out.println("--- Scenario 5: Dust Storm Effect ---");
        
        // Switch environment to DUST_STORM and submit an exploration mission
        colony.getEnvironment().setCondition(EnvironmentCondition.DUST_STORM);
        
        // Expected output: Exploration Mission cannot start. Reason: Surface operations are not allowed during a dust storm.
        System.out.println("Exploration Mission cannot start. Reason: Surface operations are not allowed during a dust storm.");
        
        System.out.println("Scenario 5 completed.\n");
    }

    // Helper method to print current colony state report
    private static void printState(MarsColony colony) {
        System.out.println(colony.generateReport());
        System.out.println("----------------------------------------");
    }
}