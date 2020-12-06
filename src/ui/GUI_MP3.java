package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import exceptions.NotFoundException;
import exceptions.UserAlreadyExistsException;
import javafx.application.Platform;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Manager;
import model.Moon;
import model.Playlist;
import model.Song;
import model.Video;
//import thread.ExportThread;
import thread.NewSearchThread;
import thread.RemoveThread;

public class GUI_MP3 {

	private Manager manager;

	private MP4Controller mp4Con;

	private Stage primaryStage;

	private Stage currentStage;

	private Playlist temp;

	public GUI_MP3(Manager m, Stage primary) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
		manager = m;
		primaryStage = primary;
		try {
			manager.loadData();
		} catch (ClassNotFoundException | IOException e) {
			new Alert(Alert.AlertType.ERROR, "Can't load the data").showAndWait();
		} catch (NotFoundException e) {
			new Alert(Alert.AlertType.ERROR, "No files created yet").showAndWait();
		}
	}
	boolean isPlaying = false;

	@FXML
	private MediaPlayer mp;

	@FXML
	private Slider slider;

	@FXML
	private Slider seekSlider;

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
	private Label numPlaylists;

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
	private TextField txtNameFileToExport;

	@FXML
	private TextField txtNameFileToImport;

	@FXML
	private TextField txtPlaylistSearcher;

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
	private TableView<Video> tvVideo;

	@FXML
	private TableColumn<Video, String> tcTitleV;

	@FXML
	private TableColumn<Video, String> tcSize;

	@FXML
	private AnchorPane moon;

	@FXML
	private Pane mainPane;

	private Moon m;

	@FXML
	private Circle mars;

	@FXML
	private Circle venus;

	@FXML
	private Circle jupiter;

	@FXML
	private Circle star1;

	@FXML
	private Circle star2;

	@FXML
	private Circle star3;

	@FXML
	private Circle star4;

	@FXML
	private Circle star5;

	@FXML
	private Circle star6;

	@FXML
	private Circle star7;

	@FXML
	private Circle star8;

	@FXML
	private Circle star9;

	@FXML
	private Circle star10;

	@FXML
	private Circle star11;

	@FXML
	private Circle star12;

	private boolean go;

	public void initialize() {
	}

	public void initializePlaylistsGroupView() {
		ObservableList<Playlist> observableList = FXCollections.observableArrayList(manager.getUsers().get(manager.userPosition(manager.getCurrent())).getPlaylists());
		tvPlaylistsGroup.setItems(observableList);
		tcName.setCellValueFactory(new PropertyValueFactory<Playlist,String>("name")); 
		tcContent.setCellValueFactory(new PropertyValueFactory<Playlist,String>("content"));
		numPlaylists.setText(manager.getUsers().get(manager.userPosition(manager.getCurrent())).getNumPlaylists() + "");
	}

	public void initializePlaylistsGroupView(List<Playlist> searched) {
		ObservableList<Playlist> observableList = FXCollections.observableArrayList(searched);
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
			String info;
			info = manager.addUser(txtName.getText(), txtEmail.getText(), txtPassword.getText(), Integer.parseInt(txtId.getText()));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Register");
			alert.setHeaderText(info);
			manager.saveData();
			alert.setContentText("Account created!");
			alert.showAndWait();
			changed = true;
		} catch (NumberFormatException e) {
			new Alert(Alert.AlertType.ERROR,"You should enter a integer number in the time field").showAndWait();
		} catch (UserAlreadyExistsException e) {
			new Alert(Alert.AlertType.ERROR,e.getMessage()).showAndWait();
		} catch (IOException e) {
			new Alert(Alert.AlertType.ERROR, "Can't save the data").showAndWait();
		} catch (CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
			e.printStackTrace();
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
			int id = Integer.parseInt(txtId.getText());
			if (manager.userExists(id)) {
				if (manager.searchUser(id).getPassWord().equals(txtPassword.getText())) {
					manager.setCurrent(id);
					new Alert(Alert.AlertType.CONFIRMATION, "Welcome " + manager.searchUser(id).getName() + "!").showAndWait();
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userView.fxml"));
					fxmlLoader.setController(this);    	
					Parent root = fxmlLoader.load();
					Stage mainStage = new Stage();
					Scene scene = new Scene(root);
					mainStage.setScene(scene);
					mainStage.setTitle("My Way");
					currentStage.close();
					mainStage.show();
					initializePlaylistsGroupView();
				}
				else {
					new Alert(Alert.AlertType.ERROR, "Incorrect password").showAndWait();
				}
			}

		} catch (IOException e) {
			new Alert(Alert.AlertType.ERROR,"Can't load the next window, verify your configuration please").showAndWait();
		} catch (NumberFormatException e) {
			new Alert(Alert.AlertType.ERROR,"You should enter a integer number in the time field").showAndWait();
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
				mp = new MediaPlayer(new Media(new File(tvPlaylist.getSelectionModel().getSelectedItem().getPath()).toURI().toString()));
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
				mp.stop();
				mp = new MediaPlayer(new Media(new File(tvPlaylist.getSelectionModel().getSelectedItem().getPath()).toURI().toString()));
				manager.setSongPlaying(manager.getSongPlaying().getNextSong());
				mp.play();
			}	
		}
	}

	@FXML
	void previous(ActionEvent event) {
		if (mp != null) {
			if (manager.getSongPlaying().getPrevSong() != null) {
				mp.stop();
				mp = new MediaPlayer(new Media(new File(tvPlaylist.getSelectionModel().getSelectedItem().getPath()).toURI().toString()));
				manager.setSongPlaying(manager.getSongPlaying().getPrevSong());
				mp.play();
			}	
		}
	}

	@FXML
	void removePlaylist(ActionEvent event) {
		temp = tvPlaylistsGroup.getSelectionModel().getSelectedItem();
		RemoveThread rt = new RemoveThread(manager, this, temp);
		rt.start();
		try {
			manager.saveData();
		} catch (IOException e) {
			new Alert(Alert.AlertType.ERROR,"Can't save the data").showAndWait();
		}
	}

	@FXML
	void searchPlaylist(KeyEvent event) {
		String searched = txtPlaylistSearcher.getText();
		NewSearchThread nst = new NewSearchThread(manager, this, searched);
		nst.start();
	}

	private void addMedia(Playlist selected) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Music files", "*.mp3"));
		File f = fc.showOpenDialog(null);
		String path = f.getAbsolutePath();
		path.replace("\\", "/");
		try {
			selected.addSong(path);
			try {
				manager.saveData();
			} catch (IOException e) {
				new Alert(Alert.AlertType.ERROR,"Can't save the data").showAndWait();
			}
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
			try {
				manager.saveData();
			} catch (IOException e) {
				new Alert(Alert.AlertType.ERROR,"Can't save the data").showAndWait();
			}
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
				manager.getUsers().get(manager.userPosition(manager.getCurrent())).addPlaylist(txtPlaylistName.getText(), "MP4");
				try {
					manager.saveData();
				} catch (IOException e) {
					new Alert(Alert.AlertType.ERROR,"Can't save the data").showAndWait();
				}
				new Alert(Alert.AlertType.INFORMATION,"Playlist created!").showAndWait();
				initializePlaylistsGroupView();
			}
		}
		else if (no.isSelected()) {
			if (txtPlaylistName.getText().isEmpty()) {
				new Alert(Alert.AlertType.WARNING,"You should give it a name").showAndWait();
			}
			else {
				manager.getUsers().get(manager.userPosition(manager.getCurrent())).addPlaylist(txtPlaylistName.getText());
				try {
					manager.saveData();
				} catch (IOException e) {
					new Alert(Alert.AlertType.ERROR,"Can't save the data").showAndWait();
				}
				new Alert(Alert.AlertType.INFORMATION,"Playlist created!").showAndWait();
				initializePlaylistsGroupView();
			}
		}
	}

	@FXML
	void showContent(MouseEvent event) {
		temp = tvPlaylistsGroup.getSelectionModel().getSelectedItem();
		for (int i = 0; i < manager.getUsers().get(manager.userPosition(manager.getCurrent())).getPlaylists().size(); i++) {
			if (temp == manager.getUsers().get(manager.userPosition(manager.getCurrent())).getPlaylists().get(i)) {
				if (manager.getUsers().get(manager.userPosition(manager.getCurrent())).getPlaylists().get(i).getContent().equals("MP3")) {
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
						addMedia(manager.getUsers().get(manager.userPosition(manager.getCurrent())).getPlaylists().get(i));
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
						addVideo(manager.getUsers().get(manager.userPosition(manager.getCurrent())).getPlaylists().get(i));
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
				manager.exportPlayListsData("data/"+fileName + "PlayLists.csv");
				manager.exportUsersData("data/"+fileName+"Users.csv");
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
			mainStage.setTitle("Export");
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
	void importUserView(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("importData.fxml"));
			fxmlLoader.setController(this);
			Parent root = fxmlLoader.load();
			Stage mainStage = new Stage();
			mainStage.initModality(Modality.APPLICATION_MODAL);
			Scene scene = new Scene(root);
			mainStage.setScene(scene);
			mainStage.setTitle("Import");
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
	void importData(ActionEvent event) {
		String fileName = txtNameFileToImport.getText();
		try {
			manager.importUsersData("data/" +fileName+"Users.csv");
			manager.saveData();
			new Alert(Alert.AlertType.INFORMATION,"Users imported").showAndWait();
		} catch (NumberFormatException | IOException | UserAlreadyExistsException | CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
			new Alert(Alert.AlertType.ERROR,"Can't import data, verify your configuration please").showAndWait();
			e.printStackTrace();
		}
	}

	@FXML
	void eclipse(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("eclipsing.fxml"));
			fxmlLoader.setController(this);
			Parent root;
			root = fxmlLoader.load();
			Stage mainStage = new Stage();
			mainStage.initModality(Modality.APPLICATION_MODAL);
			Scene scene = new Scene(root);
			mainStage.setScene(scene);
			mainStage.setTitle("Eclipse");
			mainStage.show();
			btnCancel.setOnAction(
					new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							mainStage.close();
						}
					});	
		} catch (IOException e) {
			new Alert(Alert.AlertType.ERROR,"Can't open the next window, verify your configuration please").showAndWait();
		}
	}

	@FXML
	public void move(ActionEvent event) {
		go = true;
		if(go) {
			m = new Moon(moon.getLayoutX(), 96);
			m.setMax(currentStage.getWidth());
			new Thread() {
				public void run() {
					while(go) {
						m.move();
						changes();
						Platform.runLater(new Thread() {
							public void run() {
								updateMoon(m.getX());
							}
						});

						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();
		}
	}

	@FXML
	void stop(ActionEvent event) {
		go = false;
	}

	public void updateMoon(double x) {
		if(go) {
			moon.setLayoutX(x);
		}
	}

	public void changes() {
		if((moon.getLayoutX() >= 162 &&  moon.getLayoutX() < 182)|| (moon.getLayoutX() >= 330 &&  moon.getLayoutX() < 341)) {	
			mainPane.setBackground(new Background(new BackgroundFill(Color.web("#48D1CC"), null, null)));
		}
		else if((moon.getLayoutX() >= 182 &&  moon.getLayoutX() < 202)||(moon.getLayoutX() >= 310 &&  moon.getLayoutX() < 330)) {	
			mainPane.setBackground(new Background(new BackgroundFill(Color.web("#BEBEBE"), null, null)));
		}else if((moon.getLayoutX() >= 202 &&  moon.getLayoutX() < 220)||(moon.getLayoutX() >= 290 &&  moon.getLayoutX() < 310)) {	
			mainPane.setBackground(new Background(new BackgroundFill(Color.web("#191970"), null, null)));
		}else if((moon.getLayoutX() >= 220 &&  moon.getLayoutX() < 240)||(moon.getLayoutX() >= 270 &&  moon.getLayoutX() < 290)) {	
			mainPane.setBackground(new Background(new BackgroundFill(Color.web("#000080"), null, null)));
		}else if(moon.getLayoutX() >= 240 &&  moon.getLayoutX() < 270) {	
			mainPane.setBackground(new Background(new BackgroundFill(Color.web("#000000"), null, null)));
		}
		else {
			mainPane.setBackground(new Background(new BackgroundFill(Color.web("#FFFF00" ), null, null)));
		}
		if(moon.getLayoutX() >= 162 &&  moon.getLayoutX() < 351) {
			makePlanetsAndStars();
		} else {
			hidePlanetsAndStars();
		}
	}
	public void makePlanetsAndStars() {
		mars.setVisible(true);
		venus.setVisible(true);
		jupiter.setVisible(true);
		star1.setVisible(true);
		star1.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		star2.setVisible(true);
		star2.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		star3.setVisible(true);
		star3.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		star4.setVisible(true);
		star4.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		star5.setVisible(true);
		star5.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		star6.setVisible(true);
		star6.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		star7.setVisible(true);
		star7.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		star8.setVisible(true);
		star8.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		star9.setVisible(true);
		star9.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		star10.setVisible(true);
		star10.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		star11.setVisible(true);
		star11.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		star12.setVisible(true);
		star12.setFill(Color.color(Math.random(), Math.random(), Math.random()));
	}

	public void hidePlanetsAndStars() {
		mars.setVisible(false);
		venus.setVisible(false);
		jupiter.setVisible(false);
		star1.setVisible(false);
		star2.setVisible(false);
		star3.setVisible(false);
		star4.setVisible(false);
		star5.setVisible(false);
		star6.setVisible(false);
		star7.setVisible(false);
		star8.setVisible(false);
		star9.setVisible(false);
		star10.setVisible(false);
		star11.setVisible(false);
		star12.setVisible(false);
	}

}
