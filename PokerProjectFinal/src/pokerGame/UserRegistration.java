package pokerGame;

import java.io.FileNotFoundException;
import org.bishopireton.files.MyFiles;

/**
 * A backend registration system that now includes the current money value
 * for each user.
 * 
 * The text file is assumed to store three values per user:
 *   1. username
 *   2. password
 *   3. current money (as an integer in string form)
 * 
 * @author Mrs. Kelly
 */
public class UserRegistration {

    // The file name for the user registration data.
    private static final String filename = "TextFileFolder/Text.txt";
    
    // The array of users.
    private static User[] users;
    
    public UserRegistration() {
        // Empty constructor.
    }
    
    /**
     * Reads the text file and creates an array of users.
     * Assumes three lines per user (username, password, current money).
     */
    private static void openRegistration() {
        String[] strings;
        MyFiles file = new MyFiles(filename);
        
        strings = file.readToArray();
        if (strings == null) return;
        
        // Calculate how many users there are.
        users = new User[strings.length / 3];
        for (int u = 0; u < strings.length; u += 3) {
            int money = 0;
            try {
                money = Integer.parseInt(strings[u+2]);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing money for user " + strings[u] + ". Defaulting to 0.");
            }
            users[u / 3] = new User(strings[u], strings[u+1], money);
        }
        file.close();
    }
    
    /**
     * Writes the current array of users to the text file.
     * Each user is written as three lines: username, password, and current money.
     */
    public static void closeRegistration() {
        if (users == null) return;
        MyFiles file = new MyFiles(filename);
        String[] strings = new String[users.length * 3];
        for (int u = 0; u < users.length; u++) {
            strings[3*u] = users[u].getUser();
            strings[3*u + 1] = users[u].getPassword();
            strings[3*u + 2] = Integer.toString(users[u].getCurrentMoney());
        }
        file.writeToFile(strings);
        file.close();
    }
    
    /**
     * Checks if the given username is already taken.
     * 
     * @param username the username to check.
     * @return true if the username is already in use; false otherwise.
     */
    public boolean isUsernameTaken(String username) {
        if (users == null) // if nothing is in the array (if the text file is blank)
            return false; 

        for (User user : users) { 
            if (user.getUser().equals(username)) { 
                return true; 
            }
        }
        return false;
    }
    
    /**
     * Opens registration, prints the users, and then closes registration.
     * 
     * @param args unused
     * @throws FileNotFoundException when file is not located.
     */
    public static void registerUsers() throws FileNotFoundException {
        openRegistration();
        if (users != null) {
            for (User u : users) {
                System.out.println(u);
            }
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
