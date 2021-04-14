package photos.main;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;

public class Admin {
	
	public static ArrayList<User> userList = new ArrayList<User>();
	private static User currentUser;
	public static final String storeDir = "dat";
	public static final String storeFile = "users3.dat";

	public static ArrayList<User> getUserList() {
		return userList;
	}

	public static void setUserList(ArrayList<User> list) {
		userList = list;
	}
	
	public static void addUser(String userName) {
		User u = new User(userName);
		userList.add(u);
	}
	
	public static void addUser(User newUser) {
		userList.add(newUser);
	}
	
	public static void deleteUser(int index) {
		userList.remove(index);
	}
	
	public static User containsUser(String userName) {
		for(User u: userList) {
			if(u.getUserName().equals(userName)) {
				return u;
			}
		}
		return null;
	}
	
	public static User getCurrentUser() {
		return currentUser;
	}
	
	public static void setCurrentUser(User u) {
		currentUser = u;
	}
	
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
	
	//this is where we load the users into the admin's list
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
