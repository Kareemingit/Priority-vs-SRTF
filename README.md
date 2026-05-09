# Priority-vs-SRTF

A Java-based simulator for comparing CPU scheduling algorithms and analyzing their performance using standard scheduling metrics.

This project currently implements:

- **Priority Scheduling**
- **Shortest Remaining Time First (SRTF)**

The simulator evaluates and compares the algorithms using:

- Turnaround Time
- Waiting Time
- Response Time

---

# Features

- Simulate multiple CPU scheduling algorithms
- Compare scheduling behavior and execution order
- Calculate performance metrics for each process
- Analyze average scheduling statistics
- GUI-based interaction using Java Swing/JavaFX (depending on implementation)
- Modular project structure for easy extension

---

# Project Structure

```text
src/
│
├── gui/
│   └── MainGUI.java
│
├── metrics/
│   └── ScheduleResult.java
│
├── model/
│   └── Process.java
│
├── scheduler/
│   ├── PriorityScheduler.java
│   └── SRTFScheduler.java
│
├── util/
│
└── test-cases/
```

## Folder Description

| Folder | Description |
|---|---|
| `gui/` | Contains the graphical user interface |
| `metrics/` | Stores scheduling results and calculated metrics |
| `model/` | Contains core data models such as processes |
| `scheduler/` | Implements CPU scheduling algorithms |
| `util/` | Utility/helper classes |
| `test-cases/` | Sample scheduling test cases |

---

# Scheduling Algorithms

## 1. Priority Scheduling

Priority Scheduling selects the process with the highest priority for execution.

### Characteristics
- Can be preemptive or non-preemptive
- Lower priority value may indicate higher priority (depending on implementation)
- May suffer from starvation

---

## 2. Shortest Remaining Time First (SRTF)

SRTF is the preemptive version of Shortest Job First (SJF).

### Characteristics
- Always executes the process with the shortest remaining burst time
- Minimizes average waiting time
- Requires continuous burst-time tracking

---

# Performance Metrics

The simulator calculates the following metrics:

## Turnaround Time (TAT)

```text
Turnaround Time = Completion Time - Arrival Time
```

Measures the total time spent in the system.

---

## Waiting Time (WT)

```text
Waiting Time = Turnaround Time - Burst Time
```

Measures how long a process waits in the ready queue.

---

## Response Time (RT)

```text
Response Time = First Execution Time - Arrival Time
```

Measures how quickly a process gets CPU attention.

---

# Technologies Used

- Java
- Object-Oriented Programming (OOP)
- Java Collections Framework
- GUI Framework (Swing/JavaFX)

---

# How to Run

## Prerequisites

- Java JDK 8 or higher
- IDE such as:
  - IntelliJ IDEA
  - Eclipse
  - VS Code

---

## Clone the Repository

```bash
git clone https://github.com/Kareemingit/Priority-vs-SRTF.git
```

---

## Open the Project

Open the project folder in your preferred Java IDE.

---

## Run the Application

Run:

```text
src/gui/MainGUI.java
```

---

# Example Input

| Process | Arrival Time | Burst Time | Priority |
|---|---|---|---|
| P1 | 0 | 7 | 2 |
| P2 | 2 | 4 | 1 |
| P3 | 4 | 1 | 3 |

---

# Example Output

## Priority Scheduling

| Process | Waiting Time | Turnaround Time | Response Time |
|---|---|---|---|
| P1 | 4 | 11 | 0 |
| P2 | 0 | 4 | 0 |
| P3 | 7 | 8 | 7 |

Average metrics are also calculated and displayed.

---

# Educational Purpose

This project was developed for educational purposes to help understand:

- CPU scheduling concepts
- Process management
- Scheduling performance analysis
- Operating Systems fundamentals

---
# License

This project is open-source and available under the MIT License.
