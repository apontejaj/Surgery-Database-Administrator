import java.sql.ResultSet;
import java.sql.SQLException;

public class patModel {
	
	user userLogged = null;
	ResultSet rs = null;
	Object[][] data = null;
	patView V = null;
	String dbQuery = null;
	
	public patModel(user userRef, patView viewRef){
		
		userLogged = userRef;
		V = viewRef;
		
	}
	
	public Object[][] queryForView(){
		
		dbQuery = "SELECT * FROM `surgery`.`patients`;";
		
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
			    
				String ID = rs.getString("ID_Pat");
				String name = rs.getString("Name");
		    	String surname = rs.getString("Surname");
		    	String address = rs.getString("address");
		    	String number = rs.getString("Tel_Number");
		    		
		    	data [rowCounter][0] = ID;
		    	data [rowCounter][1] = name;
		    	data [rowCounter][2] = surname;
		    	data [rowCounter][3] = address;
		    	data [rowCounter][4] = number;
		    		
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

		dbQuery = "SELECT * FROM `surgery`.`patients` WHERE ID_Pat = "+id+";";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		displaying();
		
	}
		
	public void displaying(){
		
		try{
			
			while(rs.next()){
	
				String name = rs.getString("Name");
				String surname = rs.getString("Surname");
				String address = rs.getString("Address");
		    	String number = rs.getString("Tel_Number");
		    	
		    	V.nameTF.setText(name);
		    	V.surnameTF.setText(surname);
		    	V.addressTF.setText(address);
		    	V.numberTF.setText(number);
		    			    	
		    }
			
		}
		
		catch(SQLException ex) {
			
			// handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
			
		}
		
	}

	public void newEntry(){
		
		String name = V.nameTF.getText();
		String surname = V.surnameTF.getText();
		String address = V.addressTF.getText();
		String number = V.numberTF.getText();
		
		dbQuery = "INSERT INTO `surgery`.`patients` (`Name`, `Surname`, `Address`, `Tel_Number`) VALUES ('"+name+"', '"+surname+"', '"+address+"', '"+number+"');";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		V.drawingTable();
	}

	public void delete(String id){
		
		dbQuery = "DELETE FROM `surgery`.`patients` WHERE  `ID_Pat`= "+id+";";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		V.drawingTable();
		
	}
	
	public void save(String id){
		
		String name = V.nameTF.getText();
		String surname = V.surnameTF.getText();
		String address = V.addressTF.getText();
		String number = V.numberTF.getText();
					
		dbQuery = "UPDATE `surgery`.`patients` SET `Name`='"+name+"', `Surname`='"+surname+"', `Address`='"+address+"', `Tel_Number`='"+number+"' WHERE  `ID_Pat`="+id+";";
		
		database DB = new database ();
		rs = (ResultSet) DB.accessDB(dbQuery);
		
		V.drawingTable();
				
	}
}
