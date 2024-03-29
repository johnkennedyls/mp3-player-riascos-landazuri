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

public class Song implements Serializable{

	private static final long serialVersionUID = 1;
	private String artist;
	private String album;
	private String title;
	private String path;
	private Song nextSong;
	private Song prevSong;
	/**
	 * Representa una canci�n
	 * @param path:String ruta de la canci�n.
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 */
	
	
	public Song(String pathF) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setPath(pathF);
		AudioFile f = AudioFileIO.read(new File(pathF));
		Tag tag = f.getTag();
		artist = tag.getFirst(FieldKey.ARTIST);
		album = tag.getFirst(FieldKey.ALBUM);
		title = tag.getFirst(FieldKey.TITLE);
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * Representa el estado del objeto song
	 * @return msg:estado del objeto song
	 */
	public String toString() {
		String msg = "";
		String separator = ";";
		msg += title + separator + artist + separator + album + "\n";
		return msg;
	}
	
}
