package model;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.junit.jupiter.api.Test;

class PlaylistTest {

	private Playlist p;

	public void setupScenary1() {
		p = new Playlist("Test");
	}

	@Test
	public void testAddSong() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary1();
		p.addSong("data/3 Doors Down - Here Without You.mp3");
		assertNotNull(p.getFirstSong());
		assertEquals("3DoorsDownVEVO" ,p.getFirstSong().getArtist(), "Test failed");
		assertEquals("3 Doors Down - Here Without You" ,p.getFirstSong().getTitle(), "Test failed");
		assertEquals("3DoorsDownVEVO" ,p.getFirstSong().getAlbum(), "Test failed");
	}

	@Test
	public void testSearchSong() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary1();
		p.addSong("data/3 Doors Down - Here Without You.mp3");
		Song b = p.searchSong("3 Doors Down - Here Without You");
		assertNotNull(b);
		p.removeSong("3 Doors Down - Here Without You");
		assertNull(p.getFirstSong());
	}
	
	@Test
	public void testRemoveSong() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary1();
		p.addSong("data/3 Doors Down - Here Without You.mp3");
		p.removeSong("3 Doors Down - Here Without You");
		assertNull(p.getFirstSong());
	}
}
