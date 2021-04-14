package photos.model;

import java.io.Serializable;

/**
 * Represents a Tag object for a photo
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class Tag implements Serializable  {
	

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Tag value
	 */
	private String value;
	
	/**
	 * Creates a Tag object
	 * 
	 * @param name tag name
	 * @param value tag value
	 */
	public Tag(String value) {
		this.value = value;
	}
	
	
	/**
	 * Returns tag value
	 * 
	 * @return tag value
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Sets the tag value
	 * 
	 * @param value tag value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Checks if given tag is equal to called tag (checks for equality of value field)
	 */
	public boolean equals(Object obj) {
		if (obj==null || this.getClass()!=obj.getClass()) {
			return false;
		}
		Tag t = (Tag)obj;
		//return this.name.equals(t.name) && this.value.equals(t.value);
		return this.value.equals(t.value);
	}
	
	/**
	 * String representation of a Tag object (just value field)
	 */
	public String toString() {
		return value;
	}
}

