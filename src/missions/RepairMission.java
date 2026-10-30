/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package missions;

import java.util.Set;
import facilities.*;
import resources.ResourceType;
import agents.Capability;


/**
 *
 * @author asus
 */
public final class RepairMission extends Mission {

    private Facility targetFacility;
    private Equipment targetEquipment;

    public RepairMission(
            int id,
            int duration,
            int minAgents,
            int spareParts,
            Facility targetFacility,
            Equipment targetEquipment,
            Set<Capability> requiredCapabilities) {

        super(id, "Repair target", duration, false, false, minAgents, requiredCapabilities);

        if (targetFacility == null && targetEquipment == null) {
            throw new IllegalArgumentException(
                    "A facility or equipment target is required");
        }
        this.targetFacility = targetFacility;
        this.targetEquipment = targetEquipment;
        requireResource(ResourceType.SPARE_PARTS, spareParts);
        if (targetFacility != null) {
            addTargetFacility(targetFacility);
        }

        if (targetEquipment != null) {
            requireEquipment(targetEquipment);
        }
    }

    public Facility getTargetFacility() {
        return targetFacility;
    }

    public Equipment getTargetEquipment() {
        return targetEquipment;
    }
}
