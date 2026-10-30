import agents.*;
import core.*;
import emergencies.*;
import facilities.*;
import missions.*;
import resources.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private static MarsColony buildColony() {
        Environment env = new Environment();

        Habitat habitat = new Habitat("Habitat-A", 10);
        habitat.addResource(ResourceType.OXYGEN, 500);
        habitat.addResource(ResourceType.WATER, 300);
        habitat.addResource(ResourceType.BATTERY_POWER, 1000);
        habitat.addResource(ResourceType.MEDICAL_SUPPLIES, 20);
        habitat.addResource(ResourceType.SPARE_PARTS, 20);

        List<Facility> facilities = new ArrayList<>();
        facilities.add(habitat);

        ResourcePool pool = new ResourcePool(facilities);
        MissionManager manager = new MissionManager(pool, env);
        MarsColony colony = new MarsColony(env, pool, manager);

        Scientist scientist = new Scientist("S-1", "Dr. Alice", Set.of("biology", "geology"));
        Engineer engineer = new Engineer("E-1", "Bob", Set.of("repair", "construction"));
        ExplorationRover rover = new ExplorationRover("R-1", "Rover-1");
        RepairRobot repairRobot = new RepairRobot("R-2", "RepairBot");

        colony.addCrew(scientist);
        colony.addCrew(engineer);
        colony.addRobot(rover);
        colony.addRobot(repairRobot);
        colony.addFacility(habitat);

        return colony;
    }

    private static void scenario1Exploration(MarsColony colony) {
        System.out.println("--- Scenario 1: Successful Exploration ---");
        printState(colony);

        Scientist scientist = new Scientist("S-2", "Dr. Charlie", Set.of("geology"));
        ExplorationRover rover = new ExplorationRover("R-3", "Rover-3");

        ExplorationMission mission = new ExplorationMission(
                1,
                "Sector 7",
                3,
                20,
                "geology",
                ResourceType.FUEL,
                new ArrayList<>(),
                false,
                1,
                EnumSet.of(Capability.SURFACE_TRAVEL, Capability.RESEARCH)
        );

        OperationResult result = colony.getMissionManager().submit(mission, List.of(scientist, rover));
        advanceMission(colony, 5);
        System.out.println("Mission Execution Result: " + result.getReason());
        System.out.println("Scenario 1 completed.\n");
    }

    private static void scenario2Rejection(MarsColony colony) {
        System.out.println("--- Scenario 2: Mission Rejection ---");

        Engineer engineer = new Engineer("E-2", "Engineer-Maria", Set.of("repair"));
        engineer.setAvailable(false);

        ExplorationRover rover = new ExplorationRover("R-4", "Rover-4");
        ExplorationMission badMission = new ExplorationMission(
                2,
                "Bad Mission",
                2,
                10,
                "repair",
                ResourceType.FUEL,
                new ArrayList<>(),
                false,
                1,
                EnumSet.of(Capability.SURFACE_TRAVEL)
        );

        OperationResult result = colony.getMissionManager().submit(badMission, List.of(engineer, rover));
        System.out.println("Expected Rejection Result: " + result.getReason());
        System.out.println("Scenario 2 completed.\n");
    }

    private static void scenario3ResourceShortage(MarsColony colony) {
        System.out.println("--- Scenario 3: Resource Shortage ---");

        Map<ResourceType, Integer> hugeDemand = new HashMap<>();
        hugeDemand.put(ResourceType.OXYGEN, 9999);

        OperationResult result = colony.getResourcePool().canSupply(hugeDemand);
        System.out.println("Resource Check Result: " + result.getReason());
        System.out.println("Scenario 3 completed.\n");
    }

    private static void scenario4OxygenLeak(MarsColony colony) {
        System.out.println("--- Scenario 4: Oxygen Leak Emergency ---");

        Habitat habitat = (Habitat) colony.getFacilities().get(0);
        OxygenLeak oxygenLeak = new OxygenLeak(habitat, 50);

        System.out.println("Emergency Triggered: Oxygen Leak");
        System.out.println("Handling emergency...");

        colony.triggerEmergency(oxygenLeak);

        System.out.println("Emergency State: " + oxygenLeak.getState());
        System.out.println("Emergency Reason: " + oxygenLeak.getReason());
        System.out.println("Remaining Oxygen: " + habitat.getOxygen());
        System.out.println("Scenario 4 completed.\n");
    }

    private static void scenario5DustStorm(MarsColony colony) {
        System.out.println("--- Scenario 5: Dust Storm Effect ---");

        colony.getEnvironment().setCondition(EnvironmentCondition.DUST_STORM);

        Scientist scientist = new Scientist("S-3", "Dr. Quinn", Set.of("meteorology"));
        ExplorationRover rover = new ExplorationRover("R-5", "Rover-5");

        ExplorationMission stormMission = new ExplorationMission(
                5,
                "Storm Exploration",
                4,
                30,
                "meteorology",
                ResourceType.FUEL,
                new ArrayList<>(),
                true,
                1,
                EnumSet.of(Capability.SURFACE_TRAVEL, Capability.RESEARCH)
        );

        OperationResult result = colony.getMissionManager().submit(stormMission, List.of(scientist, rover));
        System.out.println("Result during Dust Storm: " + result.getReason());
        System.out.println("Scenario 5 completed.\n");
    }

    private static void advanceMission(MarsColony colony, int steps) {
        for (int i = 0; i < steps; i++) {
            colony.getMissionManager().onTick(colony);
        }
    }

    private static void printState(MarsColony colony) {
        System.out.println(colony.generateReport());
        System.out.println("----------------------------------------");
    }
}