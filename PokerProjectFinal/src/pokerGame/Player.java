package pokerGame;

import java.util.ArrayList;

/**
 * This class is supposed to represent a player (human or ai), it represents their name, money, and whether they are human
 * It also has information on their current bet and if they have folded.
 * @author Patrick Christmas
 * @version January 2 2025
 **/
public class Player {
    // name of the player
    private String name;

    // amount of money the player has
    private int money;

    // the player's current bet in the round
    private int currentBet;

    // whether the player has folded
    private boolean hasFolded;

    // list of cards held by the player
    private ArrayList<Card> cards;

    // whether the player is a human or AI
    private boolean isHuman;
   
    //rank object
    private Rank rank;
   
    //elo (determines rank)
    private int elo;

    // constructs a player with name, money, and type
    public Player(String name, int initialMoney, boolean isHuman) {
        this.name = name;
        this.money = initialMoney;
        this.isHuman = isHuman;
        this.hasFolded = false; // starts not folded
        this.currentBet = 0; // starts with 0 bet
        this.cards = new ArrayList<>(); // initialize cards
        this.rank = new Rank();
        this.elo = 1000; // Default starting Elo
    }

    /**
     * constructs a player with name, money, type, and custom Elo
     * @param name
     * @param initialMoney
     * @param isHuman
     * @param elo
     */
    public Player(String name, int initialMoney, boolean isHuman, int elo) {
        this(name, initialMoney, isHuman);
        this.elo = elo;
    }

    /**
     * getter for elo rating
     * @return elo
     */
    public int getElo() {
        return elo;
    }

    /**
     * setter for elo rating
     * @param elo
     */
    public void setElo(int elo) {
        this.elo = elo;
    }

    /**
     * getter for player rank object
     * @return rank
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * setter for player rank
     * @param rank
     */
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    /**
     * getter for the player's name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the player's money
     * @return money
     */
    public int getMoney() {
        return money;
    }

    /**
     * sets the player's card hand
     * @param newCards
     */
    public void setCards(ArrayList<Card> newCards) {
        this.cards = newCards;
    }

    /**
     * sets whether the player has folded
     * @param folded
     */
    public void setFolded(boolean folded) {
        this.hasFolded = folded;
    }

    /**
     * setter for the money
     * @param money
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * getter for the current bet
     * @return current bet
     */
    public int getCurrentBet() {
        return currentBet;
    }

    /**
     * setter for the currnet bet
     * @param currentBet
     */
    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    /**
     * checks if the player has folded
     * @return
     */
    public boolean hasFolded() {
        return hasFolded;
    }

    /**
     * folds the player
     */
    public void fold() {
        this.hasFolded = true;
    }

    /**
     * gets the cards held by the player
     * @return cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     *  checks if the player is a human
     * @return
     */
    public boolean isHuman() {
        return isHuman;
    }

    /**
     * adds a card to the player's hand
     * @param card
     */
    public void addCard(Card card) {
        if (cards == null) {
            cards = new ArrayList<>(); // ensures cards are initialized
        }
        cards.add(card);
    }

    /**
     * resets player state for the next round
     */
    public void resetForNewRound() {
        this.currentBet = 0;
        this.cards.clear();  
        this.hasFolded = false;
    }
    
    

    // returns a string representation of the player
    @Override
    public String toString() {
        return name + " [Money: $" + money + ", Bet: $" + currentBet + ", Folded: " + hasFolded + "]";
    }
}