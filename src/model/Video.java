package model;

import java.io.File;
import java.io.Serializable;

import javafx.scene.media.Media;

public class Video implements Serializable{

	private static final long serialVersionUID = 1;
	private Media media;
	private String name;
	private String size;
	private boolean isPlaying;
	private Video nextVideo;
	private Video prevVideo;
	private String filePath;
	private double sizing;
	/**
	 * Constructor de la clase video
	 * @param path : ruta del video
	 */
	public Video(String path) {
		File file = new File(path);
		filePath = file.toURI().toString();
		media = new Media(filePath);
		name = file.getName();
		sizing = file.length()/1048576;
		size = sizing + " MB";
		
		
	}
/**
 * Devuelve el valor del atributo media.
 * @return media:Media representa el archivo a reproducir.
 */
	public Media getMedia() {
		return media;
	}
/**
 * Configura un nuevo valor para el atributo media.
 * @param media:Media el nuevo video a reproducir.		
 */
	public void setMedia(Media media) {
		this.media = media;
	}
/**
 * Devuelve el valor del atributo name.
 * @return name:String atributo name.
 */
	public String getName() {
		return name;
	}
/**
 * Se encarga de cambiar el nombre del video.
 * @param name:String nombre del video.
 */
	public void setName(String name) {
		this.name = name;
	}
/**
 * Se encarga de devolver el tamaño en MB del video.
 * @return size:String tamaño del video.
 */
	public String getSize() {
		return size;
	}
/**
 * Se encarga de cambiar el tamaño del video.
 * @param size:String nuevo tamaño del video.
 */
	public void setSize(String size) {
		this.size = size;
	}
/**
 * Devuelve si se reproduce o no la canción.
 * @return isPlaying:boolean si se reproduce o no la canción.
 */
	public boolean isPlaying() {
		return isPlaying;
	}
/**
 * Se encarga de configurar si se reproduce o no la canción.
 * @param isPlaying:boolean true or false.
 */
	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
/**
 * Se encarga de devolver el siguiente video.
 * @return nextVideo:Video siguiente viideo.
 */
	public Video getNextVideo() {
		return nextVideo;
	}
/**
 * Se encarga de configurar el siguiente video.
 * @param nextVideo:Video Representa el siguiente video de la lista.
 */
	public void setNextVideo(Video nextVideo) {
		this.nextVideo = nextVideo;
	}
	/**
	 * Se encarga de devolver el anterior video.
	 * @return prevVideo:Video anterior viideo.
	 */
	public Video getPrevVideo() {
		return prevVideo;
	}
	/**
	 * Se encarga de configurar el anterior video.
	 * @param prevVideo:Video Representa el anterior video de la lista.
	 */
	public void setPrevVideo(Video prevVideo) {
		this.prevVideo = prevVideo;
	}
/**
 * Devuelve la ruta del video.
 * @return filePath:String ruta del video
 */
	public String getFilePath() {
		return filePath;
	}
/**
 * Configura nueva ruta del video.
 * @param filePath:String nueva ruta del video.
 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
/**
 * Retorna el tamaño del video en valor numérico..
 * @return sizing:double tamaño del video numérico.
 */
	public double getSizing() {
		return sizing;
	}
/**
 * Se encarga de configurar un nuevo valor numérico de tamaño para el video
 * @param sizing:double nuevo tamaño numérico.
 */
	public void setSizing(double sizing) {
		this.sizing = sizing;
	}
	/**
	 * Devuelve el estado del objeto video.
	 * @return msg:String estado del objeto.
	 */
	public String toString() {
		String msg = "";
		String separator = ", ";
		msg = name + separator + size;
		return msg;
	}

}
