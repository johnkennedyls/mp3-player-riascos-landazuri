package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import exceptions.NotFoundException;

public class Playlist implements Serializable{

	private static final long serialVersionUID = 1;
	private int idPlaylist;
	private String name;
	private String content;
	private int idUser;
	private Song firstSong;
	private Video firstVideo;
	private List<Song> playlist;
	private List<Video> playlistV;
/**
 * Representa una lista de canciones
 * @param na: String nombre de la lista.
 */
	public Playlist(String na) {
		name = na;
		playlist = new ArrayList<>();
		content = "MP3";
	}
/**
 * Representa una lista de videos
 * @param na: String nombre de la lista
 * @param cont: String nombre del tipo de archivo(MP4)
 */
	public Playlist(String na, String cont) {
		name = na;
		content = cont;
		playlistV = new ArrayList<>();
	}
/**
 * Añade una canción a la lista.
 * @param path:String ruta de la canción
 * @throws CannotReadException
 * @throws IOException
 * @throws TagException
 * @throws ReadOnlyFileException
 * @throws InvalidAudioFrameException
 */
	public void addSong(String path) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		Song newSong = new Song(path);
		if(firstSong==null) {
			firstSong = newSong;
			playlist();
		}else {
			recursiveAddSong(firstSong, newSong);
		}
	}
	/**
	 * Añade una canción de forma recursiva.
	 * @param current: canción actual.
	 * @param newSong: nueva canción.
	 */

	private void recursiveAddSong(Song current, Song newSong) {
		if (current.getNextSong() != null) {
			recursiveAddSong(current.getNextSong(), newSong);
			return;
		}
		else {
			current.setNextSong(newSong);
			newSong.setPrevSong(current);
			playlist();
		}
	}
	/**
	 * Añade un video a la lista.
	 * @param path:String ruta del video.
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 */
	public void addVideo(String path) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		Video newVideo = new Video(path);
		if(firstVideo==null) {
			firstVideo = newVideo;
			playlistV();
		}else {
			recursiveAddVideo(firstVideo, newVideo);
		}
	}
	/**
	 * Añade un video de forma recursiva.
	 * @param current: video actual.
	 * @param newSong: nuevo video.
	 */


	private void recursiveAddVideo(Video current, Video newVideo) {
		if (current.getNextVideo() != null) {
			recursiveAddVideo(current.getNextVideo(), newVideo);
			return;
		}
		else {
			current.setNextVideo(newVideo);
			newVideo.setPrevVideo(current);
			playlistV();
		}
	}	
/**
 * Busca una  canción en la lista
 * @param c, nombre a buscar.
 * @return found:Song la canción buscada.
 * @throws NotFoundException
 */
	public Song searchSong(String c) throws NotFoundException {
		Song found =recursiveSearchSong(firstSong, c);
		if (found == null) {
			throw new NotFoundException();
		}
		return found;
	}
/**
 * Busca canciones de forma recursiva.
 * @param current: canción actual en la lista.
 * @param c:nombre a buscar;
 * @return found:Song la canción buscada.
 */
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
	/**
	 * Busca un  video en la lista
	 * @param c, nombre a buscar.
	 * @return found:Video video buscado.
	 * @throws NotFoundException
	 */
	public Video searchVideo(String c) {
		Video found = recursiveSearchVideo(firstVideo, c);
		return found;
	}
	/**
	 * Busca videos de forma recursiva.
	 * @param current: video actual en la lista.
	 * @param c:nombre a buscar;
	 * @return found:Video video buscado.
	 */
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
/**
 * Elimina una canción.
 * @param c:String Canción a eliminar
 * @return b:Song canción eliminada.
 */
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
	/**
	 * Elimina un video.
	 * @param c:String video a eliminar
	 * @return b:Video video eliminado.
	 */
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
/**
 * Verifica que la lista sea de canciones y la guarda.
 * 
 */
	public void playlist(){
		playlist.clear();
		Song song = firstSong;
		while (song != null) {
			playlist.add(song);
			song = song.getNextSong();
		}
	}
	/**
	 * Verifica que la lista sea de videos y la guarda.
	 * 
	 */
	public void playlistV(){
		playlistV.clear();
		Video video = firstVideo;
		while (video != null) {
			playlistV.add(video);
			video = video.getNextVideo();
		}
	}

	public Song getFirstSong() {
		return firstSong;
	}

	public void setFirstSong(Song fs) {
		firstSong = fs;
	}

	public Video getFirstVideo() {
		return firstVideo;
	}

	public void setFirstVideo(Video fv) {
		firstVideo = fv;
	}

	public String getContent() {
		return content;
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

	public List<Video> getPlaylistV() {
		return playlistV;
	}

	public List<Song> getPlaylist() {
		return playlist;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retorna el estado de la lista.
	 * return msg:String estado de la lista.
	 */
	public String toString() {
		String msg = "PlayList " + name + "\n";
		if(content.equals("MP3")) {
			msg += toStringSongs() + "\n";
		}else {
			msg += toStringVideos();
		}
		return msg;
	}

	/**
	 * Retorna el estado de las cancioneso.
	 * return msg:String estado de las canciones.
	 */



	public String toStringSongs() {
		String msg = "";
		String separator = ";";
		msg += "Title" + separator + "Artist" + separator + "Album\n";
		if (playlist != null) {
			for(Song song : playlist) {
				msg += song.toString();
			}
		}
		return msg;
	}

	/**
	 * Retorna el estado de los videos.
	 * return msg:String estado de los videos.
	 */


	public String toStringVideos() {
		String msg = "";
		String separator = ";";
		msg += "Title" + separator + "Size\n";
		if (playlistV != null) {
			for(Video video : playlistV) {
				msg += video.toString();
			}
		}
		return msg;
	}

}
