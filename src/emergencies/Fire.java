package emergencies;
import facilities.*;
import resources.*;
import java.util.Map;
import core.MarsColony;

public class Fire extends Emergency {
    private final Laboratory laboratory;

    public Fire(Laboratory laboratory, String reason) {
        super(EmergencyType.FIRE, reason);
        this.laboratory = laboratory;
    }

    @Override    
    protected Set<Capability> getRequiredCapabilities() {
        return Set.of(Capability.FIREFIGHTING);
    }
    protected Map<ResourceType, Integer> getResponseCost() {
        return Map.of(ResourceType.WATER, 5);
    }

    protected void onDetected(MarsColony c)   {
        laboratory.ignite(); 
        laboratory.damageAllEquipment(); 
    }
    protected void onResolved(MarsColony c)   { 
        laboratory.extinguish(); 
    }
    protected void onUnresolved(MarsColony c) { 
        laboratory.setStatus(FacilityStatus.DESTROYED); 
    }
}
