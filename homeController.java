import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class homeController implements ActionListener {

	login loginPage = null;
	homeView homePage = null;
	user userLogged = null;
	
	public homeController(login loginRef, homeView homeRef, user userRef){
		
		loginPage = loginRef;
		homePage = homeRef;
		userLogged = userRef;
		
	}
	
	
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("msgs")){
			
			new msgsView(userLogged, loginPage, homePage);
			homePage.setVisible(false);
			
		}
	
		else if (e.getActionCommand().equals("apps")){
			
			new appsView(userLogged, loginPage, homePage);
			homePage.setVisible(false);		
			
		}
		
		else if (e.getActionCommand().equals("bills")){
			
			new billsView(userLogged, loginPage, homePage);
			homePage.setVisible(false);
			
		}
		
		else if (e.getActionCommand().equals("pats")){
			
			new patView(userLogged, loginPage, homePage);
			homePage.setVisible(false);
			
		}
		
		else if (e.getActionCommand().equals("logout")){
			
			homePage.setVisible(false);
			loginPage.setVisible(true);
			
		}
		
		
	}

}
