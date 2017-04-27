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

public class patView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	
	boolean createNewButtonFlag = false;
	boolean statusFlag = true;
	user userLogged = null;
	patController L = null;
	patModel M = null;
	login loginPage = null;
	homeView homePage = null;
	JTable pats = null;
	JTextField nameTF = null;
	JTextField surnameTF = null;
	JTextArea addressTF = null;
	JTextField numberTF = null;
	JPanel left = null;
	
	public patView(user userRef, login loginRef, homeView homeRef){
		
		this.setSize(1000,600);                		// This is the frame, the numbers are the size of the windows 
		this.setTitle("Patients");     				// Setting the title of the window
		this.setVisible(true);                		// We're saying to the window to show itself 
		userLogged = userRef;
		loginPage = loginRef;
		homePage = homeRef;
				
		M = new patModel(userLogged, this);
		L = new patController(M, this, loginPage, homePage);
		

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
		JLabel addressLabel = new JLabel("Address");
		addressTF = new JTextArea(10,10);
		JLabel numberLabel = new JLabel("Phone Number");
		numberTF = new JTextField(10);
		
						
		JButton createNew = new JButton("Create New");
		createNew.setVisible(createNewButtonFlag);
		createNew.addActionListener(L);
		createNew.setActionCommand("createNew");
		
		JButton save = new JButton("Save");
		save.addActionListener(L);
		save.setActionCommand("save");
		
		JButton delete = new JButton("Delete");
		delete.addActionListener(L);
		delete.setActionCommand("delete");
		
		GroupLayout layout = new GroupLayout(right);
		right.setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				
			layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameLabel)
						.addComponent(surnameLabel)
						.addComponent(addressLabel)
						.addComponent(numberLabel))
				
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameTF)
						.addComponent(surnameTF)
						.addComponent(addressTF)
						.addComponent(numberTF)
						.addGroup(layout.createSequentialGroup()
								.addComponent(createNew)
								.addComponent(save)
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
						.addComponent(addressLabel)
						.addComponent(addressTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(numberLabel)
						.addComponent(numberTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						
						.addComponent(createNew)
						.addComponent(save)
						.addComponent(delete))
		
		);
		
		
		validate();
		repaint();
		
		
	}
	
	public JTable getTable() {
	      return pats;
	   }
	
	public void drawingTable(){
		
		left.removeAll();
		
		String[] columnNames = {"ID", "Name","Surname","Address","Phone Number"};

		Object [][] data = M.queryForView();
		
		pats = new JTable(data, columnNames);
		pats.getSelectionModel().addListSelectionListener(L);
		JScrollPane js=new JScrollPane(pats);
		left.add(js);
		
		this.validate();
		this.repaint();
		
	}
	

}
