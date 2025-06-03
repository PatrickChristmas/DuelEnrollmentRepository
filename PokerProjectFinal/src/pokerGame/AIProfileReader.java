package pokerGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * AIProfileReader is responsible for loading and returning a formatted profile for an AI player from a text file
 * The text file should contain one AI profile per 6 lines
 * @author Patrick Christmas
 * @version June 2 2025
 */
public class AIProfileReader {

    /**
     * retrieves the profile of an AI player by name from AIPlayers.txt
     *
     * @param aiName the name of the AI player to search for (case-insensitive)
     * @return a formatted string representing the AI's profile or an error message
     */
	public static String getProfileForAI(String aiName) {
	    try (BufferedReader reader = new BufferedReader(new FileReader("TextFileFolder/AIPlayers.txt"))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            if (line.trim().equalsIgnoreCase(aiName.trim())) {
	                StringBuilder profile = new StringBuilder();
	                profile.append("Name: ").append(line).append("\n");
	                profile.append("Bio: ").append(reader.readLine()).append("\n");
	                profile.append("Money: ").append(reader.readLine()).append("\n");
	                profile.append("Wins: ").append(reader.readLine()).append("\n");
	                profile.append("Losses: ").append(reader.readLine()).append("\n");
	                profile.append("Elo: ").append(reader.readLine()).append("\n");

	               

	                return profile.toString();
	            }
	        }
	        return "No profile found for " + aiName;
	    } catch (IOException e) {
	        return "ERROR: Failed to load profile for " + aiName + ": " + e.getMessage();
	    }
	}

    /**
     * returns a description of  AIProfileReader 
     * @return string description of the class
     */
    @Override
    public String toString() {
        return "AIProfileReader[Reads and formats AI profile data from text file]";
    }
}