package pokerGame;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author PatrickChristmas
 * @version March 25 2025
 * The GamePanel class is the panel where the poker game is displayed. It handles the drawing of cards, chips, 
 * updating the user's bet, current money, and the animation of chips and cards. It involves the user's interaction by   
 * pressing arrow keys and clicking a button to draw a card
 */

public class GamePanel extends JPanel implements KeyListener {
	/**
	 * names of the card images
	 */
	private ArrayList<String> cardImage;

	/**
	 * holds drawn card drawings
	 */
	private ArrayList<Drawing> drawnCards;

	/**
	 * cards shared on table
	 */
	private ArrayList<Card> communityCards;

	/**
	 * the player who won
	 */
	private Player winningPlayer;

	/**
	 * winning player cards
	 */
	private ArrayList<Card> winningCards;

	/**
	 * combined bets from ai
	 */
	private int totalAIBets;

	/**
	 * if flop is on table
	 */
	private boolean flopDealt = false;

	/**
	 * user hand info
	 */
	private String userHandDescription;

	/**
	 * how many times user raised
	 */
	private int userRaiseCount;

	/**
	 * other players' hands
	 */
	private ArrayList<ArrayList<Card>> otherPlayersHands;

	/**
	 * base panel color
	 */
	private Color color;

	/**
	 * offset for drawing cards
	 */
	private static int number;

	/**
	 * player name
	 */
	private String username;

	/**
	 * current bet amount
	 */
	private int currentBet;

	/**
	 * money the user has
	 */
	private int currentMoney;

	/**
	 * count of red chips
	 */
	private int numOfRed;

	/**
	 * count of green chips
	 */
	private int numOfGreen;

	/**
	 * count of blue chips
	 */
	private int numOfBlue;

	/**
	 * texture for background
	 */
	private Image backgroundTexture;

	/**
	 * red chip image
	 */
	private Image redChip;

	/**
	 * green chip image
	 */
	private Image greenChip;

	/**
	 * blue chip image
	 */
	private Image blueChip;

	/**
	 * card back image
	 */
	private Image backOfCard;

	/**
	 * red chip x pos
	 */
	private final static int RED_CHIP_X = 500;

	/**
	 * green chip x pos
	 */
	private final static int GREEN_CHIP_X = 400;

	/**
	 * blue chip x pos
	 */
	private final static int BLUE_CHIP_X = 450;

	/**
	 * space between chips
	 */
	private final static int CHIP_SPACING = 15;

	/**
	 * red chip x for anim
	 */
	private int animatedRedChipX;

	/**
	 * green chip x for anim
	 */
	private int animatedGreenChipX;

	/**
	 * blue chip x for anim
	 */
	private int animatedBlueChipX;

	/**
	 * red chip y for anim
	 */
	private int animatedRedChipY;

	/**
	 * green chip y for anim
	 */
	private int animatedGreenChipY;

	/**
	 * blue chip y for anim
	 */
	private int animatedBlueChipY;

	/**
	 * red chip start y
	 */
	private int INITIAL_RED_CHIP_Y = 100;

	/**
	 * green chip start y
	 */
	private int INITIAL_GREEN_CHIP_Y = 100;

	/**
	 * blue chip start y
	 */
	private int INITIAL_BLUE_CHIP_Y = 100;

	/**
	 * ai chip x
	 */
	private int aiRedChipX, aiGreenChipX, aiBlueChipX;

	/**
	 * ai chip y
	 */
	private int aiRedChipY, aiGreenChipY, aiBlueChipY;

	/**
	 * how fast chips move
	 */
	private int chipMoveSpeed = 2;

	/**
	 * chip animation timer
	 */
	private Timer chipAnimationTimer;

	/**
	 * screen height size
	 */
	private int screenHeight;

	/**
	 * screen width size
	 */
	private int screenWidth;

	/**
	 * game deck
	 */
	private Deck deck;

	/**
	 * button to begin game
	 */
	private JButton startGameButton;

	/**
	 * locks bets after draw
	 */
	private int signalNum;

	/**
	 * if game started
	 */
	private boolean gameStarted = false;

	/**
	 * fold button
	 */
	private JButton foldButton;

	/**
	 * call button
	 */
	private JButton callButton;

	/**
	 * raise button
	 */
	private JButton raiseButton;

	/**
	 * list of players
	 */
	private ArrayList<Player> players;

	/**
	 * whose turn it is
	 */
	private int currentPlayerIndex;

	/**
	 * total pot money
	 */
	private int pot;

	/**
	 * helper for game logic
	 */
	private GamePanelActions actions;

    
    
 // takes in the username
 	/**
      * Constructor for the GamePanel class.
      * Initializes the panel, loads images, and sets up the game.
      * 
      * @param username1, The username of the player
      */
    public GamePanel(String username1) {
        username = username1;
        
        
        color = new Color(139, 0, 0);
        
        // declare the arrayLists 
     	cardImage = new ArrayList<>();
     	drawnCards = new ArrayList<>(); 
     	communityCards = new ArrayList<>();
        
     	// flop has not been dealt yet (comes after all four players have gone) 
     	flopDealt = false;
        
     	// description of the user's hand (written on bottom left)
     	userHandDescription = "";

     	// assigns the animatedChipX to the final static variables 
     	animatedRedChipX = RED_CHIP_X;
     	animatedGreenChipX = GREEN_CHIP_X;
     	animatedBlueChipX = BLUE_CHIP_X;
        
        animatedRedChipY = INITIAL_RED_CHIP_Y;
        animatedGreenChipY = INITIAL_GREEN_CHIP_Y;
        animatedBlueChipY = INITIAL_BLUE_CHIP_Y;

        
        aiRedChipX = 200;
        aiGreenChipX = 300;
        aiBlueChipX = 400;
        aiRedChipY = 50;
        aiGreenChipY = 50;
        aiBlueChipY = 50;

        setBackground(color);
		// calls imageLoader, so the Files are declared and 'matched' with their proper file.
		imageLoader(); 

		// shuffles the deck so it is not the same every round
        GameLogic gameLogic = new GameLogic();
        deck = gameLogic.getDeck();

        addItemsToCards();
        
        
        // adds the listener  
     	addKeyListener(this);
     	setFocusable(true); 
     	requestFocusInWindow();

     	// gets the current Money from the SettingsPanel Class 
     	SettingsPanel usp = new SettingsPanel();
     	currentMoney = usp.getSelectedAmount(); 
     	initializePlayers(4, currentMoney); // Example: 4 players with $1000 each

     	// sets default bet to 500
     	currentBet = 0;

     	// starts the animations 
     	startChipAnimation(); 
        createStartGameButton();
        createPokerActionButtons();

        // initialize the game actions methods
        actions = new GamePanelActions();
        actions.setPlayers(players);
        actions.setCurrentPlayerIndex(currentPlayerIndex);
        actions.setPot(pot);
        
        actions.setGameStarted(gameStarted);
        actions.setCurrentBet(currentBet);
        actions.setUserRaiseCount(userRaiseCount);
        
        actions.setCommunityCards(communityCards);
        actions.setDeck(deck);
        actions.setFlopDealt(flopDealt);

        // callback for state changes (AI turn done, etc)
        actions.setOnStateChange(new Runnable() {
            @Override
            public void run() {
                syncState();
                repaint();
            }
        });

        // callback for chip animation whenever the pot changes
        actions.setOnAnimateChips(new Runnable() {
            @Override
            public void run() {
            // if the current player is the user (index 0), animate user chips
            if (currentPlayerIndex == 0) {
                resetUserChipPositions();
                startChipAnimation(); // reuse your user animation
            } else {
                resetAIChipPositions();
                startAIChipAnimation(); // new AI animation
            }
            }
        });
    }
    
    /**
	 * array that initializes the players (generates them a name, initial money, and their cards) 
	 * @param numberOfPlayers, number of players in the game (4)
	 * @param initialMoney, starting money for the players (1000 or 2000 or 3000) 
	 */
    
    private void initializePlayers(int numberOfPlayers, int initialMoney) {
    	// Array of possible player names
        String[] possibleNames = {"Keith", "Tim", "Toby", "Patrick", "Daniel", "Mrs. Kelly",
                                  "Mrs. Baniqued", "Ron", "Craig", "Jack", "Abraham", "Rosseau",
                                  "Jacques", "Maximillian", "Rui", "Bob", "Thomas", "John"};
        // Shuffle the array manually
        Random rand = new Random();
        for (int i = possibleNames.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            // Swap elements
            String temp = possibleNames[i];
            possibleNames[i] = possibleNames[j];
            possibleNames[j] = temp;
        }
        players = new ArrayList<>(); // initialize the list to hold players
        
        // loops through the number of players and creates each one
        for (int i = 0; i < numberOfPlayers; i++) {
        	// pick a name from the shuffled array
            String playerName = (i == 0) ? username : possibleNames[i];
            boolean isHuman = (i == 0);
            Player player = new Player(playerName, initialMoney, isHuman);

            // deal two cards to each player
            for (int j = 0; j < 2; j++) {
                if (!deck.isEmpty()) {
                    player.addCard(deck.dealCard()); // add a card if the deck isn't empty
                }
            }
            players.add(player); // add player to the list
        }
        currentPlayerIndex = 0; // starts with the first player
	    pot = 0; // initialize the pot amount
    }

    
    /**
	 * Starts the poker game, handles the starting bet, draws the initial cards for the player, and actual "initializes" the other players 
	 */
    private void startGame() {
    	// checks if the game has already started
        if (!gameStarted) {
        	gameStarted = true; // marks the game as started
	        signalNum += 50; // increases the signal number by 50 (signals that the game has begun)
            Player humanPlayer = players.get(0);
            
            // subtract bet only if it hasn't been deducted yet
            if (currentBet > 0 && humanPlayer.getCurrentBet() == 0) {
                humanPlayer.setMoney(humanPlayer.getMoney() - currentBet); // deducts the bet from the human player's money
                humanPlayer.setCurrentBet(currentBet); // updates the human player's current bet
                pot += currentBet; // adds the bet to the pot
            }

            // initializes the hands for other players
            initializeOtherPlayersHands(3, 2);

            startGameButton.setEnabled(false); // disables the start game button
            repaint();
        }
    }
    
    
    /**
	 * intializes the other places cards, or otherwise creates the hands for these players depending on the amonunt of cards and number of players specified by the parameter
	 * @param numberOfPlayers, number of other players in the game 
	 * @param cardsPerPlayer, amount of cards the other players in the game should have 
	 */
    private void initializeOtherPlayersHands(int numberOfPlayers, int cardsPerPlayer) {
        otherPlayersHands = new ArrayList<>(); // initializes the list to hold hands for other players
        
        // loops through the number of players
        for (int i = 0; i < numberOfPlayers; i++) {
            ArrayList<Card> hand = new ArrayList<>(); // creates a new hand for each player
            
            // deal cards to the player
            for (int j = 0; j < cardsPerPlayer; j++) {
                if (!deck.isEmpty()) {
                    hand.add(deck.dealCard()); // adds a card to the hand if the deck is not empty
                } 
            }
            otherPlayersHands.add(hand); // adds the hand to the list of other players' hands
        }
    }
    
    /**
	 * creates the start button 
	 */
    private void createStartGameButton() {
    	startGameButton = new JButton("Start Game"); // creates the button with the text "Start Game"
	    startGameButton.setFont(new Font("Arial", Font.BOLD, 30)); // sets the font of the button text
	    startGameButton.setBackground(Color.WHITE); // sets the background color to white
	    startGameButton.setForeground(Color.BLACK); // sets the text color to black
        
	     // add an ActionListener to the button
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(); // calls startGame method when the button is clicked
                syncState(); 
                repaint();
            }
        });
        add(startGameButton); // adds the button to the panel
    } 
    
    
    /**
	 * creates the poker action buttons (Fold, Call, Raise) and adds them to the action panel
	 */
    private void createPokerActionButtons() {
        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(new Color(8,99,44)); // matches table background
        
        // Fold button
        foldButton = new JButton("Fold");
        foldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions.fold(); // calls the fold method
                syncState();
                repaint();
            }
        });
        
        // Call button
        callButton = new JButton("Call");
        callButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions.call(); // calls the call method
                syncState();
                repaint();
            }
        });
        
        // Raise button
        raiseButton = new JButton("Raise");
        raiseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actions.raise(); // calls the raise method
                syncState(); 
                repaint();
            }
        });
        
        // add buttons to the action panel
        actionPanel.add(foldButton);
        actionPanel.add(callButton);
        actionPanel.add(raiseButton);
        
        // add the action panel to the bottom of the main panel
        add(actionPanel, BorderLayout.SOUTH);
    }

    /**
     * syncs the main panels state with the actions helper
     */
    private void syncState() {
        currentBet = actions.getCurrentBet();
        pot = actions.getPot();
        currentPlayerIndex = actions.getCurrentPlayerIndex();
        userRaiseCount = actions.getUserRaiseCount();
        communityCards = actions.getCommunityCards();
        totalAIBets = actions.getTotalAIBets();
        winningPlayer = actions.getWinningPlayer();
        winningCards = actions.getWinningCards();
        flopDealt = actions.isFlopDealt();
    }

    /**
     * resets user’s chips to the 'starting' location
     */
    private void resetUserChipPositions() {
        animatedRedChipX = RED_CHIP_X;
        animatedGreenChipX = GREEN_CHIP_X;
        animatedBlueChipX = BLUE_CHIP_X;
        animatedRedChipY = INITIAL_RED_CHIP_Y;
        animatedGreenChipY = INITIAL_GREEN_CHIP_Y;
        animatedBlueChipY = INITIAL_BLUE_CHIP_Y;
    }

    /**
     * reset AI’s chips to some seat location
     */
    private void resetAIChipPositions() {
        aiRedChipX = 200;
        aiGreenChipX = 300;
        aiBlueChipX = 400;
        aiRedChipY = 50;
        aiGreenChipY = 50;
        aiBlueChipY = 50;
    }

    
 // used to animated the chips 
	   /**
  * Starts the chip animation. Chips will move towards the center of the screen.
  */
private void startChipAnimation() {
    	
    	// 3000 ms delay (3 seconds)
        Timer delayTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// creates a timer so the Chip can constantly update so it can move
                chipAnimationTimer = new Timer(16, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	// moves chips towards the center
                        int[] newXs = GamePanelRenderer.moveChipsTowardsCenterX(animatedRedChipX, animatedGreenChipX, animatedBlueChipX,getWidth(), chipMoveSpeed);
                        animatedRedChipX = newXs[0];
                        animatedGreenChipX = newXs[1];
                        animatedBlueChipX = newXs[2];

                        int[] newYs = GamePanelRenderer.moveChipsTowardsCenterY(animatedRedChipY, animatedGreenChipY, animatedBlueChipY,getHeight(), chipMoveSpeed);
                        animatedRedChipY = newYs[0];
                        animatedGreenChipY = newYs[1];
                        animatedBlueChipY = newYs[2];

                        repaint(); // updates the chips positions

                        // stop if user chips are at center
                        boolean doneX = (animatedRedChipX == getWidth()/2 && animatedGreenChipX == getWidth()/2 && animatedBlueChipX == getWidth()/2);
                        boolean doneY = (animatedRedChipY == getHeight()/2 - 200 &&  animatedGreenChipY == getHeight()/2 - 200 && animatedBlueChipY == getHeight()/2 - 200);
                        if (doneX && doneY) {
                        	// if they are at the center, stop
                            ((Timer)e.getSource()).stop();
                        }
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

/**
 * starts the ai's chip animation to go to the middle, (I wish I would spawn these from multiple directions :( 
 */
    private void startAIChipAnimation() {
    	// creates a Timer to control the chip animation
        Timer aiChipAnimationTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] newXs = GamePanelRenderer.moveChipsTowardsCenterX(aiRedChipX, aiGreenChipX, aiBlueChipX,getWidth(), chipMoveSpeed);
                aiRedChipX = newXs[0];
                aiGreenChipX = newXs[1];
                aiBlueChipX = newXs[2];

                int[] newYs = GamePanelRenderer.moveChipsTowardsCenterY(aiRedChipY, aiGreenChipY, aiBlueChipY,getHeight(), chipMoveSpeed);
                aiRedChipY = newYs[0];
                aiGreenChipY = newYs[1];
                aiBlueChipY = newYs[2];

                repaint(); // refreshes the UI to show the updated positions

                // stop if AI chips are at center
                boolean doneX = (aiRedChipX == getWidth()/2 && aiGreenChipX == getWidth()/2 && aiBlueChipX == getWidth()/2);
                boolean doneY = (aiRedChipY == getHeight()/2 - 200 && aiGreenChipY == getHeight()/2 - 200 && aiBlueChipY == getHeight()/2 - 200);
                if (doneX && doneY) {
                    ((Timer)e.getSource()).stop(); // stops the timer when all chips reach the center
                }
            }
        });
        aiChipAnimationTimer.start(); // starts the timer to begin animation
    }
    
   
    /**
     * Loads images for the poker table, chips, and background.
     */
    private void imageLoader() {
    	// Matches each file to their image
        File file2 = new File("Images/RedCarpetTexture.PNG");
        
        File file3 = new File("Images/CasinoChipRed.PNG");
        File file4 = new File("Images/CasinoChipGreen.PNG");
        File file5 = new File("Images/CasinoChipBlue.PNG");
        
        File file6 = new File("Images/BackOfCardImage.PNG"); // Back of card image
        
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int)(size.getWidth()*.6);
        screenHeight = (int)(size.getHeight()*.6);

        try {
        	// reads the actual image into the File variable 
            backgroundTexture = ImageIO.read(file2).getScaledInstance(-600, 2700, Image.SCALE_SMOOTH);
            
            redChip = ImageIO.read(file3).getScaledInstance(75,75, Image.SCALE_SMOOTH);
            greenChip = ImageIO.read(file4).getScaledInstance(75,75, Image.SCALE_SMOOTH);
            blueChip = ImageIO.read(file5).getScaledInstance(75,75, Image.SCALE_SMOOTH);
            backOfCard = ImageIO.read(file6).getScaledInstance(75,100, Image.SCALE_SMOOTH);
            
            
            
         // catch an error with the images not being found. 
        } catch(IOException e) {
            System.out.println("redChip, blueChip, greenChip, or RedCarpetTexture not found.");
            backgroundTexture = null;
            redChip = null;
            greenChip = null;
            blueChip = null;
            backOfCard = null;
        }
    }
    
    
    
    
 // method to increase the bet amount, also repaints the amount after it increases
    /**
     * Increases the current bet by a specified amount and repaints the panel to reflect the changes.
     * 
     * @param amount The amount to increase the bet by
     */
    private void increaseBet(int amount) {
    	// gets the human player (assumed to be the first player)
        Player humanPlayer = players.get(0);
        // calculates the new bet amount
        int newBet = currentBet + amount;
             
        try {
            // check if the new bet is negative
            if (newBet < 0) {
                throw new InvalidBetException("Bet cannot be negative.");
            }
            // check if the new bet exceeds the player's current money
            else if (newBet > humanPlayer.getMoney()) {
                throw new InvalidBetException("Bet exceeds available balance.");
            } else {
                currentBet = newBet; // updates the current bet if valid
            }
        } catch (InvalidBetException e) {
            // handles the exception by displaying an error message
            System.out.println("Error: " + e.getMessage());
        }
        
        
        
        repaint();
    }
    
    
    /**
	 * Draws the username, table, chips, and cards, pot, community cards, ai chips, information on the ai, and the player, the user's hand, the ai's cards, and the flop/turn/river cards.
	 * @param g, the Graphics object used for drawing the image.
	 */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GamePanelRenderer.drawTable(g, backgroundTexture);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Egyptienne", Font.BOLD, 40));
        
        // draw username and game details
        g.drawString("Username: " + username, 50, 50);
        Player humanPlayer = players.get(0);
        g.drawString("Current Bet: $" + currentBet, 50, 100);
        g.drawString("Current Money: $" + humanPlayer.getMoney(), 50, 150);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // enable anti-aliasing

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        GamePanelRenderer.renderTable(g2d, panelWidth, panelHeight);

        // display winner and their cards if round is over
        if (winningPlayer != null && winningCards != null) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 30));  
            String winnerText = winningPlayer.getName() + " wins with the best hand!";    
            int textWidth = g.getFontMetrics().stringWidth(winnerText);
            int textX = (getWidth()-textWidth)/2;
            int textY = 250;
            
            g.drawString(winnerText, textX, textY);

            int cardWidth2 = 75; 
            int cardHeight2 = 100; 
            int spacing2 = 20;    
            int startX2 = (getWidth() - (winningCards.size() * (cardWidth2 + spacing2))) / 2;
            int y2 = getHeight() / 2 + 50;
            for (int i = 0; i < winningCards.size(); i++) {
                Card card = winningCards.get(i);
                Image cardImage = Toolkit.getDefaultToolkit().getImage(card.getImagePath());
                g.drawImage(cardImage, startX2 + i * (cardWidth2 + spacing2), y2, cardWidth2, cardHeight2, null);
            }
        }

        // draws the users chips only after the game starts
        if (signalNum > 0) {
            GamePanelRenderer.drawChips(g, currentBet,animatedRedChipX, animatedGreenChipX, animatedBlueChipX,animatedRedChipY, animatedGreenChipY, animatedBlueChipY,redChip, greenChip, blueChip);
        }

        // if the game has not started, skip drawing  
        if (!gameStarted) {
            return;
        }

        // draws other players cards face down
        GamePanelRenderer.drawOtherPlayersCards(g2d, otherPlayersHands, getWidth(), getHeight(), backOfCard, players);

        // draws the list of players in the top right, highlighting current
        GamePanelRenderer.drawPlayers(g, players, currentPlayerIndex, getWidth());

        // draws the community cards in the center
        GamePanelRenderer.drawCommunityCards(g2d, communityCards, getWidth(), getHeight());

        // draws the user hand description at bottom left
        GamePanelRenderer.drawUserHand(g, userHandDescription, getHeight());

        // draws the users actual  cards at bottom left
        GamePanelRenderer.drawPlayerCards(g2d, humanPlayer, getHeight());

        // clears any leftover drawnCards
        GamePanelRenderer.clearInvalidDrawnCards(drawnCards);

        // draws the AI chips in the center representing total bets
        GamePanelRenderer.drawAIChips(g, totalAIBets,aiRedChipX, aiGreenChipX, aiBlueChipX,aiRedChipY, aiGreenChipY, aiBlueChipY,redChip, blueChip, greenChip);

        // shows the total pot text
        GamePanelRenderer.drawPotText(g, pot, getWidth(), getHeight());
    }
    
    

    @Override
	public void keyPressed(KeyEvent e) {
	    if (e.getKeyCode() == KeyEvent.VK_UP) {
	        increaseBet(10); // Increase bet by 10
	    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
	        increaseBet(-10); // Decrease bet by 10
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
 	
 // adds the String names of the images to the arrayList cardImage
	  /**
   * Adds cards to the deck by populating the cardImage list.
   */
    private void addItemsToCards() {
        cardImage.clear(); // Clear any existing card images to avoid duplication
        
        // Iterate through the deck to populate cardImage in the shuffled order
        for (Card card : deck.getCards()) {
            cardImage.add(card.getImagePath());
        }
    }
    
    
    
    // toString 
 	/**
      * 
      * 
      * @return a string including username, 
      *  current bet, drawn cards, red chips, blue chips, green chips, total pot, current money, flop dealt, community cards, number of players, current player, 
      */
    @Override
    public String toString() {
        String currentPlayerName = "None";
        if (players.get(currentPlayerIndex) != null) {
            currentPlayerName = players.get(currentPlayerIndex).getName();
        }
        return "GamePanel [" +
               "Username: " + username + ", " +
               "Current Bet: $" + currentBet + ", " +
               "Current Money: $" + currentMoney + ", " +
               "Total Pot: $" + pot + ", " +
               "Drawn Cards: " + drawnCards.size() + ", " +
               "Red Chips: " + numOfRed + ", " +
               "Green Chips: " + numOfGreen + ", " +
               "Blue Chips: " + numOfBlue + ", " +
               "Flop Dealt: " + flopDealt + ", " +
               "Community Cards: " + communityCards.size() + ", " +
               "Number of Players: " + players.size() + ", " +
               "Current Player: " + currentPlayerName +
               "]";
    }
    
    
    

    @Override
    public String getName() {
        return "GamePanel";
    }
}
