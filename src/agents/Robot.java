package agents;

import common.*;
import missions.*;
import emergencies.*;

import java.util.Set;
/**
 *
 * @author fsara
 */
public abstract class Robot implements Agent,Tickable {
 protected String id;
    protected String name;
    protected double battery;
    protected boolean damaged;
    protected Location location; 
    protected boolean available;
    protected Set<Capability> capabilities;

    public Robot(String id, String name, Set<Capability> capabilities) {
        this.id = id;
        this.name = name;
        this.battery = 100.0;
        this.damaged = false;
        this.available = true;
        this.capabilities = capabilities;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getBattery() { return battery; }
    public boolean isDamaged() { return damaged; }
    @Override
    public Location getLocation() { return location; }
    public Set<Capability> getCapabilities() { return capabilities; }

    @Override
    public boolean isAvailable() {
        return available && isOperational();
    }

    @Override
    public void setAvailable(boolean available) {
        this.available = available;
    }


    @Override
    public boolean isOperational() {
        return !damaged && battery > 0;
    }

    @Override
    public boolean canPerform(Mission mission) { 
        return isAvailable();
    }

    @Override
    public boolean canHandle(EmergencyType emergencyType) { 
        return false;
    }

    
    @Override
    public void onTick(int units) {
        if (!available) {
            drainBattery(2 * units);
        } else {
            drainBattery(1 * units);
        }
    }

    public void drainBattery(int amount) {
        this.battery = Math.max(0.0, this.battery - amount);
        if (this.battery == 0) {
            this.available = false;
        }
    }

    public void recharge(int amount) {
        this.battery = Math.min(100.0, this.battery + amount);
    }

    @Override
    public void damage() {
        this.damaged = true;
        this.available = false;
    }

    @Override
    public void repair() {
        this.damaged = false;
        this.battery = 100.0;
    }

    @Override
    public void setLocation(Location location) { 
        this.location = location;
    }

  
    public abstract void performTask();
   
}
