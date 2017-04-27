import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class prescription extends JFrame implements ActionListener, ListSelectionListener {
	
	private static final long serialVersionUID = 1L;
	
	JTable options = null;
	int counter = 0;
	ResultSet rs = null;
	Object [][] opt = null;
	String appID = null;
	String dbQuery = null;
	String patient = null;
	JPanel first = null;
	JTextField medTF = null;
	int catA = 0;
	int catB = 0;
	int catC = 0;
	
	public prescription(String id){
				
		appID = id;
		gettingPatient();

		this.setSize(500,600);                		// This is the frame, the numbers are the size of the windows 
		this.setTitle("Presciptions");     				// Setting the title of the window
		this.setVisible(true);                		// We're saying to the window to show itself 

		this.setLayout(new GridLayout(3,0));
		
		first = new JPanel();
		this.add(first);
		
		drawingTable();
		
		JPanel second = new JPanel();
		this.add(second);
		
			JLabel medLabel = new JLabel("Medication");
			second.add(medLabel);
			
			medTF = new JTextField(15);
			second.add(medTF);
							
		JPanel third = new JPanel();
		this.add(third);
		
			JButton prescribe = new JButton("Prescribe");
			third.add(prescribe);
			prescribe.addActionListener(this);
			prescribe.setActionCommand("prescribe");
			
			JButton delete = new JButton("Delete");
			third.add(delete);
			delete.addActionListener(this);
			delete.setActionCommand("delete");
			
			JButton save = new JButton("Save");
			third.add(save);
			save.addActionListener(this);
			save.setActionCommand("save");
			
		validate();
		repaint();

	}
	
	public void storingOptions(){
		
		opt = new Object [100][3];
		
		try{
			
			int rowCounter = 0;
			
			while(rs.next()){
			    
				String id = rs.getString("ID_Presc");
		    	String date = rs.getString("Date");
		    	String medication = rs.getString("Medication");
		    	
		    	opt [rowCounter][0] = id;
		    	opt [rowCounter][1] = date;
		    	opt [rowCounter][2] = medication;
		    	
		    	if (medication.equals("VND1") || medication.equals("XXV2") || medication.equals("HNF232") || medication.equals("GB334")){
		    		
		    		catA++;
		    				    		
		    	}
		    	
		    	else if (medication.equals("X34") || medication.equals("HH5") || medication.equals("DDF23") || medication.equals("JHH7")){
		    		
		    		catB++;
		    		
		    	}
		    	
		    	else if (medication.equals("543H") || medication.equals("344BB") || medication.equals("JUY9") || medication.equals("232B")){
		    		
		    		catC++;
		    		
		    	}

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
	
	public void alert(){
		
		if (catA >= 2){
			
			JOptionPane.showMessageDialog(this, "Be careful as two or more medicines from GROUP A have been prescribed to this patient");
			
		}
		if (catB >= 2){
			
			JOptionPane.showMessageDialog(this, "Be careful as two or more medicines from GROUP B have been prescribed to this patient");
			
		}
		if (catC >= 2){
			
			JOptionPane.showMessageDialog(this, "Be careful as two or more medicines from GROUP C have been prescribed to this patient");
			
		}
		catA = 0;
		catB = 0;
		catC = 0;
		
	}
	
	public void gettingPatient(){
		
		dbQuery = "SELECT * FROM `surgery`.`prescriptions` INNER JOIN `surgery`.`appointments` ON prescriptions.Consultation = appointments.ID_App WHERE ID_App = "+appID+";";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		try{
			
			while(rs.next()){
			    
				patient = rs.getString("Patient");
		    		
		    }
			
		}
		
		catch(SQLException ex) {
			
			// handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
			
		}
	    		
	}

	public void gettingMedication(){
		
		dbQuery = "SELECT * FROM `surgery`.`prescriptions` INNER JOIN `surgery`.`appointments` ON prescriptions.Consultation = appointments.ID_App INNER JOIN `surgery`.`patients` ON appointments.Patient = patients.ID_Pat WHERE ID_Pat = "+patient+";";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		storingOptions();
				
	}

	public void drawingTable(){
		
		first.removeAll();
				
		gettingMedication();
		
		String[] columnNames = {"ID","Date","Medication"};
	
		options = new JTable(opt, columnNames);
		options.getSelectionModel().addListSelectionListener(this);
		JScrollPane js = new JScrollPane(options);
		first.add(js);
		
		alert();
		this.validate();
		this.repaint();
				
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("prescribe")){
			
			String med = medTF.getText();
			
			dbQuery = "INSERT INTO `surgery`.`prescriptions` (`Consultation`, `Medication`) VALUES ('"+appID+"', '"+med+"');";
			
			database DB = new database ();
			rs = (ResultSet) DB.accessDB(dbQuery);
			
			drawingTable();
						
		}
		
		else if (e.getActionCommand().equals("delete")){
			
			String id = (String) options.getValueAt(options.getSelectedRow(), 0);
			
			dbQuery = "DELETE FROM `surgery`.`prescriptions` WHERE  `ID_Presc`= "+id+";";
			
			database DB = new database ();
			rs = (ResultSet) DB.accessDB(dbQuery);
			
			drawingTable();
			
		}
		
		else if (e.getActionCommand().equals("save")){
			
			String id = (String) options.getValueAt(options.getSelectedRow(), 0);
			String med = medTF.getText();
			
			
			dbQuery = "UPDATE `surgery`.`prescriptions` SET `Medication` = '"+med+"' WHERE  `ID_Presc`="+id+";";
			
			database DB = new database ();
			rs = (ResultSet) DB.accessDB(dbQuery);
			
			drawingTable();
				
		}
	
	}

	public void valueChanged(ListSelectionEvent arg0) {
		
		counter ++;
		
		if (counter == 2){
			
			String med = (String) options.getValueAt(options.getSelectedRow(), 2);
			medTF.setText(med);
			
			counter = 0;
			
		}
		
	}
	


}
