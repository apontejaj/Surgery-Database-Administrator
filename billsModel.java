import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;

public class billsModel {
	
	user userLogged = null;
	ResultSet rs = null;
	Object[][] data = null;
	String dbQuery = null;
	billsView V = null;
	askingWhich aw = null;
	
	public billsModel(user userRef, billsView viewRef){
		
		userLogged = userRef;
		V = viewRef;
		
	}
	
	public Object[][] queryForView(){
		
		dbQuery = "SELECT * FROM `surgery`.`bills`;";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		storingMsgsData();
		
		return data;
		
	}
	
	public void gettingNames(int rowCounter){
				
		try{
			    
		    	String id_pat = rs.getString("Patient");
		    	
		    	String dbQueryNames = "SELECT * FROM `surgery`.`patients` where (`ID_Pat` = '"+id_pat+"');";
				
				database DBnames = new database ();
				ResultSet rsPats = (ResultSet) DBnames.accessDB(dbQueryNames);
				
				while(rsPats.next()){
				
			    	String patName = rsPats.getString("Name");
			    	String patSurname = rsPats.getString("Surname");
			    		
			    	data [rowCounter][1] = patName;
			    	data [rowCounter][2] = patSurname;
					
				}	
		    
		}
		
		catch(SQLException ex) {
			
			// handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
			
		}
		
	}
	
	public void storingMsgsData(){
		
		data = new Object[100][5];
		
		try{
			
			int rowCounter = 0;
			
			while(rs.next()){
			    
				gettingNames(rowCounter);
				String ID = rs.getString("ID_Bill");
		    	String date = rs.getString("Date");
		    	String status = rs.getString("Status");
		    	
		    	data [rowCounter][0] = ID;
		    	data [rowCounter][3] = date;
		    	data [rowCounter][4] = status;
		    		
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

	public void select(String id){
		
		dbQuery = "SELECT * FROM `surgery`.`bills` INNER JOIN `surgery`.`patients` ON bills.Patient = patients.ID_Pat WHERE ID_Bill = "+id+";";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		displaying();
		
	}
		
	public void displaying(){
		
		try{
			
			while(rs.next()){
	
				String name = rs.getString("Name");
				String surname = rs.getString("Surname");
				String date = rs.getString("Date");
		     	String status = rs.getString("Status");
		    	
		    	V.nameTF.setText(name);
		    	V.surnameTF.setText(surname);
		    	V.dateTF.setText(date);
		    			    	
		    	JComboBox statusTF = V.getStatus();
		    	statusTF.setSelectedItem(status);
		    	
		    }
			
		}
		
		catch(SQLException ex) {
			
			// handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
			
		}
		
	}

	public void lookingForPatient(){

		String name = V.nameTF.getText();
		String surname = V.surnameTF.getText();
		String question = "Which patient is the bill for?";
		
		aw = new askingWhich(question,name, surname, null, null, this);
		
	}
	
	public void newEntry(){
			
			String patient = aw.getAnswer();
			
			dbQuery = "INSERT INTO `surgery`.`bills` (`Patient`) VALUES ('"+patient+"');";
			
			database DB = new database ();
			rs = (ResultSet) DB.accessDB(dbQuery);
			
			V.drawingTable();
		
	}
	
	public void delete(String id){
		
		dbQuery = "DELETE FROM `surgery`.`bills` WHERE  `ID_Bill`= "+id+";";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		V.drawingTable();
		
		
	}
	
	public void save(String id){
		
		String status = (String) V.statusTF.getSelectedItem();
		
		dbQuery = "UPDATE `surgery`.`bills` SET `Status`='"+status+"' WHERE  `ID_Bill`="+id+";";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		V.drawingTable();
				
	}
	

	
}
