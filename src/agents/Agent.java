/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author asus
 */
package agents;

import java.util.Set;

import common.Location;
import emergencies.EmergencyType;
import missions.Mission;

/**
 *
 * @author fsara
 */
public interface Agent {
    String getId();
    String getName();
    Location getLocation();
    void setLocation(Location location);

    boolean isAvailable();
    void setAvailable(boolean available);

    boolean isOperational();
    Set<Capability> getCapabilities();

    boolean canPerform(Mission mission);
    boolean canHandle(EmergencyType emergencyType);
    void damage();
    void repair();
}
