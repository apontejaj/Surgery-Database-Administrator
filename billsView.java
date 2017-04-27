import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class billsView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	
	boolean statusFlag = true;
	user userLogged = null;
	billsController L = null;
	billsModel M = null;
	login loginPage = null;
	homeView homePage = null;
	JTable bills = null;
	JTextField nameTF = null;
	JTextField surnameTF = null;
	JTextField dateTF = null;
	JComboBox statusTF = null;
	JPanel left = null;
	
	
	public billsView(user userRef, login loginRef, homeView homeRef){
		
		this.setSize(1000,600);                		// This is the frame, the numbers are the size of the windows 
		this.setTitle("Bills");     				// Setting the title of the window
		this.setVisible(true);                		// We're saying to the window to show itself 
		userLogged = userRef;
		
		M = new billsModel(userLogged, this);
		loginPage = loginRef;
		homePage = homeRef;
		L = new billsController(M, this, loginPage, homePage);
		
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
			
			statusFlag = false;
			
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
		JLabel dateLabel = new JLabel("Date");
		dateTF = new JTextField(10);
		
		JLabel statusLabel = new JLabel("Status");
		statusLabel.setVisible(statusFlag);
		
		String[] statusStrings = { "Paid", "Unpaid" };
		statusTF = new JComboBox(statusStrings);
		statusTF.setVisible(statusFlag);
						
		JButton createNew = new JButton("Create New");
		createNew.addActionListener(L);
		createNew.setActionCommand("createNew");
		
		JButton save = new JButton("Save");
		save.addActionListener(L);
		save.setVisible(statusFlag);
		save.setActionCommand("save");
		
		JButton delete = new JButton("Delete");
		delete.addActionListener(L);
		delete.setActionCommand("delete");
		
		JButton detail = new JButton("See details");
		detail.addActionListener(L);
		detail.setActionCommand("detail");
		
		GroupLayout layout = new GroupLayout(right);
		right.setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameLabel)
						.addComponent(surnameLabel)
						.addComponent(dateLabel)
						.addComponent(statusLabel))
				
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameTF)
						.addComponent(surnameTF)
						.addComponent(dateTF)
						.addComponent(statusTF)
						.addGroup(layout.createSequentialGroup()
								.addComponent(createNew)
								.addComponent(save)
								.addComponent(detail)
								.addComponent(delete)))
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
						.addComponent(dateLabel)
						.addComponent(dateTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(statusLabel)
						.addComponent(statusTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(createNew)
						.addComponent(save)
						.addComponent(detail)
						.addComponent(delete))
		
		);
				
		validate();
		repaint();
		
	}
	
	public JTable getTable() {
	      return bills;
	   }

	public JComboBox getStatus(){
		return statusTF;
	
		
	}

	public void drawingTable(){
	
		left.removeAll();
		
		String[] columnNames = {"ID","Name","Surname","Date","Status"};

		Object [][] data = M.queryForView();
		
		bills = new JTable(data, columnNames);
		bills.getSelectionModel().addListSelectionListener(L);
		JScrollPane js=new JScrollPane(bills);
		left.add(js);
		
		this.validate();
		this.repaint();
		
	}

}
