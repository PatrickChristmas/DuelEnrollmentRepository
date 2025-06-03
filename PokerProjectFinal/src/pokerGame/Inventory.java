package pokerGame;

import java.util.ArrayList;

/**
 * Inventory class that stores owned badges and coin balance.
 * Used for tracking player achievements and in game currency.
 *
 * @author Patrick
 * @version June 2 2025
 */
public class Inventory {
    /** 
     * List of badges the player owns
     **/
    private ArrayList<Badge> ownedBadges;

    /** 
     * Number of coins the player currently holds
     **/
    private int coins;

    /**
     * Constructs a new Inventory object with no badges and 0 coins
     */
    public Inventory() {
        this.ownedBadges = new ArrayList<>();
        this.coins = 0;
    }


    /**
     * adds coins to the inventory.
     * @param amount the number of coins to add (MUUUST BE POSITIVE!!!!!)
     */
    public void addCoins(int amount) {
        if (amount > 0) coins += amount;
    }

    /**
     * spends a given number of coins.
     * @param amount the number of coins to spend
     * @return true if the player had enough coins, false otherwise
     */
    public boolean spendCoins(int amount) {
        if (amount <= coins) {
            coins -= amount;
            return true;
        }
        return false;
    }

    /**
     * returns the current coin balance.
     * @return number of coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * sets the coin balance to a specific value.
     * @param coins new coin balance
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }


    /**
     * adds a badge to the inventory if it is not already owned.
     * @param badge the badge to add
     */
    public void addBadge(Badge badge) {
        if (!owns(badge.getId())) {
            ownedBadges.add(badge);
        }
    }

    /**
     * checks whether the inventory owns a badge with a given ID.
     * @param badgeID the ID of the badge to check
     * @return true if the badge is owned, false otherwise
     */
    public boolean owns(String badgeID) {
        for (Badge badge : ownedBadges) {
            if (badge.getId().equals(badgeID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns a copy of the list of owned badges
     * @return ArrayList of owned Badge objects
     */
    public ArrayList<Badge> getOwnedBadges() {
        return new ArrayList<>(ownedBadges);
    }

    /**
     * returns a string summary of the inventory
     * @return string showing coin count and badge count
     */
    @Override
    public String toString() {
        return "Inventory [Coins: " + coins + ", Badges Owned: " + ownedBadges.size() + "]";
    }
}