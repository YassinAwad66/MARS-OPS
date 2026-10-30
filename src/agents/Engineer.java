/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author asus
 */
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author fsara
 */
public class Engineer extends CrewMember {
   public Engineer(String id, String name, Set<String> skills) {
        super(id, name, skills, new HashSet<>());
        if (this.capabilities != null) {
            this.capabilities.add(Capability.REPAIR);
            this.capabilities.add(Capability.FIRE_RESPONSE);
        }
    }

    @Override
    public boolean canPerform(Mission mission) {
        return isAvailable();
    }

    @Override
    public boolean canHandle(EmergencyType emergencyType) {
        return emergencyType != null && emergencyType.name().equalsIgnoreCase("FIRE");
    }

    @Override
    public void performDuty() {
        System.out.println(name + " is repairing facility equipment.");
    }
}
