package pokerGame;

import java.util.ArrayList;

/**
 * Represents an AI
 */
public class AIPlayer extends Player {

    private String bio;
    private int wins;
    private int losses;
    private int elo;
    private String imageID; 

    /**
     * constructs an AIPlayer using profile information
     *
     * @param name screen name
     * @param bio short bio of AI
     * @param money money at the start of a round
     * @param wins total wins 
     * @param losses total losses 
     * @param rank rank of AI
     * @param imageID file name
     */
    public AIPlayer(String name,String bio, int money, int wins, int losses, int elo, String imageID) {
        super(name, money, false);
        this.bio = bio;
        this.wins = wins;
        this.losses = losses;
        this.elo = elo;
        this.imageID = imageID;
    }
 

    /**
     * returns the description of the ai player
     * @return the player bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * sets the description of the ai player
     * @param bio the new bio for the player
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * returns the number of wins the ai player has
     * @return the number of wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * sets the number of wins for the ai player
     * @param wins the new number of wins
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * returns the number of losses the ai player has
     * @return the number of losses
     */
    public int getLosses() {
        return losses;
    }

    /**
     * sets the number of losses for the ai player
     * @param losses the new number of losses
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * returns the current rank of the ai player
     * @return the rank as a string
     */
    
    public int getElo() {
        return elo;
    }
    
 

    /**
     * sets the rank of the ai player
     * @param rank the new rank to assign
     */
    public void setElo(int elo) {
        this.elo = elo;
    }

    /**
     * returns the image ID associated with the ai player
     * @return the image ID
     */
    public String getImageID() {
        return imageID;
    }

    /**
     * sets the image ID for the ai player
     * @param imageID the new image identifier
     */
    public void setImageID(String imageID) {
        this.imageID = imageID;
    }


    /**
     *  increment wins/losses after a round
     */
    public void recordResult(boolean won) {
        if (won) {
            wins++;
        } else {
            losses++;
        }
    }

    @Override
    public String toString() {
        return "AIPlayer[" + getName() + ", $" + getMoney() + ", W:" + wins + ", L:" + losses + ", Elo:" + elo + "]";
    }
}
