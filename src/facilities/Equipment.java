package facilities;

public class Equipment {
    private String name;
    private boolean working;

    public Equipment(String name){
        setName(name);
        this.working = true;
    }
    public boolean setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        this.name = name;
        return true;
    }
    public String getName() {
        return name;
    }
    public boolean isWorking() {
        return working;
    }

    public void damage(){
        working = false;
    }

    public void repair(){
        working = true;
    }
}
