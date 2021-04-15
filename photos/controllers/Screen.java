package photos.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photos.main.Alerts;
import photos.main.Photos;

/**
 * Allows user to go back to previous screen, logout, or quit application
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public interface Screen {
	
	/**
	 * Goes back to previous screen
	 * 
	 * @param e ActionEvent
	 * @throws IOException IO error
	 */
	void goBack(ActionEvent e) throws IOException;
	
	void logout(ActionEvent e) throws IOException;
	void quit(ActionEvent e) throws IOException;

	/**
	 * Logs out of application
	 * 
	 * @param e ActionEvent
	 * @throws IOException IO error
	 */
	public default void logOut(ActionEvent e) throws IOException {
		String header = "Are you sure you want to logout";
		String title = "Logout Confirmation";
		boolean result = Alerts.confirmation(header,title);
		if (result) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/photos/view/login.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			Stage primaryStage = (Stage) Photos.pStage.getScene().getWindow();
			primaryStage.setTitle("Login");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.centerOnScreen();
			primaryStage.show();
		}
	}
	
	/**
	 * Quits whole application
	 * 
	 * @param e ActionEvent
	 */
	public default void Quit(ActionEvent e) {
		String header = "Are you sure you want to quit the whole application";
		String title = "Quit Confirmation";
		boolean result = Alerts.confirmation(header,title);
		if (result) {
			//save user list first
			e.consume();
			Stage primaryStage = (Stage) Photos.pStage.getScene().getWindow();
			primaryStage.close();
		}
	}
	
	//displays confirmation dialog box
	//returns true if user hits yes, false if user hits cancel

}
