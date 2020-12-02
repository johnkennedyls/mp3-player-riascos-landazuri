package model;

import java.io.File;


import javafx.scene.media.Media;

public class Video {
	private Media media;
	private String name;
	private String size;
	private String duration;
	private boolean isPlaying;
	private Video nextVideo;
	private Video prevVideo;
	private String filePath;
	private double sizing;
	
	public Video(String path) {
		File file = new File(path);
		filePath = file.toURI().toString();
		media = new Media(filePath);
		name = file.getName();
		sizing = file.length()/1048576;
		size = sizing + " MB";
		
		
	}
	
}
