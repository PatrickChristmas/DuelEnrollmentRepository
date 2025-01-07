package pokerGame;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @version January 3 2024 
 * @author Patrick Christmas 
 * Represents a standard deck of 52 playing cards.
 * Provides methods to initialize the deck, shuffle it, deal cards, and check its state.
 */
public class Deck {
    // list to store the cards in the deck
    private ArrayList<Card> cards;

    /**
     * Constructs a new Deck and initializes it with 52 cards.
     */
    public Deck() {
        cards = new ArrayList<>(); // initializes the deck as an ArrayList
        initializeDeck();
    }

    /**
     * Initializes the deck with 52 playing cards (13 ranks and 4 suits).
     * Cards are added with the associated image path for each card.
     */
    private void initializeDeck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};

        // loops through each suit and rank to create the deck
        for (String suit : suits) {
            for (String rank : ranks) {
                String imagePath = "Images/" + rank + "Of" + suit + ".PNG";
                cards.add(new Card(rank, suit, imagePath)); // adds a new card with rank, suit, and image path
            }
        }
    }

    /**
     * Shuffles the deck using Collections.shuffle to randomize the order of cards.
     */
    public void shuffle() {
        Collections.shuffle(cards); // shuffles the ArrayList to randomize the deck
    }

    /**
     * Deals the top card from the deck.
     */
    public Card dealCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0); // removes and returns the top card from the deck
        }
        return null; // returns null if the deck is empty
    }

    /**
     * Checks if the deck is empty
     */
    public boolean isEmpty() {
        return cards.isEmpty(); // checks if the ArrayList is empty
    }

    /**
     * Gets the list of cards in the deck.
     */
    public ArrayList<Card> getCards() {
        return cards; // returns the full ArrayList for external use
    }

    /**
     * Gets the current size of the deck.
     */
    public int size() {
        return cards.size(); // returns the current size of the deck
    }
    
    @Override
    public String toString() {
        return "Deck [Number of Cards: " + cards.size() + "]";
    }
    
}