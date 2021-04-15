package photos.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import photos.model.Photo;
import photos.model.PhotoListener;
import photos.model.TestListener;

/**
 * Sets thumbnail of photo
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 *
 */

public class PhotoThumbnailController {
	
	  /**
	   * Displayed image
	   */
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
	  
	  
	  /**
	   * Current photo
	   */
	  private Photo tn;
	  
	  /**
	   * Listens for photo interaction
	   */
	  private PhotoListener myListener;
	  
	  /**
	   * Test listener
	   */
	  private TestListener testListener;

	  /**
	   * Sets displayed image
	   * 
	   * @param tn current photo
	   * @param myListener listens for photo interaction
	   * @param testListener test
	   */
	  public void setData(Photo tn, PhotoListener myListener, TestListener testListener) {
		  this.tn = tn;
		  this.myListener = myListener;
		  this.testListener = testListener;
		  Image image = null;
		  image = new Image("file:" + tn.getImgSrc());

		  this.image.setImage(image);
		  
	  }
	  
	  /**
	   * Gets displayed image
	   * 
	   * @return displayed image
	   */
	  public ImageView getImageView() {
		  return this.image;
	  }
}
