package ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MP4Controller implements Initializable {

	private MediaPlayer mediaPlayer;

	@FXML
	private MediaView mediaView;

	@FXML
	private Slider slider;

	@FXML
	private Slider seekSlider;

	/**
	 * Este método es el constructor de MP4Controller
	 * @param filePath es la ruta del video
	 */
	public MP4Controller(String filePath) {
		Media media = new Media(filePath);
		mediaPlayer = new MediaPlayer(media);
	}
	
	/**
	 * Este método inicializa el MediaPlayer, configura la vista, el volumen y el slider que indica la duración
	 */
	private void components() {
		mediaView.setMediaPlayer(mediaPlayer);
		DoubleProperty width = mediaView.fitWidthProperty();
		DoubleProperty hight = mediaView.fitHeightProperty();

		width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
		hight.bind(Bindings.selectDouble(mediaView.sceneProperty(), "hight"));

		slider.setValue(mediaPlayer.getVolume() * 100);
		
		slider.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				mediaPlayer.setVolume(slider.getValue()/100);

			}
		});

		mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {

			@Override
			public void changed(ObservableValue<? extends Duration> observable, Duration oldValue,
					Duration newValue) {
				seekSlider.setValue(newValue.toSeconds());

			}
		});

		seekSlider.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				mediaPlayer.seek(Duration.seconds(seekSlider.getValue()));							
			}

		});
	}

	/**
	 * Este método pausa el video
	 * @param event
	 */
	@FXML
	private void pauseVideo(ActionEvent event) {
		mediaPlayer.pause();
	}

	/**
	 * Este método reanuda el video
	 * @param event
	 */
	@FXML
	private void playVideo(ActionEvent event) {
		components();
		mediaPlayer.play();
		mediaPlayer.setRate(1);
	}

	/**
	 * Este método reinicia el video
	 * @param event
	 */
	@FXML
	private void stopVideo(ActionEvent event) {
		mediaPlayer.stop();
	}

	/**
	 * Este metodo hace que el video vaya más rapido
	 * @param event
	 */
	@FXML
	private void fastVideo(ActionEvent event) {
		mediaPlayer.setRate(1.5);
	}

	/**
	 * Este método hace que el video vaya aun mas rapido
	 * @param event
	 */
	@FXML
	private void fasterVideo(ActionEvent event) {
		mediaPlayer.setRate(2);
	}

	/**
	 * Este método hace que el video vaya más despacio
	 * @param event
	 */
	@FXML
	private void slowVideo(ActionEvent event) {
		mediaPlayer.setRate(.75);
	}

	/**
	 * Este método hace que el video vaya aún más despacio
	 * @param event
	 */
	@FXML
	private void slowerVideo(ActionEvent event) {
		mediaPlayer.setRate(.50);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

}
