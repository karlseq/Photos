package photos.model;

import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Represents Album object
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class Album implements Serializable {
	

	/**
	 * Default serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Blank image
	 */
	public static final String blankTemplate = "./data/blank.jpg";

	/**
	 * Name of album
	 */
	private String albumName;
	
	/**
	 * Date range of photos in album
	 */
	@SuppressWarnings("unused")
	private String dateRange;
	
	/**
	 * List of photos in album
	 */
	private List<Photo> photos;
	
	/**
	 * Path of image
	 */
	@SuppressWarnings("unused")
	private String imgSrc;


	/**
	 * Creates album object
	 * 
	 * @param albumName name of album
	 */
	public Album(String albumName) {
		this.albumName = albumName;
		this.photos = new ArrayList<>();
	}
	
	/**
	 * Gets album name
	 * 
	 * @return album name
	 */
	public String getAlbumName() {
		return albumName;
	}

	/**
	 * Gets date range of photos in album
	 * 
	 * @return date range
	 */
	public String getDateRange() {
		if(photos.size() == 0) return null;
		
		LocalDate minDate = LocalDate.MAX;
		LocalDate maxDate = LocalDate.MIN;
		
		for(Photo p : photos) {
			if(p.date.compareTo(minDate) <=0) {
				minDate = p.date;
			}
			if(p.date.compareTo(maxDate) >= 0) {
				maxDate = p.date;
			}
		}
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/YY");
		
		return dtf.format(minDate) + " - " + dtf.format(maxDate);
		
	}
	
	/**
	 * Gets photos of album
	 * 
	 * @return list of photos of album
	 */
	public List<Photo> getPhotos() {
		return photos;
	}
	
	/**
	 * Gets most recent photo
	 * 
	 * @return most recent photo
	 */
	public Photo getLastPhoto() {
		Photo p = photos.get(photos.size()-1);
		return p;
	}

	/**
	 * Sets album name
	 * 
	 * @param albumName album name
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	/**
	 * Sets date range
	 * 
	 * @param dateRange date range
	 */
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
	
	/**
	 * Sets photo list
	 * 
	 * @param photos list of photos
	 */
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	/**
	 * Gets path of image
	 * 
	 * @return image path
	 */
	public String getImgSrc() {
		if(this.getPhotos().size() == 0) {
			return Album.blankTemplate;
		}
		return this.photos.get(photos.size() - 1).getImgSrc();
	}

	/**
	 * Sets image path
	 * 
	 * @param imgSrc image path
	 */
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	
	@Override
	public String toString() {
		return this.albumName;
	}
	
	/**
	 * Renames album
	 * 
	 * @param newName new album name
	 */
	public void rename(String newName) {
		this.albumName = newName;
	}
	

	
	
}
