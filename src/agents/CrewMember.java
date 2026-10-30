/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author asus
 */
package agents;
import java.util.Set;

import common.*;
import core.MarsColony;
import emergencies.EmergencyType;
import missions.Mission;


public abstract class CrewMember implements Agent, Tickable {
    protected String id;
    protected String name;
    protected double health;
    protected Location location; 
    protected Set<String> skills;
    protected double fatigue;
    protected boolean available;
    protected Set<Capability> capabilities;

    public CrewMember(String id, String name, Set<String> skills, Set<Capability> capabilities) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.capabilities = capabilities;
        this.health = 100.0;
        this.fatigue = 0.0;
        this.available = true;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getHealth() { return health; }
    public Location getLocation() { return location; }
    public Set<String> getSkills() { return skills; }
    public double getFatigue() { return fatigue; }
    public boolean isAvailableFlag() { return available; }
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
        return health > 0 && fatigue < 100.0; 
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
    public void onTick(MarsColony colony) {

        if (!available) {
            fatigue = Math.min(100, fatigue + 5);
        } else {
            fatigue = Math.max(0, fatigue - 2);
        }
    }

   
    public void treat(int amount) {
        this.health = Math.min(100.0, this.health + amount);
    }

    
    public boolean hasSkill(String skill) {
        return skills != null && skills.contains(skill);
    }

    @Override
    public void setLocation(Location location) { 
        this.location = location;
    }

    @Override
    public void damage() {
        this.health = Math.max(0, this.health - 50);
    }

    @Override
    public void repair() {
        this.health = 100.0;
    }

   
    public abstract void performDuty();
}
