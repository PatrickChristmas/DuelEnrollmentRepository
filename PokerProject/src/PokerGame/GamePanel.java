package PokerGame;

import javax.swing.JPanel;
import java.util.Random;
import java.awt.Graphics; 
import java.awt.Color;
import java.awt.Font;
import pokerGame.Drawing;
import javax.imageio.ImageIO;
import javax.swing.JButton; 
import java.awt.BorderLayout; 
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.Image;
import java.io.IOException;

import java.awt.event.KeyListener; 
import java.awt.event.KeyEvent; 

public class GamePanel extends JPanel implements KeyListener {
	private Drawing curCard; // current card being drawn
	private ArrayList<String> cardImage;  // array of the names of card images
	private ArrayList<Drawing> drawnCards; // array of the Drawings of the already drawnCards
	private Color color; // color
	private int randomNum; // randomNum field, used to draw a random card
	private static int number; // number used to draw the cards further apart
    private String username; // username, used in this class so we can draw it herre
	private int currentBet; // current bet amount 
	
	private int numOfRed; // number of Red chips
	private int numOfGreen; // number of Green chips
	private int numOfBlue; // number of Blue chips
	
	
	// Images of the 'objects' drawn
	private Image table; 
	private Image backgroundTexture; 
	private Image redChip;
	private Image greenChip;
	private Image blueChip;
	
	// represent where the chip is drawn or how the chip is spaced. 
	private static final int RED_CHIP_X = 500; 
	private static final int GREEN_CHIP_X = 400; 
	private static final int BLUE_CHIP_X = 450;
	private static final int CHIP_SPACING = 15; 
	
	// signal num is used to indicate that the user has drawn a card, and can no longer alter their bets. Used to help draw the Chips.
	private int signalNum; 
	// takes in the username
	GamePanel(String username1) {
		username = username1; 
		
		color = new Color(139, 0, 0); // red
		
		// declare the arrayLists 
		cardImage = new ArrayList<>();
		drawnCards = new ArrayList<>(); 
		
		setBackground(color);
		// calls imageLoader, so the Files are declared and 'matched' with their proper file.
		imageLoader(); 
		// creates a button to draw cards with 
		JButton addedButton = createDrawButton(); 
		// fills up the cardImage arrayList with the names of the files for the card images
		addItemsToCards();
		// adds button to the top
		add(addedButton, BorderLayout.NORTH);	
		
		// adds the listener  
		addKeyListener(this);
		setFocusable(true); 
		requestFocusInWindow(); 
		
		// sets default bet to 500
		currentBet = 500; 
	} 
	private void imageLoader() {
		// Matches each file to their image
		File file1 = new File("Images/PokerTable.PNG");
		File file2 = new File("Images/RedCarpetTexture.PNG");
		
		File file3 = new File("Images/CasinoChipRed.PNG");
		File file4 = new File("Images/CasinoChipGreen.PNG");
		File file5 = new File("Images/CasinoChipBlue.PNG");
		try {
			// reads the actual image into the File variable 
			table = ImageIO.read(file1).getScaledInstance(3500, 1500, Image.SCALE_SMOOTH);
			backgroundTexture = ImageIO.read(file2).getScaledInstance(-600, 2700, Image.SCALE_SMOOTH);
			
			redChip = ImageIO.read(file3).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			greenChip = ImageIO.read(file4).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			blueChip = ImageIO.read(file5).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			// catch an error with the images not being found.  
		} catch(IOException e) {
			System.out.println("Table image, redChip, blueChip, greenChip, or RedCarpetTexture not found.");
			table = null;
			backgroundTexture = null; 
			redChip = null;
			greenChip = null;
			blueChip = null; 
		}
		
	} 	
	
	// method to increase the bet amount, also repaints the amount after it increases
	private void increaseBet(int amount) {
		currentBet += amount;
		if(currentBet < 0) {
			currentBet = 0;
		}
		repaint();
	}
	
	// creates the draw button at the top of the screen.
	private JButton createDrawButton() {
		JButton button = new JButton("Draw Another Card");
		button.setFont(new Font("Egyptienne", Font.BOLD, 40));

		button.setForeground(Color.BLACK);
		button.setBackground(Color.WHITE);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// draws the Card
				drawCard(); 
				// signal number used to indicate that the user has drawn a card and it is appropriate to draw the chips
				signalNum+=50; 
			}
		}
		);
		return button; 
	}
	// draw cards
	private void drawCard() { 
		// checks if the arrayList is empty 
		if (cardImage.isEmpty()) {
			System.out.println("Out of Cards to draw"); 
			return; 
		}
		// only on the first call of drawCard, number will equal 0 and it will declare number as its intended initial value
		if (number == 0) {
			number = 45; 
		}
			 // gets a random number from the list of cards
			 randomNum = (int) (Math.random() * cardImage.size());
			 // cardfile becomes the randomly chosen card image name
			 String cardFile = cardImage.get(randomNum);
			 // draws the randomly drawn card
			 Drawing newCard = new Drawing(cardFile, 300 + number, 750); 
			 // adds newCard to drawnCards, so the program knows which cards have been drawn
			 drawnCards.add(newCard);
			 // the curCard is changed to the new card.
			 curCard = newCard;
			 repaint(); 
			 // removes the random card from the set of Card Images, so it does not draw again
			 cardImage.remove(randomNum); 
			 // moves the cards along
			 number+=50; 

			
				
			
		
	}
	
	// draws the Chips
	private void drawChips(Graphics g) {
		System.out.println("Drawing Chips"); 
		
		// for spacing purposes 
		int spacerR = 0;
		int spacerG = 0;
		int spacerB = 0;
		
		// place holder number so it does not change the actual bet
		int placeHolderNum = currentBet; 
		
		// gets number of red Chips
		numOfRed = placeHolderNum / 1000;
		placeHolderNum%=1000; 
		
		// gets number of Blue Chips
		numOfBlue = placeHolderNum / 100;
		placeHolderNum%=100;
		
		// gets number of Green Chips
		numOfGreen = placeHolderNum / 10; 
		
		// checks if the chip image is null, then draws the number of chips, then spaces them out 
		if(redChip!=null) {
			for(int i = 0; i < numOfRed; i++) {
				g.drawImage(redChip, RED_CHIP_X, spacerR, null); 
				spacerR+=CHIP_SPACING; 
			}
		}
		
		// checks if the chip image is null, then draws the number of chips, then spaces them out 
		if(greenChip!=null) {
			for(int i = 0; i < numOfGreen; i++) {
				g.drawImage(greenChip, GREEN_CHIP_X, spacerG, null); 
				spacerG+=CHIP_SPACING; 

			} 
		}
		
		// checks if the chip image is null, then draws the number of chips, then spaces them out 
		if(blueChip!=null) {
			for(int i = 0; i < numOfBlue; i++) {
				g.drawImage(blueChip, BLUE_CHIP_X, spacerB, null); 
				spacerB+=CHIP_SPACING; 

			}
		}
		
	}
	
	// draws the table and the backgroundTexture (carpet) 
	private void drawTable(Graphics g) {
		if (backgroundTexture != null) {
			g.drawImage(backgroundTexture, 0, 0, null);
		}
		if (table != null) {
			g.drawImage(table, -400, 0, null);		
		} 
		
	}
	
	@Override 
	// Actualy paints the table, chips, background texture, and draws the username and bet amount. 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawTable(g);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Egyptienne", Font.BOLD, 40));

		g.drawString("Username: "+ username, 2000, 50); 
		// for each card in drawn cards, draw that card. 
		for(Drawing card: drawnCards) {
			card.draw(g); 
		}
		// checks if a card has been drawn, if so, then chips can be drawn.
		if (signalNum > 0) {
			drawChips(g); 
		}
		// draws the bet amount 
		g.drawString("Current Bet: $" + currentBet, 50, 50); 
	
	}
	
	@Override 
	// adjusts the bet amount according to how much you press the up or down arrows. 
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			increaseBet(10); 
			System.out.println(currentBet);

		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (currentBet < 10) {
				return; 
			}
			increaseBet(-10);
		}
	}
	
	// necessary to have keyPressed
	@Override 
	public void keyTyped(KeyEvent e) {
		
	}
	
	// necessary to have keyPressed
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	
	// moves the card, not used in this lab, but will be used in later projects
	public void moveCard(int x, int y) {
		if (curCard != null) {
			curCard.move(x, y, getWidth(), getHeight());
			repaint(); 
		} 
	}
	
	
	// adds the String names of the images to the arrayList cardImage
	private void addItemsToCards() {
		

		cardImage.add("Images/AceOfSpades.PNG");

		cardImage.add("Images/TwoOfSpades.PNG");

		cardImage.add("Images/ThreeOfSpades.PNG");

		cardImage.add("Images/FourOfSpades.PNG");

		cardImage.add("Images/FiveOfSpades.PNG");

		cardImage.add("Images/SixOfSpades.PNG");

		cardImage.add("Images/SevenOfSpades.PNG");

		cardImage.add("Images/EightOfSpades.PNG");

		cardImage.add("Images/NineOfSpades.PNG");

		cardImage.add("Images/TenOfSpades.PNG");

		cardImage.add("Images/JackOfSpades.PNG");

		cardImage.add("Images/QueenOfSpades.PNG");

		cardImage.add("Images/KingOfSpades.PNG");	

		

		cardImage.add("Images/AceOfHearts.PNG");

		cardImage.add("Images/TwoOfHearts.PNG");

		cardImage.add("Images/ThreeOfHearts.PNG");

		cardImage.add("Images/FourOfHearts.PNG");

		cardImage.add("Images/FiveOfHearts.PNG");

		cardImage.add("Images/SixOfHearts.PNG");

		cardImage.add("Images/SevenOfHearts.PNG");

		cardImage.add("Images/EightOfHearts.PNG");

		cardImage.add("Images/NineOfHearts.PNG");

		cardImage.add("Images/TenOfHearts.PNG");

		cardImage.add("Images/JackOfHearts.PNG");

		cardImage.add("Images/QueenOfHearts.PNG");

		cardImage.add("Images/KingOfHearts.PNG");	

		

		cardImage.add("Images/AceOfDiamonds.PNG");

		cardImage.add("Images/TwoOfDiamonds.PNG");

		cardImage.add("Images/ThreeOfDiamonds.PNG");

		cardImage.add("Images/FourOfDiamonds.PNG");

		cardImage.add("Images/FiveOfDiamonds.PNG");

		cardImage.add("Images/SixOfDiamonds.PNG");

		cardImage.add("Images/SevenOfDiamonds.PNG");

		cardImage.add("Images/EightOfDiamonds.PNG");

		cardImage.add("Images/NineOfDiamonds.PNG");

		cardImage.add("Images/TenOfDiamonds.PNG");

		cardImage.add("Images/JackOfDiamonds.PNG");

		cardImage.add("Images/QueenOfDiamonds.PNG");

		cardImage.add("Images/KingOfDiamonds.PNG");	


		cardImage.add("Images/AceOfClubs.PNG");

		cardImage.add("Images/TwoOfClubs.PNG");

		cardImage.add("Images/ThreeOfClubs.PNG");

		cardImage.add("Images/FourOfClubs.PNG");

		cardImage.add("Images/FiveOfClubs.PNG");

		cardImage.add("Images/SixOfClubs.PNG");

		cardImage.add("Images/SevenOfClubs.PNG");

		cardImage.add("Images/EightOfClubs.PNG");

		cardImage.add("Images/NineOfClubs.PNG");

		cardImage.add("Images/TenOfClubs.PNG");

		cardImage.add("Images/JackOfClubs.PNG");

		cardImage.add("Images/QueenOfClubs.PNG");

		cardImage.add("Images/KingOfClubs.PNG");
	}
	// toString 
	@Override
	public String toString() {
	    return "GamePanel [" +
	           "Username: " + username + ", " +
	           "Current Bet: $" + currentBet + ", " +
	           "Drawn Cards: " + drawnCards.size() + ", " +
	           "Red Chips: " + numOfRed + ", " +
	           "Green Chips: " + numOfGreen + ", " +
	           "Blue Chips: " + numOfBlue +
	           "]";
	}
}