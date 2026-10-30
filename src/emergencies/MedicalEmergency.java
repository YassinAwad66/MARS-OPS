package emergencies;
import facilities.*;
import resources.*;
import java.util.*;
import core.MarsColony;
import agents.*;

public class MedicalEmergency extends Emergency {
    private final CrewMember patient;
    private final Facility facility;

    public MedicalEmergency(CrewMember patient, Facility facility) {
        super(EmergencyType.MEDICAL);
        this.patient = patient;
        this.facility = facility;
    }

    @Override
    protected Set<Capability> getRequiredCapabilities() {
        return Set.of(Capability.MEDICAL);
    }

    @Override
    protected Map<ResourceType, Integer> getResponseCost() {
        return Map.of(ResourceType.MEDICAL_SUPPLIES, 1);
    }

    @Override
    protected void onDetected(MarsColony c) {
        if (patient != null) {
            patient.damage();
        }
    }

    @Override
    protected void onResolved(MarsColony c) {
        if (patient != null) {
            patient.treat(30);
        }
        if (facility != null) {
            facility.setStatus(FacilityStatus.OPERATIONAL);
        }
    }

    @Override
    protected void onUnresolved(MarsColony c) {
        if (patient != null) {
            patient.damage();
        }
        if (facility != null) {
            facility.setStatus(FacilityStatus.UNAVAILABLE);
        }
    }
}
