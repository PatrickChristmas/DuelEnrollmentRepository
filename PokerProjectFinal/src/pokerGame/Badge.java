package pokerGame;

import javax.swing.ImageIcon;

/**
 * represents a shop badge that a player can purchase and collect
 * each badge has an ID, display name, price, and image icon
 * @author Patrick Christmas
 * @version June 1 2025
 */
public class Badge {
    private String id;
    private String displayName;
    private int price;
    private ImageIcon imageIcon;

    /**
     * Constructs a new Badge.
     * @param id            identifier for the badge
     * @param displayName  name shown in the shop
     * @param price        cost in coins to purchase
     * @param imageIcon    image for badge
     */
    public Badge(String id, String displayName, int price, ImageIcon imageIcon) {
        this.id = id;
        this.displayName = displayName;
        this.price = price;
        this.imageIcon = imageIcon;
    }

    /**
     * gets the unique ID of the badge
     * @return the badge ID
     */
    public String getId() {
        return id;
    }

    /**
     * gets the display name of the badge.
     * @return the name shown in the shop
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the price of the badge in coins.
     * @return the coin cost
     */
    public int getPrice() {
        return price;
    }

    /**
     * gets the image icon for the badge.
     * @return ImageIcon object or null
     */
    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    /**
     * returns a string representation of the badge for debugging
     * @return formatted string with ID, name, and price
     */
    @Override
    public String toString() {
        return "Badge[ID=" + id + ", Name=\"" + displayName + "\", Price=" + price + "]";
    }
}