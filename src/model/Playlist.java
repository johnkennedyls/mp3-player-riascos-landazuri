package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class Playlist {

	private int idPlaylist;
	private String name;
	private String content;
	private int idUser;
	private Song firstSong;
	private Video firstVideo;
	private List<Song> playlist;
	private List<Video> playlistV;

	public Playlist(String na) {
		name = na;
		playlist = new ArrayList<>();
		content = "MP3";
	}

	public Playlist(String na, String cont) {
		name = na;
		content = cont;
		playlistV = new ArrayList<>();
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
			newSong.setPrevSong(current);
		}
		playlist();
	}

	public void addVideo(String path) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		Video newVideo = new Video(path);

		if(firstVideo == null) {
			firstVideo = newVideo;
		}else {
			Video current = firstVideo;
			while(current.getNextVideo()!=null) {
				current = current.getNextVideo();
			}
			current.setNextVideo(newVideo);
			newVideo.setPrevVideo(current);
		}
		playlistV();
	}

	public Song searchSong(String c) {
		Song found =recursiveSearchSong(firstSong, c);
		return found;
	}

	private Song recursiveSearchSong(Song current, String c) {
		Song found = null;
		if (current != null) {
			if(c.equalsIgnoreCase(current.getTitle())) {
				found = current;
			}
			else {
				return recursiveSearchSong(current.getNextSong(), c);
			}
		}	
		return found;
	}

	public Video searchVideo(String c) {
		Video found = recursiveSearchVideo(firstVideo, c);
		return found;
	}

	private Video recursiveSearchVideo(Video current, String c) {
		Video found = null;
		if (current != null) {
			if(c.equalsIgnoreCase(current.getName())) {
				found = current;
			}
			else {
				return recursiveSearchVideo(current.getNextVideo(), c);
			}
		}	
		return found;
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

	public Video removeVideo(String c) {
		Video b = null;

		if(firstVideo!=null) {
			if(c.equalsIgnoreCase(firstVideo.getName())) {
				b = firstVideo;
				firstVideo = firstVideo.getNextVideo();
			}else {
				Video current = firstVideo;
				while(current.getNextVideo()!=null
						&& !c.equals(current.getNextVideo().getName())) {
					current = current.getNextVideo();
				}

				if(current.getNextVideo()!=null) { //current is before
					b = current.getNextVideo();
					current.setNextVideo(current.getNextVideo().getNextVideo());
				}
			}
		}

		return b;
	}

	public void playlist(){
		playlist.clear();
		Song song = firstSong;
		while (song != null) {
			playlist.add(song);
			song = song.getNextSong();
		}
	}

	public void playlistV(){
		playlistV.clear();
		Video video = firstVideo;
		while (video != null) {
			playlistV.add(video);
			video = video.getNextVideo();
		}
	}

	public List<Song> getPlaylist() {
		return playlist;
	}

	public Song getFirstSong() {
		return firstSong;
	}

	public String getContent() {
		return content;
	}

	public List<Video> getPlaylistV() {
		return playlistV;
	}

	public String getName() {
		return name;
	}

	public int getIdPlaylist() {
		return idPlaylist;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setName(String name) {
		this.name = name;
	}

}
