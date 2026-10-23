package facilities;

public class Laboratory extends Facility{
    private boolean onFire;

    public Laboratory(String name, int capacity){
        super(name, capacity);
        onFire = false;
    }

    public void ignite(){
        onFire = true;
        super.setStatus(FacilityStatus.UNAVAILABLE);
    }

    public void extinguish(){
        onFire = false;
        super.setStatus(FacilityStatus.OPERATIONAL);
    }

    public void damageAllEquipment(){
        for(Equipment e : super.getEquipments()){
            e.damage();
        }
    }
}
