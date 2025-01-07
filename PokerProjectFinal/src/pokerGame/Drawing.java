package pokerGame;

import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

/**
 * @author PatrickChristmas
 * @version December 20 2024.
 * The Drawing class is used for handling and rendering images (representing cards) in the game. 
 * It allows loading images, drawing them to a specified location, handling basic movement and boundaries.
 * This class is primarily used within the GamePanel class for drawing of the cards.
 */
public class Drawing {

    /**
     * The height of the card.
     */
    private static int height = 100;

    /**
     * The width of the card.
     */
    private static int width = 100;

    /**
     * The image of the card
     */
    private Image card;

    /**
     * The point representing the coordinates of where the card will be drawn.
     */
    private Point point;

    /**
     * Constructs a Drawing object for a card and sets the drawing location
     * 
     * @param filename, the path to the image file to be loaded.
     * @param x, the x coordinate where the card will be drawn.
     * @param y, the y coordinate where the card will be drawn.
     */
    public Drawing(String filename, int x, int y) {
        File file = new File(filename);
        try {
            // SCALE_SMOOTH used to resize the image
            card = ImageIO.read(file).getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch(IOException e) { 
            System.out.println("Image Not Found " + filename);
            card = null;
        }
        // stores the point of where we want to draw the cards
        point = new Point(x, y);
    }

    /**
     * Draws the card image at the coordinates.
     * If the image is not found, an error message will be drawn instead.
     * 
     * @param g, the Graphics object used for drawing the image.
     */
    public void draw(Graphics g) {
        // if the image exists
        if (card != null) {
            // draw the image at the desired points
            g.drawImage(card, (int) point.getX(), (int) point.getY(), null);
        } else {
            // error handling
            g.drawString("Image not found", (int) point.getX(), (int) point.getY());
        }
    }

    /**
     * Moves the card by the x and y amounts
     * If the card moves beyond the boundaries of the panel, it wraps around to the other side.
     * 
     * @param changex, the amount by which the x coordinate will be changed.
     * @param changey, the amount by which the y coordinate will be changed.
     * @param panelWidth, used to check for horizontal boundary wrapping.
     * @param panelHeight, used to check for vertical boundary wrapping.
     */
    public void move(int changex, int changey, int panelWidth, int panelHeight) {
        point.translate(changex, changey);
        
        int x = (int) point.getX();
        int y = (int) point.getY();

        // account for wrapping when the card goes beyond the panel boundaries
        if (x > panelWidth) {
            x = 0; 
        }
        if (x < 0) {
            x = panelWidth - width;
        }
        if (y < 0) {
            y = panelHeight - height;
        }
        if(y > panelHeight) {
            y = 0;
        }

        point.move(x, y);
    }

    /**
     * Returns the width of the card.
     * 
     * @return the width of the card.
     */
    public static int getWidth() {
        return width;
    }

    /**
     * Returns the height of the card.
     * 
     * @return the height of the card.
     */
    public static int getHeight() {
        return height;
    }

    /**
     *
     * @return a card width, height, status, and position
     */
    @Override
    public String toString() {
        String cardStatus = ""; 
        if (card != null) {
            cardStatus = "Loaded";
        } else {
            cardStatus = "Not Loaded";
        }

        return "Drawing [Width: " + width + ", Height: " + height + 
               ", Card Status: " + cardStatus + ", Position: (" + point.getX() + ", " + point.getY() + ")]";
    }
}