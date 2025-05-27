package pokerGame;

import java.awt.Image;

/**
 * @author Patrick Christmas
 * @version May 26 2025
 */
public class CardSkin {
    public enum Rarity {
        COMMON, UNCOMMON, RARE, EPIC, LEGENDARY
    }

    private String id;               // Unique ID
    private String displayName;     
    private Rarity rarity;          
    private Image image;            

    /**
     * constructor for a card skin
     * @param id unique string ID
     * @param displayName name displayed in UI
     * @param rarity rarity level
     * @param image image used for rendering the skin
     */
    public CardSkin(String id, String displayName, Rarity rarity, Image image) {
        this.id = id;
        this.displayName = displayName;
        this.rarity = rarity;
        this.image = image;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return displayName + " [" + rarity + "]";
    }
}