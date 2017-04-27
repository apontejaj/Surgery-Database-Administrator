import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class login extends JFrame {
	
	private static final long serialVersionUID = 1L;  
	
	JTextField username = null;
	JPasswordField password = null;
	loginController L = new loginController(this);
	
	public login() {
		
		this.setSize(250,500);                		// This is the frame, the numbers are the size of the windows 
		this.setTitle("Surgery Database");     		// Setting the title of the window
		this.setVisible(true);                		// We're saying to the window to show itself 
		
		JMenuBar bar = new JMenuBar();		  		// Creating a menu bar
		this.setJMenuBar(bar);                		// Sticking the menu bar to the window
		
			JMenu file = new JMenu("File");   			// Creating a menu tab
			bar.add(file);					 			// Sticking the menu tab to the menu bar
			
				JMenuItem close = new JMenuItem("Close");
				file.add(close);
				close.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						Component frame = null;
						int n = JOptionPane.showConfirmDialog(frame,
							    "Are you sure you want to close the program?",
							    "Cofirmation",
							    JOptionPane.YES_NO_OPTION);
						if (n == 0){
							System.exit(0);
						}
						 
					} 
					} );
		
		this.setLayout(new GridLayout(6,1));
		
			JPanel first = new JPanel();
			this.add(first);
			
				JLabel welcomeLabel = new JLabel ("WELCOME"); // ASK KYLE
				first.add(welcomeLabel);
			
			JPanel second = new JPanel();
			this.add(second);
				
				JLabel usernameLabel = new JLabel ("username");
				second.add(usernameLabel);
				
			JPanel third = new JPanel();
			this.add(third);
			
				username = new JTextField(15);
				third.add(username);
			
			JPanel fourth = new JPanel();
			this.add(fourth);
					
				JLabel passwordLabel = new JLabel ("password");
				fourth.add(passwordLabel);
			
			JPanel fifth = new JPanel();
			this.add(fifth);
				
				password = new JPasswordField(15);
				fifth.add(password);
				
			JPanel sixth = new JPanel();
			this.add(sixth);
			
				JButton login = new JButton("Login!");
				sixth.add(login);
				login.addActionListener(L);
				login.setActionCommand("login");
				
		validate();
		repaint();
	}
	
	public String getUsernameFromBox() {
		
		String nameString = username.getText();
		
		return nameString;
	}
	
	public String getPasswordFromBox() {
		
		@SuppressWarnings("deprecation")  //ASK KYLE
		String passwordString = password.getText();
		
		return passwordString;
	}
	
	public void fail(){
		
		JOptionPane.showMessageDialog(this, "Please insert id and password");
	}

	public void failTwo(){
		
		JOptionPane.showMessageDialog(this, "Please insert a valid id and password");
	}
	
	public void failThree(){
		
		JOptionPane.showMessageDialog(this, "You have been locked out. Contact administrator");
	}

	
		
}


