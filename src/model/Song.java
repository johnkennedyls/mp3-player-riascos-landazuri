 package model;

import java.io.File;

import java.io.IOException;
import java.io.Serializable;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import javafx.scene.media.Media;

public class Song implements Serializable{

	private static final long serialVersionUID = 1;
	private Media media;
	private String artist;
	private String album;
	private String title;
	private boolean reproducing;
	private Song nextSong;
	private Song prevSong;
	
	public Song(String path) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		media = new Media(new File(path).toURI().toString());
		AudioFile f = AudioFileIO.read(new File(path));
		Tag tag = f.getTag();
		artist = tag.getFirst(FieldKey.ARTIST);
		album = tag.getFirst(FieldKey.ALBUM);
		title = tag.getFirst(FieldKey.TITLE);
		reproducing = false;
	}
	
	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public Song getNextSong() {
		return nextSong;
	}

	public void setNextSong(Song nextSong) {
		this.nextSong = nextSong;
	}

	public Song getPrevSong() {
		return prevSong;
	}

	public void setPrevSong(Song prevSong) {
		this.prevSong = prevSong;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isReproducing() {
		return reproducing;
	}

	public void setReproducing(boolean reproducing) {
		this.reproducing = reproducing;
	}
	
}
