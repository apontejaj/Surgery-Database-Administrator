import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class homeView extends JFrame {

	private static final long serialVersionUID = 1L;
	
	boolean msgsFlag = false;
	boolean appsFlag = false;
	boolean billsFlag = false;
	boolean patsFlag = false;
	user userLogged = null;
	login loginPage = null;
	homeController L = null;
	
	public homeView(user userRef, login loginRef){
		
		this.setSize(250,500);                		// This is the frame, the numbers are the size of the windows 
		this.setTitle("Home");     				// Setting the title of the window
		this.setVisible(true);                		// We're saying to the window to show itself 
		userLogged = userRef;
		loginPage = loginRef;
		L = new homeController(loginPage, this, userLogged);
		
		JMenuBar bar = new JMenuBar();		  		// Creating a menu bar
		this.setJMenuBar(bar);   
			
			JMenu file = new JMenu("File");   			// Creating a menu tab
			bar.add(file);					 			// Sticking the menu tab to the menu bar
			
				JMenuItem logout = new JMenuItem("logout");
				file.add(logout);
				logout.addActionListener(L);
				logout.setActionCommand("logout");
				
				
		if (userLogged.getType().equals("Doctor")){
			
			msgsFlag = true;
			appsFlag = true;
					
		}
		
		else if (userLogged.getType().equals("Receptionist")){
			
			msgsFlag = true;
			billsFlag = true;
			patsFlag = true;
			appsFlag = true;			
			
		}
		
		else if (userLogged.getType().equals("Billing")){
			
			billsFlag = true;
			
		}
		
		this.setLayout(new GridLayout(2,0));
		
		
			JPanel first = new JPanel();
			this.add(first);	
			
				JLabel name = new JLabel("Hello " + userLogged.getName());
				first.add(name);
				
				JLabel question = new JLabel("What do you want to do?"); //Check this with Kyle!!
				first.add(question);
				
			JPanel second = new JPanel();
			this.add(second);
			
				JButton msgs = new JButton("Manage Messages");
				second.add(msgs);
				msgs.addActionListener(L);
				msgs.setActionCommand("msgs");
				msgs.setVisible(msgsFlag);
				
				JButton apps = new JButton("Manage Appointments");
				second.add(apps);
				apps.addActionListener(L);
				apps.setActionCommand("apps");
				apps.setVisible(appsFlag);
				
				JButton bills = new JButton("Manage Bills");
				second.add(bills);
				bills.addActionListener(L);
				bills.setActionCommand("bills");
				bills.setVisible(billsFlag);
				
				JButton pats = new JButton("Manage Patients");
				second.add(pats);
				pats.addActionListener(L);
				pats.setActionCommand("pats");
				pats.setVisible(patsFlag);
	
				
		validate();
		repaint();
			
	}
	
	

	
	
}
