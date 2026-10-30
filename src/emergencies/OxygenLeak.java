package emergencies;
import java.util.*;
import facilities.Habitat;
import resources.ResourceType;
import core.MarsColony;
import agents.Capability;

public class OxygenLeak extends Emergency {
    private final Habitat habitat;
    private final int leakAmount;

    public OxygenLeak(Habitat habitat, int leakAmount) {
        super(EmergencyType.OXYGEN_LEAK);
        this.habitat = habitat;
        this.leakAmount = leakAmount;
    }

    @Override 
    protected Set<Capability> getRequiredCapabilities() { 
        return Set.of(Capability.REPAIR); 
    }
    @Override 
    protected Map<ResourceType, Integer> getResponseCost() {
        return Map.of(ResourceType.SPARE_PARTS, 1); 
    }

    @Override
    protected void onDetected(MarsColony c)   { 
        habitat.loseOxygen(leakAmount); 
    }

    @Override
    protected void onResolved(MarsColony c) {}

    @Override
    protected void onUnresolved(MarsColony c) { 
        habitat.loseOxygen(leakAmount * 2); 
    }
}