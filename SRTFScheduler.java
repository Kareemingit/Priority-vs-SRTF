import java.util.ArrayList;

public class SRTFScheduler {
    public static ScheduleResult run(ArrayList<Process> originalProcesses) {
        ArrayList<Process> processes = new ArrayList<>();
        for (Process p : originalProcesses) {
            processes.add(p.copy());
        }
        ScheduleResult result = new ScheduleResult(processes);
        int time = 0;
        int completed = 0;
        int n = processes.size();

        String lastPid = "";
        int segmentStart = 0;

        while (completed < n) {
            Process current = null;
            for (Process p : processes) {
                if (p.arrivalTime <= time && p.remainingTime > 0) {
                    if (current == null) {
                        current = p;
                    } else {
                        if (p.remainingTime < current.remainingTime) {
                            current = p;
                        } else if (p.remainingTime == current.remainingTime) {
                            if (p.arrivalTime < current.arrivalTime) {
                                current = p;
                            }
                        }
                    }
                }
            }

            String currentPid = (current == null) ? "Idle" : current.pid;

            if (!currentPid.equals(lastPid)) {
                if (!lastPid.isEmpty()) {
                    result.gantt.add(lastPid);
                    result.ganttEntries.add(new ScheduleResult.GanttEntry(lastPid, segmentStart, time));
                }
                segmentStart = time;
                lastPid = currentPid;
            }

            if (current == null) {
                time++;
                continue;
            }

            if (!current.started) {
                current.responseTime = time - current.arrivalTime;
                current.started = true;
            }

            current.remainingTime--;
            time++;

            if (current.remainingTime == 0) {
                completed++;
                current.completionTime = time;
                current.turnaroundTime = current.completionTime - current.arrivalTime;
                current.waitingTime = current.turnaroundTime - current.burstTime;
            }
        }

        if (!lastPid.isEmpty()) {
            result.gantt.add(lastPid);
            result.ganttEntries.add(new ScheduleResult.GanttEntry(lastPid, segmentStart, time));
        }

        calculateAverages(result);
        return result;
    }

    private static void calculateAverages(ScheduleResult result) {
        double totalWT = 0;
        double totalTAT = 0;
        double totalRT = 0;
        for (Process p : result.processes) {
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
            totalRT += p.responseTime;
        }
        int n = result.processes.size();
        result.avgWT = totalWT / n;
        result.avgTAT = totalTAT / n;
        result.avgRT = totalRT / n;
    }
}