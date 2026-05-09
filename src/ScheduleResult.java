import java.util.ArrayList;

public class ScheduleResult {

    public static class GanttEntry {
        String pid;
        int start;
        int end;

        public GanttEntry(String pid, int start, int end) {
            this.pid = pid;
            this.start = start;
            this.end = end;
        }
    }

    ArrayList<String> gantt = new ArrayList<>();
    ArrayList<GanttEntry> ganttEntries = new ArrayList<>();
    ArrayList<Process> processes;
    double avgWT;
    double avgTAT;
    double avgRT;

    public ScheduleResult(ArrayList<Process> processes) {
        this.processes = processes;
    }
}
