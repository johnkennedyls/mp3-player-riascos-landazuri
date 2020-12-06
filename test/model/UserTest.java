package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {

	private User user;
	
	public void setupScenary1() {
		user = new User();
	}
	
	@Test
	public void addPlaylistTest() {
		setupScenary1();
		user.addPlaylist("MP3");
		user.addPlaylist("MP4", "MP4");
		assertEquals(2, user.getPlaylists().size(), "Test failed");
	}
	
	@Test
	public void removePlaylistTest() {
		setupScenary1();
		user.addPlaylist("MP3");
		user.addPlaylist("MP4", "MP4");
		user.removePlaylist(user.getPlaylists().get(1));
		assertEquals(1, user.getPlaylists().size(), "Test failed");
	}

}
