package photos.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.Date;

import photos.main.Admin;
import photos.main.Alerts;
import photos.main.Photos;
import photos.main.User;
import photos.model.Album;
import photos.model.Photo;
import photos.model.PhotoListener;
import photos.model.TestListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.time.*;

/**
 * Manages albums
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class AlbumController implements Initializable, Screen{

    /**
     * Name of album
     */
	@FXML
    private Label AlbumName;

    /**
     * Displays how many photos in the album there are
     */
	@FXML
    private Label PhotoCount;

    /**
     * Scrollpane
     */
	@FXML
    private ScrollPane scroll;

   /**
    * Grid
    */
	@FXML
    private GridPane grid;
    
    /**
     * Go back to previous screen
     */
	@FXML
    private MenuItem goBack;

    /**
     * Log out of application
     */
	@FXML
    private MenuItem logout;

    /**
     * Quit whole application
     */
	@FXML
    private MenuItem quit;
    
    /**
     * Add new album
     */
	@FXML
    private Button add;

    /**
     * Add delete album
     */
	@FXML
    private Button delete;

    /**
     * Copy photo
     */
	@FXML
    private Button copy;

    /**
     * Move photo
     */
	@FXML
    private Button move;
    
    /**
     * VBox
     */
	@FXML
    private VBox bottomVBox;

    /**
     * Current user
     */
    private User user;
    
    /**
     * Current album
     */
    private Album album;
    
    /**
     * Photos that belong to current album
     */
    private List<Photo> photos;
    
	/**
	 * Photo listener
	 */
    private PhotoListener myListener;
	
    /**
     * Test listener
     */
    private TestListener testListener;
	
    /**
     * Coordinates
     */
    private int ucol = 0, urow = 1;
	
	/**
	 * Selected photo
	 */
    private Photo selectedPhoto;
	
    /**
     * Is the photo selected
     */
    private boolean photoSelected;
	
    /**
     * Displays selected image
     */
    private ImageView selectedImage;
	
	/**
	 * Number of photos
	 */
    private StringProperty count;
    /*METHODS*/
   
 
    
    /**
     * Opens a photo
     * 
     * @param photo photo to be opened
     * @throws IOException
     */
    public void openPhoto(Photo photo) throws IOException {
    	if(photoSelected) {
    		selectedImage.setEffect(null);
    		selectedImage.setStyle(null);
    		photoSelected = false;
    		disableButtons(true);
    		this.add.setDisable(false);
    		this.bottomVBox.getChildren().clear();
    		return;	
    	}
		Stage primaryStage = Photos.pStage;
		Parent root = FXMLLoader.load(getClass().getResource("/photos/view/Photo.fxml"));
		PhotoController.a = album;
		primaryStage.setTitle(generateDate(photo.date));
		primaryStage.setScene(new Scene(root, 1315, 750));
		primaryStage.show();
    }
    
    /**
     * Puts date into String form
     * 
     * @param date date of photo
     * @return returns String form of date
     */
    public static String generateDate(LocalDate date) {
    	String month = date.getMonth().toString();
    	month = month.charAt(0) + month.substring(1).toLowerCase();
    	String s = "Date: " + month + " " + date.getDayOfMonth() + " " + date.getYear();
    	return s;
    }
    
	/**
	 * Sets selected photo and stuff
	 * 
	 * @param a ImageView
	 * @param p photo
	 */
    public void foo(ImageView a, Photo p) {
		this.add.setDisable(true);
		disableButtons(false);
		
		a.setStyle("-fx-opacity: .5;");
		Lighting lighting = new Lighting();
        lighting.setDiffuseConstant(1.0);
        lighting.setSpecularConstant(0.0);
        lighting.setSpecularExponent(0.0);
        lighting.setSurfaceScale(0.0);
        lighting.setLight(new Light.Distant(45, 45, Color.LIGHTBLUE));

		this.selectedPhoto = p;
		photoSelected = true;
		this.selectedImage = a;
        selectedImage.setEffect(lighting);
	}
    
    @Override
    public void initialize(URL Location, ResourceBundle resources) {
    	disableButtons(true);
    	this.user = Admin.getCurrentUser();
    	this.album = UserMainPageController.openedAlbum;
    	this.photos = album.getPhotos();
    	count = new SimpleStringProperty();

    	ucol = photos.size() % 3;
    	urow = photos.size() / 3 + 1;
    	
    	int col = 0, row = 1;

    	if(photos.size() > 0) {
    		myListener = (a) -> {
				try {
					PhotoController.photo = a;
					PhotoController.album = this.album.getPhotos();
					openPhoto(a);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
			testListener = (a, p) -> {
				foo(a, p);
			};
    	}
    	try {
    		for(int i = 0; i < photos.size(); i++) {
    			FXMLLoader fxmlLoader = new FXMLLoader();
    			fxmlLoader.setLocation(getClass().getResource("/photos/view/PhotoThumbnail.fxml"));
    			
    			AnchorPane anchorPane = fxmlLoader.load();
    			
    			PhotoThumbnailController Thumb = fxmlLoader.getController();
    			Thumb.setData(photos.get(i), myListener, testListener);
    			
    		
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

				
				GridPane.setMargin(anchorPane, new Insets(45));
    			
    		}
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    	String albumName = this.album.getAlbumName();
    	AlbumName.setText(albumName);
    	PhotoCount.setText(Integer.toString(photos.size()) + " photos");
    }
    
    /**
     * Adds photo to album
     * 
     * @param e ActionEvent
     * @throws IOException
     */
    public void add(ActionEvent e) throws IOException {
    	List<File> list = allowUserToSelect(); //list of files
        if(list == null) return;
        List<Photo> newPhotos = addFileToPhotos(list, f -> {return new Photo(f.getPath(),new Date(f.lastModified()));}); //each file, extract path, make an image path
        newPhotos.forEach((p) -> {
        	if(this.photos.contains(p)) {
        		Alerts.duplicateError("Image");
        		return;
        	}
        	else if(Admin.getCurrentUser().userPhotos.get(p) != null) {
        		p = Admin.getCurrentUser().userPhotos.get(p);
        	}
			photos.add(p);
			Admin.getCurrentUser().userPhotos.put(p, p);
        });
        
		grid.getChildren().clear();
        for(int i = 0; i< this.photos.size(); i++) {
        	FXMLLoader fxmlLoader = new FXMLLoader();
    		fxmlLoader.setLocation(getClass().getResource("/photos/view/PhotoThumbnail.fxml"));
    		AnchorPane anchorPane = fxmlLoader.load();
    		
    		myListener = (a) -> {
    			try {
    				PhotoController.photo = a;
    				PhotoController.album = this.album.getPhotos();
    				openPhoto(a);
    			} catch (IOException ei) {
    				// TODO Auto-generated catch block
    				ei.printStackTrace();
    			}
    		};
    		testListener = (a, p) -> {
				foo(a, p);
			};
			
    		
    		PhotoThumbnailController Thumb = fxmlLoader.getController();
    		Thumb.setData(this.photos.get(i), myListener, testListener);
    		
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

    		
    		GridPane.setMargin(anchorPane, new Insets(45));
        }
        String photoOrPhotos = "photos";
        if(photos.size() == 1) {
        	photoOrPhotos = "photo";
        }

        count.set(Integer.toString(photos.size()) + " " + photoOrPhotos);
        PhotoCount.textProperty().bind(count);
    }

	/**
	 * Allows user to select pictures from their FileSystem
	 * 
	 * @return list of photos that user chose
	 */
    private List<File> allowUserToSelect() {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Select photos you would like to add");
    	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    	fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("images", "*.jpg", "*.png", "*.jpeg","*.bmp","*.gif")
                );
		Stage primaryStage = new Stage();
        List<File> list = fileChooser.showOpenMultipleDialog(primaryStage);
        primaryStage.show();
        primaryStage.close();
        return list;
	}

	@Override
	public void goBack(ActionEvent e) throws IOException {
		Stage primaryStage = Photos.pStage;
		Parent root = FXMLLoader.load(getClass().getResource("/photos/view/UserMainPage.fxml"));
		primaryStage.setTitle(Admin.getCurrentUser().getUserName() + "'s Main Page");
		primaryStage.setScene(new Scene(root, 1315, 750));
		primaryStage.show();
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
	 * Adds file to list of photos to be added to album
	 * 
	 * @param <T> file
	 * @param <R> file
	 * @param list list of files
	 * @param f file
	 * @return list of files
	 */
	private <T,R> List<R> addFileToPhotos(List<T> list, Function<T,R> f) {
		List<R> result = new ArrayList<R>();
		for(T t: list) {
			result.add(f.apply(t));
		}
		return result;
	}
	
	/**
	 * Deletes selected photo
	 * 
	 * @param e ActionEvent
	 * @throws IOException
	 */
	public void delete(ActionEvent e) throws IOException {
		this.album.getPhotos().remove(selectedPhoto);
		grid.getChildren().clear();
		ucol = 0; urow = 1;
		
		for(int i = 0; i< this.photos.size(); i++) {
        	FXMLLoader fxmlLoader = new FXMLLoader();
    		fxmlLoader.setLocation(getClass().getResource("/photos/view/PhotoThumbnail.fxml"));
    		AnchorPane anchorPane = fxmlLoader.load();
    		

    		PhotoThumbnailController Thumb = fxmlLoader.getController();
    		Thumb.setData(this.photos.get(i), myListener, testListener);
    		
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

    		
    		GridPane.setMargin(anchorPane, new Insets(45));
        }
		
        String photoOrPhotos = "photos";
        if(photos.size() == 1) {
        	photoOrPhotos = "photo";
        }

        count.set(Integer.toString(photos.size()) + " " + photoOrPhotos);
        PhotoCount.textProperty().bind(count);
		openPhoto(null);
	}
	
	/**
	 * Copies photo to other album
	 * 
	 * @param e ActionEvent
	 */
	public void copy(ActionEvent e) {
		copyOrMove(e, false);
	}
	
	/**
	 * Moves photo from current album to new album
	 * 
	 * @param e
	 */
	public void move(ActionEvent e) {
		copyOrMove(e, true);
	}
	
	/**
	 * Guide for user on how to navigate interface
	 * 
	 * @param e ActionEvent
	 */
	public void help(ActionEvent e) {
		Alerts.albumHelp();
	}
	
	/**
	 * Copy or Move action
	 * 
	 * @param e ActionEvent
	 * @param move is it a move or copy
	 */
	public void copyOrMove(ActionEvent e, Boolean move) {
		if(bottomVBox.getChildren().isEmpty()){
			
		ChoiceBox<Album> c = new ChoiceBox<Album>();
		c.getItems().addAll(user.getAlbums());
		c.getItems().remove(album);
		c.setStyle("	-fx-background-color: #FFFFFF;\n" + 
				"	-fx-background-radius: 100;");
		c.setPrefWidth(161);
		VBox vbox = new VBox(c);
		VBox.setMargin(c, new Insets(10));
		vbox.setAlignment(Pos.CENTER);

		Button button = new Button("confirm");
		button.setStyle("	-fx-background-color: #828282;\n" + 
				"	-fx-background-radius: 30;");
		button.setOnAction( ei -> {
			Album a = c.getValue();
			if(a.getPhotos().contains(selectedPhoto)) {
				Alerts.duplicateError("Image");
				return;
			}
			a.getPhotos().add(selectedPhoto);
			if(move) {
				try {
					delete(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			vbox.getChildren().clear();
		});
		
		c.getSelectionModel().selectedItemProperty().addListener((album) -> {
				vbox.getChildren().add(button);				
		} );
		
		this.bottomVBox.getChildren().add(vbox);
		};
	}
	
	/**
	 * Disables buttons
	 * 
	 * @param setType settype
	 */
	public void disableButtons(Boolean setType) {
    	delete.setDisable(setType);
    	copy.setDisable(setType);
    	move.setDisable(setType);
	}
	

}
