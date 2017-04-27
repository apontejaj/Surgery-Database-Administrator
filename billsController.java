import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class billsController implements ActionListener, ListSelectionListener {

	billsModel M = null;
	billsView V = null;
	login loginPage = null;
	homeView homePage = null;
	int counter = 0;
		
	public billsController(billsModel modelRef, billsView viewRef, login loginRef, homeView homeRef){
		
		M = modelRef;
		V = viewRef;
		loginPage = loginRef;
		homePage = homeRef;
				
	}
	
	
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("createNew")){
			
			M.lookingForPatient();
			
		}
	
		else if (e.getActionCommand().equals("save")){
			
			String id = (String) V.getTable().getValueAt(V.getTable().getSelectedRow(), 0);
			M.save(id);
			
		}
		
		else if (e.getActionCommand().equals("delete")){
			
			String id = (String) V.getTable().getValueAt(V.getTable().getSelectedRow(), 0);
			M.delete(id);
			
		}
		
		
		else if (e.getActionCommand().equals("logout")){
			
			V.setVisible(false);
			loginPage.setVisible(true);
			
		}
		
		else if (e.getActionCommand().equals("backHome")){
			
			V.setVisible(false);
			homePage.setVisible(true);
			
		}
		
		else if (e.getActionCommand().equals("detail")){
			
			String id = (String) V.getTable().getValueAt(V.getTable().getSelectedRow(), 0);
			new detail(id);
			
		}
		
	}

	public void valueChanged(ListSelectionEvent arg0) {
		
		counter ++;
		
		if (counter == 2){
			
			String id = (String) V.getTable().getValueAt(V.getTable().getSelectedRow(), 0);
			M.select(id);
			counter = 0;
			
		}
		
	}

}
