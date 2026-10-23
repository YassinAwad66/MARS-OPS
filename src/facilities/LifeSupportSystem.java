package facilities;

public class LifeSupportSystem {

    private boolean working;

    public LifeSupportSystem() {
        working = true;
    }

    public boolean isWorking() {
        return working;
    }

    public void fail() {
        working = false;
    }

    public void restore() {
        working = true;
    }
}