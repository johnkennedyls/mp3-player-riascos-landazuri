package ui;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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
	private BorderPane mainPane;
	
	@FXML
	private MediaPlayer mp;

	@FXML
	private Label songPlaying;
	
	public void initialize() {
		manager = new Manager();
	}

	@FXML
	void logIn(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logIn.fxml"));

		fxmlLoader.setController(this);    	
		Parent logInPane = fxmlLoader.load();

		mainPane.getChildren().clear();
		mainPane.setCenter(logInPane);
	}
	
	@FXML
	void signUp(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signUp.fxml"));

		fxmlLoader.setController(this);    	
		Parent signUpPane = fxmlLoader.load();

		mainPane.getChildren().clear();
		mainPane.setCenter(signUpPane);
	}

	@FXML
	void signUpUser(ActionEvent event) throws IOException  {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userView.fxml"));

		fxmlLoader.setController(this);    	
		Parent userView = fxmlLoader.load();

		mainPane.getChildren().clear();
		mainPane.setCenter(userView);
	}
	
	@FXML
	void loadLogin(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userView.fxml"));

		fxmlLoader.setController(this);    	
		Parent userView = fxmlLoader.load();

		mainPane.getChildren().clear();
		mainPane.setCenter(userView);
	}
	
	@FXML
	void back(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcome.fxml"));

		fxmlLoader.setController(this);
		mainPane.setCenter(fxmlLoader.load());
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
	void LoadSong(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("music files", "*.mp3"));
		File f = fc.showOpenDialog(null);
		String path = f.getAbsolutePath();
		path.replace("\\", "/");
		songPlaying.setText(path);
		try {
			manager.getPlaylist().addSong(path);
			Media media = manager.getPlaylist().getFirstSong().getMedia();
			while (media != null) {
				mp = new MediaPlayer(media);
				mp.setAutoPlay(true);
				isPlaying = true;
			}
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
	}
	
}
