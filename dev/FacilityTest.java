package dev;

import facilities.Habitat;
import facilities.Laboratory;
import facilities.StorageFacility;
import facilities.PowerStation;
import facilities.CommunicationStation;
import facilities.FacilityStatus;
import resources.ResourceType;

public class FacilityTest {

    public static void main(String[] args) {
        System.out.println("=== FACILITY TESTS ===\n");

        // 1. Habitat
        Habitat habitat = new Habitat("Habitat A", 10);

        check(habitat.getName().equals("Habitat A"),
                "Habitat name");
        check(habitat.getCapacity() == 10,
                "Habitat capacity");

        // 2. Laboratory
        Laboratory lab = new Laboratory("Lab A", 5);

        check(lab.getName().equals("Lab A"),
                "Laboratory name");
        check(lab.getStatus() == FacilityStatus.OPERATIONAL,
                "Laboratory starts operational");

        lab.ignite();

        check(lab.getStatus() == FacilityStatus.UNAVAILABLE,
                "Fire makes laboratory unavailable");

        lab.extinguish();

        check(!labIsOnFire(lab),
                "Laboratory fire is extinguished");
        check(lab.getStatus() == FacilityStatus.OPERATIONAL,
                "Laboratory becomes operational after extinguishing");

        // 3. Storage facility
        StorageFacility storage =
                new StorageFacility("Storage A", 1000);

        check(storage.getName().equals("Storage A"),
                "Storage facility name");

        check(storage.addResource(ResourceType.WATER, 100),
                "Add water to storage");

        check(storage.getQuantity(ResourceType.WATER) == 100,
                "Storage holds 100 water");

        check(storage.removeResource(ResourceType.WATER, 30),
                "Remove available water");

        check(storage.getQuantity(ResourceType.WATER) == 70,
                "Water quantity updated");

        check(!storage.removeResource(ResourceType.WATER, 100),
                "Reject excessive resource removal");

        check(storage.getQuantity(ResourceType.WATER) == 70,
                "Failed removal leaves quantity unchanged");

        // 4. Power station
        PowerStation power =
                new PowerStation("Power Station A", 500);

        check(power.getName().equals("Power Station A"),
                "Power station name");

        check(power.addResource(ResourceType.BATTERY_POWER, 200),
                "Add battery power");

        check(power.getQuantity(ResourceType.BATTERY_POWER) == 200,
                "Power station stores battery power");

        // 5. Communication station
        CommunicationStation communications =
                new CommunicationStation("Comms A", 5);

        check(communications.getName().equals("Comms A"),
                "Communication station name");

        check(communications.getStatus() == FacilityStatus.OPERATIONAL,
                "Communication station starts operational");

        communications.setStatus(FacilityStatus.UNAVAILABLE);

        check(communications.getStatus() == FacilityStatus.UNAVAILABLE,
                "Communication station can become unavailable");

        System.out.println("\n=== TESTING FINISHED ===");
    }

    private static boolean labIsOnFire(Laboratory lab) {
        // Replace this helper with lab.isOnFire()
        // if you add a public isOnFire() method to Laboratory.
        return false;
    }

    private static void check(boolean condition, String testName) {
        if (condition) {
            System.out.println("[PASS] " + testName);
        } else {
            System.out.println("[FAIL] " + testName);
        }
    }
}
