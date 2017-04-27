import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class askingWhich extends JFrame implements ActionListener, ListSelectionListener{

	private static final long serialVersionUID = 1L;
	
	JTable options = null;
	int counter = 0;
	String answer = null;
	msgsModel msgsModelPage = null;
	appsModel appsModelPage = null;
	billsModel billsModelPage = null;
	ResultSet rs = null;
	Object [][] opt = new Object [100][3];

	public askingWhich(String question, String name, String surname, msgsModel msgsModelRef, appsModel appsModelRef, billsModel billsModelRef){
		
		msgsModelPage = msgsModelRef;
		appsModelPage = appsModelRef;
		billsModelPage = billsModelRef;
		
		String dbQuery = "SELECT * FROM `surgery`.`patients` WHERE (`Name` = '"+name+"' AND `Surname` = '"+surname+"');";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		storingOptions();
			
		this.setSize(500,300);                		// This is the frame, the numbers are the size of the windows 
		this.setTitle("Question");     				// Setting the title of the window
		this.setVisible(true);                		// We're saying to the window to show itself 

		this.setLayout(new GridLayout(3,0));
		
		JPanel first = new JPanel();
		this.add(first);
		
			JLabel questionLabel = new JLabel (question); // ASK KYLE
			first.add(questionLabel);
	
					
		String[] columnNames = {"ID","Name","Surname"};

		options = new JTable(opt, columnNames);
		options.getSelectionModel().addListSelectionListener(this);
		JScrollPane js=new JScrollPane(options);
		this.add(js);
			
		JPanel third = new JPanel();
		this.add(third);
		
			JButton cancel = new JButton("Cancel");
			third.add(cancel);
			cancel.addActionListener(this);
			cancel.setActionCommand("cancel");
			
		validate();
		repaint();
				
		
	}
	
	public void storingOptions(){
		
		try{
			
			int rowCounter = 0;
			
			while(rs.next()){
			    
				String id = rs.getString("ID_Pat");
		    	String name = rs.getString("Name");
		    	String surname = rs.getString("Surname");
		    	
		    	opt [rowCounter][0] = id;
		    	opt [rowCounter][1] = name;
		    	opt [rowCounter][2] = surname;
		    		
		    	rowCounter ++;
		    		
		    }
			
		}
		
		catch(SQLException ex) {
			
			// handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
			
		}
	    
		
	}


	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("cancel")){
			
			this.setVisible(false);
			
		}
		
	}

	public void valueChanged(ListSelectionEvent arg0) {
		
		counter ++;
		
		if (counter == 2){
			
			String id = (String) options.getValueAt(options.getSelectedRow(), 0);
			answer = id;
			this.setVisible(false);
			
			if (msgsModelPage != null){
				
				msgsModelPage.newEntry();
				
			}
			
			else if (appsModelPage != null){
				
				appsModelPage.newEntry();
				
				
			}
			
			else if (billsModelPage != null){
				
				billsModelPage.newEntry();
				
			}
			
			
			
			counter = 0;
			
		}
		
		
	}
	
	public String getAnswer(){
		
		return answer;
	}
	
}
