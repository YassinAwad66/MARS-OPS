package core;

 public record OperationResult(boolean success, String reason) {
    // Static method for successful operation
    public static OperationResult ok() {
        return new OperationResult(true, null);
    }

    // Static method for failed operation with a reason
    public static OperationResult fail(String reason) {
        return new OperationResult(false, reason);
    }
}