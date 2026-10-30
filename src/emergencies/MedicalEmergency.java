package emergencies;
import facilities.Facility;
import resources.*;
import java.util.*;
import core.MarsColony;

public class MedicalEmergency extends Emergency {
    private final CrewMember patient;
    private final Facility facility;

    public MedicalEmergency(CrewMember patient, Facility facility) {
        super(EmergencyType.MEDICAL);
        this.patient = patient;
        this.facility = facility;
    }

    protected Set<Capability> getRequiredCapabilities() { return Set.of(Capability.MEDICAL); }
    protected Map<ResourceType, Integer> getResponseCost() { return Map.of(ResourceType.MEDICAL_SUPPLIES, 1); }

    protected void onDetected(MarsColony c)   { /* patient health drops */ }
    protected void onResolved(MarsColony c)   { /* patient recovers */ }
    protected void onUnresolved(MarsColony c) { /* patient health worsens */ }
}
