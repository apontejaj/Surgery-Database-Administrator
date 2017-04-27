import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginController implements ActionListener{

	login loginPage = null;
	int numberOfFails = 0;
	
	public loginController(login login){
		loginPage = login;
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("login")){
			
			loginModel model = new loginModel(loginPage);
			boolean answer = model.validation();
			
			if (answer){
			
				ResultSet rs = model.databaseQuery();
				
				try {
					if (!rs.isBeforeFirst()){
						
						loginPage.failTwo();
						model.saveAttempt("Failure");
						
					}
					
					else {
						
						int lock = model.checkingLockout();
						
						if (lock >= 3){
							
							loginPage.failThree();
							model.saveAttempt("Lockout");
							
						}
						else {
							
							model.saveAttempt("Success");
							model.storingInObject();
							model.callingHome();
							loginPage.setVisible(false);
							loginPage.username.setText("");
							loginPage.password.setText("");
														
						}
						
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
				
			}
			
		}
		
		
		
	}

	
	
}
