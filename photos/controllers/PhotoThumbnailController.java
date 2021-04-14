package photos.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import photos.model.Photo;
import photos.model.PhotoListener;
import photos.model.TestListener;

public class PhotoThumbnailController {
	
	  @FXML
	  private ImageView image;
	  @FXML
	  
	  private void click(MouseEvent actionEvent) {
		MouseButton button = actionEvent.getButton();
		if(button == MouseButton.PRIMARY) {
		    myListener.onClickListener(tn);
		}
		else if(button == MouseButton.SECONDARY) {
			testListener.onSecondListener(image, tn);
		}
	  }
	  
	  
	  private Photo tn;
	  private PhotoListener myListener;
	  private TestListener testListener;

	  public void setData(Photo tn, PhotoListener myListener, TestListener testListener) {
		  this.tn = tn;
		  this.myListener = myListener;
		  this.testListener = testListener;
		  Image image = null;
		  image = new Image("file:" + tn.getImgSrc());

		  this.image.setImage(image);
		  
	  }
	  
	  public ImageView getImageView() {
		  return this.image;
	  }
}
