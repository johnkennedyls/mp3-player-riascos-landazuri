package model;

import java.io.File;
import java.io.Serializable;

public class Video implements Serializable{

	private static final long serialVersionUID = 1;
	private String name;
	private String size;
	private boolean isPlaying;
	private Video nextVideo;
	private Video prevVideo;
	private String filePath;
	private double sizing;
	
	public Video(String path) {
		File file = new File(path);
		name = file.getName();
		sizing = file.length()/1048576;
		size = sizing + " MB";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public Video getNextVideo() {
		return nextVideo;
	}

	public void setNextVideo(Video nextVideo) {
		this.nextVideo = nextVideo;
	}

	public Video getPrevVideo() {
		return prevVideo;
	}

	public void setPrevVideo(Video prevVideo) {
		this.prevVideo = prevVideo;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public double getSizing() {
		return sizing;
	}

	public void setSizing(double sizing) {
		this.sizing = sizing;
	}

}
