package pokerGame;

/**
 * @version March 25 2025
 * @author Patrick Christmas 
 *  class that holds a standard deck
 */
public class GameLogic {
    private Deck deck;

    /**
     * creates and shuffles a standard deck
     */
    public GameLogic() {
        deck = new Deck();
        deck.shuffle();
    }

    /**
     * returns the deck
     * @return the deck
     */
    public Deck getDeck() {
        return deck;
    }
}
