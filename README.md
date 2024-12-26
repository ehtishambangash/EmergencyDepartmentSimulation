# Emergency Department Simulation

This project simulates the operations of an emergency department (ED) to optimize resource management and patient flow. It uses a combination of threads and resource allocation algorithms to handle tasks such as patient triage, bed and physician assignment, and resource deallocation.

---

## **Features**

1. **Patient Arrival Simulation**:
   - Arrival times are generated using a Weibull distribution.
   - Patients are categorized into triage levels based on their conditions.

2. **Resource Allocation**:
   - Beds and physicians are allocated dynamically based on patient triage.
   - Queue management ensures patients wait for available resources.

3. **Resource Deallocation**:
   - Beds and physicians are freed once a patient's treatment is complete.
   - Queued patients are assigned resources as they become available.

4. **Threaded Operations**:
   - A global clock (`ClockThread`) manages time progression.
   - A deallocation thread (`DeallocationThread`) handles bed and physician release.

5. **Logging and Visualization**:
   - Detailed logs for patient arrivals, resource assignments, and discharges.
   - Color-coded logs for better visualization of resource utilization.

---

## **How It Works**

### 1. **Simulation Initialization**
The simulation starts with the following components:
- **GlobalClock**: Tracks simulation time.
- **WeibullDistribution**: Generates random patient arrival times.
- **CalculateTriage**: Assigns a triage category to each patient.
- **BedManager** and **PhysicianManager**: Manage resources (beds and physicians).
- **QueueManager**: Handles waiting patients.

### 2. **Patient Arrival and Resource Allocation**
- New patients arrive based on the Weibull distribution.
- They are assigned a triage category and queued if no resources are available.

### 3. **Resource Deallocation**
- The `DeallocationThread` runs in parallel to:
  - Discharge patients whose treatment is complete.
  - Reassign freed resources to queued patients.

### 4. **Thread Management**
- Threads for the global clock and resource deallocation run concurrently with the main simulation.
- Threads are interrupted and joined at the end of the simulation.

---

## **Code Structure**

### **Main Components**
| Class                  | Responsibility                                                                 |
|------------------------|-------------------------------------------------------------------------------|
| `Main`                | Orchestrates the entire simulation.                                           |
| `GlobalClock`         | Manages simulation time.                                                      |
| `ClockThread`         | Updates the global clock continuously.                                        |
| `DeallocationThread`  | Handles resource deallocation and patient queue management.                   |
| `Patient`             | Represents a patient, including attributes like triage, arrival, and discharge times. |
| `BedManager`          | Allocates and deallocates beds for patients.                                   |
| `PhysicianManager`    | Allocates and deallocates physicians for patients.                             |
| `QueueManager`        | Manages the queue of patients waiting for resources.                          |
| `CalculateTriage`     | Assigns triage categories based on patient conditions.                        |
| `WeibullDistribution` | Generates arrival times for patients.                                         |

### **Key Threads**
- **`ClockThread`**: Updates the simulation clock in real-time.
- **`DeallocationThread`**: Monitors bed and physician availability, handles discharges, and reassigns resources.

---

## **Setup and Usage**

### **Requirements**
- Java 8 or above.
- Basic understanding of multi-threading in Java.

### **Steps to Run**
1. Clone this repository:
   ```bash
   git clone https://github.com/your-repo/ed-simulation.git
   cd ed-simulation
   ```
2. Compile the project:
   ```bash
   javac -d bin src/emergencyDepartment/*.java
   ```
3. Run the simulation:
   ```bash
   java -cp bin emergencyDepartment.Main
   ```

---

## **Example Output**

### **Sample Patient Log**
```
Patient ID: 1   Triage Category: 3   Arrival Gap: 12   Arrival Time: 12   Current Time: 12   Treatment Time: 30   Discharge Time: 42
Allocated General Bed with ID: 1
Allocated Physician with ID: 2
----------------------------------------------------------------
```

### **Sample Discharge Log**
```
Patient ID: 1 has been discharged from Bed ID: 1
----------------------------------------------------------------
Allocated Bed ID: 2 to Patient ID: 5 at Time: 50
Allocated Physician ID: 3 to Patient ID: 5
```

---

## **Customization**

- **Simulation Time**:
  Modify `start_time` and `end_time` in the `Main` class.

- **Resource Configuration**:
  Adjust the number of beds and physicians in the `BedManager` and `PhysicianManager` classes.

- **Arrival Distribution**:
  Change parameters in `WeibullDistribution` for different patient arrival patterns.

---

## **Future Enhancements**
- Implement additional triage levels and treatment types.
- Add real-time graphical visualization of resource utilization.
- Include patient priority for queue management.

---

## **Contributors**
- **Ehtisham Hussain**  
  - Computer Engineering Student | Freelance Developer | Founder of Karzify

---

## **License**
This project is open-source and available under the MIT License.
