package facilities;

public class CommunicationStation extends Facility{
    public CommunicationStation(String name, int capacity){
        super(name, capacity);
    }

    public boolean isCommAvailable(){
        return super.getStatus() == FacilityStatus.OPERATIONAL;
    }
}
