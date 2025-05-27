package pokerGame;

import java.util.ArrayList;

/**
 * @author Patrick Christmas
 * @version May 23 2025
 */
public class Inventory {
    private ArrayList<CardSkin> ownedSkins;
    private int coins;

    /**
     * constructs a new inventory with no skins and 0 coins
     */
    public Inventory() {
        this.ownedSkins = new ArrayList<>();
        this.coins = 0;
    }

    // coin management
    public void addCoins(int amount) {
        if (amount > 0) coins += amount;
    }

    public boolean spendCoins(int amount) {
        if (amount <= coins) {
            coins -= amount;
            return true;
        }
        return false;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    // skin management
    public void addSkin(CardSkin skin) {
        if (!owns(skin.getId())) {
            ownedSkins.add(skin);
        }
    }

    public boolean owns(String skinID) {
        for (CardSkin skin : ownedSkins) {
            if (skin.getId().equals(skinID)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<CardSkin> getOwnedSkins() {
        return new ArrayList<>(ownedSkins); 
    }

    @Override
    public String toString() {
        return "Inventory [Coins: " + coins + ", Skins Owned: " + ownedSkins.size() + "]";
    }
}
