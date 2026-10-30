# MARS-OPS

MARS-OPS is a small Java project that models the operational decisions involved in running a Mars colony. The colony has people, robots, facilities, equipment, and limited supplies. Missions and emergencies compete for those resources, while environmental conditions can make some operations unavailable.

The project is intended as an object-oriented programming and simulation exercise. It demonstrates how domain objects collaborate: a mission manager checks requests, agents contribute capabilities, a resource pool tracks shared inventory, and emergencies update the affected objects.

## What the simulation demonstrates

The `Main` class builds a sample colony and runs five scenarios:

1. **Successful exploration:** A scientist and exploration rover are assigned to a geology mission with a drill. The mission consumes fuel and battery power, advances over simulation ticks, and prints colony state before and after.
2. **Mission rejection:** An exploration mission is submitted without an agent that has the required surface-travel capability. The mission is rejected, with a reason, before becoming active.
3. **Resource shortage:** A repair mission requests more spare parts than the colony has. Submission fails and the before/after inventory output demonstrates that the failed request did not consume resources.
4. **Oxygen leak:** The simulation reports the affected habitat and available repair-capable agents. Handling the emergency consumes a spare part, reduces available oxygen, and updates the habitat status.
5. **Dust storm:** An exploration mission is submitted during a dust storm. Because it needs surface access, the mission is rejected with an environmental reason.

These are illustrative scenarios rather than a complete physical model of Mars operations. For example, emergency handling checks that the colony has a suitable capability, but does not assign a named responder to the emergency.

## Requirements

- JDK 16 or newer
- No third-party libraries or build tool are required

The project uses `List.of`, `Map.of`, and `Stream.toList`, so a recent JDK is required.

## Compile and run

Run these commands from the repository root in PowerShell:

```powershell
$sources = Get-ChildItem .\src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d .\out $sources
java -cp .\out Main
```

`javac` creates compiled classes under `out`; `Main` runs the demonstration. The `out` directory is generated build output, not source code.

## Architecture and class guide

### Core simulation

| Class | Responsibility |
|---|---|
| `Main` | Creates the sample colony and runs the five demonstration scenarios. |
| `MarsColony` | Owns the colony's agents, facilities, environment, resource pool, mission manager, and emergency log. |
| `Simulation` | Advances simulation time and calls `onTick` on tickable simulation components. |
| `Environment` | Stores the current environment condition and exposes checks such as whether surface operations or remote control are allowed. |
| `EnvironmentCondition` | Enumerates `NORMAL`, `DUST_STORM`, and `COMM_DISRUPTION`. |
| `OperationResult` | Represents a successful or failed operation and its explanatory message. |
| `ColonyReport` | Formats a textual report of colony state for display. |

### Agents

Agents implement the common `Agent` API: identity, location, availability, operational state, mission capability, and damage/repair behavior. `Capability` describes work an agent can contribute, such as `RESEARCH`, `SURFACE_TRAVEL`, `REPAIR`, `MEDICAL`, or `FIRE_RESPONSE`.

| Class | Responsibility |
|---|---|
| `CrewMember` | Abstract base for human crew. Tracks health, fatigue, skills, availability, and capabilities; subclasses implement `performDuty`. |
| `Scientist` | Crew member with research capability. |
| `Engineer` | Crew member with repair and fire-response capabilities. |
| `MedicalOfficer` | Crew member with medical capability and a patient-treatment helper. |
| `Robot` | Abstract robotic agent. Tracks battery, damage, availability, and capabilities; battery drains during ticks. |
| `ExplorationRover` | Surface rover that can carry equipment. |
| `RepairRobot` | Repair-capable robot with an equipment-repair helper. |
| `Drone` | Aerial reconnaissance robot whose ability to perform missions also depends on remote-control conditions. |

### Missions

`Mission` contains shared mission data: ID, name, duration, current state, required skills/capabilities/equipment, resource cost, and assigned agents. `MissionManager` validates submissions, checks environmental restrictions and resource availability, consumes the required resources when accepted, and advances the active mission on ticks.

Mission states are `ASSIGNED`, `IN_PROGRESS`, `COMPLETED`, and `FAILED`.

| Class | Responsibility |
|---|---|
| `ExplorationMission` | Surface mission with a region, required skill, battery requirement, equipment, and resource costs. |
| `ResearchMission` | Research mission associated with a laboratory, required skill, equipment, and optional resource cost. |
| `RepairMission` | Repair mission targeting a facility or a piece of equipment and requiring spare parts. |
| `RescueMission` | Rescue mission with communication requirements and a stranded agent. |
| `MissionManager` | Allows one active mission at a time, records mission history, validates assignments, and releases agents when a mission ends. |

### Facilities, equipment, and resources

`Facility` is the base class for colony locations. It stores a facility status, capacity, inventory, and associated equipment. `FacilityStatus` includes `OPERATIONAL`, `UNAVAILABLE`, and `DESTROYED`.

| Class | Responsibility |
|---|---|
| `Habitat` | Facility with oxygen access and a life-support system; supports oxygen loss. |
| `StorageFacility` | General facility used for storing shared resources. |
| `Laboratory` | Facility with fire state and operations for igniting, extinguishing, and damaging its equipment. |
| `PowerStation` | Facility type for colony power infrastructure. |
| `CommunicationStation` | Facility type for colony communications infrastructure. |
| `LifeSupportSystem` | Tracks whether a life-support component is working. |
| `Equipment` | Named item that can be working or damaged and repaired. |
| `ResourcePool` | Aggregates inventory across facilities, checks whether a request can be supplied, and consumes resources when available. |
| `ResourceType` | Enumerates supplies such as oxygen, water, food, fuel, battery power, medical supplies, and spare parts. |

### Emergencies

`Emergency` provides a shared handling flow: detect the event, check required capabilities, consume response resources, and call the appropriate resolved or unresolved behavior. Each emergency defines its own target and effects.

| Class | Responsibility |
|---|---|
| `OxygenLeak` | Models oxygen loss at a habitat and requires repair capability and spare parts. |
| `Fire` | Models a laboratory fire, water usage, and equipment/facility damage. |
| `MedicalEmergency` | Models harm to a crew member and treatment using medical supplies. |
| `RoverBreakdown` | Models a damaged robot and repair using spare parts. |
| `EmergencyType` / `EmergencyState` | Enumerate emergency categories and lifecycle states. |

### Shared interfaces and locations

- `Tickable` defines `onTick(MarsColony)` for objects updated as simulation time advances.
- `Location` provides the shared location naming API.
- `SurfaceLocation` represents a location on the Martian surface.

## Typical operation flows

### Submitting a mission

1. The caller creates a mission and supplies candidate agents.
2. `MissionManager` rejects invalid, incompatible, unavailable, or environment-blocked assignments.
3. It checks the mission's resource cost against the `ResourcePool`.
4. On acceptance, it consumes the costs, assigns the agents, and marks the mission active.
5. Simulation ticks move the mission through its lifecycle. The manager releases its agents when it completes or fails.

### Handling an emergency

1. `MarsColony` records the emergency and calls its handler.
2. The emergency applies its detection effects.
3. Required capabilities are checked across available colony agents.
4. Required response resources are consumed if available.
5. The emergency marks itself resolved and applies the resolution effects, or marks itself unresolved and applies the failure effects.

## Project layout

```text
src/
  Main.java
  agents/       Crew members, robots, and capabilities
  common/       Shared interfaces and location types
  core/         Colony, simulation, environment, and operation results
  emergencies/  Emergency types and their response behavior
  facilities/   Facility types, equipment, and facility status
  missions/     Mission types, states, and mission manager
  resources/    Resource types and shared resource pool
docs/
  UML/          Draw.io class, object, sequence, and state diagrams
dev/            Development test sources
```

## UML documentation

The diagrams are in [`docs/UML`](docs/UML/README.txt). The collection includes class, object, sequence, state-machine, and use-case diagrams. Open `.drawio` files with [diagrams.net](https://app.diagrams.net/) or the draw.io desktop application. As the Java implementation evolves, compare diagram names and operations with the code to keep the documentation synchronized.

## Extending the project

Possible next steps include adding a facility or agent subclass, defining a new mission or emergency, and expanding the sample colony with a scenario that exercises it. Reuse the existing base classes and enums so resource, capability, status, and tick behavior remains consistent.
