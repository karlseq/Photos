package photos.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javafx.scene.image.Image;
import photos.model.Album;
import photos.model.Photo;

/**
 * Represents the User object
 * 
 * @author ibrahimkhajanchi
 * @author KarlSequiera
 *
 */
public class User implements Serializable{
	
	/**
	 * The serial version UID used for serialization
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String blankImage = "./data/default-user-image.jpeg";

	/**
	 * The user-name for the User 
	 */
	private String username;
	
	/**
	 * The user's list of albums
	 */
	private List<Album> albums;
	
	/**
	 * The user's profile pic
	 */
	public Photo profilePic;
	
	/**
	 * HashSet of all unique tag types
	 */
	public HashSet<String> tagTypes = new HashSet<String>();
	
	
	/**
	 * Creates a user by taking a name for the user
	 * @param name: name for the user
	 */
	public User(String name) {
		this.username = name;
		this.albums = new ArrayList<>();
		this.profilePic = new Photo(blankImage,new Date()); //date is arbitrary for profile pic
	}
	
	/**
	 * Used for printing the user
	 */
	public String toString() {
		return username;
	}
	
	/**
	 * This is used to get the user's user-name
	 * @return User's user-name
	 */
	public String getUserName() {
		return username;
	}
	
	/*
	 * Tells you if a user is equal to another user by checking equality of user-names
	 */
	public boolean equals(Object obj) {
		if (obj==null || this.getClass()!=obj.getClass()) {
			return false;
		}
		User newUser = (User)obj;
		
		return this.username.equals(newUser.username);
	}
	
	/**
	 * Adds an album to ther user's album list
	 * @return false if not added, true if added
	 */
	public boolean addAlbum(Album album) {
		for(Album a : albums) {
			if(a.getAlbumName().equals(album.getAlbumName())) {
				return false;
			}
		}
		this.albums.add(album);
		return true;
	}
	
	/**
	 * Delete's album from user's album list
	 * @param album : album you wish to delete
	 * @return True if deleted, false if not deleted
	 */
	public boolean deleteAlbum(Album album) {
		return this.albums.remove(album);
	}
	
	/**
	 * Renames the given album
	 * @param a : album you wish to rename
	 * @param newName : the new name for your album
	 * @return null if if not renamed, or the renamed album
	 */
	public Album renameAlbum(Album a, String newName) {
		if(newName.isBlank()) {
			Alerts.IllegalField();
			return null;
		}
		for(Album alb: this.albums) {
			if(alb.getAlbumName().equals(newName)) {
				Alerts.duplicateError("Album with given name");
				return null;
			}
		}
		a.rename(newName);
		return a;
	}
	
	/**
	 * Get the user's album list
	 * @return album list for the user
	 */
	public List<Album> getAlbums(){
		return this.albums;
	}
	



}
