package photos.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import photos.main.Admin;
import photos.main.Alerts;
import photos.main.Photos;
import photos.main.User;
import photos.model.Photo;
import photos.model.Tag;

/**
 * Controller for the Tag scene
 * 
 * @author Ibrahim Khajanchi
 * @author Karl Sequeira
 */

public class TagController implements Screen {
	
	/**
	 * ListView widget (displays tags)
	 */
	@FXML
	private ListView<String> listView;
	
	/**
	 * ChoiceBox that contains preset tag types
	 */
	@FXML
	private ChoiceBox<String> presetTagTypes;
	
	/**
	 * TextField to input tag type
	 */
	@FXML
	private TextField tagType;
	
	/**
	 * TextField to input tag value
	 */
	@FXML
	private TextField tagValue;
	
	/**
	 * Add new tag button
	 */
	@FXML
	private Button newTag;
	
	/**
	 * Delete tag button
	 */
	@FXML
	private Button deleteTag;
	
	/**
	 * Button to go back to previous screen
	 */
	@FXML
    private MenuItem goBack;

    /**
     * Button to logout (goes to logout screen)
     */
	@FXML
    private MenuItem logout;

    /**
     * Button to quit out of the whole application 
     */
	@FXML
    private MenuItem quit;
    
    /**
     * Contains string representation of each tag (displayed to ListView)
     */
	private ObservableList<String> tagsList;
    
    /**
     * Photo of which the tags belong to
     */
	private Photo photo = PhotoController.photo; 
	
	/**
	 * Current user
	 */
	private User u = Admin.getCurrentUser();
    
    /**
     * Loads ListView with appropriate tags and preset tag types ChoiceBox
     * 
     * @param mainstage primary stage
     */
	public void start(Stage mainstage) {
    	tagsList = FXCollections.observableArrayList();
    	
    	
    	for(String key : photo.tags.keySet()) {
    		for(int i = 0; i < photo.tags.get(key).size(); i++) {
    			System.out.println(key + " " + photo.tags.get(key).get(i).toString());
    		}
    	}
    	
    	
    	
    	for (String key: photo.tags.keySet()) {
    		for (int i=0; i<photo.tags.get(key).size(); i++) {
    			String s = photo.tags.get(key).get(i).toString() + " | " + key; //to put in listview
    			tagsList.add(s);
    		}
    	}
    	listView.setItems(tagsList);
    	
    	if (tagsList!=null && !tagsList.isEmpty()) {
			listView.getSelectionModel().clearAndSelect(0);
		}
    	
    	presetTagTypes.getItems().add(null);
    	if (u.tagTypes.isEmpty()) { //first time
    		//for the choice box
    		presetTagTypes.getItems().add("location");
    		presetTagTypes.getItems().add("person");
    		
    		u.tagTypes.add("location");
    		u.tagTypes.add("person");
    	}
    	else {
	    	for (String s: u.tagTypes) {
	    		if (!presetTagTypes.getItems().contains(s)) {
	    			presetTagTypes.getItems().add(s);
	    		}
	    	}
    	}
    	
		
    	presetTagTypes
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
			(obs,oldVal,newVal) -> foo(newVal.intValue()));    
   }
    
    /**
     * Sets tagType TextField to appropriate tag type
     * 
     * @param i newVal index
     */
	public void foo(int i) {    	
    	if(i > 0) {
    		tagType.setText(presetTagTypes.getItems().get(i));
    	}
    }

    /**
     * Adds new tag to ListView
     * 
     * @param e Action event
     */
	public void addNewTag(ActionEvent e) {
    	boolean result = Alerts.confirmation("Are you sure you want to add new tag", "Add New Tag");
    	if (result) {
	    	String type = tagType.getText(); //location
	    	String value = tagValue.getText(); //NY
	    	Tag tag = new Tag(type);
	    	
	    	if (type==null || type.equals("") || value==null || value.equals("")) { //must be something in both text fields
	    		Alerts.IllegalField();
	    		return;
	    	}
	    	//case 1: you already have Person, Karl, and you try adding Person, Karl
	    	if(presetTagTypes.getItems().contains(type)) {
	    		if(photo.tags.get(value)!= null && photo.tags.get(value).contains(tag)) {
	    			Alerts.duplicateError("Tag");
	    			return;
    			}
	    		else {
	    			photo.tags.put(value, new ArrayList<Tag>());
	    			photo.tags.get(value).add(tag);
	    			tagsList.add(type + " | " + value);
	    		}
	    	}
	    	else {
		    	photo.tags.put(value, new ArrayList<Tag>());
		    	photo.tags.get(value).add(tag);
				tagsList.add(type + " | " + value);
				u.tagTypes.add(type);
				presetTagTypes.getItems().add(type);	
	    	}
	    	listView.getSelectionModel().select(tagsList.size()-1);
    	}
    	for(String s : presetTagTypes.getItems()) {
    		System.out.println(s);
    	}
    }
    
    /**
     * Deletes tag from ListView
     * 
     * @param e Action event
     */
	public void deleteTag(ActionEvent e) {
    	boolean result = Alerts.confirmation("Are you sure you want to delete selected tag", "Delete Tag");
    	if (result) {
    		if (tagsList.size()==0) { //trying to delete from empty tags list
    			Alerts.deleteError();
    			return;
    		}
    		int index = listView.getSelectionModel().getSelectedIndex();
    		String tagRep = listView.getSelectionModel().getSelectedItem(); //how the tag is represented on the listview widget
    		int barIndex = tagRep.indexOf("|");
    		String key = tagRep.substring(0, barIndex-1);
    		String value = tagRep.substring(barIndex+2,tagRep.length());
    		
    		//removes tag from listview
    		if (tagsList.size()==1) {
    			tagsList.remove(index);
    		}
    		else if (index==tagsList.size()-1) {
    			listView.getSelectionModel().selectPrevious();
				tagsList.remove(index);
    		}
    		else {
    			listView.getSelectionModel().selectNext();
				tagsList.remove(index);
    		}
    		
    		if (photo.tags.get(value).size()==1) { //specific key only has 1 value to it
    			photo.tags.remove(value);
    		}
    		else {
    			Tag tag = new Tag(key);
    			photo.tags.get(value).remove(tag);
    		}
    	}
    	for(String s : presetTagTypes.getItems()) {
    		System.out.println(s);
    	}
    }
	
	@Override
	public void goBack(ActionEvent e) throws IOException {
		Stage primaryStage = Photos.pStage;
		Parent root = FXMLLoader.load(getClass().getResource("/photos/view/Photo.fxml"));
		primaryStage.setTitle("Photo View");
		primaryStage.setScene(new Scene(root, 1315, 810));
		primaryStage.show();
	}
	
	/**
	 * Logs out (goes to login screen)
	 */
	public void logout(ActionEvent e) throws IOException {
		logOut(e);
	}
	
	/**
	 * Quits the whole application
	 */
	public void quit(ActionEvent e) throws IOException {
		Admin.writeUser();
		Quit(e);
	}
}