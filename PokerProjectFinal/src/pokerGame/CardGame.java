package pokerGame;

import java.util.Random;

// class not yet used, planning to use for a simple higher or lower game. 
// Need to create a constructor for the cards before this class can be used. 
public class CardGame {

	// 13 different cards (1- 10 + king, queen, jack) 
	private static final int MIN_CARD = 1;
    private static final int MAX_CARD = 13;

    // generates a random card from the 1 - 13 
    public static int generateRandomCard() {
        Random rand = new Random();
        return rand.nextInt(MAX_CARD) + MIN_CARD;
    }

    // compares the user's card with the computer's card
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

    
        return result; // returns the comparison result, useful in future iterations .
    }
}