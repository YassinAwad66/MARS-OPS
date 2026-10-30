package missions;
import java.util.List;
import java.util.Map;
import java.util.Set;
import resources.ResourceType;
import facilities.Equipment;    
import agents.Capability;

public final class ExplorationMission extends Mission {

    private final String region;

    public ExplorationMission(
            int id,
            String region,
            int duration,
            int batteryNeeded,
            String requiredSkill,
            Map<ResourceType, Integer> resourceCosts,
            List<Equipment> requiredEquipment,
            boolean needsCommunication,
            int minAgents,
            Set<Capability> requiredCapabilities) {

        super(id, "Explore " + region, duration, true, needsCommunication, minAgents, requiredCapabilities);

        this.region = region;
        requireSkill(requiredSkill);
        requireBattery(batteryNeeded);

        if (resourceCosts != null) {
            for (Map.Entry<ResourceType, Integer> entry : resourceCosts.entrySet()) {
                requireResource(entry.getKey(), entry.getValue());
            }
        }

        if (requiredEquipment != null) {
            for (Equipment equipment : requiredEquipment) {
                requireEquipment(equipment);
            }
        }
    }

    public String getRegion() {
        return region;
    }
}
