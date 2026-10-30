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
import core.*;

import java.util.HashSet;

/**
 *
 * @author fsara
 */
public class Drone extends Robot  {
  private Environment environment; 

    public Drone(String id, String name, Environment environment) {
        super(id, name, new HashSet<>());
        this.capabilities.add(Capability.AERIAL_RECON);
        this.environment = environment;
    }

    @Override
    public boolean canPerform(Mission mission) { 
        if (environment != null && !environment.allowsRemoteControl()) {
            return false;
        }
        return super.canPerform(mission);
    }

    @Override
    public void performTask() {
        System.out.println("Drone " + name + " is performing aerial reconnaissance.");
    }
}
