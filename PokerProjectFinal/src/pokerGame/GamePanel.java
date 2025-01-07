package pokerGame;

import javax.swing.JPanel;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import java.awt.BasicStroke;
import java.awt.BorderLayout; 
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.Timer;
import java.awt.Dimension;




import java.awt.event.KeyListener; 
import java.awt.event.KeyEvent; 

/**
 * @author PatrickChristmas
 * @version January 7 2025.
 * The GamePanel class is the panel where the poker game is displayed. It handles the drawing of cards, chips, 
 * updating the user's bet, current money, and the animation of chips and cards. It involves the user's interaction by   
 * pressing arrow keys and clicking a button to draw a card.
 */
public class GamePanel extends JPanel implements KeyListener {
	private ArrayList<String> cardImage;  // names of the card images
	private ArrayList<Drawing> drawnCards; // holds drawn card drawings
	private ArrayList<Card> communityCards; // cards shared on table
		
	private Player winningPlayer; // the player who won
	private ArrayList<Card> winningCards; // winning player cards
		
	private int totalAIBets; // combined bets from ai
		
	private boolean flopDealt = false; // if flop is on table
		
	private String userHandDescription; // user hand info

	private int userRaiseCount; // how many times user raised

	private ArrayList<ArrayList<Card>> otherPlayersHands; // other players' hands

	private Color color; // base panel color
	private static int number; // offset for drawing cards
	private String username; // player name
	private int currentBet; // current bet amount
	private int currentMoney; // money the user has
		
	private int numOfRed; // count of red chips
	private int numOfGreen; // count of green chips
	private int numOfBlue; // count of blue chips

	// images of the 'objects' drawn
	private Image backgroundTexture; // texture for background
	private Image redChip; // red chip image
	private Image greenChip; // green chip image
	private Image blueChip; // blue chip image
	private Image backOfCard; // card back image

	// represent where the chip is drawn or how the chip is spaced
	private final static int RED_CHIP_X = 500; // red chip x pos
	private final static int GREEN_CHIP_X = 400; // green chip x pos
	private final static int BLUE_CHIP_X = 450; // blue chip x pos
	private final static int CHIP_SPACING = 15; // space between chips

	// used for the 'animation' of the chips, just their X value
	private int animatedRedChipX; // red chip x for anim
	private int animatedGreenChipX; // green chip x for anim
	private int animatedBlueChipX; // blue chip x for anim

	// used for the 'animation' of the chips, just their Y value
	private int animatedRedChipY; // red chip y for anim
	private int animatedGreenChipY; // green chip y for anim
	private int animatedBlueChipY; // blue chip y for anim

	// starting Y positions for the chips
	private int INITIAL_RED_CHIP_Y = 100; // red chip start y
	private int INITIAL_GREEN_CHIP_Y = 100; // green chip start y
	private int INITIAL_BLUE_CHIP_Y = 100; // blue chip start y

	private int aiRedChipX, aiGreenChipX, aiBlueChipX; // ai chip x
	private int aiRedChipY, aiGreenChipY, aiBlueChipY; // ai chip y

	private int chipMoveSpeed = 2; // how fast chips move
	private Timer chipAnimationTimer; // chip animation timer


	private int screenHeight; // screen height size
	private int screenWidth; // screen width size 

	private Deck deck; // game deck
	private JButton startGameButton; // button to begin game

	// signal num is used to indicate that the user has drawn a card, and can no longer alter their bets. Used to help draw the Chips.
	private int signalNum; // locks bets after draw

	private boolean gameStarted = false; // if game started
	
	// action buttons
	private JButton foldButton; 
	private JButton callButton; 
	private JButton raiseButton; 

	private ArrayList<Player> players; // list of players
	private int currentPlayerIndex; // whose turn it is
	private int pot; // total pot money
    



	// takes in the username
	/**
     * Constructor for the GamePanel class.
     * Initializes the panel, loads images, and sets up the game.
     * 
     * @param username1, The username of the player
     */
	GamePanel(String username1) {
		username = username1; 
		
		color = new Color(139, 0, 0); // red
						
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
		deck = new Deck();
		deck.shuffle();
		
		addItemsToCards();

		// adds the listener  
		addKeyListener(this);
		setFocusable(true); 
		requestFocusInWindow(); 
		
		// gets the current Money from the UserNamePanel Class 
		UserNamePanel usp = new UserNamePanel();
		currentMoney = usp.getSelectedAmount(); 
		initializePlayers(4, currentMoney); // Example: 4 players with $1000 each
				 		
		// sets default bet to 500
		currentBet = 0; 
		// starts the animations 
		startChipAnimation(); 
        createStartGameButton();
        createPokerActionButtons();

	} 
	/**
	 * array that initializes the players (generates them a name, initial money, and their cards) 
	 * @param numberOfPlayers, number of players in the game (4)
	 * @param initialMoney, starting money for the players (1000 or 2000 or 3000) 
	 */
	
	private void initializePlayers(int numberOfPlayers, int initialMoney) {
	    // Array of possible player names
	    String[] possibleNames = {"Keith", "Tim", "Toby", "Patrick", "Daniel", "Mrs. Kelly", "Mrs. Baniqued", "Ron", "Craig", 
	                              "Jack", "Abraham", "Rosseau", "Jacques", "Maximillian", "Rui", "Bob", "Thomas", "John"};
	    
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
	        boolean isHuman = (i == 0); // first player is human, others are AI
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
	     screenWidth = (int) (size.getWidth() * .6);
	     screenHeight = (int) (size.getHeight() * .6); 
		try {
			// reads the actual image into the File variable 
			backgroundTexture = ImageIO.read(file2).getScaledInstance(-600, 2700, Image.SCALE_SMOOTH);
			
			redChip = ImageIO.read(file3).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			greenChip = ImageIO.read(file4).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			blueChip = ImageIO.read(file5).getScaledInstance(75, 75, Image.SCALE_SMOOTH);
			backOfCard = ImageIO.read(file6).getScaledInstance(75, 100, Image.SCALE_SMOOTH);

	        
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

	    // ensure the new bet is within valid bounds
	    if (newBet >= 0 && newBet <= humanPlayer.getMoney()) {
	        currentBet = newBet; // updates the current bet if valid
	    }

	    repaint(); 
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

	        // deal cards only if the user has no cards
	        if (humanPlayer.getCards().isEmpty()) {
	            drawCard(humanPlayer); // draws a card for the human player
	            drawCard(humanPlayer); // draws a second card for the human player
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
	        }
	    });

	    add(startGameButton); // adds the button to the panel
	}
	   
	/**
	 * creates the poker action buttons (Fold, Call, Raise) and adds them to the action panel
	 */
	private void createPokerActionButtons() {
	    JPanel actionPanel = new JPanel();
	    actionPanel.setBackground(new Color(8, 99, 44)); // matches table background

	    // Fold button
	    foldButton = new JButton("Fold");
	    foldButton.addActionListener(new ActionListener() { 
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            fold(); // calls the fold method
	        }
	    });

	    // Call button
	    callButton = new JButton("Call");
	    callButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            call(); // calls the call method
	        }
	    });

	    // Raise button
	    raiseButton = new JButton("Raise");
	    raiseButton.addActionListener(new ActionListener() { 
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            raise(); // calls the raise method
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
	 * handles the action when a player folds
	 */
	private void fold() {
	    Player currentPlayer = players.get(currentPlayerIndex); // gets the current player
	    currentPlayer.fold(); // marks the current player as folded
	    System.out.println(currentPlayer.getName() + " folded."); // prints fold message
	    nextTurn(); // moves to the next player's turn
	}
	

	/**
	 * handles the action when a player calls the current bet
	 */
	private void call() {
	    Player currentPlayer = players.get(currentPlayerIndex); // gets the current player
	    int highestBet = getCurrentHighestBet(); // gets the current highest bet
	    int amountToCall = highestBet - currentPlayer.getCurrentBet(); // calculates the amount needed to call

	    // checks if the player has enough money to call
	    if (currentPlayer.getMoney() >= amountToCall) {
	        currentPlayer.setMoney(currentPlayer.getMoney() - amountToCall); // subtracts the call amount from the player's money
	        currentPlayer.setCurrentBet(currentPlayer.getCurrentBet() + amountToCall); // adds the call amount to the player's bet
	        pot += amountToCall; // adds the call amount to the pot
	        System.out.println(currentPlayer.getName() + " called."); // prints call message
	    } else {
	        System.out.println(currentPlayer.getName() + " cannot afford to call!"); // prints not enough funds message
	    }
	    nextTurn(); // moves to the next player's turn
	}

	/**
	 * handles the action when a player raises the bet
	 */
	private void raise() {
	    Player currentPlayer = players.get(currentPlayerIndex); // gets the current player
	    int raiseAmount = 100; // example fixed raise amount

	    // checks if the player has enough money to raise
	    if (currentPlayer.getMoney() >= raiseAmount) {
	        currentPlayer.setMoney(currentPlayer.getMoney() - raiseAmount); // subtracts raise amount from player's money
	        currentPlayer.setCurrentBet(currentPlayer.getCurrentBet() + raiseAmount); // adds raise amount to the player's bet
	        pot += raiseAmount; // adds the raise amount to the pot

	        // increments the raise counter if the current player is the user
	        if (currentPlayerIndex == 0) {
	            userRaiseCount++; // tracks the number of raises made by the user
	        }

	        System.out.println(currentPlayer.getName() + " raised by $" + raiseAmount); // prints the raise message
	    } else {
	        System.out.println(currentPlayer.getName() + " cannot afford to raise!"); // prints if the player cannot afford the raise
	    }
	    nextTurn(); // moves to the next player's turn

	    // accounts for the raised bet money
	    int unaccountedForBetMoney = userRaiseCount * 100; // calculates unaccounted bet money based on user's raises
	    currentBet += unaccountedForBetMoney; // adds unaccounted bet money to the current bet
	}
	    
	/**
	 * handles the logic for switching to the next player's turn
	 */
	private void nextTurn() {
	    // loops to find the next player who has not folded
	    do {
	        currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // increments the index and wraps around if necessary
	    } while (players.get(currentPlayerIndex).hasFolded()); // skips folded players

	    Player currentPlayer = players.get(currentPlayerIndex); // gets the current player
	    System.out.println("It's " + currentPlayer.getName() + "'s turn."); // prints the player's name whose turn it is

	    // if it's the user's turn
	    if (currentPlayerIndex == 0) {
	        // deal the flop if it hasn't been dealt yet
	        if (!flopDealt) {
	            dealFlop(); // deals the flop
	            flopDealt = true; // sets flopDealt to true to indicate the flop has been dealt
	        } 
	        // deal the turn if its the right stage and all players have acted
	        else if (communityCards.size() == 3 && allPlayersHaveActed()) {
	            dealTurn(); // deals the turn card
	        }
	        // deal the river if its the right stage and all players have acted
	        else if (communityCards.size() == 4 && allPlayersHaveActed()) {
	            dealRiver(); // deals the river card
	        }
	    } else {
	        aiTakeTurn(currentPlayer); // AI takes its turn if it's not the user's turn
	    }

	    // after the river card, evaluate the winner if all players have acted
	    if (communityCards.size() == 5 && allPlayersHaveActed()) {
	        determineWinner(); // evaluate and determine the winner after the river
	    }

	    repaint(); // refresh the UI to reflect the next player's turn
	}
	    
	    
	 // check if all players have taken their action in the current round
	    private boolean allPlayersHaveActed() {
	        for (Player player : players) {
	            if (!player.hasFolded() && player.getCurrentBet() == 0) {
	                return false; // a player has not acted yet 
	            }
	        }
	        return true;
	    }
	    
	    
	    /**
	     * determines the winner of the round based on the players' hands and community cards
	     */
	    private void determineWinner() {
	        ArrayList<Player> activePlayers = new ArrayList<>(); // stores the active players who haven't folded

	        // loops through the players and adds those who haven't folded to activePlayers
	        for (Player player : players) {
	            if (!player.hasFolded()) {
	                activePlayers.add(player); // adds player to the active list if not folded
	            }
	        }

	        // checks if there are no active players
	        if (activePlayers.isEmpty()) {
	            System.out.println("No active players left."); // prints message if no active players
	            return;
	        }

	        // checks if there's only one active player (the winner by default)
	        if (activePlayers.size() == 1) {
	            Player soleWinner = activePlayers.get(0); // gets the only remaining player
	            System.out.println(soleWinner.getName() + " wins by default (all other players folded)."); // announces the winner
	            soleWinner.setMoney(soleWinner.getMoney() + pot); // adds the pot to the winner's money
	            winningPlayer = soleWinner; // sets the winning player
	            winningCards = new ArrayList<>(soleWinner.getCards()); // stores the winner's cards
	            repaint(); 
	            return;
	        }

	        // Use PokerComparisons to determine the winner if there are multiple active players
	        Player winner = PokerComparisons.evaluateWinner(activePlayers, communityCards);

	        // checks if there is a winner
	        if (winner != null) {
	            System.out.println(winner.getName() + " wins the pot of $" + pot + " with the best hand!"); // announces the winner
	            winner.setMoney(winner.getMoney() + pot); // adds the pot to the winner's money
	            winningPlayer = winner; // sets the winning player

	            // prints the values of the winner's cards
	            System.out.print("Winning cards: ");
	            
	            // prints each card's value
	            for (Card card : winner.getCards()) {
	                System.out.print(card.getValue() + " ");
	            }
	          
	            System.out.println(); // moves to the next line after printing card values

	            // creates the combined list of the winner's hand and community cards for further evaluation
	            ArrayList<Card> combinedCards = new ArrayList<>(winner.getCards());
	            combinedCards.addAll(communityCards);

	            // evaluates the winner's best hand based on the number of combined cards
	            if (combinedCards.size() >= 7) {
	                winningCards = PokerLogic.getBestHand(combinedCards, 7); // 7 card hand evaluation
	            } else if (combinedCards.size() == 6) {
	                winningCards = PokerLogic.getBestHand(combinedCards, 6); // 6 card hand evaluation
	            } else {
	                winningCards = PokerLogic.getBestHand(combinedCards, 5); // 5 card hand evaluation
	            }
	        } else {
	            System.out.println("It's a draw. Pot remains unchanged."); // prints if it's a draw
	            winningPlayer = null; // no winner in case of a draw
	            winningCards = null; // no winning cards in case of a draw
	        }

	        repaint(); // update the UI to reflect the winner and cards
	        resetRound(); // reset the round for the next one
	    }
	    
	       
	    /**
	     * handles the AI player's turn logic, including fold, call, or raise actions
	     */
	    private void aiTakeTurn(Player aiPlayer) {
	        // creates a Timer to simulate a delay for the ai's decision-making process
	        Timer aiTurnTimer = new Timer(1000, new ActionListener() { // 
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // generates a random action for the ai (fold, call, or raise)
	                int action = new Random().nextInt(100);
	                int amountToCall = getCurrentHighestBet() - aiPlayer.getCurrentBet(); // calculates the amount needed to call

	                // Folds is a 5% chance
	                if (action < 5) {
	                    aiPlayer.fold(); // AI folds
	                    System.out.println(aiPlayer.getName() + " folded."); // Print fold message
	                } 
	                // call is a 65% chance
	                else if (action >= 5 && action < 70) {
	                    if (aiPlayer.getMoney() >= amountToCall) { // checks if ai can afford to call
	                        aiPlayer.setMoney(aiPlayer.getMoney() - amountToCall); // subtracts the call amount from ai's money
	                        aiPlayer.setCurrentBet(aiPlayer.getCurrentBet() + amountToCall); // adds the call amount to ai's bet
	                        pot += amountToCall; 
	                        System.out.println(aiPlayer.getName() + " called."); 
	                    } else {
	                        aiPlayer.fold(); // folds if AI cannot afford to call
	                        System.out.println(aiPlayer.getName() + " folded due to insufficient funds."); 
	                    }
	                } 
	                // raise is a 30% chance
	                else {
	                    int raiseAmount = 100; // Fixed raise amount
	                    if (aiPlayer.getMoney() >= amountToCall + raiseAmount) { // checks if ai can afford to raise
	                        aiPlayer.setMoney(aiPlayer.getMoney() - (amountToCall + raiseAmount)); // subtracts the raise amount from ai money
	                        aiPlayer.setCurrentBet(aiPlayer.getCurrentBet() + amountToCall + raiseAmount); // adds raise to ai's bet
	                        pot += amountToCall + raiseAmount; // adds raise amount to the pot
	                        System.out.println(aiPlayer.getName() + " raised by $" + raiseAmount);
	                    } else {
	                        aiPlayer.fold(); // Folds if ai cannot afford to raise
	                        System.out.println(aiPlayer.getName() + " folded due to insufficient funds."); 
	                    }
	                }

	                // updates the total ai bets after the turn
	                totalAIBets = 0; // reset total bets before accumulation
	                for (Player player : players) {
	                    if (!player.isHuman() && !player.hasFolded()) {
	                        totalAIBets += player.getCurrentBet(); // adds up the ai's bets
	                    }
	                }

	                // Start the animation for the ai's chips moving
	                startAIChipAnimation();

	                // Stop the timer after the ai's turn is complete
	                ((Timer) e.getSource()).stop();
	                nextTurn(); // Move to the next turn
	            }
	        });
	        
	        aiTurnTimer.setRepeats(false); // Makes sure that the timer runs only once
	        aiTurnTimer.start(); // Starts the timer to simulate ai's turn
	    }
	    
	    
	    
	    /**
	     * starts the ai's chip animation to go to the middle, (I wish I would spawn these from multiple directions :( 
	     */
	    private void startAIChipAnimation() {
	        // creates a Timer to control the chip animation
	        Timer aiChipAnimationTimer = new Timer(16, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                moveAIChipsTowardsCenter(); // move chips towards the center
	                repaint(); // refreshes the UI to show the updated positions

	                // stops the timer only when all chips reach the center
	                int centerX = getWidth() / 2;
	                int centerY = getHeight() / 2 - 200;
	                if (aiRedChipX >= centerX && aiRedChipY >= centerY &&
	                    aiGreenChipX >= centerX && aiGreenChipY >= centerY &&
	                    aiBlueChipX >= centerX && aiBlueChipY >= centerY) {
	                    ((Timer) e.getSource()).stop(); // stops the timer when all chips reach the center
	                }
	            }
	        });
	        aiChipAnimationTimer.start(); // starts the timer to begin animation
	    }
	    
	    
	    
	    
	    /**
	     * draws the AI player's chips on the screen based on the total bet amount
	     */
	    private void drawAIChips(Graphics g) {
	        int remainingAmount = totalAIBets; // total amount of AI's bets

	        // calculate number of red, blue, and green chips
	        int numRed = remainingAmount / 1000;
	        remainingAmount %= 1000;

	        int numBlue = remainingAmount / 100;
	        remainingAmount %= 100;

	        int numGreen = remainingAmount / 10;

	        // adjust chip numbers based on conversion logic
	        if (numBlue >= 10) {
	            numRed += numBlue / 10;
	            numBlue %= 10;
	        }

	        if (numGreen >= 10) {
	            numBlue += numGreen / 10;
	            numGreen %= 10;
	        }

	        int chipsPerRow = 4; // chips per row

	        // draw red chips
	        if (redChip != null) {
	            for (int i = 0; i < numRed; i++) {
	                int row = i / chipsPerRow;
	                int col = i % chipsPerRow;
	                int x = aiRedChipX + col * (CHIP_SPACING + 50);
	                int y = aiRedChipY + row * (CHIP_SPACING + 15);
	                g.drawImage(redChip, x, y, null);
	            }
	        }

	        // draw blue chips
	        if (blueChip != null) {
	            for (int i = 0; i < numBlue; i++) {
	                int row = i / chipsPerRow;
	                int col = i % chipsPerRow;
	                int x = aiBlueChipX + col * (CHIP_SPACING + 50);
	                int y = aiBlueChipY + row * (CHIP_SPACING + 15);
	                g.drawImage(blueChip, x, y, null);
	            }
	        }

	        // draw green chips
	        if (greenChip != null) {
	            for (int i = 0; i < numGreen; i++) {
	                int row = i / chipsPerRow;
	                int col = i % chipsPerRow;
	                int x = aiGreenChipX + col * (CHIP_SPACING + 50);
	                int y = aiGreenChipY + row * (CHIP_SPACING + 15);
	                g.drawImage(greenChip, x, y, null);
	            }
	        }
	    }
	    
	    /**
	     * The HighestBet will always be the current bet, hopefully if I revisit this project, I will actually make the ai bet dynamic amounts
	     * @return currentBet
	     */
	    private int getCurrentHighestBet() {
	        // for  ease of use and so its not to hard to code, return the current bet as the highest bet
	        return currentBet;
	    } 
	    
	    
	 // deals the turn (fourth community card)
	    private void dealTurn() {
	        if (communityCards.size() == 3) { // checks if the flop has been dealt
	            communityCards.add(deck.dealCard()); // adds the turn card to community cards
	            System.out.println("Turn dealt: " + communityCards.get(3)); // prints the turn card
	            repaint(); 
	        } else {
	            System.out.println("Turn has already been dealt or insufficient cards in the deck."); 
	        }
	    }

	 // deals the river (fifth community card)
	    private void dealRiver() {
	        if (communityCards.size() == 4) { // checks if the turn has been dealt
	            communityCards.add(deck.dealCard()); // adds the river card to community cards
	            System.out.println("River dealt: " + communityCards.get(4)); // prints the river card
	            repaint(); // refreshes the UI to show the new community card
	        } else {
	            System.out.println("River has already been dealt or insufficient cards in the deck."); // prints message if the river cannot be dealt
	        }
	    }
	    // deals the flop (first three community cards)
	    private void dealFlop() {
	        // ensure the deck has enough cards for the flop
	        if (deck.size() < 3) {
	            System.out.println("Not enough cards in the deck for the flop."); // prints error if not enough cards
	            return;
	        }

	        // ensure the flop hasn't already been dealt
	        if (!communityCards.isEmpty()) {
	            System.out.println("The flop has already been dealt."); // prints message if the flop is already dealt
	            return;
	        }

	        // deal three cards for the flop
	        for (int i = 0; i < 3; i++) {
	            communityCards.add(deck.dealCard()); // adds each card to the community cards
	        }

	        // debug display the community cards
	        System.out.println("Flop dealt: " + communityCards);

	        // update and evaluate the user's full hand
	        Player user = players.get(0); // assume the first player is the user
	        if (user == null) {
	            System.out.println("Error: No user player found."); // prints error if user is not found
	            return;
	        }

	        // create the user's full hand by combining their cards with the community cards
	        ArrayList<Card> userHand = new ArrayList<>(user.getCards());
	        userHand.addAll(communityCards);

	        // evaluate the user's hand after the flop
	        userHandDescription = PokerLogic.evaluateHand(user.getCards(), communityCards);

	        // debug: display the user's evaluated hand
	        System.out.println("User's hand after flop: " + userHandDescription);

	        // repaint the panel to show the updated community cards and hand evaluation
	        repaint();
	    }
	    

	 // resets the round, clearing bets and resetting player statuses
	    private void resetRound() {
	        pot = 0; 
	        for (Player player : players) {
	            player.setCurrentBet(0); 
	            player.fold();
	        }
	        currentPlayerIndex = 0; 
	        gameStarted = false; // marks the game as not started
	        repaint(); 
	    }
	    
	

	    // draws the user's hand description on the screen
	    private void drawUserHand(Graphics g) {
	        g.setColor(Color.WHITE); // sets the text color
	        g.setFont(new Font("Arial", Font.BOLD, 20)); // sets the font for drawing text
	        if (userHandDescription != null && !userHandDescription.isEmpty()) {
	            g.drawString("Your Hand: " + userHandDescription, 50, getHeight() - 50); // displays the user's hand
	        } else {
	            g.drawString("Your Hand: Evaluating...", 50, getHeight() - 50); // displays message if hand is still being evaluated
	        }
	    }
	    
	
	/**
     * Draws a random card
     */
	    private void drawCard(Player player) {
	        if (deck.size() > 0) {
	            // draw a card from the deck
	            Card dealtCard = deck.dealCard();

	            // adds the card to the player's  hand
	            player.addCard(dealtCard);

	            // adds the pictures of the card
	            String cardImagePath = dealtCard.getImagePath();
	            Drawing newCardDrawing = new Drawing(cardImagePath, 300 + number, screenHeight - 200);

	            drawnCards.add(newCardDrawing);
	            // increments for spacing 
	            number += 50;
	            repaint();
	        } else {
	            System.out.println("No more cards in the deck.");
	        }
	    }
	
	
	/**
	 * Draws the chips based on the currentBet
	 * @param g, Draws items using the Graphics object 
	 */
	private void drawChips(Graphics g) {
	    // Gets number of red Chips
	    numOfRed = currentBet / 1000;

	    // Placeholder number so it does not change the actual bet
	    int placeHolderNum = currentBet;

	    // Gets number of Blue Chips
	    numOfBlue = placeHolderNum / 100;
	    placeHolderNum %= 100;

	    // Gets number of Green Chips
	    numOfGreen = placeHolderNum / 10;

	    // Debugging output for chip loading
	    if (redChip == null) System.out.println("Red chip image not loaded");
	    if (greenChip == null) System.out.println("Green chip image not loaded");
	    if (blueChip == null) System.out.println("Blue chip image not loaded");

	    // Define maximum chips per row
	    int chipsPerRow = 4;

	    // Draw red chips in rows
	    if (redChip != null) {
	        for (int i = 0; i < numOfRed; i++) {
	            int row = i / chipsPerRow; // Determine the current row
	            int col = i % chipsPerRow; // Determine the column within the row
	            int x = animatedRedChipX + col * (CHIP_SPACING + 50); // Adjust horizontal spacing
	            int y = animatedRedChipY + row * (CHIP_SPACING + 15); // Adjust vertical spacing
	            g.drawImage(redChip, x, y, null);
	        }
	    }

	    // Draw green chips in rows
	    if (greenChip != null) {
	        for (int i = 0; i < numOfGreen; i++) {
	            int row = i / chipsPerRow; // Determine the current row
	            int col = i % chipsPerRow; // Determine the column within the row
	            int x = animatedGreenChipX + col * (CHIP_SPACING + 50); // Adjust horizontal spacing
	            int y = animatedGreenChipY + row * (CHIP_SPACING + 15); // Adjust vertical spacing
	            g.drawImage(greenChip, x, y, null);
	        }
	    }

	    // Draw blue chips in rows
	    if (blueChip != null) {
	        for (int i = 0; i < numOfBlue; i++) {
	            int row = i / chipsPerRow; // Determine the current row
	            int col = i % chipsPerRow; // Determine the column within the row
	            int x = animatedBlueChipX + col * (CHIP_SPACING + 50); // Adjust horizontal spacing
	            int y = animatedBlueChipY + row * (CHIP_SPACING + 15); // Adjust vertical spacing
	            g.drawImage(blueChip, x, y, null);
	        }
	    }
	    


	    
	}
	
	/**
	 * Draws the other players cards (as in the backs of their cards) 
	 * it makes sure that they are drawn AROUND the table to simulate there being actual players there. 
	 * @param g2d
	 */
	private void drawOtherPlayersCards(Graphics2D g2d) {
	    // returns if there are no other players or if their hands are empty
	    if (otherPlayersHands == null || otherPlayersHands.isEmpty()) {
	        return;
	    }

	    // adjusted table size
	    int tableWidth = 1600;
	    int tableHeight = 1000;
	    // back of card dimensions
	    int cardWidth = 75;
	    int cardHeight = 100;
	    // space between cards for each player
	    int spacing = 20;

	    // gets panel dimensions
	    int panelWidth = getWidth();
	    int panelHeight = getHeight();

	    // predefined positions for other players' cards
	    int[][] positions = {
	        {panelWidth / 2 - tableWidth / 3, panelHeight / 8},        // top player
	        {panelWidth / 8, panelHeight / 2 - tableHeight / 8},      // left player
	        {panelWidth - panelWidth / 8 - cardWidth, panelHeight / 2 - tableHeight / 8} // right  player
	    };

	    // loop through other players and draw their cards
	    for (int i = 0; i < otherPlayersHands.size(); i++) {
	        ArrayList<Card> hand = otherPlayersHands.get(i);
	        int[] pos = positions[i];
	        int x = pos[0];
	        int y = pos[1];

	        // draw player's name above their cards
	        Player player = players.get(i + 1); // offset by 1 to skip the user
	        g2d.setColor(Color.YELLOW);
	        g2d.setFont(new Font("Arial", Font.BOLD, 16));
	        g2d.drawString(player.getName(), x + (hand.size() * (cardWidth + spacing)) / 4, y - 20);

	        // draw the cards
	        for (int j = 0; j < hand.size(); j++) {
	            if (backOfCard != null) {
	                g2d.drawImage(backOfCard, x + j * (cardWidth + spacing), y, cardWidth, cardHeight, null);
	            }
	        }
	    }
	}
	
	
	/**
	 * Draws the "players", which is just their information
	 * @param g
	 */
	private void drawPlayers(Graphics g) {
	    // positions players on the screen
	    int x = getWidth() - 550; // move 300 pixels left from the original position
	    int y = 50;
	    // space between player statuses
	    int spacing = 40;
	    g.setFont(new Font("Arial", Font.PLAIN, 20));

	    // loop through players and draw their status
	    for (int i = 0; i < players.size(); i++) {
	        Player player = players.get(i);
	        g.setColor(Color.WHITE); // default color
	        if (i == currentPlayerIndex) {
	            g.setColor(Color.YELLOW); // highlight current player
	        }	
	        String status = "Active"; // default status
	        if (player.hasFolded()) {
	            status = "Folded"; // set status to "folded" if the player has folded
	        }
	        
	        g.drawString(player.getName() + " | Money: $" + player.getMoney() + " | Status: " + status, x, y);
	        y += spacing;
	    }
	}
	
	
	// draws the table and the backgroundTexture (carpet) 
	/**
	 * Draws the table and the background image to a given size. Outdated method name, but it works!
	 * @param g, the Graphics object used for drawing the image.
	 */
	private void drawTable(Graphics g) {
		if (backgroundTexture != null) {
			g.drawImage(backgroundTexture, 0, 0, null);
		}
		 
		
	}
	
	// draws the community cards on the screen
	private void drawCommunityCards(Graphics2D g2d) {
		// check if there are community cards to draw
	    if (communityCards == null || communityCards.isEmpty()) 
	    	return; 

	    int cardWidth = 75; 
	    int cardHeight = 100; 
	    int spacing = 20; 

	    // calculate the starting x position to center the cards horizontally
	    int startX = (getWidth() - (communityCards.size() * cardWidth + (communityCards.size() - 1) * spacing)) / 2;
	    int y = getHeight() / 2 - cardHeight / 2; // vertical center position

	    // draw each community card
	    for (int i = 0; i < communityCards.size(); i++) {
	        Card card = communityCards.get(i);
	        if (card != null) {
	            Image cardImage = Toolkit.getDefaultToolkit().getImage(card.getImagePath()); // load the card image
	            g2d.drawImage(cardImage, startX + i * (cardWidth + spacing), y, cardWidth, cardHeight, null); // draw the card
	        }
	    }
	}

	// draws the player's cards on the screen
	private void drawPlayerCards(Graphics2D g2d, Player player) {
	    if (player == null || player.getCards().isEmpty()) return; // check if the player has cards to draw

	    int cardWidth = 75; 
	    int cardHeight = 100; 
	    int spacing = 20; 

	    int startX = 50; // adjust x position based on layout
	    int y = getHeight() - cardHeight - 150; // position cards up by 150 pixels from the bottom

	    ArrayList<Card> playerCards = player.getCards(); // get the player's cards

	    // draw label "Your Cards" above the user's cards
	    g2d.setColor(Color.YELLOW); 
	    g2d.setFont(new Font("Arial", Font.BOLD, 20)); 
	    g2d.drawString("Your Cards", startX, y - 20); // draw label above the cards

	    // draw each card of the player
	    for (int i = 0; i < playerCards.size(); i++) {
	        Card card = playerCards.get(i);
	        if (card != null) {
	            Image cardImage = Toolkit.getDefaultToolkit().getImage(card.getImagePath()); // load the card image
	            g2d.drawImage(cardImage, startX + i * (cardWidth + spacing), y, cardWidth, cardHeight, null); // draw the card
	        }
	    }
	}
	
	
	/**
	 * Draws the username, table, chips, and cards, pot, community cards, ai chips, information on the ai, and the player, the user's hand, the ai's cards, and the flop/turn/river cards.
	 * @param g, the Graphics object used for drawing the image.
	 */
	@Override 
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    drawTable(g);

	    g.setColor(Color.WHITE);
	    g.setFont(new Font("Egyptienne", Font.BOLD, 40));

	    // draw username and game details
	    g.drawString("Username: " + username, 50, 50); 
	    Player humanPlayer = players.get(0); 
	    g.drawString("Current Bet: $" + currentBet, 50, 100);
	    g.drawString("Current Money: $" + (humanPlayer.getMoney()), 50, 150);
	    
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // enable anti-aliasing

	    int panelWidth = getWidth();
	    int panelHeight = getHeight();
	    int tableWidth = 1600;
	    int tableHeight = 1000;

	    int x = (panelWidth - tableWidth) / 2;
	    int y = (panelHeight - tableHeight) / 2;

	    // draw gradient table
	    GradientPaint tableGradient = new GradientPaint(x + 50, y + 50, new Color(30, 120, 30),
	                                                    x + 50, y + tableHeight - 100, new Color(20, 90, 20));
	    g2d.setPaint(tableGradient);
	    g2d.fillOval(x, y, tableWidth, tableHeight);

	    // draw table border and inner circle
	    g2d.setColor(new Color(139, 69, 19));
	    g2d.setStroke(new BasicStroke(15));
	    g2d.drawOval(x, y, tableWidth, tableHeight);

	    g2d.setColor(Color.YELLOW);
	    g2d.setStroke(new BasicStroke(5));
	    g2d.drawOval(x + 30, y + 30, tableWidth - 60, tableHeight - 60);

	    // draw card placeholders
	    int cardWidth = 120;
	    int cardHeight = 160;
	    int spacing = 40;
	    int startX = panelWidth / 2 - 5 * (cardWidth + spacing) / 2;
	    int centerY = panelHeight / 2 - cardHeight / 2;

	    for (int i = 0; i < 5; i++) {
	        int xCard = startX + i * (cardWidth + spacing);

	        g2d.setColor(new Color(0, 0, 0, 50)); // shadow effect
	        g2d.fillRoundRect(xCard + 5, centerY + 5, cardWidth, cardHeight, 10, 10);

	        g2d.setColor(new Color(34, 139, 34)); // table color
	        g2d.fillRoundRect(xCard, centerY, cardWidth, cardHeight, 10, 10);

	        g2d.setColor(Color.WHITE); // thin white outline
	        g2d.setStroke(new BasicStroke(2));
	        g2d.drawRoundRect(xCard, centerY, cardWidth, cardHeight, 10, 10);
	    }

	    // display winner and their cards if round is over
	    if (winningPlayer != null && winningCards != null) {
	        g.setColor(Color.YELLOW);
	        g.setFont(new Font("Arial", Font.BOLD, 30));
	        String winnerText = winningPlayer.getName() + " wins with the best hand!";
	        int textWidth = g.getFontMetrics().stringWidth(winnerText);
	        int textX = (getWidth() - textWidth) / 2;
	        int textY = 250;

	        g.drawString(winnerText, textX, textY);

	        // draw the winning player's cards
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

	    // draw chips only when cards are drawn
	    if (signalNum > 0) {
	        drawChips(g);
	    }

	    if (!gameStarted) {
	        return; // Exit if the game hasn't started
	    }

	    drawOtherPlayersCards(g2d);
	    drawPlayers(g);
	    drawCommunityCards(g2d);
	    drawUserHand(g);

	    // draw the player's cards
	    Player user = players.get(0);
	    drawPlayerCards(g2d, user);

	    clearInvalidDrawnCards();
	    drawAIChips(g);
	    drawPotText(g);
	}
	
	/**
	 *  draws the total pot text on the screen
	 * @param g
	 */
	private void drawPotText(Graphics g) {
	    g.setColor(Color.YELLOW); // sets the text color to yellow
	    g.setFont(new Font("Arial", Font.BOLD, 30)); // sets the font

	    String potText = "Total Pot: $" + pot; // prepares the pot text
	    int x1 = (getWidth() - 100) / 2; // assumes fixed width of 100 pixels for the text
	    int x = (getWidth() - x1) /2 ; 
	    int y = getHeight() / 2 - 200;        // positions the text above the center

	    g.drawString(potText, x, y); 
	}
	
	
	// moves the ai chips towards the center of the screen
	private void moveAIChipsTowardsCenter() {
	    int centerX = getWidth() / 2; // calculate the center X position
	    int centerY = getHeight() / 2 - 200; // calculate the center Y position 

	    // moves red chip towards the center horizontally
	    if (aiRedChipX < centerX) 
	    	aiRedChipX += chipMoveSpeed;
	    
	  
	    if (aiGreenChipX < centerX) 
	    	aiGreenChipX += chipMoveSpeed;
	    
	    if (aiBlueChipX < centerX) 
	    	aiBlueChipX += chipMoveSpeed;

	    // move red chip towards the center vertically
	    if (aiRedChipY < centerY) 
	    	aiRedChipY += chipMoveSpeed;
	    
	    if (aiGreenChipY < centerY) 
	    	aiGreenChipY += chipMoveSpeed;
	    
	    if (aiBlueChipY < centerY) 
	    	aiBlueChipY += chipMoveSpeed;

	    repaint(); 
	}
	
	
	/**
	 * clears the invalid drawn cards, removes two extra cards from being drawn on the bottom left of the frame
	 */
	private void clearInvalidDrawnCards() {
	    if (drawnCards != null) {
	        drawnCards.clear(); // Clear any invalid drawn cards
	    }
	}
	
	
	
	/**
	 * Moves the chips towards the center of the screen's X.
	 */
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
	            animatedGreenChipX = centerX + 400; 
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
	

	
	
	// moves chips towards the center y 
	/**
	 * Moves the chips towards the center of the screen's Y
	 */
	private void moveChipsTowardsCenterY() {
	    int centerY = getHeight() / 2 - 200; // center y coordinate

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
	
	
	
	
	
	
	
	/**
	 * If the Up arrow or the Down arrow are pressed, it increases or decreases the bet amount. 
	 */
 
	// adjusts the bet amount according to how much you press the up or down arrows. 
	
	
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
}