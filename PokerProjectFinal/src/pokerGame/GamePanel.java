package pokerGame;

import javax.swing.JPanel;
import java.util.Random;
import java.awt.Graphics; 
import java.awt.Color;
import java.awt.Font;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.BorderLayout; 
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.Timer;
import java.awt.Dimension;



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
	private int currentMoney; 
	
	private int numOfRed; // number of Red chips
	private int numOfGreen; // number of Green chips
	private int numOfBlue; // number of Blue chips
	
	
	// images of the 'objects' drawn
	private Image table; 
	private Image backgroundTexture; 
	private Image redChip;
	private Image greenChip;
	private Image blueChip;
	
	// represent where the chip is drawn or how the chip is spaced. 
	private final static int RED_CHIP_X = 500; 
	private final static int GREEN_CHIP_X = 400; 
	private final static int BLUE_CHIP_X = 450;
	private final static int CHIP_SPACING = 15; 
	
	// used for the 'animation' of the chips, just their X value 
	private int animatedRedChipX;
	private int animatedGreenChipX;
	private int animatedBlueChipX;
	
	// used for the 'animation' of the chips, just their Y value 
	private int animatedRedChipY; 
	private int animatedGreenChipY; 
	private int animatedBlueChipY; 
	
	// starting Y positions for the chips 
	private int INITIAL_RED_CHIP_Y = 100; 
	private int INITIAL_GREEN_CHIP_Y = 100; 
	private int INITIAL_BLUE_CHIP_Y = 100; 
	
	
	

    private int chipMoveSpeed = 2; // Speed of the chip movement, 2 pixels
    private Timer chipAnimationTimer; // Timer for chip animation
    
    private int animatedCardX; // used for animating the card (Does not work in current stage)
    private int animatedCardY; // used for animating the card (Does not work in current stage)
    private int targetX; // used for animating the card (Does not work in current stage)
    private int targetY; // used for animating the card (Does not work in current stage)
    private int cardMoveSpeed = 5; // moves 5 pixels 
    private Timer cardAnimationTimer;

    // used for the screen's height and width
    private int screenWidth;
    private int screenHeight; 
    
    
	
	// signal num is used to indicate that the user has drawn a card, and can no longer alter their bets. Used to help draw the Chips.
	private int signalNum; 
	

	// takes in the username
	GamePanel(String username1) {
		username = username1; 
		
		color = new Color(139, 0, 0); // red
		
		
		
		// declare the arrayLists 
		cardImage = new ArrayList<>();
		drawnCards = new ArrayList<>(); 
		
		// assigns the animatedChipX to the final static variables 
		animatedRedChipX = RED_CHIP_X;
		animatedGreenChipX = GREEN_CHIP_X;
		animatedBlueChipX = BLUE_CHIP_X;
		
		animatedRedChipY = INITIAL_RED_CHIP_Y;
		animatedGreenChipY = INITIAL_GREEN_CHIP_Y;
		animatedBlueChipY = INITIAL_BLUE_CHIP_Y;
		
		
		
		animatedCardX = getWidth() - 200;  //starts 200 pixels from the upper right
		animatedCardY = 50;  // starts at 50 pixels
		targetX = getWidth() / 2 - 100; // its supposed to go 100 pixels to the left of the center of the screen
		targetY = getHeight() / 2; // supposed to go to the center of the screen 
		
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
		
		// gets the current Money from the UserNamePanel Class (DOES NOT WORK! getSelectedAmount(), does not work right now)
		UserNamePanel usp = new UserNamePanel();
		currentMoney = usp.getSelectedAmount(); 
		System.out.println(currentMoney);
		
		 
		
		// sets default bet to 500
		currentBet = 0; 
		// starts the animations 
		startChipAnimation(); 
		startCardAnimation(); 
	} 
		
	// used to animated the chips 
	private void startChipAnimation() {
		
	    // 3000 ms delay (3 seconds)
	    Timer delayTimer = new Timer(3000, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // creates a timer so the Chip can constantly update so it can move
	            chipAnimationTimer = new Timer(16, new ActionListener() { 
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                    // Move chips towards the center
	                    moveChipsTowardsCenterX();
	                    moveChipsTowardsCenterY();

	                    repaint(); // updates the chips positions 
	                }
	            });
	            chipAnimationTimer.start();
	            ((Timer) e.getSource()).stop(); // Stops the delay timer 
	        }
	    });
	    
	    // Start the delay timer
	    delayTimer.setRepeats(false); // Only execute once
	    // starts the delayTimer 
	    delayTimer.start();
	}
	
	
	 private void startCardAnimation() {
	        cardAnimationTimer = new Timer(16, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	// Moves the cards towards the center 
	                moveCardTowardsCenterX();
	                moveCardTowardsCenterY();

	                // checks if it has reached the targetX and targetY
	                if (animatedCardX == targetX && animatedCardY == targetY) {
	                    cardAnimationTimer.stop(); // stops the animation 
	                }
	                repaint(); // updates the position 
	            }
	        });
	        cardAnimationTimer.start(); // starts the animation timer
	    }
	
	 
	 
	private void imageLoader() {
		// Matches each file to their image
		File file1 = new File("Images/PokerTable.PNG");
		File file2 = new File("Images/RedCarpetTexture.PNG");
		
		File file3 = new File("Images/CasinoChipRed.PNG");
		File file4 = new File("Images/CasinoChipGreen.PNG");
		File file5 = new File("Images/CasinoChipBlue.PNG");
		
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	     screenWidth = (int) (size.getWidth() * .6);
	     screenHeight = (int) (size.getHeight() * .6); 
		try {
			// reads the actual image into the File variable 
			table = ImageIO.read(file1).getScaledInstance((screenWidth * 2 - 100), (screenHeight * 2), Image.SCALE_SMOOTH);
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
	private void drawCard() {
	    // Check if there are cards left to draw
	    if (cardImage.isEmpty()) {
	        System.out.println("Out of Cards to draw"); 
	        return; 
	    }

	    // Initialize number if it's the first call
	    if (number == 0) {
	        number = 45; 
	    }

	    // Get a random card from the cardImage list
	    randomNum = (int) (Math.random() * cardImage.size());
	    String cardFile = cardImage.get(randomNum);
	    Drawing newCard = new Drawing(cardFile, 300 + number, 750); 

	    drawnCards.add(newCard);
	    curCard = newCard;

	    // updates the card's initial animated position
	    animatedCardX = 300 + number; // updates where the card is drawn
	    animatedCardY = 750; // initial y position 

	    // removes the drawn card from the available cards
	    cardImage.remove(randomNum); 
	    number += 50; 

	    // starts the card animation
	    startCardAnimation(); 
	    repaint(); // repaints to show the new cards
	}
	
	private void drawChips(Graphics g) {
	   

	    // gets number of red Chips
	    numOfRed = currentBet / 1000;

	    // place holder number so it does not change the actual bet
	    int placeHolderNum = currentBet; 

	    // gets number of Blue Chips
	    numOfBlue = placeHolderNum / 100;
	    placeHolderNum %= 100;

	    // gets number of Green Chips
	    numOfGreen = placeHolderNum / 10; 

	    // Debugging output for chip loading
	    if (redChip == null) System.out.println("Red chip image not loaded");
	    if (greenChip == null) System.out.println("Green chip image not loaded");
	    if (blueChip == null) System.out.println("Blue chip image not loaded");

	    // draw red chips at the new position 
	    if (redChip != null) {
	        for (int i = 0; i < numOfRed; i++) {
	            g.drawImage(redChip, animatedRedChipX, animatedRedChipY + (i * CHIP_SPACING), null);
	        }
	    }

	    // draw green chips at the new position 
	    if (greenChip != null) {
	        for (int i = 0; i < numOfGreen; i++) {
	            g.drawImage(greenChip, animatedGreenChipX, animatedGreenChipY + (i * CHIP_SPACING), null);
	        }
	    }

	    // draw blue chips at the new position 
	    if (blueChip != null) {
	        for (int i = 0; i < numOfBlue; i++) {
	            g.drawImage(blueChip, animatedBlueChipX, animatedBlueChipY + (i * CHIP_SPACING), null);
	        }
	    }
	}
	
	// draws the table and the backgroundTexture (carpet) 
	private void drawTable(Graphics g) {
		if (backgroundTexture != null) {
			g.drawImage(backgroundTexture, 0, 0, null);
		}
		if (table != null) {
			g.drawImage(table, (int) (-screenWidth * .4 ), 0, null);		
		} 
		
	}
	
	@Override 
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    drawTable(g);

	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Egyptienne", Font.BOLD, 40));

	   
	    g.drawString("Username: " + username, 50, 50); // draws the username string on the gamepanel

	    // draws all drawn cards
	    for (Drawing card : drawnCards) {
	        card.draw(g); 
	    }
	    
	    if (curCard != null) {
	        curCard.draw(g);  // draws the card that is moving 
	    }
	    
	    
	    // only draws the chips when the cards have been drawn 
	    if (signalNum > 0) {
	        drawChips(g); 
	    }

	    // current bet vs current money 
	 
	    g.drawString("Current Bet: $" + currentBet, 50, 100); 
	    if (currentMoney - currentBet > 0) {
	    	g.drawString("Current Money: $" + (currentMoney - currentBet), 50, 150); 
	    }
	}
	
	private void moveChipsTowardsCenterX() {
	    int centerX = getWidth() / 2; // center of the X 

	    // moves red chips towards center (if it is from the right) and stops 
	    if (animatedRedChipX < centerX) {
	        animatedRedChipX += chipMoveSpeed; 
	        if (animatedRedChipX > centerX) {
	            animatedRedChipX = centerX; 
	        }
	        
	        // moves the red chips (if it is from the left) to the center and stops 
	    } else if (animatedRedChipX > centerX) {
	        animatedRedChipX -= chipMoveSpeed; 
	        if (animatedRedChipX < centerX) {
	            animatedRedChipX = centerX; 
	        }
	    }

	    // moves green chips towards center (if it is from the right) and stops 
	    if (animatedGreenChipX < centerX) {
	        animatedGreenChipX += chipMoveSpeed; // Move right towards center
	        if (animatedGreenChipX > centerX) {
	            animatedGreenChipX = centerX; 
	        }
	        
		// moves green chips towards center (if it is from the left) and stops 
	    } else if (animatedGreenChipX > centerX) {
	        animatedGreenChipX -= chipMoveSpeed; 
	        if (animatedGreenChipX < centerX) {
	            animatedGreenChipX = centerX; 
	        }
	    }

	    // moves blue chips towards center (if it is from the right) and stops 
	    if (animatedBlueChipX < centerX) {
	        animatedBlueChipX += chipMoveSpeed; 
	        if (animatedBlueChipX > centerX) {
	            animatedBlueChipX = centerX; 
	        }
	        
		// moves green chips towards center (if it is from the left) and stops 
	    } else if (animatedBlueChipX > centerX) {
	        animatedBlueChipX -= chipMoveSpeed; 
	        if (animatedBlueChipX < centerX) {
	            animatedBlueChipX = centerX; 
	        }
	    }
	}
	
	private void moveCardTowardsCenterX() {
	    // moves the card towards target X coordinate (center) then stops (this is approaching from the right)
	    if (animatedCardX < targetX) {
	        animatedCardX += cardMoveSpeed; 
	        if (animatedCardX > targetX) {
	            animatedCardX = targetX; 
	        }
	        
	     // moves the card towards target X coordinate (center) then stops (this is approaching from the left)
	    } else if (animatedCardX > targetX) {
	        animatedCardX -= cardMoveSpeed; 
	        if (animatedCardX < targetX) {
	            animatedCardX = targetX; 
	        }
	    }
	}

	private void moveCardTowardsCenterY() {
	     // moves the card towards target Y coordinate (center) then stops (this is approaching from the bottom)
	    if (animatedCardY < targetY) {
	        animatedCardY += cardMoveSpeed; 
	        if (animatedCardY > targetY) {
	            animatedCardY = targetY; 
	        }
	     // moves the card towards target Y coordinate (center) then stops (this is approaching from the top)
	    } else if (animatedCardY > targetY) {
	        animatedCardY -= cardMoveSpeed; 
	        if (animatedCardY < targetY) {
	            animatedCardY = targetY; 
	        }
	    }
	}
	
	
	// moves chips towards the center y 
	private void moveChipsTowardsCenterY() {
	    int centerY = getHeight() / 2; // center y coordinate

	 // moves the red chip towards target Y coordinate (center) then stops (this is approaching from the bottom)
	    if (animatedRedChipY < centerY) {
	        animatedRedChipY += chipMoveSpeed; 
	        if (animatedRedChipY > centerY) {
	            animatedRedChipY = centerY; 
	        }
	   	 // moves the red chip towards target Y coordinate (center) then stops (this is approaching from the top)

	    } else if (animatedRedChipY > centerY) {
	        animatedRedChipY -= chipMoveSpeed; 
	        if (animatedRedChipY < centerY) {
	            animatedRedChipY = centerY; 
	        }
	    }

		 // moves the green chip towards target Y coordinate (center) then stops (this is approaching from the bottom)
	    if (animatedGreenChipY < centerY) {
	        animatedGreenChipY += chipMoveSpeed; 
	        if (animatedGreenChipY > centerY) {
	            animatedGreenChipY = centerY; 
	        }
	   	 // moves the green chip towards target Y coordinate (center) then stops (this is approaching from the top)
	    } else if (animatedGreenChipY > centerY) {
	        animatedGreenChipY -= chipMoveSpeed; 
	        if (animatedGreenChipY < centerY) {
	            animatedGreenChipY = centerY; 
	        }
	    }

		 // moves the blue chip towards target Y coordinate (center) then stops (this is approaching from the bottom)
	    if (animatedBlueChipY < centerY) {
	        animatedBlueChipY += chipMoveSpeed; 
	        if (animatedBlueChipY > centerY) {
	            animatedBlueChipY = centerY; 
	        }
	   	 // moves the blue chip towards target Y coordinate (center) then stops (this is approaching from the top)
	    } else if (animatedBlueChipY > centerY) {
	        animatedBlueChipY -= chipMoveSpeed; 
	        if (animatedBlueChipY < centerY) {
	            animatedBlueChipY = centerY; 
	        }
	    }
	}
	
	
	
	@Override 
	// adjusts the bet amount according to how much you press the up or down arrows. 
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP && currentBet < currentMoney) {
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