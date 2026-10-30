package core;

public class OperationResult {

    private boolean success;
    private String reason;

    // Private constructor
    private OperationResult(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }

    // Successful operation without a message
    public static OperationResult ok() {
        return new OperationResult(true, null);
    }

    // Successful operation with a message
    public static OperationResult success(String reason) {
        return new OperationResult(true, reason);
    }

    // Failed operation with a reason
    public static OperationResult fail(String reason) {
        return new OperationResult(false, reason);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getReason() {
        return reason;
    }
}