package photos.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import photos.main.User;

import java.time.*;

/**
 * Represents a Photo object
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class Photo implements Serializable {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Path of the image
	 */
	private String imgSrc;
	
	/**
	 * HashMap of tags
	 */
	public HashMap<String,ArrayList<Tag>> tags = new HashMap<String,ArrayList<Tag>>();
	public HashSet<String> tagTypes = new HashSet<String>();
	
	/**
	 * Caption of the photo
	 */
	public String caption;
	
	/**
	 * Date the photo was taken
	 */
	public LocalDate date;
	
	/**
	 * Creates a Photo object
	 * 
	 * @param imgSrc path of the image
	 * @param d date of image
	 */
	public Photo(String imgSrc, Date d) {
		this.imgSrc = imgSrc;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int month = cal.get(Calendar.MONTH) + 1; //add 1 to sync with LocalDate
		int year = cal.get(Calendar.YEAR);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		this.date = LocalDate.of(year, month, dayOfMonth);
	}

	/**
	 * Gets path of the image
	 * 
	 * @return returns path of image
	 */
	public String getImgSrc() {
		return imgSrc;
	}

	/**
	 * Sets path of the image
	 * 
	 * @param imgSrc path of the image
	 */
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	
	/**
	 * Sets the caption of the photo
	 * 
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public boolean equals(Object obj) {
		if (obj==null || this.getClass()!=obj.getClass()) {
			return false;
		}
		Photo newPhoto = (Photo)obj;
		System.out.println("newPHoto: " + newPhoto.getImgSrc());
		System.out.println(this.imgSrc);
		System.out.println(this.imgSrc.equals(newPhoto.imgSrc));
		return this.imgSrc.equals(newPhoto.imgSrc);
	}
	
	
}
