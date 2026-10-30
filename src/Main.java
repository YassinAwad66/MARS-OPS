import agents.*;
import core.*;
import emergencies.*;
import facilities.*;
import missions.*;
import resources.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    // Objects from the object diagram that are shared between buildColony() and scenario 1
    private static Scientist sci1;
    private static ExplorationRover rover1;
    private static Equipment drill1;

    public static void main(String[] args) {
        System.out.println("=== MARS-OPS Simulation Started ===\n");

        MarsColony colony = buildColony();
        Simulation simulation = new Simulation(colony);

        scenario1Exploration(colony, simulation);
        scenario2Rejection(colony);
        scenario3ResourceShortage(colony);
        scenario4OxygenLeak(colony);
        scenario5DustStorm(colony);

        System.out.println("\n=== MARS-OPS Simulation Completed ===");
    }

    private static MarsColony buildColony() {
        Environment env = new Environment();                       // env1, condition = NORMAL

        Habitat habitat = new Habitat("Habitat A", 6);             // habA: name "Habitat A", capacity 6
        habitat.addResource(ResourceType.OXYGEN, 300);             // habA stock {OXYGEN=300}
        habitat.addResource(ResourceType.WATER, 300);
        habitat.addResource(ResourceType.BATTERY_POWER, 1000);
        habitat.addResource(ResourceType.MEDICAL_SUPPLIES, 20);
        habitat.addResource(ResourceType.SPARE_PARTS, 20);

        StorageFacility storage = new StorageFacility("Storage", 100);
        storage.addResource(ResourceType.OXYGEN, 200);
        storage.addResource(ResourceType.FUEL, 100);               // diagram shows 90 AFTER the mission deducts 10
        storage.addResource(ResourceType.BATTERY_POWER, 50);
        storage.addResource(ResourceType.SPARE_PARTS, 20);

        List<Facility> facilities = new ArrayList<>();
        facilities.add(habitat);
        facilities.add(storage);                                   // facilities = [habA, store1]

        ResourcePool pool = new ResourcePool(facilities);          // pool1, facilities = [habA, store1]
        MissionManager manager = new MissionManager(pool, env);    // mgr1
        MarsColony colony = new MarsColony(env, pool, manager);    // colony1

        Scientist scientist = new Scientist("S-1", "Dr. Nour", Set.of("geology"));   // sci1: "Dr. Nour", skills {"geology"}
        Engineer engineer = new Engineer("E-1", "Bob", Set.of("repair", "construction"));
        ExplorationRover rover = new ExplorationRover("R-1", "Rover-1");             // rover1: "Rover-1"
        RepairRobot repairRobot = new RepairRobot("R-2", "RepairBot");
        rover.drainBattery(10);
        drill1 = new Equipment("Drill");
        rover.load(drill1);
        habitat.addEquipment(drill1);

        sci1 = scientist;
        rover1 = rover;

        colony.addCrew(scientist);
        colony.addCrew(engineer);
        colony.addRobot(rover);
        colony.addRobot(repairRobot);
        colony.addFacility(habitat);
        colony.addFacility(storage);

        return colony;
    }

    private static void scenario1Exploration(MarsColony colony, Simulation simulation) {
        System.out.println("--- Scenario 1: Successful Exploration ---");
        System.out.println("State before execution:");
        printState(colony);

        ExplorationMission mission = new ExplorationMission(
                1,
                "Valles Sector",
                3,
                20,
                "geology",
                Map.of(ResourceType.FUEL, 10, ResourceType.BATTERY_POWER, 5),
                List.of(drill1),
                false,
                1,
                EnumSet.of(Capability.SURFACE_TRAVEL, Capability.RESEARCH)
        );

        OperationResult result = colony.getMissionManager().submit(mission, List.of(sci1, rover1));
        if (result.isSuccess()) {
            simulation.advanceTime(mission.getDuration() + 1);
            if (mission.getState() == MissionState.COMPLETED) {
                System.out.println("Mission completed successfully: " + mission.getState());
            } else {
                System.out.println("Mission failed during execution.");
                System.out.println("Reason: " + mission.getFailureReason());
            }
        } else {
            System.out.println("Mission cannot be assigned.");
            System.out.println("Reason: " + result.getReason());
        }
        System.out.println("State after execution:");
        printState(colony);
        System.out.println("Scenario 1 completed.\n");
    }

    private static void scenario2Rejection(MarsColony colony) {
        System.out.println("--- Scenario 2: Mission Rejection ---");

        Scientist unsuitableScientist = new Scientist("S-2", "Dr. Maria", Set.of("geology"));
        ExplorationMission badMission = new ExplorationMission(
                2,
                "Bad Mission",
                2,
                10,
                "geology",
                ResourceType.FUEL,
                new ArrayList<>(),
                false,
                1,
                EnumSet.of(Capability.SURFACE_TRAVEL, Capability.RESEARCH)
        );

        OperationResult result = colony.getMissionManager().submit(badMission, List.of(unsuitableScientist));
        System.out.println("Mission cannot be assigned.");
        System.out.println("Reason: " + result.getReason());
        System.out.println("Mission state: " + badMission.getState());
        System.out.println("Active mission: " + colony.getMissionManager().getActiveMission());
        System.out.println("Scenario 2 completed.\n");
    }

    private static void scenario3ResourceShortage(MarsColony colony) {
        System.out.println("--- Scenario 3: Resource Shortage ---");

        Engineer engineer = (Engineer) colony.getCrew().get(1);
        Equipment target = new Equipment("Spare panel");
        RepairMission mission = new RepairMission(
                3,
                2,
                1,
                9999,
                null,
                target,
                EnumSet.of(Capability.REPAIR)
        );
        int partsBefore = colony.getTotal(ResourceType.SPARE_PARTS);
        OperationResult result = colony.getMissionManager().submit(mission, List.of(engineer));
        int partsAfter = colony.getTotal(ResourceType.SPARE_PARTS);

        System.out.println("Mission accepted: " + result.isSuccess());
        System.out.println("Reason: " + result.getReason());
        System.out.println("Spare parts before: " + partsBefore);
        System.out.println("Spare parts after: " + partsAfter);
        System.out.println("Scenario 3 completed.\n");
    }

    private static void scenario4OxygenLeak(MarsColony colony) {
        System.out.println("--- Scenario 4: Oxygen Leak Emergency ---");

        Habitat habitat = (Habitat) colony.getFacilities().get(0);
        OxygenLeak oxygenLeak = new OxygenLeak(habitat, 50);

        System.out.println("Emergency detected: Oxygen leak at " + habitat.getName());
        List<Agent> responders = colony.getAvailableAgents().stream()
                .filter(agent -> agent.getCapabilities().contains(Capability.REPAIR))
                .toList();
        System.out.println("Suitable responders: " + responders.stream()
                .map(Agent::getName)
                .toList());
        System.out.println("Spare parts before response: " + colony.getTotal(ResourceType.SPARE_PARTS));

        colony.triggerEmergency(oxygenLeak);

        System.out.println("Emergency State: " + oxygenLeak.getState());
        System.out.println("Emergency Reason: " + oxygenLeak.getReason());
        System.out.println("Remaining Oxygen: " + habitat.getOxygen());
        System.out.println("Facility status: " + (habitat.isOperational() ? "OPERATIONAL" : "UNAVAILABLE"));
        System.out.println("Spare parts after response: " + colony.getTotal(ResourceType.SPARE_PARTS));
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
                false,
                1,
                EnumSet.of(Capability.SURFACE_TRAVEL, Capability.RESEARCH)
        );

        OperationResult result = colony.getMissionManager().submit(stormMission, List.of(scientist, rover));
        System.out.println("Exploration mission cannot start.");
        System.out.println("Reason: " + result.getReason());
        System.out.println("Active mission: " + colony.getMissionManager().getActiveMission());
        System.out.println("Scenario 5 completed.\n");
    }

    private static void printState(MarsColony colony) {
        System.out.println(colony.generateReport());
        System.out.println("----------------------------------------");
    }
}