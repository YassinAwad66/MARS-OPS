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
public class MedicalOfficer extends CrewMember {
    public MedicalOfficer(String id, String name, Set<String> skills) {
        super(id, name, skills, new HashSet<>());
        if (this.capabilities != null) {
            this.capabilities.add(Capability.MEDICAL); 
        }
    }

    @Override
    public boolean canPerform(Mission mission) {
        return isAvailable();
    }

    @Override
    public boolean canHandle(EmergencyType emergencyType) {
        return emergencyType != null && emergencyType.name().equalsIgnoreCase("MEDICAL");
    }

    public void treatPatient(CrewMember patient, int amount) {
        if (patient != null) {
            patient.treat(amount);
        }
    }

    @Override
    public void performDuty() {
        System.out.println(name + " is providing medical care.");
    }
}
