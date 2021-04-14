package photos.main;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Alerts {
	
	public static void duplicateError(String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Duplicate " + error);
		alert.setHeaderText(error + " already exists");
		alert.showAndWait();
	}
	
	public static void deleteError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Deletion Error");
		alert.setHeaderText("Can't delete from empty user list");
		alert.showAndWait();
	}
	
	public static void IllegalField() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Illegal Field Error");
		alert.setHeaderText("Please Enter a Correct Field");
		alert.showAndWait();
	}
	
	public static void NonexistentUser() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText("User does not exist");
		alert.showAndWait();
	}
	
	public static void noHits(String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid Input");
		alert.setHeaderText("No Photos Match Given " + error);
		alert.showAndWait();
	}
	
	public static void success(String succ) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
		alert.setHeaderText(succ + " Successfully Added");
		alert.showAndWait();
	}
	
	public static void albumHelp() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Help");
		alert.setHeaderText(" -Left click on an image to open slideshow view.  \n"
				+ "-Right click to add/delete/copy/move");
		alert.showAndWait();
	}
	
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
