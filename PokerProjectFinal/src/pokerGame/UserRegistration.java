package pokerGame;



import java.io.FileNotFoundException;
import org.bishopireton.files.MyFiles;

/**
 * A backend registration system. Provides an open and a close registration.
 * @author Mrs. Kelly
 */
public class UserRegistration {

	/**
	 * As this is just used for a specific class example the 
	 * file name is stored in a static final variable
	 */
	private static final String filename = "TextFileFolder/Text.txt";
	/**
	 * the array of usernames and passwords
	 */
	private static User[] users;
	
	/**
	 * standard constructor
	 */
	public UserRegistration() {
		
	}
	/**
	 * uses the MyFiles library class to read in the users file with usernames and passwords
	 * then creates an array of users. Each user has a username and a password.
	 */
	private static void openRegistration() {
		String[] strings;
		MyFiles file = new MyFiles(filename);
		
		strings = file.readToArray();
		if (strings == null) return;
		users = new User[strings.length/2];
		for (int u = 0; u < strings.length; u+=2) {
			users[u/2] = new User(strings[u],	strings[u+1]);
		}
		file.close();
	}

	/**
	 * uses the MyFiles library to write the users array to the users file
	 * after converting the users array into a string array. 
	 */
	public static void closeRegistration() {
		if (users == null) return;
		MyFiles file = new MyFiles(filename);
		String[] strings = new String[users.length*2];
		for (int u = 0; u < users.length; u++) {
			strings[2*u] = users[u].getUser();
			strings[2*u+1] = users[u].getPassword();
		}
		file.writeToFile(strings);
		file.close();
	}
	
	// method to check if the userName is already taken
	/**
	 * Checks if the username is already in the text file 
	 * @param username, takes in the user's selected username 
	 * @return boolean, true if the username is already in the textFile, false if it is not 
	 */
	public boolean isUsernameTaken(String username) {
	    if (users == null) // if nothing is in the array (if the textfile is blank)
	    	return false; 

	    for (User user : users) { // checks if the user name is already in the TextFile 
	        if (user.getUser().equals(username)) { // if its finds it 
	            return true; 
	        }
	    }
	    return false; // if the username is not found in the textfile but the textfile has already been used. 
	}
	
	/**
	 * Opens up registration, prints the usernames and passwords, closes the registration
	 * @param args unused
	 * @throws FileNotFoundException when file is not located
	 */
	
	public static void registerUsers() throws FileNotFoundException {
        openRegistration();
        for (User u : users) {
            System.out.println(u); 
        }
        closeRegistration(); 
    }
	
	@Override
	public String toString() {
	    if (users == null || users.length == 0) {
	        return "No registered users.";
	    }
	    String result = "Registered Users: ";
	    for (User user : users) {
	        result += user.getUser() + " ";  // Assumes User class has a getUser method
	    }
	    return result;
	}
	

}