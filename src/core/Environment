public class Environment implements Tickable {
    private EnvironmentCondition condition;

    public Environment() {
        this.condition = EnvironmentCondition.NORMAL; // Default state
    }

    public void setCondition(EnvironmentCondition condition) {
        this.condition = condition;
    }

    public EnvironmentCondition getCondition() {
        return condition;
    }

    public boolean isDustStorm() {
        return condition == EnvironmentCondition.DUST_STORM;
    }

    public boolean isCommsDisrupted() {
        return condition == EnvironmentCondition.COMM_DISRUPTION;
    }

    public boolean allowsSurfaceOperations() {
        return !isDustStorm(); // Dust storms prohibit surface operations[cite: 45]
    }

    public boolean allowsRemoteControl() {
        return !isCommsDisrupted(); // Communication disruptions prevent remote drone control[cite: 45]
    }

    @Override
    public void onTick(MarsColony colony) {
        // Can be used to update or simulate environmental changes over time if needed
    }
}