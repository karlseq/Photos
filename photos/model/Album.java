package photos.model;

import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Album implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String blankTemplate = "./data/blank.jpg";

	private String albumName;
	@SuppressWarnings("unused")
	private String dateRange;
	private List<Photo> photos;
	@SuppressWarnings("unused")
	private String imgSrc;


	public Album(String albumName) {
		this.albumName = albumName;
		this.photos = new ArrayList<>();
	}
	
	
	public String getAlbumName() {
		return albumName;
	}

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
	public List<Photo> getPhotos() {
		return photos;
	}
	public Photo getLastPhoto() {
		Photo p = photos.get(photos.size()-1);
		return p;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public String getImgSrc() {
		if(this.getPhotos().size() == 0) {
			return Album.blankTemplate;
		}
		return this.photos.get(photos.size() - 1).getImgSrc();
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	
	@Override
	public String toString() {
		return this.albumName;
	}
	
	public void rename(String newName) {
		this.albumName = newName;
	}
	

	
	
}
