package photos.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photos.main.Admin;
import photos.main.Alerts;
import photos.main.Photos;
import photos.main.User;

/**
 * Controller for Login Screen
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class LoginController implements Initializable {
	
	/**
	 * TextField for the user to input username
	 */
	@FXML 
	TextField usernameText;
	
	/**
	 * Button for login
	 */
	@FXML 
	Button loginButton;
	
	/**
	 * Instance of AdminController
	 */
	public static AdminController adminController;
	
	/**
	 * Handles login ActionEvent
	 * 
	 * @param e ActionEvent triggered when login button is pressed
	 * @throws IOException
	 */
	public void login(ActionEvent e) throws IOException {
		String userName = usernameText.getText();
		if (userName.equals("admin")) { //go to admin screen
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/photos/view/admin.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root, 600, 550);
			Stage primaryStage = (Stage) loginButton.getScene().getWindow();
			adminController = loader.getController();
			adminController.start(primaryStage);
			primaryStage.setTitle("Admin");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			return;
		}
		else {
			//try to find user in the list
			User u = Admin.containsUser(userName);
			if(u != null) {
				Admin.setCurrentUser(u);
				Stage primaryStage = Photos.pStage;
				Parent root = FXMLLoader.load(getClass().getResource("/photos/view/UserMainPage.fxml"));
				primaryStage.setTitle(Admin.getCurrentUser().getUserName() + "'s Main Page");
				primaryStage.setScene(new Scene(root, 1315, 810));
				primaryStage.centerOnScreen();
				primaryStage.show();
			}
			else {
				Alerts.NonexistentUser();
			}
		}

	}
	
	/**
	 * Calls login method
	 * 
	 * @param e ActionEvent
	 * @throws IOException
	 */
	public void onEnter(ActionEvent e) throws IOException {
		login(e);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		BooleanBinding bb = new BooleanBinding() {
			{
		        super.bind(usernameText.textProperty());
		    }

		    @Override
		    protected boolean computeValue() {
		        return (usernameText.getText().isEmpty());
		    }
		};
		loginButton.disableProperty().bind(bb);		
	}
	
	
}
