
package project;


import java.util.List;
import java.util.Set;

public final class ResearchMission extends Mission {

    protected Laboratory laboratory;

    public ResearchMission(
            int id,
            String requiredSkill,
            int duration,
            Laboratory laboratory,
            List<Equipment> requiredEquipment,
            ResourceType resourceType,
            int resourceAmount,
            int minAgents,
            Set<Capability> requiredCapabilities) {
        super(id, "Research " + requiredSkill, duration, false, false, minAgents, requiredCapabilities);
        if (laboratory == null) {
            throw new IllegalArgumentException("Laboratory is required");
        }

        this.laboratory = laboratory;
        addTargetFacility(laboratory);
        requireSkill(requiredSkill);

        if (requiredEquipment != null) {
            for (Equipment equipment : requiredEquipment) {
                requireEquipment(equipment);
            }
        }

        if (resourceAmount > 0) {
            requireResource(resourceType, resourceAmount);
        }
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }
}
