
import java.util.List;
import java.util.Set;

public final class ExplorationMission extends Mission {

    private final String region;

    public ExplorationMission(
            int id,
            String region,
            int duration,
            int batteryNeeded,
            String requiredSkill,
            ResourceType resourceType,
            List<Equipment> requiredEquipment,
            boolean needsCommunication,
            int minAgents,
            Set<Capability> requiredCapabilities) {

        super(id, "Explore " + region, duration, true, needsCommunication, minAgents, requiredCapabilities);

        this.region = region;
        requireSkill(requiredSkill);
        requireBattery(batteryNeeded);

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
