package photos.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import photos.main.Admin;
import photos.model.*;

public class AlbumThumbnailController {

    @FXML
    private ImageView image;

    @FXML
    private Label albumLabel;

    @FXML
    private Label photoCount;

    @FXML
    private Label dateRange;
    
    @FXML
    private void click(MouseEvent actionEvent) {
    	myListener.onClickListener(album);
    }
    
    private Album album;
    private MyListener myListener;

    
    
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
