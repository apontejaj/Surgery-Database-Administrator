import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class detail extends JFrame implements ActionListener, ListSelectionListener {
	
	private static final long serialVersionUID = 1L;
	
	JTable options = null;
	int counter = 0;
	ResultSet rs = null;
	Object [][] opt = null;
	String billID = null;
	String dbQuery = null;
	String patient = null;
	JPanel first = null;
	JPanel third = null;
	JComboBox conceptTF = null;
	String total = null;
	JLabel totalLabel = null;
	
	public detail (String id){
				
		billID = id;
		
		this.setSize(500,600);                		// This is the frame, the numbers are the size of the windows 
		this.setTitle("Detail");     				// Setting the title of the window
		this.setVisible(true);                		// We're saying to the window to show itself 

		this.setLayout(new GridLayout(3,0));
		
		first = new JPanel();
		this.add(first);
		
		drawingTable();
		
		JPanel second = new JPanel();
		this.add(second);
		
			JLabel medLabel = new JLabel("Description");
			second.add(medLabel);
			
			String[] conceptStrings = { "Consultation", "Blood Test", "Repeat Prescription", "Vaccination" };
			conceptTF = new JComboBox(conceptStrings);
			second.add(conceptTF);
							
		third = new JPanel();
		this.add(third);
		
			JButton add = new JButton("Add to bill");
			third.add(add);
			add.addActionListener(this);
			add.setActionCommand("add");
			
			JButton remove = new JButton("Remove from bill");
			third.add(remove);
			remove.addActionListener(this);
			remove.setActionCommand("remove");
			
			gettingTotal();
			
		validate();
		repaint();

	}
	
	private void gettingTotal() {
		
		dbQuery = "SELECT SUM(Price) FROM (`surgery`.`bills` INNER JOIN `surgery`.`details` ON ID_Bill = Bill) where ID_Bill = "+billID+" ;";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		try{
			
			while(rs.next()){
			    
				total = rs.getString("SUM(Price)");
					
			}
		}	
		catch(SQLException ex) {
			
			// handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
			
		}
		
		totalLabel = new JLabel("Total: " + total);
		third.add(totalLabel);
		
		validate();
		repaint();
		
	}

	public void storingOptions(){
		
		opt = new Object [100][4];
		
		try{
			
			int rowCounter = 0;
			
			while(rs.next()){
			    
				String id = rs.getString("ID_det");
				String bill = rs.getString("Bill");
		    	String concept = rs.getString("concept");
		    	String price = rs.getString("Price");
		    	
		    	opt [rowCounter][0] = id;
		    	opt [rowCounter][1] = bill;
		    	opt [rowCounter][2] = concept;
		    	opt [rowCounter][3] = price;
		    		
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
	
	public void gettingDetails(){
		
		dbQuery = "SELECT * FROM `surgery`.`details` WHERE `Bill` = "+billID+";";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		storingOptions();
				
	}

	public void drawingTable(){
		
		first.removeAll();
				
		gettingDetails();
		
		String[] columnNames = {"ID","Bill","Concept","Price"};
	
		options = new JTable(opt, columnNames);
		options.getSelectionModel().addListSelectionListener(this);
		JScrollPane js = new JScrollPane(options);
		first.add(js);
		
		this.validate();
		this.repaint();
				
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("add")){
			
			String concept = (String) conceptTF.getSelectedItem();
			String price = "";
			
			if (concept.equals("Consultation")){
				
				price = "50";
				
			}
			
			else if (concept.equals("Blood Test")){
				
				price = "20";
				
			}
			
			else if (concept.equals("Repeat Prescription")){
				
				price = "15";
				
			}
			
			else if (concept.equals("Vaccination")){
				
				price = "10";
				
			}
			
			dbQuery = "INSERT INTO `surgery`.`details` (`Bill`, `Concept`, `Price`) VALUES ('"+billID+"', '"+concept+"', '"+price+"');";
			
			database DB = new database ();
			rs = (ResultSet) DB.accessDB(dbQuery);
			
			drawingTable();
			third.remove(totalLabel);
			gettingTotal();
						
		}
		
		else if (e.getActionCommand().equals("remove")){
			
			String id = (String) options.getValueAt(options.getSelectedRow(), 0);
			
			dbQuery = "DELETE FROM `surgery`.`details` WHERE  `ID_det`= "+id+";";
			
			database DB = new database ();
			rs = (ResultSet) DB.accessDB(dbQuery);
			
			drawingTable();
			third.remove(totalLabel);
			gettingTotal();
			
		}
			
	}

	public void valueChanged(ListSelectionEvent arg0) {
		
		counter ++;
		
		if (counter == 2){
			
			String det = (String) options.getValueAt(options.getSelectedRow(), 2);
			conceptTF.setSelectedItem(det);;
			
			counter = 0;
			
		}
		
	}
		
}
