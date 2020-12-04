package ui;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;


import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import exceptions.UserAlreadyExistsException;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Slider;
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

	private Manager manager = new Manager();

	private MP4Controller mp4Con;

	private Stage primaryStage;

	private Stage currentStage;

	private Playlist temp;

	public GUI_MP3(Manager m, Stage primary){
		manager = m;
		primaryStage = primary;
	}
	boolean isPlaying = false;

	@FXML
	private MediaPlayer mp;

	@FXML
	private Slider slider;
	
	@FXML
	private Slider seekSlider;
	
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
	private TableColumn<Playlist, String> tcContent;

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
	
	@FXML
    private TextField txtNameFileToExport;

   

	public void initialize() {
	}

	private void initializePlaylistsGroupView() {
		ObservableList<Playlist> observableList = FXCollections.observableArrayList(manager.getPlaylists());
		tvPlaylistsGroup.setItems(observableList);
		tcName.setCellValueFactory(new PropertyValueFactory<Playlist,String>("name")); 
		tcContent.setCellValueFactory(new PropertyValueFactory<Playlist,String>("content")); 
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
	void logIn(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("logIn.fxml"));
			fxmlLoader.setController(this);   
			Parent root;
			root = fxmlLoader.load();
			Stage mainStage = new Stage();
			Scene scene = new Scene(root);
			mainStage.setScene(scene);
			mainStage.setTitle("My Way");
			mainStage.show();
			currentStage = mainStage;
			primaryStage.close();
		} catch (IOException e) {
			new Alert(Alert.AlertType.ERROR,"Can't load the next window, verify your configuration please").showAndWait();
		}
	}

	@FXML
	void signUp(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signUp.fxml"));
			fxmlLoader.setController(this);    	
			Parent root = fxmlLoader.load();
			Stage mainStage = new Stage();
			Scene scene = new Scene(root);
			mainStage.setScene(scene);
			mainStage.setTitle("My Way");
			mainStage.show();
			currentStage = mainStage;
			primaryStage.close();
		} catch (IOException e) {
			new Alert(Alert.AlertType.ERROR,"Can't load the next window, verify your configuration please").showAndWait();
		}
	}

	@FXML
	void signUpUser(ActionEvent event) {
		boolean changed = false;

		try {
			String info = manager.addUser(txtName.getText(), txtEmail.getText(), txtPassword.getText(), Integer.parseInt(txtId.getText()));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Register");
			alert.setHeaderText(info);
			alert.setContentText("Account created!");
			alert.showAndWait();
			changed = true;
		} catch (NumberFormatException e) {
			new Alert(Alert.AlertType.ERROR,"You should enter a integer number in the time field").showAndWait();
		} catch (UserAlreadyExistsException e) {
			new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
		}

		if (changed) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userView.fxml"));
				fxmlLoader.setController(this);    	
				Parent root;
				root = fxmlLoader.load();
				Stage mainStage = new Stage();
				mainStage.initModality(Modality.NONE);
				Scene scene = new Scene(root);
				mainStage.setScene(scene);
				mainStage.setTitle("My Way");
				currentStage.close();
				mainStage.show();
			} catch (IOException e) {
				new Alert(Alert.AlertType.ERROR,"Can't load the next window, verify your configuration please").showAndWait();
			}
		}
	}

	@FXML
	void loadLogin(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userView.fxml"));
			fxmlLoader.setController(this);    	
			Parent root = fxmlLoader.load();
			Stage mainStage = new Stage();
			Scene scene = new Scene(root);
			mainStage.setScene(scene);
			mainStage.setTitle("My Way");
			currentStage.close();
			mainStage.show();
		} catch (IOException e) {
			new Alert(Alert.AlertType.ERROR,"Can't load the next window, verify your configuration please").showAndWait();
		}
	}

	@FXML
	void back(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("welcome.fxml"));
			fxmlLoader.setController(this);
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("My Way");
			currentStage.close();
			primaryStage.show();
		} catch (IOException e) {
			new Alert(Alert.AlertType.ERROR,"Can't load the next window, verify your configuration please").showAndWait();
		}
	}

	@FXML
	void selectSongs(MouseEvent event) {
		if (tvPlaylist.getSelectionModel().getSelectedItem() != null) {
			manager.setSongPlaying(tvPlaylist.getSelectionModel().getSelectedItem());
			if (!isPlaying) {
				mp = new MediaPlayer(tvPlaylist.getSelectionModel().getSelectedItem().getMedia());
				slider.setValue(mp.getVolume() * 100);
				
				slider.valueProperty().addListener(new InvalidationListener() {

					@Override
					public void invalidated(Observable observable) {
						mp.setVolume(slider.getValue()/100);
					}
				});
				mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {

					@Override
					public void changed(ObservableValue<? extends Duration> observable, Duration oldValue,
							Duration newValue) {
						seekSlider.setValue(newValue.toSeconds());
					}
				});

				seekSlider.setOnMousePressed(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						mp.seek(Duration.seconds(seekSlider.getValue()));							
					}

				});
				mp.play();
				isPlaying = true;
			}
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
			selected.addVideo(path);
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void createNewPlaylist(ActionEvent event) {
		try {
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
		} catch (IOException e) {
			new Alert(Alert.AlertType.ERROR,"Can't load the next window, verify your configuration please").showAndWait();
		}
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
	void showContent(MouseEvent event) {
		temp = tvPlaylistsGroup.getSelectionModel().getSelectedItem();
		for (int i = 0; i < manager.getPlaylists().size(); i++) {
			if (temp == manager.getPlaylists().get(i)) {
				if (manager.getPlaylists().get(i).getContent().equals("MP3")) {
					if (event.getButton()==MouseButton.PRIMARY) {
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("songsTable.fxml"));
							fxmlLoader.setController(this);    	
							Parent songsTable = fxmlLoader.load();
							userView.setCenter(songsTable);
							initializeSongsView(temp);
						} catch (IOException e) {
							new Alert(Alert.AlertType.ERROR,"Can't load the next window, verify your configuration please").showAndWait();
						}
					}
					else if (event.getButton()==MouseButton.SECONDARY) {
						addMedia(manager.getPlaylists().get(i));
						initializeSongsView(temp);
					}
				}
				else {
					if (event.getButton()==MouseButton.PRIMARY) {
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("videoTable.fxml"));
							fxmlLoader.setController(this);    	
							Parent videoTable = fxmlLoader.load();
							userView.setCenter(videoTable);
							initializeVideosView(temp);
						} catch (IOException e) {
							new Alert(Alert.AlertType.ERROR,"Can't load the next window, verify your configuration please").showAndWait();
						}
					}
					else if (event.getButton()==MouseButton.SECONDARY) {
						addVideo(manager.getPlaylists().get(i));
						initializeVideosView(temp);
					}
				}
			}
		}
	}

	@FXML
	void openVideo(MouseEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mp4.fxml"));
			Video v = tvVideo.getSelectionModel().getSelectedItem();
			mp4Con = new MP4Controller(v.getFilePath());
			fxmlLoader.setController(mp4Con);
			Parent root = fxmlLoader.load();
			Stage mainStage = new Stage();
			mainStage.initModality(Modality.NONE);
			Scene scene = new Scene(root);
			mainStage.setScene(scene);
			mainStage.setTitle("Video view");
			mainStage.show();
		} catch (IOException e) {
			new Alert(Alert.AlertType.ERROR,"Can't load the next window, verify your configuration please").showAndWait();
		}
	}
	
	 @FXML
	    void exportData(ActionEvent event) {
		 
		 String fileName = txtNameFileToExport.getText();
			try {
				manager.exportPlayListsData(fileName + "PlayLists.csv");
				manager.exportUsersData(fileName+"Users.csv");
				new Alert(Alert.AlertType.INFORMATION,"Playlists and users exported").showAndWait();
			} catch (FileNotFoundException e) {
				new Alert(Alert.AlertType.ERROR,"Can't export data, verify your configuration please").showAndWait();
				e.printStackTrace();
			}
			
		    
	    }
	 
	  @FXML
	    void exportUserView(ActionEvent event) {
		  initFXMLToExportData();
	    }

	private void initFXMLToExportData() {
		 try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("exportData.fxml"));
				fxmlLoader.setController(this);
				Parent root = fxmlLoader.load();
				Stage mainStage = new Stage();
				mainStage.initModality(Modality.APPLICATION_MODAL);
				Scene scene = new Scene(root);
				mainStage.setScene(scene);
				mainStage.setTitle("Give me fle name to export :v");
				mainStage.show();
				btnCancel.setOnAction(
						new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								mainStage.close();
							}
						});	
				
			} catch (IOException e) {
				new Alert(Alert.AlertType.ERROR,"Can't load the next window, verify your configuration please").showAndWait();
			}
		
	}

}
