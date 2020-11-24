package ui;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import model.Manager;

public class GUI_MP3 {

	private Manager manager;
	
	public GUI_MP3(Manager m) {
		manager = m;
	}
	boolean isPlaying = false;
	
	@FXML
	private MediaPlayer mp;

    @FXML
    private Label songPlaying;

    @FXML
    void Forward(ActionEvent event) {
    	if (mp != null) {
			mp.seek(mp.getCurrentTime().multiply(1.5));
		}
    }

    @FXML
    void LoopMusic(ActionEvent event) {
    	if (isPlaying == true && mp != null) {
    		 mp.setOnEndOfMedia(new Runnable() {
    		       public void run() {
    		         mp.seek(Duration.ZERO);
    		       }
    		   });
    		  mp.play();
		}
    }

    @FXML
    void PauseMusic(ActionEvent event) {
    	if (isPlaying == true && mp != null) {
			mp.pause();
			isPlaying = false;
		}
    }

    @FXML
    void PlayMusic(ActionEvent event) {
    	if (isPlaying == false && mp != null) {
			mp.play();
			isPlaying = true;
		}
    }
    
    @FXML
    void StopMusic(ActionEvent event) {
    	if (mp != null) {
			mp.stop();
			isPlaying = false;
		}
    }

    @FXML
    void Backward(ActionEvent event) {
    	if (mp != null) {
			mp.seek(mp.getCurrentTime().divide(1.5));
		}
    }
    
    @FXML
    void LoadSong(ActionEvent event) {
    	FileChooser fc = new FileChooser();
    	fc.getExtensionFilters().add(new ExtensionFilter("music files", "*.mp3"));
    	File f = fc.showOpenDialog(null);
    	String path = f.getAbsolutePath();
    	path.replace("\\", "/");
    	songPlaying.setText(path);
    	Media media = new Media(new File(path).toURI().toString());
    	mp = new MediaPlayer(media);
    	mp.setAutoPlay(true);
    	isPlaying = true;
    }

}
