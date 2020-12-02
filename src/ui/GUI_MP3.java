package ui;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Manager;
import model.Playlist;
import model.Song;
import model.Video;
import model.VideoManager;

public class GUI_MP3 {

	private Manager manager;

	public GUI_MP3(Manager m){
		manager = m;
	}
	boolean isPlaying = false;

	@FXML
	private BorderPane mainPane;

	@FXML
	private MediaPlayer mp;

	@FXML
	private Label currentTime;

	@FXML
	private Label finalDuration;

	@FXML
	private Button btnCancel;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtPassword;

	@FXML
	private TextField txtId;

	@FXML
	private TableView<Playlist> tvPlaylistGroup;

	@FXML
	private TableColumn<Playlist, String> tcName;

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

	@FXML
	private TableView<VideoManager> tvVideo;

	@FXML
	private TableColumn<Video, Integer> tcNumberV;

	@FXML
	private TableColumn<Video, String> tcTitleV;

	@FXML
	private TableColumn<Video, String> tcAuthor;

	@FXML
	private TableColumn<Video, String> tcWeight;

	@FXML
	private TableColumn<Video, String> tcDurationV;

	public void initialize() {
		manager = new Manager();
	}

	private void initializeTableView() {
		ObservableList<Playlist> observableList = FXCollections.observableArrayList(manager.getPlaylistManager().getPlaylists());
		tvPlaylistGroup.setItems(observableList);
		tcName.setCellValueFactory(new PropertyValueFactory<Playlist,String>("name")); 
	}

	@FXML
	void logIn(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logIn.fxml"));
		Parent root = fxmlLoader.load();
		Stage mainStage = new Stage();
		Scene scene = new Scene(root);
		mainStage.setScene(scene);
		mainStage.setTitle("My Way");
		mainStage.show();
	}

	@FXML
	void signUp(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signUp.fxml"));
		fxmlLoader.setController(this);    	
		Parent root = fxmlLoader.load();
		Stage mainStage = new Stage();
		Scene scene = new Scene(root);
		mainStage.setScene(scene);
		mainStage.setTitle("My Way");
		mainStage.show();
	}

	@FXML
	void signUpUser(ActionEvent event) throws IOException  {
		boolean changed = false;

		try {
			String info = manager.getRegister().createRegister(txtName.getText(), txtEmail.getText(), txtPassword.getText(), Integer.parseInt(txtId.getText()));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Register");
			alert.setHeaderText(info);
			alert.setContentText("Account created!");

			alert.showAndWait();
			changed = true;
		} catch (NumberFormatException e) {
			new Alert(Alert.AlertType.ERROR,"You should enter a integer number in the time field").showAndWait();
		} catch (SQLException e) {
			new Alert(Alert.AlertType.ERROR,"The register can't be created").showAndWait();
		}

		if (changed) {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userView.fxml"));
			fxmlLoader.setController(this);    	
			Parent root = fxmlLoader.load();
			Stage mainStage = new Stage();
			mainStage.initModality(Modality.NONE);
			Scene scene = new Scene(root);
			mainStage.setScene(scene);
			mainStage.setTitle("My Way");
			mainStage.show();
			mainPane.setVisible(true);
			mainPane.setDisable(true);
		}
	}

	@FXML
	void loadLogin(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userView.fxml"));
		fxmlLoader.setController(this);    	
		Parent root = fxmlLoader.load();
		Stage mainStage = new Stage();
		Scene scene = new Scene(root);
		mainStage.setScene(scene);
		mainStage.setTitle("My Way");
		mainStage.show();

	}

	@FXML
	void back(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcome.fxml"));

		fxmlLoader.setController(this);
		mainPane.setCenter(fxmlLoader.load());
	}

	@FXML
	void selectSongs(MouseEvent event) {
		manager.setSongPlaying(tvPlaylist.getSelectionModel().getSelectedItem());
		mp = new MediaPlayer(tvPlaylist.getSelectionModel().getSelectedItem().getMedia());
		mp.play();
		isPlaying = true;
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
			manager.getPlaylistManager().getPlaylists().get(0).addSong(path);
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
		initializeTableView();
	}

	@FXML
	void createNewPlaylist(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("playlistName.fxml"));
		fxmlLoader.setController(this);
		Parent root = fxmlLoader.load();
		Stage mainStage = new Stage();
		mainStage.initModality(Modality.APPLICATION_MODAL);
		Scene scene = new Scene(root);
		mainStage.setScene(scene);
		mainStage.setTitle("Give me a name <3");
		mainStage.show();
		btnCancel.setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						mainStage.close();
					}
				});				
	}
	
	@FXML
	void showContent(MouseEvent event) {
		manager.setSongPlaying(tvPlaylist.getSelectionModel().getSelectedItem());
		mp = new MediaPlayer(tvPlaylist.getSelectionModel().getSelectedItem().getMedia());
		mp.play();
		isPlaying = true;
	}

	@FXML
	void openVideo(MouseEvent event) {

	}

	@FXML
	void goMP4(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mp4.fxml"));

		mainPane.getChildren().clear();
		mainPane.setCenter(fxmlLoader.load());
	}

}
