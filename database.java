import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class database {
	
	public ResultSet accessDB (String query){
		
		try {
			
			  Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			}
		
		catch(Exception e1 ){
			
			
		}
		
		Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	
    	try {
    		
    	    conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/surgery?user=root&password=");
    	    stmt = conn.createStatement();
    	    stmt.execute(query); {
    	    	
    	    rs = stmt.getResultSet();
    	    
    	    } 
    	   
    	} catch (SQLException ex) {
    		
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
    	    
    	}
		return rs;

		
	}

}