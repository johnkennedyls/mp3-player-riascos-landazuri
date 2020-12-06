package model;

import static org.junit.jupiter.api.Assertions.*;

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
	
	public void setupScenary1() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, UserAlreadyExistsException {
		m = new Manager("Test");
	}
	
	public void setupScenary3() throws UserAlreadyExistsException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		m = new Manager("Test");
		m.addUser("Juan", "Juan@gmail.com", "1234", 1234);
		m.addUser("Jose", "Jose@gmail.com", "1234", 4567);	
		m.addUser("Pedro", "Pedro@gmail.com", "1234", 9874);
		m.addUser("Jorge", "Jorge@gmail.com", "1234", 6844);
		m.addUser("Mario", "Mario@gmail.com", "1234", 6584);
	}
	
	public void setupScenary4() throws UserAlreadyExistsException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		m = new Manager("Test");
		m.addUser("Juan", "Juan@gmail.com", "1234", 9874);
		m.addUser("Jose", "Jose@gmail.com", "1234", 4567);	
		m.addUser("Pedro", "Pedro@gmail.com", "1234", 1234);
		m.addUser("Jorge", "Jorge@gmail.com", "1234", 6844);
		m.addUser("Mario", "Mario@gmail.com", "1234", 6584);
		m.addUser("Juan", "Juan@gmail.com", "1234", 9874);
	}
	
	@Test
	public void addUserTest() throws UserAlreadyExistsException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary1();
		m.addUser("Juan", "Juan@gmail.com", "1234", 1234);
		assertEquals(1, m.getUsers().size(), "Not added ");
	}
	
	@Test
	public void addUserTest2() throws UserAlreadyExistsException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary3();
		assertEquals(5, m.getUsers().size(), "Not added all");
	}
	
	@Test
	public void addUserTest3() throws UserAlreadyExistsException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary4();
		m.addUser("Juan", "Juan@gmail.com", "1234", 9874);
		assertEquals(5, m.getUsers().size(), "Add an incorrect one");
	}
	
	@Test
	public void removeUserTest() throws UserAlreadyExistsException, UserDoesNotExistsException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		setupScenary4();
		m.removeUser(9874);
		assertEquals(4, m.getUsers().size(), "Doesn't remove the user");
	}
	
	@Test
	public void userSizeTest() throws UserAlreadyExistsException, UserDoesNotExistsException, CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException, ClassNotFoundException, NotFoundException {
		setupScenary1();
		m.loadData();
		assertEquals(3, m.getUsers().size(), "Serializing");
	}
}
