package facilities;

import resources.ResourceType;

public class Habitat extends Facility{
    private final LifeSupportSystem lifeSupport = new LifeSupportSystem();
    public Habitat(String name, int capacity){
        super(name, capacity);
    }
    
    public int getOxygen(){
        return getQuantity(ResourceType.OXYGEN);
    }

    public void loseOxygen(int amount){
        int lost = Math.min(amount, getQuantity(ResourceType.OXYGEN));
        removeResource(ResourceType.OXYGEN, lost);
    }
}
