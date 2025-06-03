package pokerGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.bishopireton.files.MyFiles;
import java.util.ArrayList;
/**
 *  class for managing user data such as stats and account updates.
 *  @author Patrick Christmas
 *  @version June 1 2025 
 */
public class UserManager {
	
	/**
     * updates a users money, win count, or loss count in the user data file.
     * If the user won, their win count is incremented and vice versa for losing 
     * also updates the users current money. 
     * @param username the username of the user to update
     * @param newMoney the users new money value to store
     * @param won      true if the user won the game false if they lost
     */
    public static void updateUserStats(String username, int newMoney, boolean won) {
        File file = new File("TextFileFolder/Text.txt");
        File tempFile = new File("TextFileFolder/temp.txt");

        try (
            BufferedReader reader = new BufferedReader(new FileReader(file));
            PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals(username)) {
                    writer.println(line); // Usernme
                    String password = reader.readLine(); // Password
                    writer.println(password);
                    reader.readLine();                         
                    writer.println(newMoney); // Updated money

                    int oldWins = Integer.parseInt(reader.readLine());
                    int oldLosses = Integer.parseInt(reader.readLine());

                    if (won) {
                        writer.println(oldWins + 1); // Updated wins
                        writer.println(oldLosses); 
                    } else {
                        writer.println(oldWins);  // wins unchaged
                        writer.println(oldLosses + 1);  // Updted losses
                    }

                    String elo = reader.readLine(); // elo
                    writer.println(elo);
                } else {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Replace old file
        file.delete();
        tempFile.renameTo(file);
    }
}