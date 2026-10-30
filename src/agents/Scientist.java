/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author asus
 */
package agents;

import java.util.HashSet;
import java.util.Set;
import emergencies.EmergencyType;
import missions.Mission;

/**
 *
 * @author fsara
 */
public class Scientist extends CrewMember {
 public Scientist(String id, String name, Set<String> skills) {
        super(id, name, skills, new HashSet<>());
        if (this.capabilities != capabilities) { // أو this.capabilities
            this.capabilities.add(Capability.RESEARCH);
        }
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
    public void performDuty() {
        System.out.println(name + " is conducting scientific research.");
    }
}
