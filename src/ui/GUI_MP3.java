package ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import model.Manager;
import model.Song;

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
	private TableView<Song> tvPlaylist;

	@FXML
	private TableColumn<Song, String> tcNumber;

	@FXML
	private TableColumn<Song, String> tcTitle;

	@FXML
	private TableColumn<Song, String> tcArtist;

	@FXML
	private TableColumn<Song, String> tcAlbum;

	@FXML
	private TableColumn<Song, String> tcDuration;

	public void initialize() {
		manager = new Manager();
	}

	private void initializeTableView() {
		ArrayList<Song> playlist = playlist();
		if (!playlist.isEmpty()) {
			ObservableList<Song> observableList;
			observableList = FXCollections.observableArrayList(playlist);

			tvPlaylist.setItems(observableList);
			tcTitle.setCellValueFactory(new PropertyValueFactory<Song,String>("title")); 
			tcArtist.setCellValueFactory(new PropertyValueFactory<Song,String>("artist"));
			tcAlbum.setCellValueFactory(new PropertyValueFactory<Song,String>("album"));
		}
	}

	private ArrayList<Song> playlist(){
		ArrayList<Song> playlist = new ArrayList<>();
		Song song = manager.getPlaylist().getFirstSong();
		while (song != null) {
			playlist.add(song);
			song = song.getNextSong();
		}
		return playlist;
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
	public void showStudents(MouseEvent event) {
		System.out.println("Hola"); 
		System.out.println(tvPlaylist.getSelectionModel().getSelectedItem());
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
	void next(ActionEvent event) {
		if (mp != null) {
			if (manager.getSongPlaying().getNextSong() != null) {
				mp = new MediaPlayer(manager.getSongPlaying().getNextSong().getMedia());
				mp.play();
			}	
		}
	}

	@FXML
	void previous(ActionEvent event) {
		if (mp != null) {
			if (manager.getSongPlaying().getPrevSong() != null) {
				mp = new MediaPlayer(manager.getSongPlaying().getPrevSong().getMedia());
				mp.play();
			}	
		}
	}

	@FXML
	void LoadSong(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("music files", "*.mp3"));
		File f = fc.showOpenDialog(null);
		String path = f.getAbsolutePath();
		path.replace("\\", "/");
		try {
			manager.getPlaylist().addSong(path);
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		initializeTableView();
	}

}
