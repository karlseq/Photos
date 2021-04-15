package photos.main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;

/**
 * Main application
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class Photos extends Application {
	
	/**
	 * Primary stage
	 */
	public static Stage pStage;
	
	@Override
	public void start(Stage primaryStage)
	throws Exception {

		File persistence = new File("./dat/users7.dat");
		if (persistence.length() != 0) {
			Admin.loadUsers();
		}

		
		if(!Admin.userList.contains(new User("stock"))) {
			User stock = new User("stock");
			Album a = new Album("stock");
			addStockPhotos(a);
			
			stock.addAlbum(a);
			Admin.addUser(stock);			
		}

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/photos/view/login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		pStage = primaryStage;
		Scene scene = new Scene(root, 500, 600);
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
		try {
			Admin.writeUser();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds stock photos to stock album
	 * 
	 * @param a stock album
	 */
	public static void addStockPhotos(Album a) {

		File f = new File("./data/stock1.jpg");
		File f1 = new File("./data/stock2.jpg");
		File f2 = new File("./data/stock3.jpg");
		File f3 = new File("./data/stock4.jpg");
		File f4 = new File("./data/stock5.jpg");

		Photo p = new Photo(f.getPath(), new Date(f.lastModified()));
		Photo p1 = new Photo(f1.getPath(), new Date(f1.lastModified()));
		Photo p2 = new Photo(f2.getPath(), new Date(f2.lastModified()));
		Photo p3 = new Photo(f3.getPath(), new Date(f3.lastModified()));
		Photo p4 = new Photo(f4.getPath(), new Date(f4.lastModified()));

	
		a.getPhotos().addAll(Arrays.asList(p, p1, p2, p3, p4));
	}
	
}
