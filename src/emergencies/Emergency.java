package emergencies;

import java.util.*;
import core.MarsColony;
import resources.*;

public abstract class Emergency {
    protected final EmergencyType type;
    protected EmergencyState state = EmergencyState.PENDING;
    protected String reason;

    protected Emergency(EmergencyType type, String reason) {
        this.type = type;
        this.reason = reason;
    }

    // template method: subclasses can't change the order of steps
    public final OperationResult handle(MarsColony colony) {
        onDetected(colony);

        if (!colony.hasCapabilities(getRequiredCapabilities())) {
            return fail(colony, "Missing capability");
        }

        OperationResult paid = colony.getResourcePool().consume(getResponseCost());
        if (!paid.isSuccess()) {
            return fail(colony, "Not enough resources");
        }

        state = EmergencyState.RESOLVED;
        reason = "Resolved";
        onResolved(colony);
        return OperationResult.success(type + " resolved");
    }

    private OperationResult fail(MarsColony colony, String why) {
        state = EmergencyState.UNRESOLVED;
        reason = why;
        onUnresolved(colony);
        return OperationResult.fail(type + " unresolved: " + why);
    }

    public EmergencyState getState() { 
        return state; 
    }
    public String getReason() {
         return reason; 
    }

    protected abstract Set<Capability> getRequiredCapabilities();
    protected abstract Map<ResourceType, Integer> getResponseCost();
    protected abstract void onDetected(MarsColony colony);
    protected abstract void onResolved(MarsColony colony);
    protected abstract void onUnresolved(MarsColony colony);
}