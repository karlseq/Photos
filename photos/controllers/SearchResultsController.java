package photos.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import photos.main.Admin;
import photos.main.Alerts;
import photos.main.Photos;
import photos.main.User;
import photos.model.Album;
import photos.model.Photo;
import photos.model.PhotoListener;
import photos.model.TestListener;

public class SearchResultsController implements Initializable, Screen {
	
	public static boolean isSearch;
	@FXML
    private Label AlbumName;

    @FXML
    private Label PhotoCount;
    
    @FXML
    private TextField albumTextField;
    
    @FXML
    private Button addSearchAlbum;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;
    
    @FXML
    private MenuItem back;

    @FXML
    private MenuItem logout;

    @FXML
    private MenuItem quit;
    
    
    private User u = Admin.getCurrentUser();
    private Album hits;
    private List<Photo> photos;
	private StringProperty count;
	private int ucol = 0, urow = 1;
	private PhotoListener myListener;
	private TestListener testListener;
	private Photo selectedPhoto;
	private boolean photoSelected;
	private ImageView selectedImage;
	
    public void openPhoto(Photo photo) throws IOException {
    	if(photoSelected) {
    		selectedImage.setEffect(null);
    		selectedImage.setStyle(null);
    		photoSelected = false;
    		return;	
    	}
    	isSearch = true;
		Stage primaryStage = Photos.pStage;
		Parent root = FXMLLoader.load(getClass().getResource("/photos/view/Photo.fxml"));
		primaryStage.setTitle(Admin.getCurrentUser() + "'s Main Page");
		primaryStage.setScene(new Scene(root, 1315, 810));
		primaryStage.show();
    }
    
	public void foo(ImageView a, Photo p) {
	
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
    	this.hits = UserMainPageController.searchResults;
    	this.photos = hits.getPhotos();
    	count = new SimpleStringProperty();

    	ucol = photos.size() % 3;
    	urow = photos.size() / 3 + 1;
    	
    	int col = 0, row = 1;

    	if(photos.size() > 0) {
    		myListener = (a) -> {
				try {
					PhotoController.photo = a;
					PhotoController.album = this.hits.getPhotos();
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
    	String albumName = this.hits.getAlbumName();
    	AlbumName.setText(albumName);
    	PhotoCount.setText(Integer.toString(photos.size()) + " photos");
    }

	public void addAlbum(ActionEvent e) {
		if(albumTextField.getText().isBlank()) {
			Alerts.IllegalField();
			return;
		}
		String savedName = this.hits.getAlbumName();
		this.hits.setAlbumName(albumTextField.getText());
		boolean added = u.addAlbum(this.hits);
		Alerts.success("Album");
		if(!added) {
    		Alerts.duplicateError("Album");
    		this.hits.setAlbumName(savedName);
    		return;
    	}
	}
    
    @Override
	public void goBack(ActionEvent e) throws IOException {
		isSearch = false;
		Stage primaryStage = Photos.pStage;
		Parent root = FXMLLoader.load(getClass().getResource("/photos/view/UserMainPage.fxml"));
		primaryStage.setTitle(Admin.getCurrentUser()+ "'s Main Page");
		primaryStage.setScene(new Scene(root, 1315, 810));
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
}
