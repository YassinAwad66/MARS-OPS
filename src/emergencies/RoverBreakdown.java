package emergencies;
import java.util.*;
import resources.ResourceType;
import core.MarsColony;

public class RoverBreakdown extends Emergency {
    private final Robot rover;

    public RoverBreakdown(Robot rover, String reason) {
        super(EmergencyType.ROVER_BREAKDOWN, reason);
        this.rover = rover;
    }

    protected Set<Capability> getRequiredCapabilities() { 
        return Set.of(Capability.REPAIR); 
    }
    protected Map<ResourceType, Integer> getResponseCost() { 
        return Map.of(ResourceType.SPARE_PARTS, 2); 
    }

    protected void onDetected(MarsColony c)   { /* rover marked broken (P2's Robot API) */ }
    protected void onResolved(MarsColony c)   { /* rover back in service */ }
    protected void onUnresolved(MarsColony c) { /* rover stays out of action */ }
}
