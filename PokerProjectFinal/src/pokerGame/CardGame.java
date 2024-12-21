package pokerGame;

import java.util.Random;

/**
 * @author PatrickChristmas
 * @version December 20 2024.
 * The CardGame class is used to simulate a simple higher or lower card game. 
 * The class provides methods to generate random cards and compare the user's card with the computer's card.
 * 
 * This class is not currently yet used, and will be useful for a higher or lower game after creating a constructor for the cards.
 */
public class CardGame {

    /**
     * The minimum card value.
     */
    private static final int MIN_CARD = 1;

    /**
     * The maximum card value.
     */
    private static final int MAX_CARD = 13;

    /**
     * Generates a random card value between 1 and 13.
     * 
     * @return a random card value.
     */
    public static int generateRandomCard() {
        Random rand = new Random();
        return rand.nextInt(MAX_CARD) + MIN_CARD;
    }

    /**
     * Compares the user's card with the randomly generated computer's card.
     * 
     * @param randomCard, the card generated for the computer (value between 1 and 13).
     * @param userCard, the card selected by the user (value between 1 and 13).
     * @return an integer representing the result of the comparison:
     *             
     */
    public static int compareCards(int randomCard, int userCard) {
        
        // used to compare user result to the computer result
        int result;
        if (userCard < randomCard) {
            result = -1;
        } else if (userCard > randomCard) {
            result = 1;
        } else {
            result = 0;
        }

        String message = "";  // message to be printed

        // determines the result of the comparison using switch
        switch (result) {
            case 1:
                message = "You win! Your card is higher than the computer's.";
                break;
            case -1:
                message = "You lose! Your card is lower than the computer's.";
                break;
            case 0:
                message = "It's a tie! Both cards are equal.";
                break;
            default:
                message = "Unexpected result.";
                break;
        }

        return result; // returns the comparison result, useful in future iterations.
    }
}