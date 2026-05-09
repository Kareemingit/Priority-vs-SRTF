package metrics;

import java.util.ArrayList;
import model.Process;

public class ScheduleResult {

    public static class GanttEntry {
        public String pid;
        public int start;
        public int end;

        public GanttEntry(String pid, int start, int end) {
            this.pid = pid;
            this.start = start;
            this.end = end;
        }
    }

    public ArrayList<String> gantt = new ArrayList<>();
    public ArrayList<GanttEntry> ganttEntries = new ArrayList<>();
    public ArrayList<Process> processes;
    public double avgWT;
    public double avgTAT;
    public double avgRT;

    public ScheduleResult(ArrayList<Process> processes) {
        this.processes = processes;
    }
}
