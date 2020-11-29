package model;

import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class Playlist {

	private Song firstSong;
	
	public Playlist() {	
	}
	
	public void addSong(String path) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		Song newSong = new Song(path);
		
		if(firstSong==null) {
			firstSong = newSong;
		}else {
			Song current = firstSong;
			while(current.getNextSong()!=null) {
				current = current.getNextSong();
			}
			current.setNextSong(newSong);
		}
	}
	
	public Song searchSong(String c) {
		Song b = null;
		
		Song current = firstSong;
		while(current!=null && b==null) {
			if(c.equalsIgnoreCase(current.getTitle())) {
				b = current;
			}
			current = current.getNextSong();
		}
		
		return b;
	}
	
	public Song removeSong(String c) {
		Song b = null;
		
		if(firstSong!=null) {
			if(c.equalsIgnoreCase(firstSong.getTitle())) {
				b = firstSong;
				firstSong = firstSong.getNextSong();
			}else {
				Song current = firstSong;
				while(current.getNextSong()!=null
						&& !c.equals(current.getNextSong().getTitle())) {
					current = current.getNextSong();
				}
				
				if(current.getNextSong()!=null) { //current is before
					b = current.getNextSong();
					current.setNextSong(current.getNextSong().getNextSong());
				}
			}
		}
		
		return b;
	}

	public Song getFirstSong() {
		return firstSong;
	}
	
}
