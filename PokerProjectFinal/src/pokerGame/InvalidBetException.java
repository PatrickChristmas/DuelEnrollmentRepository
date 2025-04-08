package pokerGame;

/**
 * ibe is an exception that is thrown when an invalid bet (i.e. greater than betters amount of money or negative) amount is detected. 
 */
public class InvalidBetException extends Exception {

    /**
     * constructs a new invalidBetException with a default message.
     */
    public InvalidBetException() {
        super("Invalid bet amount.");
    }
    
    /**
     * constructs a new InvalidBetException with a  message.
     *
     * @param message the message explaining the bet error
     */
    public InvalidBetException(String message) {
        super(message);
    }
}
