package photos.model;

/**
 * Listens for photo interaction
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public interface PhotoListener {
	
	/**
	 * Listens for user to click photo
	 * 
	 * @param photo photo that user clicks
	 */
	public void onClickListener(Photo photo);
}
