package photos.controllers;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import photos.main.Admin;
import photos.main.Alerts;
import photos.main.Photos;
import photos.main.User;
import photos.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;

/**
 * Manages user main page when they first log in
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class UserMainPageController implements Initializable, Screen{
	
	/**
	 * Opened album
	 */
	public static Album openedAlbum;
	
	/**
	 * Search results album
	 */
	public static Album searchResults;

	/**
	 * Profile pic
	 */
    @FXML
    private ImageView profilePic;
    
	/**
	 * Username
	 */
    @FXML
    private Label userName;
    
    /**
     * Number of albums
     */
    @FXML
    private Label albumCount;

    /**
     * Scroll
     */
    @FXML
    private ScrollPane scroll;

    /**
     * Grid
     */
    @FXML
    private GridPane grid;
    
    /**
     * Logs out of application
     */
    @FXML
    private MenuItem logout;
    
    /**
     * Quits whole application
     */
    @FXML
    private MenuItem quit;
    
    /**
     * Add new album
     */
    @FXML
    private Button add;
    
    /**
     * Name of new album to add
     */
    @FXML
    private TextField albumTextField;
    
    /**
     * Number of albums
     */
    @FXML
    private Label albumLabelCount;
    
    /**
     * ChoiceBox for all albums
     */
    @FXML
    private ChoiceBox<Album> choiceBox;
    
    /**
     * ChoiceBox of albums to rename
     */
    @FXML
    private ChoiceBox<Album> renameChoicebox;
    
    /**
     * New album name
     */
    @FXML
    private TextField renameTextfield;
    
    /**
     * Search by tag TextField
     */
    @FXML
    private TextField searchBox;
    
    /**
     * Start date for search
     */
    @FXML
    private DatePicker fromDatePicker;

    /**
     * End date for search
     */
    @FXML
    private DatePicker toDatePicker;
    
    /**
     * Circle for profile pic
     */
    @FXML
    private Circle circle;

    /**
     * Listens for user interaction
     */
    private MyListener myListener;
    
    /**
     * Current user
     */
    private User u;
	
    /**
     * Count
     */
    private StringProperty count;
	
    /**
     * 1 or more albums
     */
    private StringProperty albumOrAlbums;

	/**
	 * Coordinates
	 */
    public static int ucol = 0, urow = 1;

    
    /**
     * Opens album
     * 
     * @param album album to open
     * @throws IOException IO error
     */
    public void openAlbum(Album album) throws IOException {
    	openedAlbum = album;
		Stage primaryStage = Photos.pStage;
		Parent root = FXMLLoader.load(getClass().getResource("/photos/view/Album.fxml"));
		primaryStage.setTitle(album.getAlbumName());
		primaryStage.setScene(new Scene(root, 1315, 750));
		primaryStage.show();
    }
    
    /**
     * Takes you to search results screen
     * 
     * @param album search results album
     * @throws IOException IO error
     */
    public void openHits(Album album) throws IOException {
    	searchResults = album;
		Stage primaryStage = Photos.pStage;
		Parent root = FXMLLoader.load(getClass().getResource("/photos/view/SearchResults.fxml"));
		primaryStage.setTitle(album.getAlbumName());
		primaryStage.setScene(new Scene(root, 1315, 750));
		primaryStage.show();
    }
    
    @Override
    public void initialize(URL Location, ResourceBundle resources) {
    	count = new SimpleStringProperty();
    	albumOrAlbums = new SimpleStringProperty();
    	this.choiceBox.getItems().add(null);
    	this.renameChoicebox.getItems().add(null);

    	this.u = Admin.getCurrentUser();
    	Image image = new Image("file:" + u.profilePic.getImgSrc());
 
    	
    	circle.setFill(new ImagePattern(image));
    	circle.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);\n");
    	
    	this.userName.setText(u.getUserName());
    	ucol = u.getAlbums().size() % 3;
    	urow = u.getAlbums().size() / 3 + 1;
    	
    	List<Album> albums = u.getAlbums(); 
    	
    	int col = 0, row = 1;
    	if(albums.size() > 0) {
    		myListener = (a) -> {
				try {
					openAlbum(a);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
    	}
    	try {
    		for(int i = 0; i < albums.size(); i++) {
    			
    			FXMLLoader fxmlLoader = new FXMLLoader();
    			fxmlLoader.setLocation(getClass().getResource("/photos/view/AlbumThumbnail.fxml"));
    			
    			AnchorPane anchorPane = fxmlLoader.load();
    			
    			AlbumThumbnailController albThumb = fxmlLoader.getController();
    			albThumb.setData(albums.get(i), myListener);
    			
    			if(col == 3) {
    				col = 0;
    				row++;
    			}
    			
    			grid.add(anchorPane, col++, row);
    			
    			//set grid width
				grid.setMinWidth(Region.USE_COMPUTED_SIZE);
				grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
				grid.setMaxWidth(Region.USE_PREF_SIZE);
				
				//set grid height
				grid.setMinHeight(Region.USE_COMPUTED_SIZE);
				grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
				grid.setMaxHeight(Region.USE_PREF_SIZE);

				
				GridPane.setMargin(anchorPane, new Insets(30));
    			this.choiceBox.getItems().add(albums.get(i));
    			this.renameChoicebox.getItems().add(albums.get(i));

    		}
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    	int userAlbumCount = albums.size();
    	albumCount.setText(Integer.toString(userAlbumCount));
    	if(userAlbumCount == 1) {
    		albumLabelCount.setText("Album");
    	}
    	else {
    		albumLabelCount.setText("Albums");
    	}
    	
    }

	
    /**
     * Add new album
     * 
     * @param e ActionEvent
     * @throws IOException IO error
     */
	public void addNewAlbum(ActionEvent e) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/photos/view/AlbumThumbnail.fxml"));
		
		AnchorPane anchorPane = fxmlLoader.load();
		
		AlbumThumbnailController albThumb = fxmlLoader.getController();
		
		myListener = (a) -> {
			try {
				openAlbum(a);
			} catch (IOException eio) {
				// TODO Auto-generated catch block
				eio.printStackTrace();
			}
		};
		
		if(albumTextField.getText().isBlank()) {
			Alerts.IllegalField();
			return;
		}
		
    	Album newAlb = new Album(albumTextField.getText());
    	boolean added = u.addAlbum(newAlb);
    	if(!added) {
    		Alerts.duplicateError("Album");
    		return;
    	}
		albThumb.setData(newAlb, myListener);
		
		if(ucol == 3) {
			ucol = 0;
			urow++;
		}
		
		grid.add(anchorPane, ucol++, urow);
				
		//set grid width
		grid.setMinWidth(Region.USE_COMPUTED_SIZE);
		grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
		grid.setMaxWidth(Region.USE_PREF_SIZE);
		
		//set grid height
		grid.setMinHeight(Region.USE_COMPUTED_SIZE);
		grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
		grid.setMaxHeight(Region.USE_PREF_SIZE);

		
		GridPane.setMargin(anchorPane, new Insets(30));
		
		count.set(Integer.toString(u.getAlbums().size()));
		albumCount.textProperty().bind(count);
		
		if(u.getAlbums().size() == 1) {
			albumOrAlbums.set("Album");
		}
		else {
			albumOrAlbums.set("Albums");
		}
		albumLabelCount.textProperty().bind(albumOrAlbums);
		this.choiceBox.getItems().add(newAlb);
		this.renameChoicebox.getItems().add(newAlb);

		albumTextField.clear();
 
	}
	
	/**
	 * Delete album
	 * 
	 * @param e ActionEvent
	 */
	public void deleteAlbum(ActionEvent e) {
		Album a = this.choiceBox.getValue();
		if(a != null) {
			this.u.deleteAlbum(a);
			List<Album> albums = this.u.getAlbums();
			this.choiceBox.getItems().remove(a);
			this.renameChoicebox.getItems().remove(a);
			grid.getChildren().clear();
			ucol = 0; urow = 1;
			
			try {
	    		for(int i = 0; i < albums.size(); i++) {
	    			FXMLLoader fxmlLoader = new FXMLLoader();
	    			fxmlLoader.setLocation(getClass().getResource("/photos/view/AlbumThumbnail.fxml"));
	    			
	    			AnchorPane anchorPane = fxmlLoader.load();
	    			
	    			AlbumThumbnailController albThumb = fxmlLoader.getController();
	    			albThumb.setData(albums.get(i), myListener);
	    			
	    			if(ucol == 3) {
	    				ucol = 0;
	    				urow++;
	    			}
	    			
	    			grid.add(anchorPane, ucol++, urow);
	    			
	    			//set grid width
					grid.setMinWidth(Region.USE_COMPUTED_SIZE);
					grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
					grid.setMaxWidth(Region.USE_PREF_SIZE);
					
					//set grid height
					grid.setMinHeight(Region.USE_COMPUTED_SIZE);
					grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
					grid.setMaxHeight(Region.USE_PREF_SIZE);

					
					GridPane.setMargin(anchorPane, new Insets(30));
	    		}
	    	}
	    	catch(IOException eio) {
	    		eio.printStackTrace();
	    	}
	    	
			count.set(Integer.toString(u.getAlbums().size()));
			albumCount.textProperty().bind(count);
			
			if(u.getAlbums().size() == 1) {
				albumOrAlbums.set("Album");
			}
			else {
				albumOrAlbums.set("Albums");
			}
			albumLabelCount.textProperty().bind(albumOrAlbums);
		}
	}
	
	/**
	 * Rename album
	 * 
	 * @param e ActionEvent
	 */
	public void rename(ActionEvent e) {
		Album old = this.renameChoicebox.getValue();
		String newName = this.renameTextfield.getText();
		Album newAlb = u.renameAlbum(old, newName);
		if(newAlb != null) {
			List<Album> albums = this.u.getAlbums();
			grid.getChildren().clear();
			ucol = 0; urow = 1;
			try {
	    		for(int i = 0; i < albums.size(); i++) {
	    			FXMLLoader fxmlLoader = new FXMLLoader();
	    			fxmlLoader.setLocation(getClass().getResource("/photos/view/AlbumThumbnail.fxml"));
	    			
	    			AnchorPane anchorPane = fxmlLoader.load();
	    			
	    			AlbumThumbnailController albThumb = fxmlLoader.getController();
	    			albThumb.setData(albums.get(i), myListener);
	    			
	    			if(ucol == 3) {
	    				ucol = 0;
	    				urow++;
	    			}
	    			
	    			grid.add(anchorPane, ucol++, urow);
	    			
	    			//set grid width
					grid.setMinWidth(Region.USE_COMPUTED_SIZE);
					grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
					grid.setMaxWidth(Region.USE_PREF_SIZE);
					
					//set grid height
					grid.setMinHeight(Region.USE_COMPUTED_SIZE);
					grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
					grid.setMaxHeight(Region.USE_PREF_SIZE);

					
					GridPane.setMargin(anchorPane, new Insets(30));
	    		}
	    	}
	    	catch(IOException eio) {
	    		eio.printStackTrace();
	    	}
			
			updateNameinChoiceBox(newName, newAlb);
			this.renameTextfield.clear();
			this.renameChoicebox.getSelectionModel().clearSelection();
		}
	}
	
	/**
	 * Search by tag
	 * 
	 * @param e ActionEvent
	 * @throws IOException IO error
	 */
	public void search(ActionEvent e) throws IOException {
		String text = searchBox.getText();
		if(text == null || text.indexOf("=") == -1) {
			Alerts.IllegalField();
			return;
		}
		
		//person=Ibrahim AND location=New York
		text.trim();
		String[] arr = text.split("\\=");
		Album hits = new Album(text + " hits");
		if(arr.length == 2) {
			String key = arr[0], value = arr[1];
			for(Album album : u.getAlbums()) {
				for(Photo photo : album.getPhotos()) {
					if(photo.tags.get(value) != null){
						if(photo.tags.get(value).contains(new Tag(key)) && !hits.getPhotos().contains(photo)) hits.getPhotos().add(photo);
					}
				}
			}
		}
		else {
			for(String st : arr) {
				System.out.println(st);
			}
			String key1 = arr[0], value1 = null;
			String operation = null;
			String key2 = null, value2 = arr[2];
			if(arr[1].indexOf("AND")!= -1) {
				value1 = arr[1].substring(0, arr[1].indexOf("AND")-1);
				operation = "AND";
				key2 = arr[1].substring(arr[1].indexOf("AND")+4, arr[1].length());
			}
			else {
				value1 = arr[1].substring(0, arr[1].indexOf("OR")-1);
				operation = "OR";
				key2 = arr[1].substring(arr[1].indexOf("OR")+3, arr[1].length());
			}
		
			
			if(operation.equals("AND")) {
				for(Album album : u.getAlbums()) {
					for(Photo photo : album.getPhotos()) {
						if(photo.tags.get(value1) != null && photo.tags.get(value2) != null){
							if(photo.tags.get(value1).contains(new Tag(key1)) && photo.tags.get(value2).contains(new Tag(key2)) && !hits.getPhotos().contains(photo) ) {
								hits.getPhotos().add(photo);
							}
						}
					}
				}				
			}
			else if(operation.equals("OR")) {
				for(Album album : u.getAlbums()) {
					for(Photo photo : album.getPhotos()) {
						if(photo.tags.get(value1) != null) {
							if(photo.tags.get(value1).contains(new Tag(key1)) && !hits.getPhotos().contains(photo)) hits.getPhotos().add(photo);
						}
						else if(photo.tags.get(value2) != null) {
							if(photo.tags.get(value2).contains(new Tag(key2)) && !hits.getPhotos().contains(photo)) hits.getPhotos().add(photo);

						}
					}
				}
			}
		}
		
		if(!hits.getPhotos().isEmpty()) {
			openHits(hits);
		}
		else {
			Alerts.noHits("tag(s)");
			return;
		}
	}
	
	/**
	 * Search by date
	 * 
	 * @param e ActionEvent
	 * @throws IOException IO error
	 */
	public void searchByDate(ActionEvent e) throws IOException {
		
		if(fromDatePicker.getValue() == null || toDatePicker.getValue() == null) {
			Alerts.IllegalField();
			return;
			
		}
		Album hits = new Album("Search by Date hits");
		LocalDate startDate = fromDatePicker.getValue();
		LocalDate endDate = toDatePicker.getValue();
		for (Album album: u.getAlbums()) {
			for (Photo photo: album.getPhotos()) {
				if (photo.date.compareTo(startDate)>=0 && photo.date.compareTo(endDate)<=0) {
					if (!hits.getPhotos().contains(photo)) { //photo already in hits
						hits.getPhotos().add(photo);
					}
				}
			}
		}
		
		if(!hits.getPhotos().isEmpty()) {
			openHits(hits);
		}
		else {
			Alerts.noHits("Date Range");
			return;
		}
	}
	
	/**
	 * If new album is added, add its name to ChoiceBox
	 * 
	 * @param newName name of new album
	 * @param newAlbum new album
	 */
	private void updateNameinChoiceBox(String newName, Album newAlbum) {
		for(int i = 1; i < this.choiceBox.getItems().size(); i++) {
			Album old = this.choiceBox.getItems().get(i); 
			if(old.getAlbumName().equals(newName)) {
				this.choiceBox.getItems().remove(i);
				this.renameChoicebox.getItems().remove(i);

				this.choiceBox.getItems().add(i, newAlbum);
				this.renameChoicebox.getItems().add(i, newAlbum);
				return;
			}
		}
	}
	@Override
	public void goBack(ActionEvent e) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void logout(ActionEvent e) throws IOException {
		logOut(e);
		
	}
	@Override
	public void quit(ActionEvent e) throws IOException {
		Admin.writeUser();
		Quit(e);
	}
	
	/**
	 * Adds profile pic to circle
	 * 
	 * @param e ActionEvent
	 */
	public void addProfilePic(ActionEvent e) {
		File pp = allowUserToSelect();
		this.u.profilePic.setImgSrc(pp.getPath());
		Image image = new Image("file:" + u.profilePic.getImgSrc());
		circle.setFill(new ImagePattern(image));
	}
	
	/**
	 * Deletes profile pic
	 * 
	 * @param e ActionEvent
	 */
	public void deleteProfilePic(ActionEvent e) {
		this.u.profilePic.setImgSrc(User.blankImage);
		Image image = new Image("file:" + u.profilePic.getImgSrc());
		circle.setFill(new ImagePattern(image));
	}
	
	/**
	 * Allows user to select file from their file system
	 * 
	 * @return file user wants to import
	 */
	private File allowUserToSelect() {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Select photos you would like to add");
    	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    	fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("images", "*.jpg", "*.png", "*.jpeg")
                );
		Stage primaryStage = new Stage();
        File file = fileChooser.showOpenDialog(primaryStage);
        primaryStage.show();
        primaryStage.close();
        return file;
	}
	
	

	
    
    
}
