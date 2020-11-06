package comp3601.player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import comp3601.player.view.PlayerController;
import javafx.application.Application;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.stage.Stage;

public class MainApp extends Application {

	// main stage of the application
	private Stage primartStage;
	// save plane
	private BorderPane root;
	private AnchorPane playerBody;
	// list of music
	ArrayList<Media> MediaList = new ArrayList<Media>();
	
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {
		this.primartStage = primaryStage;
		this.primartStage.setTitle("Team Blue Music Player");
		FileInputStream b1 = new FileInputStream(new File("src/resources/image/group.jpg"));
//    	Image play_icon = new Image("D:\\BroswerDownload\\play-arrow.png");
    	Image groupImage = new Image(b1);
    	ImageView groupImageView = new ImageView(groupImage);
    	this.primartStage.getIcons().add(groupImage);
    	
    	
		
		// init music source
		initMedia();
		// FXML initialize
		initRoot();

	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initMedia() {
		// init music
		String url1 = this.getClass().getClassLoader()
					.getResource("resources/musicsample/sample1.mp3")
					.toExternalForm();
		String url2 = this.getClass().getClassLoader()
					.getResource("resources/musicsample/sample2.mp3")
					.toExternalForm();
		String url3 = this.getClass().getClassLoader()
					.getResource("resources/musicsample/sample3.mp3")
					.toExternalForm();
		Media m1Media = new Media(url1);
		Media m2Media = new Media(url2);
		Media m3Media = new Media(url3);
		
		MediaList.add(m1Media);
		MediaList.add(m2Media);
		MediaList.add(m3Media);
				
	}
	
	public void initRoot() {
		try {
			// Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootcontrolBox.fxml"));
            
            root = (BorderPane) loader.load();
            
            Scene scene = new Scene(root);
            primartStage.setScene(scene);
            primartStage.show();
            
            
            
            
            PlayerController playerController = loader.getController();
            playerController.setMainApp(this);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Media> getMedialist(){
		return this.MediaList;
	}
	public void addMedia(Media m) {
		this.MediaList.add(m);
	}
	
	public Stage getPrimaryStage() {
		return this.primartStage;
	}
}
