package model;

public class Process {
    public String pid;
    public int arrivalTime;
    public int burstTime;
    public int priority;
    public int remainingTime;
    public int completionTime;
    public int turnaroundTime;
    public int waitingTime;
    public int responseTime;
    public boolean started = false;

    public Process(String pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
    }

    public Process copy() {
        return new Process(pid, arrivalTime, burstTime, priority);
    }
}