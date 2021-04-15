package photos.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import photos.main.Admin;
import photos.main.Photos;
import photos.model.Album;
import photos.model.Photo;

/**
 * Manages Photo related stuff like captions, tags, and slideshow
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class PhotoController implements Initializable, Screen {
	
	/**
	 * Displayed image
	 */
    @FXML
    private ImageView image;

    /**
     * Button to go right in slideshow
     */
    @FXML
    private Button rightButton;

    /**
     * Button to go left in slideshow
     */
    @FXML
    private Button leftButton;

    /**
     * Caption of photo
     */
    @FXML
    private TextField caption;
    
    /**
     * Button to open tags of photo
     */
    @FXML
    private Button tagsButton;
    
    /**
     * Current photo
     */
    public static Photo photo;
    
    /**
     * List of all the photos in the album
     */
    public static List<Photo> album;
    
    /**
     * Current album
     */
    public static Album a;
    
    /**
     * Goes back to previous screen
     */
    @FXML
    private MenuItem goBack;

    /**
     * Logs out of application
     */
    @FXML
    private MenuItem logOut;

    /**
     * Quits whole application
     */
    @FXML
    private MenuItem quit;
    
    /**
     * Instance of tag controller
     */
    private TagController tagController;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image image = new Image("file:"+photo.getImgSrc());
		this.image.setImage(image);
		if (photo.caption!=null) {
			caption.setText(photo.caption);
		}
		caption.textProperty().addListener((observable, oldValue, newValue) -> {
			photo.caption = newValue;
		});
	}
	
	
	/**
	 * Goes right on the photo slideshow
	 * 
	 * @param e ActionEvent
	 */
	public void goRight(ActionEvent e) {
		int index = getIndex();
		if(index < album.size()-1) {
			setPhoto(index+1);
		}

	}
	
	/**
	 * Goes left on the photo slideshow
	 * 
	 * @param e ActionEvent
	 */
	public void goLeft(ActionEvent e) {
		caption.setText(photo.caption);
		int index = getIndex();
		if(index > 0) {
			setPhoto(index-1);
		}
	}
	
	/**
	 * Gets index of current photo in album's list of photos
	 * 
	 * @return index
	 */
	private int getIndex(){
		for(int i = 0; i < album.size(); i++) {
			if(album.get(i).getImgSrc() == photo.getImgSrc()) {
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Sets how the photo will look in photo view
	 * 
	 * @param index index of current photo in album's list of photos
	 */
	private void setPhoto(int index) {
		photo = album.get(index);
		caption.setText(photo.caption);
		Image image = new Image("file:" + photo.getImgSrc());
		this.image.setImage(image);	
		Stage primaryStage = Photos.pStage;
		primaryStage.setTitle(AlbumController.generateDate(photo.date));
	}


	@Override
	public void goBack(ActionEvent e) throws IOException {
		Stage primaryStage = Photos.pStage;
		Parent root = null;
		if(SearchResultsController.isSearch) {
			root = FXMLLoader.load(getClass().getResource("/photos/view/SearchResults.fxml"));
			primaryStage.setTitle("Search Results");
		}
		else {
			root = FXMLLoader.load(getClass().getResource("/photos/view/Album.fxml"));
			primaryStage.setTitle(a.getAlbumName());
		}
		primaryStage.setScene(new Scene(root, 1315, 750));
		primaryStage.show();
	}
	
	
	public void logout(ActionEvent e) throws IOException {
		logOut(e);
	}
	
	public void quit(ActionEvent e) throws IOException {
		Admin.writeUser();
		Quit(e);
	}
	
	/*
	public void hitCaption(ActionEvent e) {
		boolean result = Alerts.confirmation("Are you sure you want to add/rename caption", "Add/Rename Caption");
		if (result) {
			String s = caption.getText();
			if (s==null || s.equals("")) {
				Alerts.IllegalField();
				return;
			}
			photo.setCaption(s);
			caption.setText(s);
		}
	}
	*/
	
	/**
	 * Opens tags window
	 * 
	 * @param e ActionEvent
	 * @throws IOException IO error
	 */
	public void hitTags(ActionEvent e) throws IOException {
		/*Stage primaryStage = Main.pStage;
		Parent root = FXMLLoader.load(getClass().getResource("/photos/view/Tags.fxml"));
		primaryStage.setTitle(Admin.getCurrentUser().getUserName() + "'s Main Page");
		primaryStage.setScene(new Scene(root, 1315, 810));
		primaryStage.centerOnScreen();
		primaryStage.show();*/

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/photos/view/Tags.fxml"));
		Parent root = loader.load();
		Stage primaryStage = (Stage) tagsButton.getScene().getWindow();
		tagController = loader.getController();
		tagController.start(primaryStage);
		primaryStage.setTitle(Admin.getCurrentUser().getUserName() + "'s Main Page");
		primaryStage.setScene(new Scene(root, 1315, 750));
		primaryStage.centerOnScreen();
		primaryStage.show();
	}


}
