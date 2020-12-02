package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.junit.jupiter.api.Test;

public class ManagerTest {

	private Manager m;
	
	public void setupScenary1() {
		m = new Manager();
	}
	
	public void setupScenary2() {
		m = new Manager();
		m.addPlaylist("MP3");
		m.addPlaylist("MP4", "MP4");
	}
	
	@Test
	public void addPlaylistSongsTest() {
		setupScenary1();
		m.addPlaylist("MP3");
		m.addPlaylist("MP4", "MP4");
		assertNotNull(m.getPlaylists().get(0));
		assertNotNull(m.getPlaylists().get(1));
	}
	
	@Test
	public void contentTest() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary2();
		m.getPlaylists().get(0).addSong("data/3 Doors Down - Here Without You.mp3");
		m.getPlaylists().get(0).addSong("data/505 lyrics - Arctic Monkeys.mp3");
		assertNotNull(m.getPlaylists().get(0).getFirstSong());
		assertNotNull(m.getPlaylists().get(0).getFirstSong().getNextSong());
	}
	
}
