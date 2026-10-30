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

import facilities.Equipment;
/**
 *
 * @author fsara
 */
public class RepairRobot extends Robot{
    public RepairRobot(String id, String name) {
        super(id, name, new HashSet<>());
        this.capabilities.add(Capability.REPAIR);
    }

    public void repairEquipment(Equipment eq) { 
        if (eq != null) {
            eq.repair();
        }
    }

    @Override
    public void performTask() {
        System.out.println("RepairRobot " + name + " is repairing equipment.");
    }
}