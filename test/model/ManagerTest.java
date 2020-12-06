package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.junit.jupiter.api.Test;

import exceptions.NotFoundException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserDoesNotExistsException;

public class ManagerTest {

	private Manager m;
	
	public void setupScenary1() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, UserAlreadyExistsException  {
		m = new Manager();
	}
	
	public void setupScenary2() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, UserAlreadyExistsException  {
		m = new Manager();
		m.addUser("Juan", "Juan@gmail.com", "1234", 1234);
		m.addUser("Jose", "Jose@gmail.com", "1234", 4567);	
		m.addUser("Pedro", "Pedro@gmail.com", "1234", 9874);
		m.saveDataTest();
	}
	
	public void setupScenary3() throws UserAlreadyExistsException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		m = new Manager();
		m.addUser("Juan", "Juan@gmail.com", "1234", 1234);
		m.addUser("Jose", "Jose@gmail.com", "1234", 4567);	
		m.addUser("Pedro", "Pedro@gmail.com", "1234", 9874);
		m.addUser("Jorge", "Jorge@gmail.com", "1234", 6844);
		m.addUser("Mario", "Mario@gmail.com", "1234", 6584);
	}
	
	public void setupScenary4() throws UserAlreadyExistsException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		m = new Manager();
		m.addUser("Juan", "Juan@gmail.com", "1234", 9874);
		m.addUser("Jose", "Jose@gmail.com", "1234", 4567);	
		m.addUser("Pedro", "Pedro@gmail.com", "1234", 1234);
		m.addUser("Jorge", "Jorge@gmail.com", "1234", 6844);
		m.addUser("Mario", "Mario@gmail.com", "1234", 6584);
		m.addUser("Juan", "Juan@gmail.com", "1234", 9874);
	}
	
	@Test
	public void addUserTest()  {
		try {
			setupScenary1();
			m.addUser("Juan", "Juan@gmail.com", "1234", 1234);
			assertEquals(1, m.getUsers().size(), "Not added ");
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException
				| UserAlreadyExistsException e) {
			assertNull(m.getUsers().get(0));
		}	
	}
	
	@Test
	public void addUserTest2()  {
		try {
			setupScenary3();
			assertEquals(5, m.getUsers().size(), "Not added all");
		} catch (UserAlreadyExistsException | CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			for (int i = 0; i < m.getUsers().size(); i++) {
				assertNull(m.getUsers().get(i));
			}
		}
		
	}
	
	@Test
	public void addUserTest3()  {
		try {
			setupScenary4();
		} catch (UserAlreadyExistsException | CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			assertEquals(5, m.getUsers().size(), "Add an incorrect one");
		}
		
	}
	
	@Test
	public void removeUserTest()  {
		try {
			setupScenary3();
			m.removeUser(9874);
			assertEquals(4, m.getUsers().size(), "Doesn't remove the user");
		} catch (UserAlreadyExistsException | CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException | UserDoesNotExistsException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deserializeTest()  {
		try {
			setupScenary2();
			m.loadDataTest();
			assertEquals(3, m.getUsers().size(), "Serializing");
		} catch (ClassNotFoundException | IOException | NotFoundException | CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException | UserAlreadyExistsException e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void serializeTest()  {
		try {
			setupScenary1();
			m.addUser("Juan", "Juan@gmail.com", "1234", 1234);
			m.addUser("Jose", "Jose@gmail.com", "1234", 4567);	
			m.saveDataTest();
			File f = new File("data/usersTest.xd");
			assertNotNull(f);
		} catch (IOException | UserAlreadyExistsException | CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	public void searchingMethodsTest() {
		try {
			setupScenary4();
			boolean prove = m.userExists(4567);
			assertEquals(true, prove, "Searching nice");
			User searched = m.searchUser(4567);
			assertEquals(searched, m.getUsers().get(1), "Searching nice");
			int position = m.userPosition(4567);
			assertEquals(1, position, "Searching nice");
		} catch (UserAlreadyExistsException | CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void sortMethod() {
		try {
			setupScenary3();
			m.bubbleSort(m.getUsers());
			int lessOne = m.getUsers().get(0).getId();
			boolean yes = true;
			for (int i = 1; i < m.getUsers().size(); i++) {
				if (m.getUsers().get(i).getId() < lessOne) {
					yes = false;
				}
			}
			assertEquals(true, yes, "Not sorted");
			int higherOne = m.getUsers().get(4).getId();
			boolean yes2 = true;
			for (int i = 0; i < m.getUsers().size(); i++) {
				if (m.getUsers().get(i).getId() > higherOne) {
					yes = false;
				}
			}
			assertEquals(true, yes2, "Not sorted");
		} catch (UserAlreadyExistsException | CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			for (int i = 0; i < m.getUsers().size(); i++) {
				assertNull(m.getUsers().get(i));
			}
		}
	}
	
	@Test
	public void exportUsersTest() {
		try {
			setupScenary3();
			m.exportUsersData("data/exportUserTest.csv");
			File f = new File("data/exportUserTest.csv");
			assertNotNull(f);
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException
				| UserAlreadyExistsException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void exportPlaylistsTest() {
		try {
			setupScenary1();
			m.addUser("Juan", "Juan@gmail.com", "1234", 1234);
			m.saveDataTest();
			m.getUsers().get(0).addPlaylist("MP3");
			m.exportPlayListsData("data/exportPlaylistsTest.csv");
			File f = new File("data/exportPlaylistsTest.csv");
			assertNotNull(f);
		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException
				| UserAlreadyExistsException e) {
			e.printStackTrace();
		}
	}
}
