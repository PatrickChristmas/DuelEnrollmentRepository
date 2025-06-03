package pokerGame;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @version January 1 2025
 * @author Patrick Christmas 
 * Represents a playing card with a value, type (suit), and image.
 */
public class Card {
    
    /**
     * The value of the card 
     */
    private String value;

    /**
     * The type of the card's suit 
     */
    private String type;

    /**
     * The file path to the image representing the card.
     */
    private String imagePath;

    /**
     * Constructs a new Card with the specified value, type, and image path.
     * 
     * @param value the value of the card
     * @param type the type of the card's suit
     * @param imagePath the file path to the card's image
     */
    public Card(String value, String type, String imagePath) {
        this.value = value;
        this.type = type;
        this.imagePath = imagePath;
    }

    /**
     * Gets the value of the card.
     * 
     * @return the value of the card
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the card.
     * 
     * @param value the new value of the card
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /**
     * shortens the names of the suits and ranks, helpful for debugging
     * @return the shorten String of rank and suit
     */
    public String getShortName() {
        String rankShort = value.equals("10") ? "T" : value.substring(0, 1).toUpperCase();
        String suitShort = type.substring(0, 1).toLowerCase(); 
        return rankShort + suitShort;
    }
    

    /**
     * Gets the image of the card.
     * 
     * @return the image of the card, or null if the image cannot be read
     */
    public Image getImage() {
        try {
            return ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the type of the card's suit.
     * 
     * @return the type of the card's suit
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the card's suit.
     * 
     * @param type the new type of the card's suit
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the file path to the image of the card.
     * 
     * @return the file path to the card's image
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets the file path to the image of the card.
     * 
     * @param imagePath the new file path to the card's image
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Returns a string representation of the card.
     * 
     * @return a string in the format "value of type"
     */
    @Override
    public String toString() {
        return value + " of " + type;
    }
}