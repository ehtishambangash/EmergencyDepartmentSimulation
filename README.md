# Emergency Department (ED) Simulation for Resource Management

This project simulates the operations of a Hospital Emergency Department (ED) to optimize resource management, patient flow, and triage prioritization. It aims to improve hospital efficiency by modeling various aspects of ED operations, such as patient arrivals, medical conditions, resource allocation (beds, physicians, medical staff), and triage prioritization. The system includes probabilistic models to simulate patient arrival times, treatment durations, and the resource utilization.

The project is built using Java and leverages concepts such as Object-Oriented Programming (OOP), multithreading, and probability distributions (Weibull, Pearson VI, Exponential) to model realistic ED operations.

## Table of Contents
1. [Introduction](#introduction)
2. [Problem Statement](#problem-statement)
3. [Project Objectives](#project-objectives)
4. [Key Challenges Addressed](#key-challenges-addressed)
5. [Implementation Details](#implementation-details)
    1. [Main Classes](#main-classes)
    2. [Thread Usage](#thread-usage)
    3. [Data Representation](#data-representation)
6. [Key Features](#key-features)
7. [Challenges and Solutions](#challenges-and-solutions)
8. [Flow of the Simulation](#flow-of-the-simulation)
9. [Graphical User Interface (GUI)](#graphical-user-interface-gui)
10. [Technologies Used](#technologies-used)
11. [Results and Analysis](#results-and-analysis)
12. [Conclusion](#conclusion)
13. [Future Work](#future-work)
14. [Contact Information](#contact-information)

## Introduction
The Emergency Department (ED) is a crucial area in healthcare systems, requiring swift decision-making and efficient resource management. This project simulates various aspects of an ED, including patient arrivals, triage categorization, resource allocation (beds, physicians), and treatment, to improve the overall efficiency of ED operations. 

### Simulation Overview
The system models the arrival of patients, assigns them diseases, calculates their triage category, and allocates resources such as beds and physicians based on severity. It also tracks resource utilization and provides real-time statistics and visualizations to help optimize the ED's operations.

## Problem Statement
The ED faces several challenges related to optimizing patient flow, reducing waiting times, and improving resource utilization. This simulation aims to address these challenges by modeling the interactions and workflows in the ED, focusing on key resources such as beds and physicians while considering patient triage, arrivals, and treatments.

## Project Objectives
- **Optimize Resource Management:** Ensure efficient use of beds, physicians, and other medical resources.
- **Simulate Patient Flow:** Model patient arrivals, their conditions, and triage categories.
- **Implement Probabilistic Models:** Use distributions such as Weibull, Pearson VI, and Exponential to simulate patient arrival times and severity.
- **Create a Real-time Dashboard:** Visualize real-time data about patient arrivals, bed occupancy, and overall ED performance.
- **Provide an Interactive GUI:** Allow users to monitor and control the simulation, adjust parameters, and visualize outcomes.

## Key Challenges Addressed
1. **Overcrowding and Resource Constraints:** The simulation models the availability of resources such as beds and physicians to reduce overcrowding and improve patient care.
2. **Dynamic and Variable Patient Flow:** The simulation handles varying patient conditions and arrival rates using probabilistic distributions.
3. **Resource Utilization:** Tracks the utilization of beds and physicians to optimize allocation and reduce bottlenecks.
4. **Priority-based Treatment:** Patients are triaged based on urgency, ensuring high-priority patients receive timely care.
5. **Multitasking and Workflow:** Models multitasking behavior for physicians treating multiple patients simultaneously.

## Implementation Details

### Main Classes

- **BedManager Class:** Manages the allocation and deallocation of beds.
- **ClockThread Class:** Simulates the global clock to progress the simulation time.
- **DeallocationThread Class:** Manages the deallocation of resources such as beds and physicians after treatment.
- **PhysicianManager Class:** Allocates physicians based on patient severity and triage category.
- **QueueManager Class:** Manages the queue of patients waiting for treatment.
- **StatisticsCollectorThread Class:** Collects and logs statistics for performance analysis.

### Thread Usage
Threads are used throughout the simulation to handle tasks concurrently, such as updating the global clock, allocating resources, and collecting statistics. The **ClockThread** runs continuously, and the **DeallocationThread** ensures resources are freed up as patients are discharged.

### Data Representation
Simulation logs are saved to a text file (`emergency_department_log.txt`) that records each patient's interaction with the system, including arrival time, triage category, treatment duration, and discharge time. 

## Key Features
1. **Randomized Disease Assignment Based on Triage:** Diseases are assigned randomly to patients based on their triage category.
2. **Object-Oriented Programming (OOP):** The simulation uses OOP principles for a modular and extensible design.
3. **Extensibility:** The system allows easy addition of new triage categories and diseases.
4. **Graphical/Console-Based Simulation Results:** Results can be visualized using graphs or exported for analysis.
5. **Weibull, Pearson VI, and Exponential Distributions:** Used for modeling patient arrivals, treatment durations, and severity levels.

## Challenges and Solutions
- **Probability Distributions:** Used libraries like Apache Commons Math for generating probabilistic values.
- **Debugging Simulation Logic:** Added extensive logging and used thread-safe structures.
- **Resource Management:** Centralized resource management using priority queues and semaphores.
- **Global Clock Synchronization:** Used a dedicated clock thread for time-based operations.
- **Real-Time Data Fetching:** Employed separate threads for real-time data collection and visualization.
- **Performance Optimization:** Optimized memory management and minimized shared resource use.

## Flow of the Simulation
1. **Initialization:** The ED environment is set up, and medical condition data is loaded.
2. **Patient Arrival:** The **ClockThread** triggers patient arrivals based on a Weibull distribution.
3. **Resource Allocation:** The **BedManager** allocates a bed based on the patient's triage category.
4. **Patient Treatment and Deallocation:** Once treatment is complete, the **DeallocationThread** frees resources for new patients.
5. **End of Simulation:** The simulation runs until the user decides to end it, with all relevant data logged.

## Graphical User Interface (GUI)
The simulation features an interactive GUI with real-time statistics and graphical representations. 

### Main Statistics Window
- **Bed Statistics:** Displays the types of beds, number, and utilization rates.
- **Physician Statistics:** Displays physician utilization.
- **Progress Bars:** Visualizes the overall bed and physician utilization.

### Graph Window
Displays six different graphs, including:
- **Triage Bar Graph**
- **Patient Seen vs Not Seen Pie Chart**
- **Number of People Waiting vs Time Line Graph**
- **Utilization of Beds vs Time Line Graph**
- **Utilization of Physicians vs Time Line Graph**
- **Treatment Time Distribution**

## Technologies Used
- **JavaFX** for GUI development.
- **Apache Commons Math** for probability distributions.
- **Multithreading** for concurrent operations and real-time data collection.

## Results and Analysis
The system provides real-time performance data, including resource utilization and treatment times, which can be analyzed for optimizing ED operations.

## Conclusion
This simulation offers valuable insights into improving ED efficiency by optimizing resource allocation and patient flow. It can be extended for use in real-world applications or research into healthcare optimization.

## Future Work
Future enhancements may include:
- Integration with real-time hospital data.
- Extending the simulation to include additional departments.
- Implementing machine learning for predictive resource allocation.

## Contact Information
If you have any questions, feedback, or suggestions, feel free to reach out to me:

- **Name:** Ehtisham Hussain
- **Email:** ehtishamhussain@example.com
- **LinkedIn:** [LinkedIn Profile](https://www.linkedin.com/in/ehtishamhussain)
- **GitHub:** [GitHub Profile](https://github.com/ehtishamhussain)
