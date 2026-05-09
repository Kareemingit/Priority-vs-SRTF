import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class MainGUI extends JFrame {

    JTextField pidField;
    JTextField arrivalField;
    JTextField burstField;
    JTextField priorityField;
    JTextArea conclusionArea;

    DefaultTableModel inputModel;
    JTable inputTable;

    JTextArea priorityArea;
    JTextArea srtfArea;

    JTextArea summaryArea;
    JPanel priorityChartPanel;
    JPanel srtfChartPanel;

    ArrayList<Process> processes = new ArrayList<>();

    public MainGUI() {

        setTitle("Priority vs SRTF Scheduler");
        setSize(1200, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        pidField = new JTextField(5);
        arrivalField = new JTextField(5);
        burstField = new JTextField(5);
        priorityField = new JTextField(5);

        JButton addButton = new JButton("Add Process");
        JButton runButton = new JButton("Run Comparison");
        JButton clearButton = new JButton("Clear Inputs");
        JButton scenarioButton = new JButton("Load Scenario");

        inputPanel.add(new JLabel("PID"));      inputPanel.add(pidField);
        inputPanel.add(new JLabel("Arrival"));  inputPanel.add(arrivalField);
        inputPanel.add(new JLabel("Burst"));    inputPanel.add(burstField);
        inputPanel.add(new JLabel("Priority")); inputPanel.add(priorityField);
        inputPanel.add(addButton);
        inputPanel.add(runButton);
        inputPanel.add(clearButton);
        inputPanel.add(scenarioButton);

        inputModel = new DefaultTableModel();
        inputModel.addColumn("PID");
        inputModel.addColumn("Arrival");
        inputModel.addColumn("Burst");
        inputModel.addColumn("Priority");
        inputTable = new JTable(inputModel);
        JScrollPane tableScroll = new JScrollPane(inputTable);
        tableScroll.setPreferredSize(new Dimension(1200, 100));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(tableScroll, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(1200, 150));
        add(topPanel, BorderLayout.NORTH);

        priorityChartPanel = new JPanel();
        srtfChartPanel = new JPanel();
        priorityChartPanel.setBorder(new TitledBorder("Priority Gantt Chart"));
        srtfChartPanel.setBorder(new TitledBorder("SRTF Gantt Chart"));
        priorityChartPanel.setPreferredSize(new Dimension(580, 100));
        srtfChartPanel.setPreferredSize(new Dimension(580, 100));

        priorityArea = new JTextArea();
        srtfArea = new JTextArea();
        priorityArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        srtfArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(new TitledBorder("Priority Scheduling"));
        leftPanel.add(priorityChartPanel, BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(priorityArea), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new TitledBorder("SRTF Scheduling"));
        rightPanel.add(srtfChartPanel, BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(srtfArea), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(600);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

        summaryArea = new JTextArea(4, 0);
        summaryArea.setFont(new Font("Monospaced", Font.BOLD, 13));
        summaryArea.setEditable(false);
        JScrollPane summaryScroll = new JScrollPane(summaryArea);
        summaryScroll.setBorder(new TitledBorder("Comparison Summary"));

        conclusionArea = new JTextArea(6, 0);
        conclusionArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        conclusionArea.setEditable(false);
        JScrollPane conclusionScroll = new JScrollPane(conclusionArea);
        conclusionScroll.setBorder(new TitledBorder("Final Conclusion"));

        JPanel southPanel = new JPanel(new GridLayout(1, 2));
        southPanel.setPreferredSize(new Dimension(1200, 150));
        southPanel.add(summaryScroll);
        southPanel.add(conclusionScroll);
        add(southPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addProcess());
        runButton.addActionListener(e -> runSimulation());
        clearButton.addActionListener(e -> clearInputs());
        scenarioButton.addActionListener(e -> loadScenario());

        setVisible(true);
    }

    private void loadScenario() {

        String[] options = {
    "Scenario A: Mixed Workload",
    "Scenario B: Priority vs Burst Conflict",
    "Scenario C: Starvation-Sensitive",
    "Scenario D: Validation Case",
      };

        String choice = (String) JOptionPane.showInputDialog(
            this,
            "Choose a scenario to load:",
            "Load Scenario",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == null) return;

        clearInputs();

        if (choice.equals(options[0])) {
            loadScenarioA();
        } else if (choice.equals(options[1])) {
            loadScenarioB();
        } else if (choice.equals(options[2])) {
            loadScenarioC();
        } else if (choice.equals(options[3])) {
            loadScenarioD();
        } 

        JOptionPane.showMessageDialog(this, "Scenario loaded! Click 'Run Comparison' to simulate.");
    }

    private void loadScenarioA() {
        addScenarioProcess("P1", 0, 8, 2);
        addScenarioProcess("P2", 1, 4, 1);
        addScenarioProcess("P3", 2, 9, 3);
        addScenarioProcess("P4", 3, 5, 2);
    }

 
    private void loadScenarioB() {
        addScenarioProcess("P1", 0, 10, 1);
        addScenarioProcess("P2", 1, 2,  3);
        addScenarioProcess("P3", 2, 1,  4);
        addScenarioProcess("P4", 3, 8,  2);
    }

   
    private void loadScenarioC() {
        addScenarioProcess("P1", 0, 20, 3);
        addScenarioProcess("P2", 1, 2,  1);
        addScenarioProcess("P3", 2, 2,  1);
        addScenarioProcess("P4", 3, 2,  1);
    }

    
   private void loadScenarioD() {
    String message =
        "Scenario D: Validation Case\n\n" +
        "This scenario tests input validation.\n" +
        "Please try entering the following manually:\n\n" +
        "1. Empty PID        → should show error\n" +
        "2. Arrival = -1     → should show error\n" +
        "3. Burst = 0        → should show error\n" +
        "4. Priority = -1    → should show error\n" +
        "5. Non-numeric text → should show error\n" +
        "6. Duplicate PID    → should show error\n\n" +
        "A valid base process has been loaded for duplicate testing:";
    
    

    JOptionPane.showMessageDialog(this, message, "Scenario D - Validation", JOptionPane.INFORMATION_MESSAGE);

    addScenarioProcess("P1", 0, 5, 2);
}

 
    private void addScenarioProcess(String pid, int at, int bt, int pr) {
        Process p = new Process(pid, at, bt, pr);
        processes.add(p);
        inputModel.addRow(new Object[]{pid, at, bt, pr});
    }

    private void addProcess() {

        try {
            String pid = pidField.getText();
            int at = Integer.parseInt(arrivalField.getText());
            int bt = Integer.parseInt(burstField.getText());
            int pr = Integer.parseInt(priorityField.getText());

            if (pid.isEmpty()) {
                JOptionPane.showMessageDialog(this, "PID cannot be empty");
                return;
            }

            if (at < 0 || bt <= 0 || pr < 0) {
                JOptionPane.showMessageDialog(this, "Invalid values");
                return;
            }

            for (Process p : processes) {
                if (p.pid.equals(pid)) {
                    JOptionPane.showMessageDialog(this, "Duplicate PID");
                    return;
                }
            }

            Process p = new Process(pid, at, bt, pr);
            processes.add(p);
            inputModel.addRow(new Object[]{pid, at, bt, pr});

            pidField.setText("");
            arrivalField.setText("");
            burstField.setText("");
            priorityField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers");
        }
    }

    private void runSimulation() {

        if (processes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No processes added");
            return;
        }

        ScheduleResult priorityResult = PriorityScheduler.run(processes);
        ScheduleResult srtfResult = SRTFScheduler.run(processes);

        displayResult(priorityArea, priorityResult, "PRIORITY SCHEDULING");
        displayResult(srtfArea, srtfResult, "SRTF SCHEDULING");
        drawGanttChart(priorityChartPanel, priorityResult);
        drawGanttChart(srtfChartPanel, srtfResult);
        displaySummary(priorityResult, srtfResult);
    }

    private void displayResult(JTextArea area, ScheduleResult result, String title) {

        area.setText("");
        area.append(title + "\n\n");
        area.append(String.format("%-10s%-10s%-10s%-10s\n", "PID", "WT", "TAT", "RT"));

        for (Process p : result.processes) {
            area.append(String.format("%-10s%-10d%-10d%-10d\n",
                    p.pid, p.waitingTime, p.turnaroundTime, p.responseTime));
        }

        area.append("\n");
        area.append("Average WT  = " + result.avgWT + "\n");
        area.append("Average TAT = " + result.avgTAT + "\n");
        area.append("Average RT  = " + result.avgRT + "\n");
    }

    private void displaySummary(ScheduleResult p, ScheduleResult s) {

    summaryArea.setText("");
    summaryArea.append("COMPARISON SUMMARY\n\n");

    // WT
    if (p.avgWT < s.avgWT)
        summaryArea.append("✔ Priority produced lower Avg WT  (" + p.avgWT + " vs " + s.avgWT + ")\n");
    else if (s.avgWT < p.avgWT)
        summaryArea.append("✔ SRTF produced lower Avg WT      (" + s.avgWT + " vs " + p.avgWT + ")\n");
    else
        summaryArea.append("= Equal Avg WT                    (" + p.avgWT + ")\n");

    // TAT
    if (p.avgTAT < s.avgTAT)
        summaryArea.append("✔ Priority produced lower Avg TAT (" + p.avgTAT + " vs " + s.avgTAT + ")\n");
    else if (s.avgTAT < p.avgTAT)
        summaryArea.append("✔ SRTF produced lower Avg TAT     (" + s.avgTAT + " vs " + p.avgTAT + ")\n");
    else
        summaryArea.append("= Equal Avg TAT                   (" + p.avgTAT + ")\n");

    // RT
    if (p.avgRT < s.avgRT)
        summaryArea.append("✔ Priority produced lower Avg RT  (" + p.avgRT + " vs " + s.avgRT + ")\n");
    else if (s.avgRT < p.avgRT)
        summaryArea.append("✔ SRTF produced lower Avg RT      (" + s.avgRT + " vs " + p.avgRT + ")\n");
    else
        summaryArea.append("= Equal Avg RT                    (" + p.avgRT + ")\n");

    displayConclusion(p, s);
}
    private void displayConclusion(ScheduleResult p, ScheduleResult s) {

    conclusionArea.setText("");
    conclusionArea.append("FINAL CONCLUSION\n\n");

    int priorityWins = 0;
    int srtfWins = 0;
    if (p.avgWT  < s.avgWT)  priorityWins++; else if (s.avgWT  < p.avgWT)  srtfWins++;
    if (p.avgTAT < s.avgTAT) priorityWins++; else if (s.avgTAT < p.avgTAT) srtfWins++;
    if (p.avgRT  < s.avgRT)  priorityWins++; else if (s.avgRT  < p.avgRT)  srtfWins++;

    if (priorityWins > srtfWins)
        conclusionArea.append("► Overall: Priority performed better on this workload.\n");
    else if (srtfWins > priorityWins)
        conclusionArea.append("► Overall: SRTF performed better on this workload.\n");
    else
        conclusionArea.append("► Overall: Both algorithms performed equally.\n");

    boolean priorityHelpedUrgent = p.avgRT < s.avgRT;
    if (priorityHelpedUrgent)
        conclusionArea.append("► Priority values improved treatment of urgent processes (lower RT).\n");
    else
        conclusionArea.append("► Priority values did NOT improve response time vs SRTF.\n");

    // 3. Did SRTF favor short jobs more aggressively
    if (s.avgWT < p.avgWT)
        conclusionArea.append("► SRTF favored short jobs aggressively, achieving lower waiting time.\n");
    else
        conclusionArea.append("► SRTF did not achieve lower waiting time than Priority here.\n");

    conclusionArea.append("► Trade-off: Priority risks starvation for low-priority long jobs.\n");
    conclusionArea.append("             SRTF risks starvation for long burst jobs.\n");

    int maxWTP = 0, minWTP = Integer.MAX_VALUE;
    int maxWTS = 0, minWTS = Integer.MAX_VALUE;

    for (Process proc : p.processes) {
        if (proc.waitingTime > maxWTP) maxWTP = proc.waitingTime;
        if (proc.waitingTime < minWTP) minWTP = proc.waitingTime;
    }
    for (Process proc : s.processes) {
        if (proc.waitingTime > maxWTS) maxWTS = proc.waitingTime;
        if (proc.waitingTime < minWTS) minWTS = proc.waitingTime;
    }

    int spreadP = maxWTP - minWTP;
    int spreadS = maxWTS - minWTS;

    if (spreadP < spreadS)
        conclusionArea.append("► Fairness: Priority appeared fairer (smaller WT spread across processes).\n");
    else if (spreadS < spreadP)
        conclusionArea.append("► Fairness: SRTF appeared fairer (smaller WT spread across processes).\n");
    else
        conclusionArea.append("► Fairness: Both algorithms showed equal fairness.\n");

    conclusionArea.append("\n► Recommendation: ");
    if (priorityWins >= srtfWins && priorityHelpedUrgent)
        conclusionArea.append("Use Priority when urgent processes must be served first.\n");
    else if (srtfWins > priorityWins)
        conclusionArea.append("Use SRTF when minimizing average waiting time is the goal.\n");
    else
        conclusionArea.append("Both are viable — choose based on whether urgency or burst length matters more.\n");
}
    
    private void clearInputs() {

        processes.clear();
        inputModel.setRowCount(0);

        priorityArea.setText("");
        srtfArea.setText("");
        summaryArea.setText("");

        pidField.setText("");
        arrivalField.setText("");
        burstField.setText("");
        priorityField.setText("");

        priorityChartPanel.removeAll();
        priorityChartPanel.revalidate();
        priorityChartPanel.repaint();
        conclusionArea.setText("");
        srtfChartPanel.removeAll();
        srtfChartPanel.revalidate();
        srtfChartPanel.repaint();
    }

    private void drawGanttChart(JPanel panel, ScheduleResult result) {

        panel.removeAll();
        panel.setLayout(new BorderLayout());

        JPanel boxesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        Color[] colors = {
            new Color(173, 216, 230),
            new Color(144, 238, 144),
            new Color(255, 182, 193),
            new Color(255, 255, 153),
            new Color(216, 191, 216)
        };

        int unitSize = 30;

        for (int i = 0; i < result.ganttEntries.size(); i++) {
            ScheduleResult.GanttEntry entry = result.ganttEntries.get(i);

            int duration = entry.end - entry.start;
            int boxWidth = duration * unitSize;

            JPanel box = new JPanel(new GridBagLayout());
            box.setPreferredSize(new Dimension(boxWidth, 50));
            box.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            box.setBackground(colors[i % colors.length]);

            JLabel label = new JLabel(entry.pid);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            box.add(label);

            boxesPanel.add(box);
        }

        JPanel timesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        for (int i = 0; i < result.ganttEntries.size(); i++) {
            ScheduleResult.GanttEntry entry = result.ganttEntries.get(i);
            int duration = entry.end - entry.start;
            int boxWidth = duration * unitSize;

            JLabel timeLabel = new JLabel(String.valueOf(entry.start));
            timeLabel.setPreferredSize(new Dimension(boxWidth, 20));
            timeLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            timeLabel.setHorizontalAlignment(SwingConstants.LEFT);
            timesPanel.add(timeLabel);
        }

        if (!result.ganttEntries.isEmpty()) {
            ScheduleResult.GanttEntry last = result.ganttEntries.get(result.ganttEntries.size() - 1);
            JLabel endLabel = new JLabel(String.valueOf(last.end));
            endLabel.setFont(new Font("Arial", Font.PLAIN, 11));
            timesPanel.add(endLabel);
        }

        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.add(boxesPanel, BorderLayout.CENTER);
        chartPanel.add(timesPanel, BorderLayout.SOUTH);

        panel.add(chartPanel, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();

        if (panel.getParent() != null) {
            panel.getParent().revalidate();
            panel.getParent().repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}