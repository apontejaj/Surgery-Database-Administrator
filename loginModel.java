import java.sql.ResultSet;
import java.sql.SQLException;

public class loginModel {

	user userLogged = new user();
	ResultSet rs = null;
	login loginPage = null;
	String username = null;
	String password = null;
	
	public loginModel(login ref){
		
		loginPage = ref;
		
	}
	
	public boolean validation(){
		
		username = loginPage.getUsernameFromBox();
		password = loginPage.getPasswordFromBox();
		boolean answer = true;
		
		if (username.isEmpty() || password.isEmpty()){
						
			loginPage.fail();
			answer = false;		
		}
		
		return answer;
		
	}
	
	public ResultSet databaseQuery(){
		
		/*
		 * This method is going to generate the data base query and getting the result back
		*/
		username = loginPage.getUsernameFromBox();
		userLogged.setUsername(username);
		String password = loginPage.getPasswordFromBox();
		userLogged.setPassword(password);
		
		
		String dbQuery = "SELECT * FROM `surgery`.`users` where (`Username` = '"+username+"' and `Password` = '"+password+"');";
		
		database DB = new database ();
		return rs = (ResultSet) DB.accessDB(dbQuery);
		
		
	}

	public void storingInObject(){
		
		/*
		 * This method is storing all the data collected from the DB into an object
		*/
		
		try {
			
			while(rs.next()){
	 	    	
	 	    	String id = rs.getString("ID_User");
	 	    	userLogged.setID_User(id);
	 	       	 	    	
	 	        String name = rs.getString("Name");
	 	        userLogged.setName(name);
	 	       	 	        
	 	        String surname = rs.getString("Surname");
	 	        userLogged.setSurname(surname);
	 	        
	 	        String type = rs.getString("Type");
	 	        userLogged.setType(type);
	 	        
	 	    }
			
		}
		
		catch (SQLException ex) {
    		
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
    	    
    	}
		  
				
	}
	
	public void callingHome(){
		
		
		new homeView(userLogged, loginPage);
		
		
	}
	
	public void saveAttempt(String result){
		
		
		String dbQuery = "INSERT INTO `surgery`.`logins` (`username_used`, `Status`) VALUES ('"+username+"', '"+result+"');";
		
		database DB = new database ();
		
		ResultSet rs2 = DB.accessDB(dbQuery);
		
	}
	
	public int checkingLockout(){
		
		int numberOfFails = 0;
		String dbQuery = "SELECT * FROM `surgery`.`logins` where (`username_used` = '"+username+"');";
		
		database DB = new database ();
		ResultSet rs3 = (ResultSet) DB.accessDB(dbQuery);
		
		try {
			while (rs3.next()){
				
				String status = rs3.getString("Status");
				
				if (status.equals("Failure")){
					
					numberOfFails++;
					
				}
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return numberOfFails;
		
		
	}
	
}
