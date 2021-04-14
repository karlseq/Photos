package photos.controllers;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import photos.main.Alerts;
import photos.main.Admin;
import photos.main.User;

/**
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class AdminController implements Screen{
	/**
	 * Input username TextField
	 */
	@FXML TextField usernameText;
	
	/**
	 * Button to create new user
	 */
	@FXML Button createUser;
	
	/**
	 * Button to delete user
	 */
	@FXML Button deleteUser;
	
	/**
	 * Button to logout of application
	 */
	@FXML Button logout;
	
	/**
	 * Button to quit whole application
	 */
	@FXML Button quit;
	
	/**
	 * List of users
	 */
	@FXML ListView<User> listView; 
	
	/**
	 * Displays users to ListView
	 */
	private ObservableList<User> userList;
	
	/**
	 * First method in controller
	 * 
	 * @param mainstage primary stage
	 */
	public void start(Stage mainstage) {
		userList = FXCollections.observableArrayList();
		for (User u: Admin.userList) {
			userList.add(u);
		}
		listView.setItems(userList);
		//selects first item in song list and displays name and artist in details
		if (userList!=null && !userList.isEmpty()) {
			listView.getSelectionModel().clearAndSelect(0);
		}
	}
	
	/**
	 * Creates new user
	 * 
	 * @param e ActionEvent
	 */
	public void createUser(ActionEvent e) {
		String header = "Are you sure you want to create a new user";
		String title = "Create User Confirmation";
		boolean result = Alerts.confirmation(header,title);
		if (result) {
			String userName = usernameText.getText();
			if (userName==null || userName.equals("")) {
				Alerts.IllegalField();
				return;
			}
			User newUser = new User(userName);
			if (userList.contains(newUser)) { //duplicate user - not allowed
				Alerts.duplicateError("Album");
				return;
			}
			userList.add(newUser); //adds User to userList in this class (for displaying purposes)
			Admin.addUser(userName);
			usernameText.clear();
			listView.getSelectionModel().select(userList.size()-1); //selects the newly added user
		}
	}
	
	/**
	 * Deletes user
	 * 
	 * @param e ActionEvent
	 */
	public void deleteUser(ActionEvent e) {
		String header = "Are you sure you want to delete selected user";
		String title = "Delete Confirmation";
		if (userList.size()==0) { //trying to delete from empty userList - not allowed
			Alerts.deleteError();
			return;
		}
		boolean result = Alerts.confirmation(header,title);
		if (result) {
			int index = listView.getSelectionModel().getSelectedIndex();
			if (userList.size()==1) { //removing only item in userList
				userList.remove(index);
				Admin.deleteUser(index);
			}
			else if (index==userList.size()-1) { //removing last item in userList, select previous
				listView.getSelectionModel().selectPrevious();
				userList.remove(index);
				Admin.deleteUser(index);
			}
			else { 
				listView.getSelectionModel().selectNext();
				userList.remove(index);
				Admin.deleteUser(index);
			}
		}
	}
	
	/**
	 * Logs out of application
	 */
	public void logout(ActionEvent e) throws IOException {
		logOut(e);
	}
	
	/**
	 * Quits whole application
	 */
	public void quit(ActionEvent e) throws IOException {
		Admin.writeUser();
		Quit(e);
	}
	
	@Override
	public void goBack(ActionEvent e) throws IOException {
		// TODO Auto-generated method stubs
	}
	
}
