package facilities;

public class Laboratory extends Facility{
    private boolean onFire;

    public Laboratory(String name, int capacity){
        super(name, capacity);
        onFire = false;
    }

    public void ignite(){
        onFire = true;
        setStatus(FacilityStatus.UNAVAILABLE);
    }

    public void extinguish(){
        onFire = false;
        setStatus(FacilityStatus.OPERATIONAL);
    }

    public void damageAllEquipment(){
        for(Equipment e : getEquipments()){
            e.damage();
        }
    }
}
