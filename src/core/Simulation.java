package core;

import facilities.Facility;
import common.Tickable;
import agents.Agent;

public class Simulation {
    private MarsColony colony;
    private int time;

    // Constructor to initialize the simulation with a target colony
    public Simulation(MarsColony colony) {
        this.colony = colony;
        this.time = 0; // Simulation starts at time 0
    }

    // Advance simulation time by a specified number of units
    public void advanceTime(int units) {
        for (int i = 0; i < units; i++) {
            time++;
            executeTick();
        }
    }

    // Execute one tick across all tickable parts in the agreed order
    private void executeTick() {
        // 1. Tick the environment
        if (colony.getEnvironment() != null) {
            colony.getEnvironment().onTick(colony);
        }

        // 2. Tick the mission manager
        if (colony.getMissionManager() != null) {
            colony.getMissionManager().onTick(colony);
        }

        // 3. Tick all agents (crew members and robots) for fatigue and battery drain
        for (Agent agent : colony.getAllAgents()) {
            if (agent instanceof Tickable) {
                ((Tickable) agent).onTick(colony);
            }
        }

        // 4. Tick all facilities
        for (Facility facility : colony.getFacilities()) {
            if (facility instanceof Tickable) {
                ((Tickable) facility).onTick(colony);
            }
        }
    }

    // Get the current simulation time
    public int getTime() {
        return time;
    }

    // Get the colony associated with this simulation
    public MarsColony getColony() {
        return colony;
    }
}