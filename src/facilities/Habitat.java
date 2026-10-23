package facilities;

import resources.ResourceType;

public class Habitat extends Facility{
    private final LifeSupportSystem lifeSupport;
    public Habitat(String name, int capacity){
        super(name, capacity);
        lifeSupport = new LifeSupportSystem();
    }

    public boolean isLifeSupportWorking() {
        return lifeSupport.isWorking();
    }

    public void failLifeSupport() {
        lifeSupport.fail();
    }

    public void restoreLifeSupport() {
        lifeSupport.restore();
    }

    public int getOxygen(){
        return super.getQuantity(ResourceType.OXYGEN);
    }

    public void loseOxygen(int amount){
        int lost = Math.min(amount, super.getQuantity(ResourceType.OXYGEN));
        super.removeResource(ResourceType.OXYGEN, lost);
    }
}
