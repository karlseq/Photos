package photos.model;

/**
 * Listener interface
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public interface MyListener {
	
	/**
	 * Listens for user to click album
	 * 
	 * @param album album that's clicked
	 */
	public void onClickListener(Album album);
}
