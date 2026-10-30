package core;

import agents.Agent;
import agents.CrewMember;
import agents.Robot;
import facilities.Facility;
import missions.Mission;
import emergencies.Emergency;

public class ColonyReport {

    // Builds a comprehensive status report string for the given MarsColony
    public static String build(MarsColony colony) {
        StringBuilder report = new StringBuilder();

        report.append("========================================\n");
        report.append("          MARS-OPS COLONY REPORT        \n");
        report.append("========================================\n");

        // 1. Environment Status
        report.append("Environment Condition: ")
              .append(colony.getEnvironment() != null ? colony.getEnvironment().getCondition() : "N/A")
              .append("\n\n");

        // 2. Resource Totals (Operational facilities only)
        report.append("--- Resource Totals --- \n");
        for (ResourceType res : ResourceType.values()) {
            int total = colony.getTotal(res);
            report.append(res.name()).append(": ").append(total).append("\n");
        }
        report.append("\n");

        // 3. Agents Status (Crew Members & Robots)
        report.append("--- Colony Agents --- \n");
        if (colony.getAllAgents().isEmpty()) {
            report.append("No agents registered in the colony.\n");
        } else {
            for (Agent agent : colony.getAllAgents()) {
                report.append("ID: ").append(agent.getId())
                      .append(" | Name: ").append(agent.getName())
                      .append(" | Location: ").append(agent.getLocation() != null ? agent.getLocation().getName() : "Unknown")
                      .append(" | Available: ").append(agent.isAvailable())
                      .append(" | Operational: ").append(agent.isOperational());
                
                if (agent instanceof CrewMember) {
                    report.append(" | Health: ").append(((CrewMember) agent).getHealth());
                } else if (agent instanceof Robot) {
                    report.append(" | Battery: ").append(((Robot) agent).getBattery());
                }
                report.append("\n");
            }
        }
        report.append("\n");

        // 4. Facilities Status
        report.append("--- Colony Facilities --- \n");
        if (colony.getFacilities().isEmpty()) {
            report.append("No facilities registered in the colony.\n");
        } else {
            for (Facility facility : colony.getFacilities()) {
                report.append("Facility: ").append(facility.getName())
                      .append(" | Status: ").append(facility.isOperational() ? "OPERATIONAL" : "UNAVAILABLE/DESTROYED")
                      .append("\n");
            }
        }
        report.append("\n");

        // 5. Active Mission
        report.append("--- Active Mission --- \n");
        if (colony.getMissionManager() != null && colony.getMissionManager().getActiveMission() != null) {
            Mission active = colony.getMissionManager().getActiveMission();
            report.append("Mission ID: ").append(active.getId())
                  .append(" | State: ").append(active.getState())
                  .append("\n");
        } else {
            report.append("No active mission currently.\n");
        }
        report.append("\n");

        // 6. Recent Emergency Log Outcomes
        report.append("--- Emergency Log --- \n");
        if (colony.getEmergencyLog().isEmpty()) {
            report.append("No emergencies recorded.\n");
        } else {
            for (Emergency emergency : colony.getEmergencyLog()) {
                report.append("Emergency State: ").append(emergency.getState())
                      .append(" | Reason: ").append(emergency.getReason() != null ? emergency.getReason() : "None")
                      .append("\n");
            }
        }

        report.append("========================================");
        return report.toString();
    }
}