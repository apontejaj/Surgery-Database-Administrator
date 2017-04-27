import java.sql.ResultSet;
import java.sql.SQLException;

public class appsModel {
	
	user userLogged = null;
	ResultSet rs = null;
	Object[][] data = null;
	String dbQuery = "";
	appsView V = null;
	askingWhich aw = null;
	askingDoctor ad = null;
	
	public appsModel(user userRef, appsView viewRef){
		
		userLogged = userRef;
		V = viewRef;
		
	}
	
	public Object[][] queryForView(){
		
		if (userLogged.getType().equals("Receptionist")){
		
			dbQuery = "SELECT * FROM `surgery`.`appointments`;";
			
		}
		
		else {
	
			String ID_User = userLogged.getID_User();			
			dbQuery = "SELECT * FROM `surgery`.`appointments` where (`Doctor` = '"+ID_User+"');";
			
		}
	
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		storingMsgsData();
		
		return data;
		
	}
		
	public void storingMsgsData(){
		
		data = new Object[100][5];
		try{
			
			int rowCounter = 0;
			
			while(rs.next()){
			    
				gettingNames(rowCounter);
				String ID_App = rs.getString("ID_App");
				String date = rs.getString("Date");
		    	String time = rs.getString("Time");
		    	
		    	data [rowCounter][0] = ID_App;
		    	data [rowCounter][3] = date;
		    	data [rowCounter][4] = time;
		    		
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

	public void select(String id){
		
		dbQuery = "SELECT * FROM `surgery`.`appointments` INNER JOIN `surgery`.`patients` ON appointments.Patient = patients.ID_Pat WHERE ID_App = "+id+";";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		displaying();
		
		
	}
		
	public void displaying(){
		
		try{
			
			while(rs.next()){
	
				String name = rs.getString("Name");
				String surname = rs.getString("Surname");
				String doctor = rs.getString("Doctor");
				String date = rs.getString("Date");
				String time = rs.getString("Time");
		    	String symptoms = rs.getString("Symptoms");
		    	
		    	V.nameTF.setText(name);
		    	V.surnameTF.setText(surname);
		    	V.doctorTF.setText(doctor);
		    	V.dateTF.setText(date);
		    	V.timeTF.setText(time);
		    	V.symptomsTF.setText(symptoms);
		    			    	
		    }
			
		}
		
		catch(SQLException ex) {
			
			// handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
			
		}
		
	}

	public void lookingForDoctor(){
		
		String name = V.doctorTF.getText();
		ad = new askingDoctor(name, null, this);
		
	}
	
	public void lookingForPatient(){

		String name = V.nameTF.getText();
		String surname = V.surnameTF.getText();
		String question = "Which patient is making the appointment?";
		
		aw = new askingWhich(question,name, surname, null, this, null);
		
	}
	
	public void newEntry(){
		
		String patient = aw.getAnswer();
		String doctor = ad.getAnswer();
		
		
		String date = V.dateTF.getText();
		String time = V.timeTF.getText();
		
		dbQuery = "INSERT INTO `surgery`.`appointments` (`Patient`, `Doctor`, `Date`, `Time`) VALUES ('"+patient+"', '"+doctor+"', '"+date+"', '"+time+"');";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		V.drawingTable();

	}

	public void delete(String id){
		
		dbQuery = "DELETE FROM `surgery`.`appointments` WHERE  `ID_App`= "+id+";";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		V.drawingTable();
		
	}
	
	public void save(String id){
		
		String date = V.dateTF.getText();
		String time = V.timeTF.getText();
		String symptoms = V.symptomsTF.getText();
		
		if (userLogged.getType().equals("Doctor")){
			
			dbQuery = "UPDATE `surgery`.`appointments` SET `Symptoms`='"+symptoms+"' WHERE  `ID_App`="+id+";";
			
		}
		
		else {
			
			dbQuery = "UPDATE `surgery`.`appointments` SET `Date`='"+date+"', `Time`='"+time+"' WHERE  `ID_App`="+id+";";
			
		}
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		V.drawingTable();
				
	}
	
	
	
	
}
