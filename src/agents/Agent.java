/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author asus
 */
package marsops_agents;

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
    
    boolean canPerform(Mission mission);  
    boolean canHandle(EmergencyType emergencyType);  
    void damage();
    void repair();
}
