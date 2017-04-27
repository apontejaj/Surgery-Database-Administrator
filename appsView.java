import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class appsView extends JFrame {
	
	private static final long serialVersionUID = 1L;
		
	boolean createNewButtonFlag = false;
	boolean deleteFlag = false;
	boolean printPrescriptionFlag = true;
	boolean symptomsFlag = true;
	user userLogged = null;
	appsController L = null;
	appsModel M = null;
	login loginPage = null;
	homeView homePage = null;
	JTable apps = null;
	JTextField nameTF = null;
	JTextField surnameTF = null;
	JTextField doctorTF = null;
	JTextField dateTF = null;
	JTextField timeTF = null;
	JTextArea symptomsTF = null;
	JPanel left = null;
		
	public appsView(user userRef, login loginRef, homeView homeRef){
		
		this.setSize(1000,600);                		// This is the frame, the numbers are the size of the windows 
		this.setTitle("Appointments");     				// Setting the title of the window
		this.setVisible(true);                		// We're saying to the window to show itself 
		userLogged = userRef;
		
		M = new appsModel(userLogged, this);
		loginPage = loginRef;
		homePage = homeRef;
		L = new appsController(M, this, loginPage, homePage);
		
		JMenuBar bar = new JMenuBar();		  		// Creating a menu bar
		this.setJMenuBar(bar);   
			
			JMenu file = new JMenu("File");   			// Creating a menu tab
			bar.add(file);					 			// Sticking the menu tab to the menu bar
			
				JMenuItem logout = new JMenuItem("Logout");
				file.add(logout);
				logout.addActionListener(L);
				logout.setActionCommand("logout");
		
				JMenuItem backHome = new JMenuItem("Back to home");
				file.add(backHome);
				backHome.addActionListener(L);
				backHome.setActionCommand("backHome");

		
		if (userLogged.getType().equals("Receptionist")){
			
			createNewButtonFlag = true;
			deleteFlag = true;
			printPrescriptionFlag = false;
			symptomsFlag = false;
		}
	
		this.setLayout(new GridLayout(0,2));
		
		
		left = new JPanel();
		this.add(left);
		
		drawingTable();
				
		JPanel right = new JPanel();
		this.add(right);
		
		JLabel nameLabel = new JLabel("Name");
		nameTF = new JTextField(10);
		JLabel surnameLabel = new JLabel("Surname");
		surnameTF = new JTextField(10);
		JLabel doctorLabel = new JLabel("Doctor");
		doctorTF = new JTextField(10);
		JLabel dateLabel = new JLabel("Date");
		dateTF = new JTextField(10);
		JLabel timeLabel = new JLabel("Time");
		timeTF = new JTextField(10);
		
		JLabel symptomsLabel = new JLabel("Symptoms");
		symptomsLabel.setVisible(symptomsFlag);
		
		symptomsTF = new JTextArea(10,10);
		symptomsTF.setVisible(symptomsFlag);
								
		JButton createNew = new JButton("Create New");
		createNew.setVisible(createNewButtonFlag);
		createNew.addActionListener(L);
		createNew.setActionCommand("createNew");
		
		JButton save = new JButton("Save");
		save.addActionListener(L);
		save.setActionCommand("save");
		
		JButton delete = new JButton("Delete");
		delete.setVisible(deleteFlag);;
		delete.addActionListener(L);
		delete.setActionCommand("delete");
		
		JButton prescription = new JButton("Prescription");
		prescription.setVisible(printPrescriptionFlag);
		prescription.addActionListener(L);
		prescription.setActionCommand("prescription");
		
		GroupLayout layout = new GroupLayout(right);
		right.setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameLabel)
						.addComponent(surnameLabel)
						.addComponent(doctorLabel)
						.addComponent(dateLabel)
						.addComponent(timeLabel)
						.addComponent(symptomsLabel))
				
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameTF)
						.addComponent(surnameTF)
						.addComponent(doctorTF)
						.addComponent(dateTF)
						.addComponent(timeTF)
						.addComponent(symptomsTF)
						.addGroup(layout.createSequentialGroup()
								.addComponent(createNew)
								.addComponent(save)
								.addComponent(delete)
								.addComponent(prescription)))
		);
			
		layout.setVerticalGroup(
		
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(nameLabel)
						.addComponent(nameTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(surnameLabel)
						.addComponent(surnameTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(doctorLabel)
						.addComponent(doctorTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(dateLabel)
						.addComponent(dateTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(timeLabel)
						.addComponent(timeTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(symptomsLabel)
						.addComponent(symptomsTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						
						.addComponent(createNew)
						.addComponent(save)
						.addComponent(delete)
						.addComponent(prescription))
		
		);
		
		
		validate();
		repaint();
		
		
	}
	
	public JTable getTable() {
	      
		return apps;
	
	}

	public void drawingTable(){
		
		left.removeAll();
		apps = null;
		
		String[] columnNames = {"ID","Name","Surname","Date","Time"};

		Object [][] data = M.queryForView();
		
		apps = new JTable(data, columnNames);
		apps.getSelectionModel().addListSelectionListener(L);
		JScrollPane js=new JScrollPane(apps);
		left.add(js);
		
		this.validate();
		this.repaint();
		
	}
	
}
