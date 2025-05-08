package pokerGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.bishopireton.files.MyFiles;

/**
 * a class that manages loading and updating ai player profiles
 * @author Patrick
 * @version 4/14/2025
 */
public class AIProfileManager {

    /** 
     * path to the file
     */
    private static final String filename = "TextFileFolder/AIPlayers.txt";

    /**  
     * all AIPlayer objects loaded from the file
     */
    private static AIPlayer[] bots;

    // default constructor
    private AIProfileManager() {
    	
    }

    /**
     * reads the AI profile data from the text file
     */
    private static void openProfiles() {
        MyFiles file = new MyFiles(filename);
        String[] raw = file.readToArray();
        if (raw == null) {
            bots = new AIPlayer[0];
            return;
        }
        bots = new AIPlayer[raw.length / 7];
        for (int i = 0; i < raw.length; i += 7) {
            int money = Integer.parseInt(raw[i + 2]);
            int wins = Integer.parseInt(raw[i + 3]);
            int losses = Integer.parseInt(raw[i + 4]);
            int elo = Integer.parseInt(raw[i + 5]);

            bots[i / 7] = new AIPlayer(raw[i], raw[i + 1], money, wins, losses, elo, raw[i + 6]);
        }
        file.close();
    }

    /**
     * writes the current array back to the ai profile text file 
     */
    private static void closeProfiles() {
        if (bots == null) return;

        String[] out = new String[bots.length * 7];
        for (int i = 0; i < bots.length; i++) {
            AIPlayer ai = bots[i];
            int idx = i * 7;
            out[idx] = ai.getName();
            out[idx + 1] = ai.getBio();
            out[idx + 2] = Integer.toString(ai.getMoney());
            out[idx + 3] = Integer.toString(ai.getWins());
            out[idx + 4] = Integer.toString(ai.getLosses());
       //     out[idx + 5] = ai.getRank();
            out[idx + 6] = ai.getImageID();
        }
        MyFiles file = new MyFiles(filename);
        file.writeToFile(out);
        file.close();
    }

    /**
     * loads all AIPlayer objects from the text file and returns them as an ArrayList
     * @return an ArrayList of AIPlayer profiles.
     */
    public static ArrayList<AIPlayer> loadAIPlayers() {
        openProfiles();
        ArrayList<AIPlayer> playerList = new ArrayList<>();
        if (bots != null) {
            for (AIPlayer ai : bots) {
                playerList.add(ai);
            }
        }
        return playerList;
    }

    /**
     * replaces the internal bots array with a new list of AIPlayer object and saves them to the file
     * @param players the new list of AIPlayer profiles to save
     */
    public static void saveAIPlayers(ArrayList<AIPlayer> players) {
        bots = players.toArray(new AIPlayer[0]);
        closeProfiles();
    }

    /**
     * updates the win/loss record of a specific AI player and saves the change
     * @param name the name of the AI player
     * @param won true if the player won and false if the player lost
     */
    public static void updateStats(String name, boolean won) {
        openProfiles();
        for (AIPlayer ai : bots) {
            if (ai.getName().equalsIgnoreCase(name)) {
                ai.recordResult(won);
                break;
            }
        }
        closeProfiles();
    }

    /**
     * converts a String into an int, returns 0 if the string is not a valid int
     * @param s the string
     * @return the integer or 0 if invalid
     */
    private static int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * returns a string listing the names of all loaded AI profiles
     * @return a list of AI player names
     */
    @Override
    public String toString() {
        if (bots == null || bots.length == 0) {
            return "No AI profiles loaded.";
        }

        String result = "AI Profiles: ";
        for (AIPlayer ai : bots) {
            result += ai.getName() + " ";
        }

        return result;
    }

    /**
     * prints all AI profiles to the console for debugging purposes
     * @throws FileNotFoundException if the AI profile file is not found.
     */
    public static void listProfiles() throws FileNotFoundException {
        openProfiles();
        if (bots != null) {
            for (int i = 0; i < bots.length; i++) {
                System.out.println(bots[i]);
            }
        }
        closeProfiles();
    }
}
