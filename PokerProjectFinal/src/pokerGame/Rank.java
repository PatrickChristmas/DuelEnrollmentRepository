package pokerGame;

import java.util.ArrayList;

/**
 * Rank is based of Elo which rating is updated based on the player's performance relative to the average opponent rating
 * @author Patrick Christmas
 * @version May 5 2025
 */
public class Rank {

    private int rating;  // Current Elo rating
    private static final int DEFAULT_RATING = 1000;
   
    /**
     * Creates a rank with the default starting rating.
     */
    public Rank() {
        this.rating = DEFAULT_RATING;
    }

    /**
     * creates a rank using the elo formula 
     * @param rating starting Elo rating
     */
    public Rank(int rating) {
        this.rating = rating;
    }

    /**
     * updates this players rating based on whether they won or lost compared to the average rating of all opponents
     * @param opponentRanks the list of opponent Rank objects
     * @param didWin true if the player won the round false otherwise
     */
    public void updateRating(ArrayList<Rank> opponents, boolean didWin) {
    	int k = 30;
        if (opponents == null || opponents.isEmpty()) {
            return;
        }

        double sum = 0;
        for (Rank opponent : opponents) {
            sum += opponent.rating;
        }

        double average = sum / opponents.size();
        double expected;

        // calculate expected outcome based on Elo formula
        double exponent = (average-this.rating) / 400.0;
        expected = 1.0 / (1 + Math.pow(10, exponent));

        double actual;
        if (didWin) {
            actual = 1.0;
        } else {
            actual = 0.0;
        }

        // Calculate the change in rating
        int ratingChange = (int) (k * (actual - expected));
        this.rating += ratingChange;
    }

    /**
     * gets the current Elo rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * sets the current Elo rating 
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    

    @Override
    public String toString() {
        return "Rating: " + rating;
    }
}
