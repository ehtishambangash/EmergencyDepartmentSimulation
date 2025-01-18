package emergencyDepartment;

import java.io.FileWriter;
////////////////////////////////////////////////////////////////////////////
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

///////////////////////////////////////


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

public class Main {
	private JFrame statsFrame;
	// Hardcoded Values for Beds -------We can also change this-------
	protected static int RBeds = 2;
	protected static int ABeds = 5;
	protected static int SBeds = 4;
	protected static int MBeds = 4;
	protected static int OBeds = 5;
	protected static int OChairs = 5;
	
	/// HardCoded Values for Physicans ----------- Change also be changes
	protected static int juniorResidents = 3;
	protected static int seniorResidents = 7;
	protected static int interns = 4;
	protected static int consultants = 2;
	protected static int registrars = 2;
	
	int start_time = 0;
    static int end_time = 1000;
    static int current_time = 0;
    static int patient_count = 1;
    
    protected static int triageOnePatients=0;
    protected static int triageTwoPatients=0;
    protected static int triageThreePatients=0;
    protected static int triageFourPatients=0;
    protected static int triageFivePatients=0;
    
	protected static int pat_NotSeen_Within_Rec_Time=0;
	
	protected static int NotSeen;
	protected static int Seen;
	
	protected static int availablePhysiciansCount =0;
	protected static int maxAssignmnets=0;
	
	protected static int bedAssigned=0;
	protected static int availableBedsCount = 0;
	protected static int BedNmber=0;
	
	protected static int assignedp=0;
	
	protected static int acuteUsed=0;
	protected static int subAcuteUsed=0;
	protected static int recUsed=0;
	protected static int minorUsed=0;
	protected static int ofBedsUsed=0;
	protected static int ofChairsUsed=0;
	
	static List<Integer> queueSizes;
	static List<Double> physicianUtilizations;
	static List<Double> bedUtilizations;
	
	
	
	
    public static void main(String[] args) throws InterruptedException {
        CalculateTriage calculateTriage = new CalculateTriage("diseases.txt");
        WeibullDistribution Weibull = new WeibullDistribution();
        QueueManager Queue = new QueueManager();
        BedManager bedManager = new BedManager(RBeds,ABeds,SBeds,MBeds,OBeds,OChairs);
        PhysicianManager physicianManager = new PhysicianManager(juniorResidents, seniorResidents, interns, consultants, registrars);
        ImpatientPatient ImpatientPatient = new ImpatientPatient();
        Random random = new Random();

        //----------Clock-------Management----------------////////////
       GlobalClock globalClock = new GlobalClock();

        // clock
        Thread clockThread = new Thread(new ClockThread(globalClock));
        clockThread.start();

        // deallocation thread
 DeallocationThread deallocationThread = new DeallocationThread(globalClock, bedManager, physicianManager, Queue);
      Thread deallocationThreadInstance = new Thread(deallocationThread);
    deallocationThreadInstance.start();

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter("emergency_department_log.txt", true)); // Append mode
        } catch (IOException e) {
            System.err.println("Error opening file for writing: " + e.getMessage());
            return;
        }

        /////////////////////////////////////////////////////////
        
        /////////////////////STAT COLLECTER/////////////////////////
        StatisticsCollectorThread statsCollector = new StatisticsCollectorThread(globalClock, Queue, physicianManager.getPhysicians(), bedManager.getBeds(),bedManager);
        Thread statsThread = new Thread(statsCollector);
        statsThread.start();
        //////////////////////////////////////////
       
        

        try {
			while (globalClock.getCurrentTime() < end_time) {
			    int triage = calculateTriage.triageCal();
			    if(triage==1) {
			    	triageOnePatients = triageOnePatients+1;
			    }
			    else if(triage==2) {
			    	triageTwoPatients = triageTwoPatients+1;
			    }
			    else if(triage==3) {
			    	triageThreePatients = triageThreePatients+1;
			    }
			    else if(triage==4) {
			    	triageFourPatients = triageFourPatients+1;
			    }
			    else if(triage==5) {
			    	triageFivePatients = triageFivePatients+1;
			    }
			    
			    int arrival_gap = Weibull.generateArrivalTime();
			    int arrival = (int) (arrival_gap + globalClock.getCurrentTime());
			    while (globalClock.getCurrentTime() < arrival) {
			        // Busy wait for the arrival time
			    }
			    // Create a new patient with the current patient_count before incrementing
			    Patient newpatient = new Patient(patient_count, triage, arrival);
			    patient_count = patient_count + 1; // Increment after patient creation
			    double pddt = ImpatientPatient.calculatePDDT();
		        if (pddt > 156) {
		            current_time = current_time + arrival_gap;
				    String patientInfo = "\033[0;34m"+"Patient ID: " + newpatient.getId() +"\tDisease: "+calculateTriage.DiseaseGetter()+ "\t\t\tTriage Category: " + newpatient.getTriageCategory() +
				            "\tArrival Gap: " + arrival_gap +"TU" + "\tArrival Time: " + newpatient.getArrivalTime()+"TU" + "\tImpatient in Ward: " + random.nextInt(5) +"\tPPDT: " +(int)pddt+"TU"+"\033[0m" + "\n----------------------------------------------------------------\n";
		           


				    System.out.println(patientInfo); // Print impatiet patient to the console
				    writer.println(patientInfo); // Write imaptient to the file

		        
		        
		        }
		        else {
		        	Bed allocatedBed = bedManager.allocateBed(triage);

				    
				    current_time = current_time + arrival_gap;
				    String patientInfo = "Patient ID: " + newpatient.getId() +"\tDisease: "+calculateTriage.DiseaseGetter()+ "\t\t\tTriage Category: " + newpatient.getTriageCategory() +
				            "\tArrival Gap: " + arrival_gap+"TU" + "\tArrival Time: " + newpatient.getArrivalTime()+"TU" +
				            "\tCurrent Time: " + globalClock.getCurrentTime()+"TU" + "\tTreatment Time: " + newpatient.treatmentTime+"TU" +
				            "\t Recommended Discharge Time: " + newpatient.dischargeTime+"TU";

				    System.out.println(patientInfo); // Print to console
				    writer.println(patientInfo); // Write to file

				    if (allocatedBed != null) {
				        allocatedBed.setAvailable(false);
				        newpatient.bedAssignmnetTime = (int) globalClock.getCurrentTime();
				        newpatient.setBedAssigned(true);
				        
				        allocatedBed.assignPatient(newpatient);
				        allocatedBed.firstAssign();
				        
				        
				        /////////////
				        int[] maxAllowedTime = {0, 10, 30, 60, 120};
	                    int triageCategory = newpatient.getTriageCategory();
	                    
	                    if (triageCategory >= 1 && triageCategory <= 5 && 
	                    	    (newpatient.bedAssignmnetTime - newpatient.getArrivalTime() > maxAllowedTime[triageCategory - 1])) {
	                    	    
	                    	    Main.IncrementNotRecommended();
	                    	}
				        
				        ////////
				        System.out.println("\033[0;32m"+"Allocated " + allocatedBed.getType() + " with ID: " + allocatedBed.getId() + "\t\tBed Assignment Time: " + newpatient.bedAssignmnetTime+"TU"+"\033[0m");

				        // Physician allocation logic
				        Physician allocatedPhysician = physicianManager.allocatePhysician(triage);
				        if (allocatedPhysician != null) {
				            physicianManager.physicians.get(allocatedPhysician.getId()).addOnePatient();
				            newpatient.setPhysicianAssigned(true);
				            newpatient.setPhysician(allocatedPhysician);
				            System.out.println("Allocated " + allocatedPhysician.getType() + " with ID: " + allocatedPhysician.getId() + "\n----------------------------------------------------------------\n");
				        } else {
				            System.out.println("No available physician for Category " + triage + " patient." + "\n----------------------------------------------------------------\n");
				        }
				    } else {
				        Queue.addPatient(newpatient);
				        System.out.println("No available bed for Category " + triage + " patient. \t Queue Size" +Main.GetWaitingPatients(Queue)+ "\n----------------------------------------------------------------\n");
				    }
		        }

			    
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

        if (writer != null) {
            writer.close();
 }

        ///////////////////////////////////////////////////////////Experimentations/////////////////////
        BedNmber = bedManager.getBedsnumber();
        for (Bed bed : bedManager.getBeds()) {
            if (bed.isAvailable()) {
                
            }
            if(bed.firstAssign) {
            	bedAssigned = bedAssigned + 1;
            	if(bed.getType().equals("Acute")) {
            		acuteUsed = acuteUsed + 1;
            	}
            	else if(bed.getType().equals("Subacute")) {
            		subAcuteUsed = subAcuteUsed + 1;
            	}
            	else if(bed.getType().equals("Resuscitation")) {
            		recUsed = recUsed + 1;
            	}
            	else if(bed.getType().equals("Minor Procedure Room")) {
            		minorUsed = minorUsed + 1;
            	}
            	else if(bed.getType().equals("Overflow Bed")) {
            		ofBedsUsed = ofBedsUsed + 1;
            	}
            	else if(bed.getType().equals("Overflow Chair")) {
            		ofChairsUsed = ofChairsUsed + 1;
            	}
            }
                   };

        int PhysiciansNumber = physicianManager.getPhysiciansnumber();
        int assi=0;
        for (Physician physician : physicianManager.getPhysicians()) {
            if (physician.getMaxPatients() - physician.getCurrentPatients() > 0) {
            	int intermediate = physician.getMaxPatients() - physician.getCurrentPatients();
                availablePhysiciansCount = availablePhysiciansCount + intermediate;
            }
            maxAssignmnets = maxAssignmnets + physician.getMaxPatients();
            assignedp = assignedp + physician.getCurrentPatients();
        }

        System.out.println("\n\nQueue Size: " + Queue.getQueueSize());
        System.out.println("Bed No: " + BedNmber + "\t\t Available Beds: " + availableBedsCount);
        System.out.println("Physicians No: " + PhysiciansNumber + "\t Available Assignments of Physicians: " + availablePhysiciansCount + "\t\tMax Assignments: " + maxAssignmnets);
        System.out.println("Assigned: "+assi);
        
        clockThread.interrupt();  // Interrupt the clock thread
        deallocationThreadInstance.interrupt();
        
        statsThread.interrupt();
        statsThread.join();
        
        
        //////////////////// STAT COLLECTOR ///////////////////
        //List queueSizes = (List) statsCollector.getQueueSizes();
        //List physicianUtilizations = (List) statsCollector.getPhysicianUtilizations();
        //List bedUtilizations = (List) statsCollector.getBedUtilizations();
        /////////////////////////////////////////////////////////////
        
       
        getSimulationStatistics(statsCollector);
        clockThread.join();  // Wait for the thread to terminate
        System.out.println("Clock thread has been stopped. Exiting program.");
        deallocationThreadInstance.join();
        System.out.println("Deallocation has been stopped. Exiting program.");
        SwingUtilities.invokeLater(() -> new Main().createAndShowGUI());
        
        
        
        
    }
    
    private static void getSimulationStatistics(StatisticsCollectorThread statsCollector) {
        // Access the collected statistics
    	queueSizes = statsCollector.getQueueSizes();
    	physicianUtilizations = statsCollector.getPhysicianUtilizations();
    	bedUtilizations = statsCollector.getBedUtilizations();

        // Now you can use these lists as required
        //System.out.println("Queue Sizes: " + queueSizes);
        //System.out.println("Physician Utilizations: " + physicianUtilizations);
        //System.out.println("Bed Utilizations: " + bedUtilizations);
    }
    
    
    public static double GetBedUtil(BedManager bedManager) {
    	 BedNmber = bedManager.getBedsnumber();
         for (Bed bed : bedManager.getBeds()) {
             if(bed.firstAssign) {
             	bedAssigned = bedAssigned + 1;}
             }
         return bedAssigned/bedManager.getBedsnumber();
    }
    
    public static int GetWaitingPatients(QueueManager Queue) {
    	return Queue.getQueueSize();
    }
    
    public static void IncrementNotRecommended() {	
    	pat_NotSeen_Within_Rec_Time++;
    }
    private void createAndShowGUI() {
        statsFrame = new JFrame("Roghtoon Hospital Statistics");
        statsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        statsFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full-screen mode
        statsFrame.setLayout(new BorderLayout());
        statsFrame.setBackground(new Color(245, 245, 245));

        // Set custom logo
        try {
            ImageIcon icon = new ImageIcon("logo.png");
            statsFrame.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Logo not found.");
        }
        

        // Create a panel for the title with logo (Centered)
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JLabel titleLabel = new JLabel("Roghtoon Hospital Resource Utilization", JLabel.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setForeground(new Color(0, 123, 255)); // Accent color
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));

        
        try {
            ImageIcon logoIcon = new ImageIcon("logo.png");
            Image logoImage = logoIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            logoIcon = new ImageIcon(logoImage);
            JLabel logoLabel = new JLabel(logoIcon);
            titlePanel.add(logoLabel);
            titlePanel.add(titleLabel);
        } catch (Exception e) {
            System.err.println("Logo not found");
        }

        statsFrame.add(titlePanel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        statsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JTable doctorTable = createTable(new String[]{"Category", "Count"});
        fillDoctorData(doctorTable);
        statsPanel.add(createTablePanel("Physicians", doctorTable));

        JTable bedTable = createTable(new String[]{"Category", "Count", "Utilization (%)"});
        fillBedData(bedTable);
        statsPanel.add(createTablePanel("Beds", bedTable));

        JPanel utilizationPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        utilizationPanel.setBorder(BorderFactory.createTitledBorder("Utilization Overview"));
        int physicianUtilization = maxAssignmnets == 0 ? 0 : (assignedp * 100) / maxAssignmnets;
        int bedUtilization = BedNmber == 0 ? 0 : (bedAssigned * 100) / BedNmber;
        System.out.println("Assigned: "+bedAssigned);
        System.out.println("Available: "+availableBedsCount);

        utilizationPanel.add(createProgressBar("Physician Utilization", physicianUtilization));
        utilizationPanel.add(createProgressBar("Bed Utilization", bedUtilization));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));  // 3 buttons stacked
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Create square buttons for the Stats window with increased width and decreased height
        buttonPanel.add(createButton("Simulate", e -> JOptionPane.showMessageDialog(statsFrame, "Simulation Started!", "Run Simulation", JOptionPane.INFORMATION_MESSAGE)));
        buttonPanel.add(createButton("Graphs", e -> createAndShowGraphs()));
        buttonPanel.add(createButton("Exit", e -> System.exit(0)));

        statsFrame.add(statsPanel, BorderLayout.CENTER);
        statsFrame.add(utilizationPanel, BorderLayout.SOUTH);
        statsFrame.add(buttonPanel, BorderLayout.EAST);

        statsFrame.setVisible(true);
    }

    private JPanel createTablePanel(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JTable createTable(String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.setBackground(new Color(255, 255, 255)); // White background
        table.setForeground(new Color(50, 50, 50)); // Dark text color
        return table;
    }
    
    private void fillDoctorData(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{"Junior Residents", juniorResidents});
        model.addRow(new Object[]{"Senior Residents", seniorResidents});
        model.addRow(new Object[]{"Consultants", consultants});
        model.addRow(new Object[]{"Registrars", registrars});
        model.addRow(new Object[]{"Interns", interns});
    }

    private void fillBedData(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{"Acute Beds", ABeds, ABeds == 0 ? "0%" : (acuteUsed * 100) / ABeds + "%"});
        model.addRow(new Object[]{"Subacute Beds", SBeds, SBeds == 0 ? "0%" : (subAcuteUsed * 100) / SBeds + "%"});
        model.addRow(new Object[]{"Reclination Beds", RBeds, RBeds == 0 ? "0%" : (recUsed * 100) / RBeds + "%"});

        model.addRow(new Object[]{"Minor Procedure Beds", MBeds, MBeds == 0 ? "0%" : ( minorUsed* 100) / MBeds + "%"});
        model.addRow(new Object[]{"Overflow Chairs", OChairs, OChairs == 0 ? "0%" : ( ofChairsUsed* 100) / OChairs + "%"});
        model.addRow(new Object[]{"Overflow Beds", OBeds, OBeds == 0 ? "0%" : ( ofBedsUsed* 100) / OBeds + "%"});
    }


    private JProgressBar createProgressBar(String title, int value) {
        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(value);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Arial", Font.BOLD, 16));
        progressBar.setBorder(BorderFactory.createTitledBorder(title));
        progressBar.setForeground(new Color(0, 123, 255));
        progressBar.setBackground(new Color(220, 220, 220)); 
        return progressBar;
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(listener);
        button.setPreferredSize(new Dimension(150, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private void createAndShowGraphs() {
        JFrame graphFrame = new JFrame("Hospital Graphs");
        try {
            ImageIcon icon = new ImageIcon("logo.png");
            graphFrame.setIconImage(icon.getImage());
        } catch (Exception e) {
            System.err.println("Logo not found.");
        }
        graphFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        graphFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Full-screen mode
        graphFrame.setLayout(new BorderLayout());
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); // Buttons in a vertical grid
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        buttonPanel.add(createButton("Back to Stats", e -> graphFrame.dispose()));
        buttonPanel.add(createButton("Exit", e -> System.exit(0)));
        graphFrame.add(buttonPanel, BorderLayout.EAST);

        JPanel graphPanel = new JPanel(new GridLayout(3, 2, 10, 10)); // 3x2 grid of graphs
        

        // Triage Categories (Bar Graph)
        DefaultCategoryDataset triageDataset = new DefaultCategoryDataset();
        triageDataset.addValue(triageOnePatients, "Triage 1", "Category");
        triageDataset.addValue(triageTwoPatients, "Triage 2", "Category");
        triageDataset.addValue(triageThreePatients, "Triage 3", "Category");
        triageDataset.addValue(triageFourPatients, "Triage 4", "Category");
        triageDataset.addValue(triageFivePatients, "Triage 5", "Category");
        JFreeChart triageBarChart = ChartFactory.createBarChart("Triage Categories", "Category", "Count", triageDataset, PlotOrientation.VERTICAL, true, true, false);
        graphPanel.add(new ChartPanel(triageBarChart));

     // Utilization of Trainee Doctors vs Integer (Line Graph)
        XYSeries doctorUtilizationSeries = new XYSeries("Doctor Utilization");
        int docUtilLength = physicianUtilizations.size();
        
        for(int i=0;i<docUtilLength;i++) {
        	doctorUtilizationSeries.add((i*10), physicianUtilizations.get(i));
        }

        XYSeriesCollection doctorUtilizationDataset = new XYSeriesCollection(doctorUtilizationSeries);
        JFreeChart doctorUtilizationChart = ChartFactory.createXYLineChart(
            "Utilization of Trainee Doctors vs Time", "Time", "Utilization (%)", doctorUtilizationDataset, 
            PlotOrientation.VERTICAL, true, true, false);

        graphPanel.add(new ChartPanel(doctorUtilizationChart));

        // Percentage of Patients Seen Within Recommended Time (Pie Chart)
        DefaultPieDataset timeDataset = new DefaultPieDataset();
        
        if (patient_count != 0) {
            NotSeen = (pat_NotSeen_Within_Rec_Time * 100) / patient_count;
            Seen = 100 - NotSeen;
        } else {
            NotSeen = 0;
            Seen = 0;
        }

        timeDataset.setValue("Patients Seen "+ Seen + "%", Seen);
        timeDataset.setValue("Patients Not Seen " + NotSeen + "%", NotSeen);
        JFreeChart timePieChart = ChartFactory.createPieChart("Percentage of Patients Seen Within Recommended Time", timeDataset, true, true, true);
        PiePlot plot = (PiePlot) timePieChart.getPlot();
        plot.setSectionPaint("Patients Seen", new Color(0, 123, 255)); 
        plot.setSectionPaint("Patients Not Seen", new Color(220, 220, 220)); 
        graphPanel.add(new ChartPanel(timePieChart));

     // Utilization of Different Bed Types vs Integer (Line Graph)
        XYSeries bedUtilizationSeries = new XYSeries("Bed Utilization");
        int bedUtilLength =bedUtilizations.size();
        for(int i =0;i<bedUtilLength;i++) {
        	bedUtilizationSeries.add(i*10,bedUtilizations.get(i));
        }

        XYSeriesCollection bedUtilizationDataset = new XYSeriesCollection(bedUtilizationSeries);
        JFreeChart bedUtilizationChart = ChartFactory.createXYLineChart(
            "Utilization of Different Bed Types vs Time", "Time", "Utilization (%)", bedUtilizationDataset, 
            PlotOrientation.VERTICAL, true, true, false);

        graphPanel.add(new ChartPanel(bedUtilizationChart));


     // Number of Patients Waiting vs Integer (Line Graph)
        XYSeries waitingPatientsSeries = new XYSeries("Patients Waiting");
        int queueSizeLength = queueSizes.size();
        for (int i = 0; i < queueSizeLength; i++) {
            waitingPatientsSeries.add(i * 10, queueSizes.get(i));
        }

        XYSeriesCollection waitingPatientsDataset = new XYSeriesCollection(waitingPatientsSeries);
        JFreeChart waitingPatientsChart = ChartFactory.createXYLineChart(
            "Number of Patients Waiting vs Time", "Time", "Count", waitingPatientsDataset, 
            PlotOrientation.VERTICAL, true, true, false);

        graphPanel.add(new ChartPanel(waitingPatientsChart));


        // Total Beds and Physicians (Bar Graph)
        DefaultCategoryDataset bedsPhysiciansDataset = new DefaultCategoryDataset();
        bedsPhysiciansDataset.addValue(RBeds+ABeds+SBeds+MBeds+OBeds+OChairs, "Total Beds", "Category");
        bedsPhysiciansDataset.addValue(juniorResidents+ seniorResidents+ interns+ consultants+ registrars, "Total Physicians", "Category");
        JFreeChart bedsPhysiciansChart = ChartFactory.createBarChart("Total Beds and Physicians", "Category", "Count", bedsPhysiciansDataset, PlotOrientation.VERTICAL, true, true, false);
        graphPanel.add(new ChartPanel(bedsPhysiciansChart));

        graphFrame.add(graphPanel, BorderLayout.CENTER);
        graphFrame.setSize(1200, 800);
        graphFrame.setVisible(true);
    }
}