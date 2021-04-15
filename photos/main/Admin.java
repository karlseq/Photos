package photos.main;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Persistence class
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class Admin {
	
	/**
	 * List of all users
	 */
	public static ArrayList<User> userList = new ArrayList<User>();
	
	/**
	 * Current user
	 */
	private static User currentUser;
	
	/**
	 * Directory of persistence file
	 */
	public static final String storeDir = "dat";
	
	/**
	 * Persistence file
	 */
	public static final String storeFile = "users3.dat";

	/**
	 * Gets list of users
	 * 
	 * @return list of users
	 */
	public static ArrayList<User> getUserList() {
		return userList;
	}

	/**
	 * Sets user list
	 * @param list user list
	 */
	public static void setUserList(ArrayList<User> list) {
		userList = list;
	}
	
	/**
	 * Adds new user to user list
	 * 
	 * @param userName name of new user
	 */
	public static void addUser(String userName) {
		User u = new User(userName);
		userList.add(u);
	}
	
	/**
	 * Adds new user object
	 * 
	 * @param newUser new user
	 */
	public static void addUser(User newUser) {
		userList.add(newUser);
	}
	
	/**
	 * Deletes user from user list
	 * 
	 * @param index index of user to delete in user list
	 */
	public static void deleteUser(int index) {
		userList.remove(index);
	}
	
	/**
	 * Does user list contain given user
	 * 
	 * @param userName username
	 * @return matched user, null if no match
	 */
	public static User containsUser(String userName) {
		for(User u: userList) {
			if(u.getUserName().equals(userName)) {
				return u;
			}
		}
		return null;
	}
	
	/**
	 * Gets current user
	 * 
	 * @return current user
	 */
	public static User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Initializes current user
	 * 
	 * @param u user
	 */
	public static void setCurrentUser(User u) {
		currentUser = u;
	}
	
	/**
	 * Writes user list to dat file
	 * 
	 * @throws IOException IO error
	 */
	public static void writeUser() throws IOException {
		try {
			FileOutputStream fos = new FileOutputStream(storeDir + File.separator + storeFile);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(userList);
			oos.close();
			fos.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads user list from dat file
	 * 
	 * @throws IOException IO error
	 * @throws ClassNotFoundException class not found error
	 */
	@SuppressWarnings("unchecked")
	public static void loadUsers() throws IOException, ClassNotFoundException {
		try {
			FileInputStream fis = new FileInputStream(storeDir + File.separator + storeFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			userList = (ArrayList<User>) ois.readObject();
			ois.close();
			fis.close();
		}
		catch (IOException i) {
			i.printStackTrace();
			return;
		}
		catch (ClassNotFoundException c) {
			c.printStackTrace();
			return;
		}
	}
	
}
