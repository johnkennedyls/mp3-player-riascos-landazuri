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
	
	public void setupScenary3() {
		m = new Manager();
		m.addUser("Juan", "Juan@gmail.com", "1234", 1234);
		m.addUser("Jose", "Jose@gmail.com", "1234", 4567);	
		m.addUser("Pedro", "Pedro@gmail.com", "1234", 9874);
		m.addUser("Jorge", "Jorge@gmail.com", "1234", 6844);
		m.addUser("Mario", "Mario@gmail.com", "1234", 6584);
	}
	
	public void setupScenary4() {
		m = new Manager();
		m.addUser("Juan", "Juan@gmail.com", "1234", 9874);
		m.addUser("Jose", "Jose@gmail.com", "1234", 4567);	
		m.addUser("Pedro", "Pedro@gmail.com", "1234", 1234);
		m.addUser("Jorge", "Jorge@gmail.com", "1234", 6844);
		m.addUser("Mario", "Mario@gmail.com", "1234", 6584);
		m.addUser("Juan", "Juan@gmail.com", "1234", 9874);
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
	
	@Test
	public void addUserTest() {
		setupScenary1();
		m.addUser("Juan", "Juan@gmail.com", "1234", 1234);
		assertEquals(1, m.getUsers().size(), "Not added all");
		for (int i = 0; i < m.getUsers().size(); i++) {
			assertNotNull(m.getUsers().get(i));
		}
	}
	
	@Test
	public void addUserTest2() {
		setupScenary3();
		assertEquals(5, m.getUsers().size(), "Not added all");
	}
	
	@Test
	public void addUserTest3() {
		setupScenary4();
		assertEquals(5, m.getUsers().size(), "Add an incorrect one");
	}
	
}
