package pokerGame;


import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

public class Drawing {
	
	private static int height = 100;  // height of the card
	private static int width = 100;  // width of the card
	private Image card; // create a card of type image
	private Point point; // stores the points (x,y) of where the card will be drawn.
	
	// used in the GamePanel class, used to draw the cards
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
		point = new Point(x,y);

}
	// draws the cards
	public void draw(Graphics g) {
		// if the images exist
		if (card != null) {
			// draw the image at the desire points
			g.drawImage(card, (int) point.getX(), (int) point.getY(), null);
		} else {
			// error handling
			g.drawString("image not found", (int) point.getX(), (int) point.getY());
		}
		
	}
	// did not get to use this method in this stage of the program, but WILL be used in future programs.
	public void move(int changex, int changey, int panelWidth, int panelHeight) {
		point.translate(changex,changey);
		
		int x = (int) point.getX();
		int y = (int) point.getY();
		// these if statements are to account for wrapping. 
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
	
	// getter for width
	public static int getWidth() {
		return width;
	}
	// getter for height 
	public static int getHeight() {
		return height;
	}
	// toString
	@Override
	public String toString() {
	    String cardStatus = ""; 
	    if (card != null) {
	        cardStatus = "Loaded";
	    } else {
	        cardStatus = "Not Loaded";
	    }
	    
	    return "Drawing [Width: " + width + ", Height: " + height + ", Card Status: " + cardStatus + ", Position: (" + point.getX() + ", " + point.getY() + ")]";
	}
}