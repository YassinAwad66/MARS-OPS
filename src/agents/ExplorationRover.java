/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author asus
 */
package agents;

import common.*;
import missions.*;
import emergencies.*;
import facilities.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/**
 *
 * @author fsara
 */
public class ExplorationRover extends Robot {
     private List<Equipment> cargo; 

    public ExplorationRover(String id, String name) {
        super(id, name, new HashSet<>());
        this.capabilities.add(Capability.SURFACE_TRAVEL);
        this.capabilities.add(Capability.CARRY_EQUIPMENT);
        this.cargo = new ArrayList<>();
    }

    public void load(Equipment eq) { 
        if (eq != null) {
            cargo.add(eq);
        }ٍ
    }

    public boolean carries(String name) {
        for (Equipment eq : cargo) {
            if (eq.getName() != null && eq.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void performTask() {
        System.out.println("ExplorationRover " + name + " is exploring the surface.");
    }
}