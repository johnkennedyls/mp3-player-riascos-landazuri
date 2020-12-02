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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
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

public class GUI_MP3 {

	private Manager manager;

	private Playlist temp;

	public GUI_MP3(Manager m){
		manager = m;
	}
	boolean isPlaying = false;

	@FXML
	private MediaPlayer mp;

	@FXML
	private BorderPane mainPane;

	@FXML
	private BorderPane userView;

	@FXML
	private CheckBox yes;

	@FXML
	private CheckBox no;

	@FXML
	private Label currentTime;

	@FXML
	private Label finalDuration;

	@FXML
	private Button btnCancel;

	@FXML
	private Button createPlaylist;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtPassword;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtPlaylistName;

	@FXML
	private TableView<Playlist> tvPlaylistsGroup;

	@FXML
	private TableColumn<Playlist, String> tcName;

	@FXML
	private TableView<Song> tvPlaylist;

	@FXML
	private TableColumn<Song, String> tcTitle;

	@FXML
	private TableColumn<Song, String> tcArtist;

	@FXML
	private TableColumn<Song, String> tcAlbum;

	@FXML
	private TableColumn<Song, String> tcDuration;

	@FXML
	private TableView<Video> tvVideo;

	@FXML
	private TableColumn<Video, String> tcTitleV;

	@FXML
	private TableColumn<Video, String> tcSize;

	@FXML
	private TableColumn<Video, String> tcDurationV;

	public void initialize() {
		manager = new Manager();
	}

	private void initializePlaylistsGroupView() {
		ObservableList<Playlist> observableList = FXCollections.observableArrayList(manager.getPlaylists());
		tvPlaylistsGroup.setItems(observableList);
		tcName.setCellValueFactory(new PropertyValueFactory<Playlist,String>("name")); 
	}

	private void initializeSongsView(Playlist temp) {
		ObservableList<Song> observableList = FXCollections.observableArrayList(temp.getPlaylist());
		tvPlaylist.setItems(observableList);
		tcTitle.setCellValueFactory(new PropertyValueFactory<Song,String>("title")); 
		tcArtist.setCellValueFactory(new PropertyValueFactory<Song,String>("artist"));
		tcAlbum.setCellValueFactory(new PropertyValueFactory<Song,String>("album"));	
	}

	private void initializeVideosView(Playlist temp) {
		ObservableList<Video> observableList = FXCollections.observableArrayList(temp.getPlaylistV());
		tvVideo.setItems(observableList);
		tcTitleV.setCellValueFactory(new PropertyValueFactory<Video,String>("name")); 
		tcSize.setCellValueFactory(new PropertyValueFactory<Video,String>("size"));
	}

	@FXML
	void logIn(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logIn.fxml"));
		fxmlLoader.setController(this);   
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
			System.exit(0);
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
		if (tvPlaylist.getSelectionModel().getSelectedItem() != null) {
			manager.setSongPlaying(tvPlaylist.getSelectionModel().getSelectedItem());
			mp = new MediaPlayer(tvPlaylist.getSelectionModel().getSelectedItem().getMedia());
			mp.play();
			isPlaying = true;
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
	void addToSelectedPlaylist(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("music files", "*.mp3"));
		File f = fc.showOpenDialog(null);
		String path = f.getAbsolutePath();
		path.replace("\\", "/");
		try {
			manager.getPlaylists().get(0).addSong(path);
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
	}

	private void addMedia(Playlist selected) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Music files", "*.mp3"));
		File f = fc.showOpenDialog(null);
		String path = f.getAbsolutePath();
		path.replace("\\", "/");
		try {
			selected.addSong(path);
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
	}

	private void addVideo(Playlist selected) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Video files", "*.mp4"));
		File f = fc.showOpenDialog(null);
		String path = f.getAbsolutePath();
		path.replace("\\", "/");
		try {
			selected.addSong(path);
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
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
	void createPlaylist(ActionEvent event) {
		if (yes.isSelected() && no.isSelected()) {
			new Alert(Alert.AlertType.WARNING,"You should select just one option").showAndWait();
		}
		else if (yes.isSelected()) {
			if (txtPlaylistName.getText().isEmpty()) {
				new Alert(Alert.AlertType.WARNING,"You should give it a name").showAndWait();
			}
			else {
				manager.addPlaylist(txtPlaylistName.getText(), "MP4");
				new Alert(Alert.AlertType.INFORMATION,"Playlist created!").showAndWait();
				initializePlaylistsGroupView();
			}
		}
		else if (no.isSelected()) {
			if (txtPlaylistName.getText().isEmpty()) {
				new Alert(Alert.AlertType.WARNING,"You should give it a name").showAndWait();
			}
			else {
				manager.addPlaylist(txtPlaylistName.getText());
				new Alert(Alert.AlertType.INFORMATION,"Playlist created!").showAndWait();
				initializePlaylistsGroupView();
			}
		}
	}

	@FXML
	void showContent(MouseEvent event) throws IOException {
		temp = tvPlaylistsGroup.getSelectionModel().getSelectedItem();
		for (int i = 0; i < manager.getPlaylists().size(); i++) {
			if (temp == manager.getPlaylists().get(i)) {
				if (manager.getPlaylists().get(i).getContent() == null) {
					if (event.getButton()==MouseButton.PRIMARY) {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("songsTable.fxml"));
						fxmlLoader.setController(this);    	
						Parent songsTable = fxmlLoader.load();
						userView.setCenter(songsTable);
						initializeSongsView(temp);
					}
					else if (event.getButton()==MouseButton.SECONDARY) {
						addMedia(manager.getPlaylists().get(i));
					}
				}
				else {
					if (event.getButton()==MouseButton.PRIMARY) {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("videoTable.fxml"));
						fxmlLoader.setController(this);    	
						Parent videoTable = fxmlLoader.load();
						userView.setCenter(videoTable);
						initializeVideosView(temp);
					}
					else if (event.getButton()==MouseButton.SECONDARY) {
						addVideo(manager.getPlaylists().get(i));
					}
				}
			}
		}
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
