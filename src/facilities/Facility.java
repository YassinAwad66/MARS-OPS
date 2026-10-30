package facilities;

import java.util.EnumMap;

import common.*;
import resources.ResourceType;

import java.util.ArrayList;

public abstract class Facility implements Location {
    private EnumMap<ResourceType, Integer> stock = new EnumMap<>(ResourceType.class);
    private ArrayList<Equipment> equipments = new ArrayList<>();
    private String name;
    private int capacity;
    private FacilityStatus status;

    protected Facility(String name, int capacity){
        setName(name);
        setCapacity(capacity);
        status = FacilityStatus.OPERATIONAL;
        for(ResourceType s : ResourceType.values()){
            stock.put(s, 0);
        }
    }

    public boolean setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        this.name = name;
        return true;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity >= 0 ? capacity : 0;
    }

    public boolean setStatus(FacilityStatus status) {
        if(status == null)
            return false;
        this.status = status;
        return true;
    }

    @Override 
    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getQuantity(ResourceType type){
        return stock.get(type);
    }

    public boolean isOperational() {
        return status == FacilityStatus.OPERATIONAL;
    }

    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public boolean addResource(ResourceType type, int qty) {
        if (type == null || qty <= 0) {
            return false;
        }
        stock.put(type, stock.get(type) + qty);
        return true;
    }

    public boolean removeResource(ResourceType type, int qty) {
        if (type == null || qty <= 0 || qty > stock.get(type)) {
            return false;
        }
        stock.put(type, stock.get(type) - qty);
        return true;
        }

        public void addEquipment(Equipment e){
            equipments.add(e);
        }
}
