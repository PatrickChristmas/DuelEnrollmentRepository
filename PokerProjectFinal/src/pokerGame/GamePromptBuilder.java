package pokerGame;

import java.util.List;

/**
 * GamePromptBuilder is responsible for constructing prompt strings for the interaction with the LM. It generates a String for chat
 * @author Patrick Christmas 
 * @version June 2 2025 
 */
public class GamePromptBuilder {

    /**
     * Builds a prompt for chat interaction with the AIPlayer
     * Includes profile data and passes the user input as context for a response.
     *
     * @param player the AIPlayer being spoken to
     * @param userInput the message entered by the user
     * @return a uinque driven prompt for the AI to respond conversationally
     */
    public static String buildChatPrompt(AIPlayer player, String userInput) {
        String profile = AIProfileReader.getProfileForAI(player.getName().trim());
        
        // debugging 
        System.out.println("inside buildChatPrompt");
        System.out.println("userInput: " + userInput);
        System.out.println("player name: " + player.getName());
        System.out.println("AIProfileReader output:\n" + profile);

        // parse profile fields from the string
        String[] lines = profile.split("\n");
        String name = "", bio = "", money = "", wins = "", losses = "", elo = "", rank = "", imageID = "";
        for (String line : lines) {
            if (line.startsWith("Name:")) name = line.substring(6).trim();
            else if (line.startsWith("Bio:")) bio = line.substring(5).trim();
            else if (line.startsWith("Money:")) money = line.substring(7).trim();
            else if (line.startsWith("Wins:")) wins = line.substring(6).trim();
            else if (line.startsWith("Losses:")) losses = line.substring(8).trim();
            else if (line.startsWith("Elo:")) elo = line.substring(5).trim();
            else if (line.startsWith("Rank:")) rank = line.substring(6).trim();
            else if (line.startsWith("ImageID:")) imageID = line.substring(9).trim();
        }

        return "You are a poker AI named " + name + ".\n\n"
             + "Here is your profile:\n"
             + "Name: " + name + "\n"
             + "Bio: " + bio + "\n"
             + "Money: " + money + "\n"
             + "Wins: " + wins + "\n"
             + "Losses: " + losses + "\n"
             + "Elo: " + elo + "\n"
             + "Rank: " + rank + "\n"
             + "ImageID: " + imageID + "\n\n"
             + "The user said: \"" + userInput + "\"\n\n"
             + "Respond as yourself, staying in-character and referencing your profile and stats if relevant.\n";
    }
}
