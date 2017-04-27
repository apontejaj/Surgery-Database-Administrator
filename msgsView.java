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
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class msgsView extends JFrame {
	
	private static final long serialVersionUID = 1L;
		
	boolean createNewButtonFlag = false;
	boolean statusFlag = true;
	boolean toFlag = false;
	user userLogged = null;
	msgsController L = null;
	msgsModel M = null;
	homeView homePage = null;
	login loginPage = null;
	JTable msgs = null;
	JTextField nameTF = null;
	JTextField surnameTF = null;
	JTextField dateTimeTF = null;
	JTextField toTF = null;
	JTextArea msgTF = null;
	JTextField retTF = null;
	JComboBox statusTF = null;
	JPanel left = null;
	
	public msgsView(user userRef, login loginRef, homeView homeRef){
		
		this.setSize(1000,600);                		// This is the frame, the numbers are the size of the windows 
		this.setTitle("Messages");     				// Setting the title of the window
		this.setVisible(true);                		// We're saying to the window to show itself 
		userLogged = userRef;
		
		M = new msgsModel(userLogged, this);
		loginPage = loginRef;
		homePage = homeRef;
		L = new msgsController(M, this, loginPage, homePage);
		
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
			toFlag = true;
			
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
		JLabel dateTimeLabel = new JLabel("Date/Time");
		dateTimeTF = new JTextField();
				
		JLabel toLabel = new JLabel("To");
		toLabel.setVisible(toFlag);
		toTF = new JTextField(10);
		toTF.setVisible(toFlag);
		
		JLabel msgLabel = new JLabel("Message");
		msgTF = new JTextArea(10,10);
	
		
		JLabel retLabel = new JLabel("Return Number");
		retTF = new JTextField(10);
		
		JLabel statusLabel = new JLabel("Status");
		statusLabel.setVisible(statusFlag);
		
		String[] statusStrings = { "Read", "Unread" };
		statusTF = new JComboBox(statusStrings);
		statusTF.setVisible(statusFlag);
		
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
						.addComponent(dateTimeLabel)
						.addComponent(toLabel)
						.addComponent(msgLabel)
						.addComponent(retLabel)
						.addComponent(statusLabel))
				
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(nameTF)
						.addComponent(surnameTF)
						.addComponent(dateTimeTF)
						.addComponent(toTF)
						.addComponent(msgTF)
						.addComponent(retTF)
						.addComponent(statusTF)
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
						.addComponent(dateTimeLabel)
						.addComponent(dateTimeTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(toLabel)
						.addComponent(toTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(msgLabel)
						.addComponent(msgTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(retLabel)
						.addComponent(retTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(statusLabel)
						.addComponent(statusTF))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						
						.addComponent(createNew)
						.addComponent(save)
						.addComponent(delete))
		
		);
		
		
		validate();
		repaint();
		
	}
	
	public JTable getTable() {
	      return msgs;
	   }
	
	public JComboBox getStatus(){
	
		return statusTF;
			
	}
	
	public void drawingTable(){
		
		left.removeAll();
		
		String[] columnNames = {"ID","Name","Surname","Date/Time","Status"};

		Object [][] data = M.queryForView();
		
		msgs = new JTable(data, columnNames);
		msgs.getSelectionModel().addListSelectionListener(L);
		JScrollPane js=new JScrollPane(msgs);
		left.add(js);
		
		left.validate();
		left.repaint();
		
	}
	
}
