import java.sql.SQLException;

import javax.swing.JComboBox;

import java.sql.ResultSet;

public class msgsModel {
	
	user userLogged = null;
	ResultSet rs = null;
	Object[][] data = null;
	Object [][] options = new Object[100][3];
	String dbQuery = "";
	msgsView V = null;
	askingWhich aw = null;
	askingDoctor ad = null;
	
	public msgsModel(user userRef, msgsView viewRef){
		
		userLogged = userRef;
		V = viewRef;
		
	}
	
	public Object[][] queryForView(){
		
		
		if (userLogged.getType().equals("Receptionist")){
			
			dbQuery = "SELECT * FROM `surgery`.`messages`;";
			
		}
		
		else{
		
			String ID_User = userLogged.getID_User();
			dbQuery = "SELECT * FROM `surgery`.`messages` where (`Destination` = '"+ID_User+"');";
			
		}
		
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
				String ID_Msg = rs.getString("ID_Msg");
		    	String dateTime = rs.getString("Date/Time");
		    	String status = rs.getString("Status");
		    	
		    	data [rowCounter][0] = ID_Msg;
		    	data [rowCounter][3] = dateTime;
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
		
		dbQuery = "SELECT * FROM `surgery`.`messages` INNER JOIN `surgery`.`patients` ON messages.Patient = patients.ID_Pat WHERE ID_Msg = "+id+";";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		displaying();
		
	}
		
	public void displaying(){
		
		try{
			
			while(rs.next()){
	
				String name = rs.getString("Name");
				String surname = rs.getString("Surname");
				String dateTime = rs.getString("Date/Time");
		    	String to = rs.getString("Destination");
		    	String msg = rs.getString("Message");
		    	String ret = rs.getString("Return_Number");
		    	String status = rs.getString("Status");
		    	
		    	V.nameTF.setText(name);
		    	V.surnameTF.setText(surname);
		    	V.dateTimeTF.setText(dateTime);
		    	V.toTF.setText(to);
		    	V.msgTF.setText(msg);
		    	V.retTF.setText(ret);
		    	
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
		String question = "Which patient is leaving the message?";
		
		aw = new askingWhich(question,name, surname, this, null, null);
		
	}
			
	public void lookingForDoctor(){
		
		String name = V.toTF.getText();
		ad = new askingDoctor(name, this, null);
		
	}
		
	public void newEntry(){
		
		String patient = aw.getAnswer();
		String doctor = ad.getAnswer();
		
		
		String ret = V.retTF.getText();
		String msg = V.msgTF.getText();
		
		dbQuery = "INSERT INTO `surgery`.`messages` (`Patient`, `Destination`, `Message`, `Return_Number`) VALUES ('"+patient+"', '"+doctor+"', '"+msg+"', '"+ret+"');";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		V.drawingTable();
	}
	
	public void delete(String id){
		
		dbQuery = "DELETE FROM `surgery`.`messages` WHERE  `ID_Msg`= "+id+";";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		V.drawingTable();
		
	}
	
	public void save(String id){
		
		String msg = V.msgTF.getText();
		String status = (String) V.statusTF.getSelectedItem();
		
		if (userLogged.getType().equals("Doctor")){
			
			dbQuery = "UPDATE `surgery`.`messages` SET `Status`='"+status+"' WHERE  `ID_Msg`="+id+";";
			
		}
		
		else {
			
			dbQuery = "UPDATE `surgery`.`messages` SET `Message`='"+msg+"', `Status`='Unread' WHERE  `ID_Msg`="+id+";";
			
		}
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		V.drawingTable();
				
	}
	
}
