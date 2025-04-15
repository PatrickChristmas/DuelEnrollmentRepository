package pokerGame;

import java.io.FileNotFoundException;
import org.bishopireton.files.MyFiles;

/**
 * A backend registration system that now includes the current money value,
 * wins, and losses for each user.
 * 
 * The text file is assumed to store five values per user:
 *   1. username
 *   2. password
 *   3. current money (as an integer in string form)
 *   4. wins (as an integer)
 *   5. losses (as an integer)
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
     * Assumes five lines per user (username, password, current money, wins, losses).
     */
    private static void openRegistration() {
        String[] strings;
        MyFiles file = new MyFiles(filename);

        strings = file.readToArray();
        if (strings == null) return;

        users = new User[strings.length / 5];
        for (int u = 0; u < strings.length; u += 5) {
            int money = 0, wins = 0, losses = 0;
            try {
                money = Integer.parseInt(strings[u + 2]);
                wins = Integer.parseInt(strings[u + 3]);
                losses = Integer.parseInt(strings[u + 4]);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing numeric values for user " + strings[u] + ". Defaulting to 0.");
            }
            users[u / 5] = new User(strings[u], strings[u + 1], money, wins, losses);
        }
        file.close();
    }

    /**
     * Writes the current array of users to the text file.
     * Each user is written as five lines: username, password, current money, wins, losses.
     */
    public static void closeRegistration() {
        if (users == null) return;
        MyFiles file = new MyFiles(filename);
        String[] strings = new String[users.length * 5];
        for (int u = 0; u < users.length; u++) {
            strings[5 * u] = users[u].getUser();
            strings[5 * u + 1] = users[u].getPassword();
            strings[5 * u + 2] = Integer.toString(users[u].getCurrentMoney());
            strings[5 * u + 3] = Integer.toString(users[u].getWins());
            strings[5 * u + 4] = Integer.toString(users[u].getLosses());
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
        if (users == null)
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
            result += user.getUser() + " ";
        }
        return result;
    }
}
