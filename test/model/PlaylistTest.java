package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.junit.jupiter.api.Test;

import exceptions.NotFoundException;

class PlaylistTest {

	private Playlist p;

	public void setupScenary1() {
		p = new Playlist("Test");
	}
	
	public void setupScenary2() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		p = new Playlist("Test");
		p.addSong("multimedia/3 Doors Down - Here Without You.mp3");
		p.addSong("multimedia/505 lyrics - Arctic Monkeys.mp3");
	}
	
	public void setupScenary3() {
		p = new Playlist("Test", "MP4");
	}
	
	public void setupScenary4() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		p = new Playlist("Test", "MP4");
		p.addVideo("multimedia/11440017.MP4");
		p.addVideo("multimedia/11450004.MP4");
	}

	@Test
	public void testAddSong() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary1();
		p.addSong("multimedia/3 Doors Down - Here Without You.mp3");
		assertNotNull(p.getFirstSong());
		assertEquals("3DoorsDownVEVO" ,p.getFirstSong().getArtist(), "Test failed");
		assertEquals("3 Doors Down - Here Without You" ,p.getFirstSong().getTitle(), "Test failed");
		assertEquals("3DoorsDownVEVO" ,p.getFirstSong().getAlbum(), "Test failed");
	}
	
	@Test
	public void testAddSong2() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary2();
		assertNotNull(p.getFirstSong());
		assertNotNull(p.getFirstSong().getNextSong());
	}
	
	@Test
	public void testAddVideo() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary3();
		p.addVideo("multimedia/11440017.MP4");
		assertNotNull(p.getFirstVideo());
		assertEquals("11440017.MP4" ,p.getFirstVideo().getName(), "Test failed");
		assertEquals("51.0 MB" ,p.getFirstVideo().getSize(), "Test failed");
	}
	
	@Test
	public void testAddVideo2() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary4();
		assertNotNull(p.getFirstVideo());
		assertNotNull(p.getFirstVideo().getNextVideo());
	}

	@Test
	public void testSearchSong() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, NotFoundException {
		setupScenary1();
		p.addSong("multimedia/3 Doors Down - Here Without You.mp3");
		Song b = p.searchSong("3 Doors Down - Here Without You");
		assertNotNull(b);
	}
	
	@Test
	public void testSearchSong2() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, NotFoundException {
		setupScenary2();
		Song a = p.searchSong("3 Doors Down - Here Without You");
		Song b = p.searchSong("505 lyrics - Arctic Monkeys");
		assertEquals("505 lyrics - Arctic Monkeys", p.getFirstSong().getNextSong().getTitle(), "Como que no bro");
		assertNotNull(a);
		assertNotNull(b);
	}
	
	@Test
	public void testSearchVideo() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary3();
		p.addVideo("multimedia/11440017.MP4");
		Video b = p.searchVideo("11440017.MP4");
		assertNotNull(b);
	}
	
	@Test
	public void testSearchVideo2() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary4();
		Video a = p.searchVideo("11440017.MP4");
		Video b = p.searchVideo("11450004.MP4");
		assertEquals("11450004.MP4", p.getFirstVideo().getNextVideo().getName(), "Como que no bro");
		assertNotNull(a);
		assertNotNull(b);
	}
	
	@Test
	public void testRemoveSong() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary1();
		p.addSong("multimedia/3 Doors Down - Here Without You.mp3");
		p.removeSong("3 Doors Down - Here Without You");
		assertNull(p.getFirstSong());
	}
	
	@Test
	public void testRemoveSong2() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary2();
		p.removeSong("3 Doors Down - Here Without You");
		assertNull(p.getFirstSong().getNextSong());
		p.removeSong("505 lyrics - Arctic Monkeys");
		assertNull(p.getFirstSong());
	}
	
	@Test
	public void testRemoveVideo() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary3();
		p.addVideo("multimedia/11440017.MP4");
		p.removeVideo("11440017.MP4");
		assertNull(p.getFirstVideo());
	}
	
	@Test
	public void testRemoveVideo2() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary4();
		p.removeVideo("11440017.MP4");
		assertNull(p.getFirstVideo().getNextVideo());
		p.removeVideo("11450004.MP4");
		assertNull(p.getFirstVideo());
	}
}
