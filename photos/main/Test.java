package photos.main;

import java.util.Date;
import java.util.HashMap;

import photos.model.Photo;


public class Test {
	
	
	public static void main(String[] args) {
		HashMap<Photo, Photo> map = new HashMap<>();
		Photo one = new Photo("./data/blank.jpg", new Date(0));
		map.put(one, one);
		Photo two = new Photo("./data/blank.jpg", new Date(0));
		if(map.get(two) != null) {
			System.out.println("YO");
		}
	}
}
