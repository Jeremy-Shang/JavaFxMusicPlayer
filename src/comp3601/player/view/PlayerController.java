package comp3601.player.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


import comp3601.player.MainApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;




public class PlayerController {
	
	@FXML
	private ListView<String> playlist = new ListView<String>();
	@FXML
	private ObservableList<String> listItem = FXCollections.observableArrayList();
	
	@FXML
	private Button previousButton;
	@FXML
	private Button nextButton;
	@FXML
	private Button playButton;
	@FXML
	private Slider progressSlider;
	@FXML 
	private Slider volumnSlider;
	@FXML
	private Label statusLabel;
	@FXML
	private Label volumnLabel;
	@FXML
	private HBox hBox;
	
	private MainApp mainApp;
	
	private MediaPlayer mp;
	
	private ArrayList<Media> mediaList;
	
	private Integer index = 0;
	private boolean isPlaying;
	private boolean progressControl;
	private boolean isMute = false;
	
	// Image of the button
	ImageView playImageView;
	ImageView pauseImageView;
	ImageView nextImageView;
	ImageView preImageView;
	ImageView muteImageView;
	ImageView volumnImageView;
	/**
	 * indicate the timing of the media
	 */
	private boolean isMediaChange;
	
    @FXML
    private void initialize() {
//    	listItem =FXCollections.observableArrayList (
//    			 "Single", "Double", "Suite", "Family App");
//    	listItem.addAll("Single", "Double", "Suite", "Family App");
//    			playlist.setItems(listItem);

    	return ;
    }
	
	/**
	 * Obtain data from mainApp
	 * @param mainApp
	 * @throws FileNotFoundException 
	 */
	public void setMainApp(MainApp mainApp) throws FileNotFoundException {
		
		this.mainApp = mainApp;
//		this.mediaList = new ArrayList<Media>();
		this.mediaList = mainApp.getMedialist();
		
		System.out.println(mediaList.size());
		
    	for(Media m: mediaList) {
    		MediaPlayer p = new MediaPlayer(m);
    		p.setOnReady(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(m != null) {
		    			ObservableMap<String, Object> map= m.getMetadata();
		    			listItem.add(map.get("title").toString());
		    			
		    		}
		    		else {
		    			System.out.println("media null");
		    		}
				}
			});
    		
    	}
    	playlist.setItems(listItem);
    	
    	playlist.getSelectionModel().selectedItemProperty().addListener(
    			 new ChangeListener<String>() {
    				 public void changed(ObservableValue<? extends String> ov,
		    			 String old_val, String new_val) {
		    			 System.out.println("Listenning");
//		    			 System.out.println(new_val);
		    			 for(Media m: mediaList) {
		    				 MediaPlayer p = new MediaPlayer(m);
		    			   		p.setOnReady(new Runnable() {
		    						@Override
		    						public void run() {
		    							// TODO Auto-generated method stub
		    							try {
		    				    			ObservableMap<String, Object> map= m.getMetadata();
//		    				    			listItem.add(map.get("title").toString());
		    				    			if(new_val == map.get("title").toString()) {
		    				    				index = mediaList.indexOf(m);
		    				    				isMediaChange = true;
		    				    				isPlaying = false;
		    				    				mp.dispose();
		    				    				handlePlay();
		    				    			}
											
										} catch (Exception e) {
											// TODO: handle exception
											System.out.println("media null");
										}

		    						}
		    					});
		    			 }
		    			 
		    			 
	    			 }
    			 });
    	setInitImage();
    	hBox.setStyle("-fx-background-color: 	#333333;");
    	
	}
	public void setInitImage() throws FileNotFoundException {
		// BUTTON NEXT
		FileInputStream b1 = new FileInputStream(new File("src/resources/image/fast-forward.png"));
    	Image fast_forward = new Image(b1);
    	nextImageView = new ImageView(fast_forward);
    	nextImageView.setFitHeight(40);
    	nextImageView.setPreserveRatio(true);
    	nextButton.setGraphic(nextImageView);
    	nextButton.setStyle("-fx-background-color: transparent;");
    	// BUTTON PREVIOUS
		FileInputStream b2 = new FileInputStream(new File("src/resources/image/backward.png"));
//    	Image play_icon = new Image("D:\\BroswerDownload\\play-arrow.png");
    	Image back_forward = new Image(b2);
    	preImageView = new ImageView(back_forward);
    	preImageView.setFitHeight(40);
    	preImageView.setPreserveRatio(true);
    	previousButton.setGraphic(preImageView);
    	previousButton.setStyle("-fx-background-color: transparent;");
    	// INIT play image
		FileInputStream b3 = new FileInputStream(new File("src/resources/image/play.png"));
//    	Image play_icon = new Image("D:\\BroswerDownload\\play-arrow.png");
    	Image playImage = new Image(b3);
    	playImageView = new ImageView(playImage);
    	playImageView.setFitHeight(40);
    	playImageView.setPreserveRatio(true);
    	playButton.setGraphic(playImageView);
    	playButton.setStyle("-fx-background-color: transparent;");
    	// Backup pause
		FileInputStream b4 = new FileInputStream(new File("src/resources/image/pause.png"));
//    	Image play_icon = new Image("D:\\BroswerDownload\\play-arrow.png");
    	Image pauseImage = new Image(b4);
    	pauseImageView = new ImageView(pauseImage);
    	pauseImageView.setFitHeight(40);
    	pauseImageView.setPreserveRatio(true);
//    	pa.setGraphic(playImageView);
//    	playButton.setStyle("-fx-background-color: transparent;");
    	
    	// Init volumn
		FileInputStream b5 = new FileInputStream(new File("src/resources/image/volume.png"));
//    	Image play_icon = new Image("D:\\BroswerDownload\\play-arrow.png");
    	Image volumnImage = new Image(b5);
    	volumnImageView = new ImageView(volumnImage);
    	volumnImageView.setFitHeight(40);
    	volumnImageView.setPreserveRatio(true);
    	volumnLabel.setGraphic(volumnImageView);
    	volumnLabel.setStyle("-fx-background-color: transparent;");
    	
    	// backup mute
		FileInputStream b6 = new FileInputStream(new File("src/resources/image/mute.png"));
//    	Image play_icon = new Image("D:\\BroswerDownload\\play-arrow.png");
    	Image muteImage = new Image(b6);
    	muteImageView = new ImageView(muteImage);
    	muteImageView.setFitHeight(40);
    	muteImageView.setPreserveRatio(true);

    	
    	
    	
	}
	
	
	@FXML
	public void handlePlay() {
		if(isPlaying == true) {
			// stop playing
			stopPlaying();
			isPlaying = false;
			// maybe change image here
			playButton.setGraphic(pauseImageView);
			statusLabel.setText("Pause");
			return ;
		}
		if(mediaList == null) {
			System.out.print("error");
		}
		
		if(mp == null || isMediaChange == true) {
			mp = new MediaPlayer(mediaList.get(index));
			isMediaChange = false;
		}
		
		ObservableMap<String, Object> map= mediaList.get(index).getMetadata();
		for(String key: map.keySet()) {
			System.out.println(key+"-"+map.get(key));
		}
		playButton.setGraphic(playImageView);
		mp.play();
		

		
		
		statusLabel.setText("Now playing: " + map.get("title").toString());
		
		isPlaying = true;
		volumnSlider.setValue(mp.getVolume() * 100 - 50);
		
		
		mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {

			@Override
			public void changed(ObservableValue<? extends Duration> arg0, Duration arg1, Duration arg2) {
				// TODO Auto-generated method stub

				progressSlider.setMax(mp.getTotalDuration().toSeconds());
				if(progressControl ==  false) {
					System.out.println("changing");
					progressSlider.setValue(arg2.toSeconds());
				}
				
			}
			
		});
		
		mp.setOnEndOfMedia(new Runnable() {
			 public void run() {
				 handleNext();
			 }
		});

	}
	
	public void stopPlaying() {
		mp.pause();
	}
	@FXML
	public void handleMute() {
		if(isMute == false) {
			volumnLabel.setGraphic(muteImageView);
			mp.setVolume(0);
			isMute = true;
		}
		else {
			volumnLabel.setGraphic(volumnImageView);
			mp.setVolume(1);
			isMute = false;
		}
		
	}
	
	@FXML
	public void handleProgressPress() {
		progressControl = true;
	}
	
	@FXML
	public void handleProgressRelease() {

		if(mp != null) {
			System.out.println("doing something");
			mp.seek(Duration.seconds(progressSlider.getValue()));
		
		}
		else {
			System.out.println("error");;
		}
		progressControl = false;
	}
	
	@FXML
	public void handleVolumn() {
		
		if(volumnSlider.isValueChanging()) {
			System.out.println(volumnSlider.getValue()/100);
			mp.setVolume(volumnSlider.getValue()/100);
		}
		
	}
	@FXML
	public void handleNext() {
		isMediaChange = true;
		// reach the end of the play list
		if(mediaList.size() == index + 1) {
			index = 0;
		}
		else {
			index = mediaList.indexOf(mp.getMedia()) + 1;
		}
		isPlaying = false;
		mp.dispose();
		handlePlay();
		
	}
	@FXML
	public void handlePrevious() {
		isMediaChange = true;
		if(index == 0) {
			index = mediaList.size() - 1;
		}
		else {
			index = index - 1;
		}
		isPlaying = false;
		mp.dispose();
		handlePlay();
		
	}
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "MP3 files (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
//            mainApp.loadPersonDataFromFile(file);
        	addNewFile(file);
        }
    }
    
    public void addNewFile(File file) {
    	System.out.println(file.toString());
    	Media m = new Media(file.toURI().toString());
    	// sych mainApp
    	this.mainApp.addMedia(m);
    	
		MediaPlayer p = new MediaPlayer(m);
	   		p.setOnReady(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
		    			ObservableMap<String, Object> map= m.getMetadata();
		    			listItem.add(map.get("title").toString());		    			
						
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("here ??");
					}

				}
			});
    	playlist.setItems(listItem);
    	
    }
    

}
