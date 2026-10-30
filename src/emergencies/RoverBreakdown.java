package emergencies;
import java.util.*;
import resources.ResourceType;
import core.MarsColony;
import agents.*;

public class RoverBreakdown extends Emergency {
    private final Robot rover;

    public RoverBreakdown(Robot rover) {
        super(EmergencyType.ROVER_BREAKDOWN);
        this.rover = rover;
    }

    @Override
    protected Set<Capability> getRequiredCapabilities() {
        return Set.of(Capability.REPAIR);
    }

    @Override
    protected Map<ResourceType, Integer> getResponseCost() {
        return Map.of(ResourceType.SPARE_PARTS, 2);
    }

    @Override
    protected void onResolved(MarsColony c) {
        if (rover != null) {
            rover.repair();
            rover.setAvailable(true);
        }
    }

    @Override
    protected void onUnresolved(MarsColony c) {
        if (rover != null) {
            rover.damage();
            rover.setAvailable(false);
        }
    }
}
