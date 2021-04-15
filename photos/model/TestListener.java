package photos.model;

import javafx.scene.image.ImageView;

/**
 * Test listener
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public interface TestListener {
	
	/**
	 * Listens for photo interaction
	 * 
	 * @param image displayed image
	 * @param p photo
	 */
	public void onSecondListener(ImageView image, Photo p);

}
