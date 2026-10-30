package missions;

import java.util.Set;

import agents.Agent;
import agents.Capability;

public final class RescueMission extends Mission {

    private Agent strandedEntity;

    public RescueMission(int id, String name, int duration, boolean needsSurface, boolean needsCommunication, int minimumAgents, Set<Capability> requiredCapabilities, Agent strandedEntity) {
        super(id, name, duration, needsSurface, needsCommunication, minimumAgents, requiredCapabilities);

        if (!needsCommunication) {
            throw new IllegalArgumentException("Rescue requires communication");
        }
        if (minimumAgents < 2) {
            throw new IllegalArgumentException("Rescue requires a vehicle and personnel");
        }
        if (requiredCapabilities == null || !requiredCapabilities.contains(Capability.COMMUNICATION)) {
            throw new IllegalArgumentException("Rescue requires communication capability");
        }
        if (needsSurface && !requiredCapabilities.contains(Capability.SURFACE_TRAVEL)) {
            throw new IllegalArgumentException("Surface rescue requires surface travel capability");
        }
        this.strandedEntity = strandedEntity;
    }

    public Agent getStrandedEntity() {
        return strandedEntity;
    }

}
