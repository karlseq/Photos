package photos.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import photos.model.*;

/**
 * Sets thumbnail of album
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class AlbumThumbnailController {

    /**
     * Displayed image
     */
	@FXML
    private ImageView image;

    /**
     * Album label
     */
	@FXML
    private Label albumLabel;

    /**
     * Number of photos in album
     */
	@FXML
    private Label photoCount;

    /**
     * Date range of photos in album
     */
	@FXML
    private Label dateRange;
    
    /**
     * Listens for when user clicks on album
     * 
     * @param actionEvent ActionEvent
     */
	@FXML
    private void click(MouseEvent actionEvent) {
    	myListener.onClickListener(album);
    }
    
    /**
     * Album you're currently looking at
     */
	private Album album;
    
	/**
	 * Listens for user interaction on album
	 */
	private MyListener myListener;

    
    /**
     * Sets thumbnail of album, along with date range and photo count
     * 
     * @param at current album
     * @param myListener listens for user interaction
     */
	public void setData(Album at, MyListener myListener) {
    	this.album = at;
    	this.myListener = myListener;
    	albumLabel.setText(album.getAlbumName());
    	photoCount.setText(Integer.toString(album.getPhotos().size()));
    	if(album.getDateRange() != null) {
        	dateRange.setText(album.getDateRange());
    	}
    	else{
    		dateRange.setText("");
    	}
    	Image image = new Image("file:" + album.getImgSrc());
    	this.image.setImage(image);
    }
}
