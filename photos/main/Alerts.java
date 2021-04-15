package photos.main;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/**
 * Contains all possible error pop-ups
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class Alerts {
	
	/**
	 * Duplicate item
	 * 
	 * @param error error message
	 */
	public static void duplicateError(String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Duplicate " + error);
		alert.setHeaderText(error + " already exists");
		alert.showAndWait();
	}
	
	/**
	 * Trying to delete from empty list
	 */
	public static void deleteError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Deletion Error");
		alert.setHeaderText("Can't delete from empty list");
		alert.showAndWait();
	}
	
	/**
	 * Illegal input error
	 */
	public static void IllegalField() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Illegal Field Error");
		alert.setHeaderText("Please Enter a Correct Field");
		alert.showAndWait();
	}
	
	/**
	 * User does not exist
	 */
	public static void NonexistentUser() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText("User does not exist");
		alert.showAndWait();
	}
	
	/**
	 * No photos match search criteria
	 * 
	 * @param error search error
	 */
	public static void noHits(String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText("No Photos Match Given " + error);
		alert.showAndWait();
	}
	
	/**
	 * Successfully added item
	 * 
	 * @param succ success message
	 */
	public static void success(String succ) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText(succ + " Successfully Added");
		alert.showAndWait();
	}
	
	/**
	 * Album help for user main page
	 */
	public static void albumHelp() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Help");
		alert.setHeaderText(" -Left click on an image to open slideshow view.  \n"
				+ "-Right click to add/delete/copy/move");
		alert.showAndWait();
	}
	
	/**
	 * Confirmation pop-up
	 * 
	 * @param header confirmation body
	 * @param title confirmation title
	 * @return true if user hits ok, false if not
	 */
	public static boolean confirmation(String header, String title) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get()==ButtonType.OK) {
			return true;
		}
		else {
			return false;
		}
	}
}
