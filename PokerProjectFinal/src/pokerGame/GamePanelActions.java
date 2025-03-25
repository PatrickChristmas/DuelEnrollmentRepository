package pokerGame;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * contains all game logic methods (fold, call, raise, turn management, dealing, winner determination etc)
 */
public class GamePanelActions {

    /** 
     * the list of players in the game 
     */
    private ArrayList<Player> players;
    
    /** 
     * the index of the current player
     */
    private int currentPlayerIndex;
    
    /** 
     * the current pot amount 
     */
    private int pot;
    
    /** 
     * boolean indicating if the game has started
     */
    private boolean gameStarted;
    
    /** 
     * the current bet amount 
     */
    private int currentBet;
    
    /** 
     * the number of times the user has raised 
     */
    private int userRaiseCount;
    
    /** 
     * the community cards on the table
     */
    private ArrayList<Card> communityCards;
    
    /** 
     * the winning player of the round 
     */
    private Player winningPlayer;
    
    /** 
     * the winning cards of the round 
     */
    private ArrayList<Card> winningCards;
    
    /** 
     * the total bets made by the ai players
     */
    private int totalAIBets;
    
    /**
     *  flag indicating if the flop has been dealt 
     */
    private boolean flopDealt;
    
    /** 
     * the deck used in the game 
     */
    private Deck deck;

    // callbacks

    /*
     * the callback to notify state changes 
     */
    private Runnable onStateChange = new Runnable() {
        @Override
        public void run() {
            // default
        }
    };
    
    /*
     * the callback to animate chips when the pot changes 
     */
    private Runnable onAnimateChips = new Runnable() {
        @Override
        public void run() {
            // default
        }
    };

    /**
     * sets the callback for state change notis
     * @param r the runnable callback
     */
    public void setOnStateChange(Runnable r) {
        this.onStateChange = r;
    }

    /**
     * sets the callback for animating chips
     * @param r the runnable callback
     */
    public void setOnAnimateChips(Runnable r) {
        this.onAnimateChips = r;
    }

    // setters

    /**
     * sets the players for the game
     * @param players the list of players
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    /**
     * sets the index of the current player
     * @param index the current player index
     */
    public void setCurrentPlayerIndex(int index) {
        this.currentPlayerIndex = index;
    }
    /**
     * sets the pot amount
     * @param pot the pot amount
     */
    public void setPot(int pot) {
        this.pot = pot;
    }
    /**
     * sets the game started boolean
     * @param started true if game has started false otherwise
     */
    public void setGameStarted(boolean started) {
        this.gameStarted = started;
    }
    /**
     * sets the current bet
     * @param bet the bet amount
     */
    public void setCurrentBet(int bet) {
        this.currentBet = bet;
    }
    /**
     * sets the number of times the user has raised
     * @param count the raise count
     */
    public void setUserRaiseCount(int count) {
        this.userRaiseCount = count;
    }
    /**
     * sets the community cards on the table
     * @param communityCards the list of community cards
     */
    public void setCommunityCards(ArrayList<Card> communityCards) {
        this.communityCards = communityCards;
    }
    /**
     * sets the winning player of the round
     * @param p the winning player
     */
    public void setWinningPlayer(Player p) {
        this.winningPlayer = p;
    }
    /**
     * sets the winning cards of the round
     * @param cards the list of winning cards
     */
    public void setWinningCards(ArrayList<Card> cards) {
        this.winningCards = cards;
    }
    /**
     * sets the total bets made by ai players
     * @param total the total ai bets
     */
    public void setTotalAIBets(int total) {
        this.totalAIBets = total;
    }
    /**
     * sets the flop dealt boolean
     * @param b true if flop has been dealt false otherwise
     */
    public void setFlopDealt(boolean b) {
        this.flopDealt = b;
    }
    /**
     * sets the deck for the game
     * @param deck the game deck
     */
    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    // getters

    /**
     * gets the current bet amount
     * @return the current bet
     */
    public int getCurrentBet() {
        return currentBet;
    }
    /**
     * gets the current pot amount
     * @return the pot amount
     */
    public int getPot() {
        return pot;
    }
    /**
     * gets the index of the current player
     * @return the current player index
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
    /**
     * gets the number of times the user has raised
     * @return the user raise count
     */
    public int getUserRaiseCount() {
        return userRaiseCount;
    }
    /**
     * gets the community cards
     * @return the list of community cards
     */
    public ArrayList<Card> getCommunityCards() {
        return communityCards;
    }
    /**
     * gets the winning player
     * @return the winning player
     */
    public Player getWinningPlayer() {
        return winningPlayer;
    }
    /**
     * gets the winning cards
     * @return the list of winning cards
     */
    public ArrayList<Card> getWinningCards() {
        return winningCards;
    }
    /**
     * gets the total bets made by ai players
     * @return the total ai bets
     */
    public int getTotalAIBets() {
        return totalAIBets;
    }
    /**
     * checks if the flop has been dealt
     * @return true if flop is dealt false otherwise
     */
    public boolean isFlopDealt() {
        return flopDealt;
    }

    /**
     * handles the action when a player folds
     */
    public void fold() {
        Player currentPlayer = players.get(currentPlayerIndex); // gets the current player
        currentPlayer.fold(); // marks the current player as folded
        System.out.println(currentPlayer.getName() + " folded."); // prints fold message
        nextTurn(); // moves to the next players turn
    }

    /**
     * handles the action when a player calls the current bet
     */
    public void call() {
        Player currentPlayer = players.get(currentPlayerIndex); // gets the current player
        int highestBet = getCurrentHighestBet(); // gets the current highest bet
        int amountToCall = highestBet - currentPlayer.getCurrentBet(); // calculates the call amount

        if (currentPlayer.getMoney() >= amountToCall) {
            currentPlayer.setMoney(currentPlayer.getMoney() - amountToCall); // subtracts call amount from player's money
            currentPlayer.setCurrentBet(currentPlayer.getCurrentBet() + amountToCall); // adds call amount to player's bet
            pot += amountToCall; // adds call amount to pot
            System.out.println(currentPlayer.getName() + " called."); // prints call message

            // animate chips if callback is set
            if (onAnimateChips != null) {
                onAnimateChips.run();
            }
        } else {
            System.out.println(currentPlayer.getName() + " cannot afford to call!"); // prints not enough funds message
        }
        nextTurn(); // moves to the next player's turn
    }

    /**
     * handles the action when a player raises the bet
     */
    public void raise() {
        Player currentPlayer = players.get(currentPlayerIndex); // gets the current player
        int raiseAmount = 100; // fixed raise amount
        
        // checks if the player has enough money to raise
        if (currentPlayer.getMoney() >= raiseAmount) {
            currentPlayer.setMoney(currentPlayer.getMoney() - raiseAmount); // subtracts raise amount from player's money
            currentPlayer.setCurrentBet(currentPlayer.getCurrentBet() + raiseAmount); // adds raise to player's bet
            pot += raiseAmount; // adds raise amount to pot
            
            // increments the raise counter if the current player is the user
            if (currentPlayerIndex == 0) {
                userRaiseCount++; // tracks the number of raises made by the user
            }
            System.out.println(currentPlayer.getName() + " raised by $" + raiseAmount); // prints raise message

            // animate chips if callback is set
            if (onAnimateChips != null) {
                onAnimateChips.run();
            }
        } else {
            System.out.println(currentPlayer.getName() + " cannot afford to raise!"); // prints if the player cannot afford the raise
        }

        currentBet = getCurrentHighestBet(); // updates current bet to highest bet
        nextTurn(); // moves to the next player's turn
    }

    /**
     * deals the flop (first three community cards)
     */
    public void dealFlop() {
        if (deck.size() < 3) {
            System.out.println("Not enough cards in the deck for the flop."); // prints error if deck too small
            return;
        }
        if (!communityCards.isEmpty()) {
            System.out.println("The flop has already been dealt."); // prints message if flop already dealt
            return;
        }
        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.dealCard()); // adds a card to the community cards
        }
        System.out.println("Flop dealt: " + communityCards); // prints the dealt flop

        Player user = players.get(0); // assume first player is user
        if (user == null) {
            System.out.println("Error: No user player found."); // prints error if user not found
            return;
        }
        ArrayList<Card> userHand = new ArrayList<>(user.getCards()); // copies user's cards
        userHand.addAll(communityCards); // adds community cards to users hand
        String evaluation = PokerLogic.evaluateHand(user.getCards(), communityCards); // evaluates the hand
        System.out.println("User's hand after flop: " + evaluation); // prints the evaluation 
    }

    /**
     * deals the turn (fourth community card)
     */
    public void dealTurn() {
        if (communityCards.size() == 3) {
            communityCards.add(deck.dealCard()); // adds the turn card to community cards
            System.out.println("Turn dealt: " + communityCards.get(3)); // prints the turn card
        } else {
            System.out.println("Turn has already been dealt or insufficient cards in the deck."); // prints message if turn already dealt
        }
    }

    /**
     * deals the river (fifth community card)
     */
    public void dealRiver() {
        if (communityCards.size() == 4) {
            communityCards.add(deck.dealCard()); // adds the river card to community cards
            System.out.println("River dealt: " + communityCards.get(4)); // prints the river card
        } else {
            System.out.println("River has already been dealt or insufficient cards in the deck."); // prints message if river already dealt
        }
    }

    /**
     * handles the logic for switching to the next player's turn
     */
    public void nextTurn() {
        do {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // increments index and wraps around
        } while (players.get(currentPlayerIndex).hasFolded()); // skips folded players

        Player currentPlayer = players.get(currentPlayerIndex); // gets the current player
        System.out.println("It's " + currentPlayer.getName() + "'s turn."); // prints whose turn it is

        if (currentPlayerIndex == 0) {
            if (!flopDealt) {
                dealFlop(); // deals the flop if not yet dealt
                flopDealt = true; 
            } else if (communityCards.size() == 3 && allPlayersHaveActed()) {
                dealTurn(); // deals the turn card
            } else if (communityCards.size() == 4 && allPlayersHaveActed()) {
                dealRiver(); // deals the river card
            }
        } else {
            aiTakeTurn(currentPlayer); // ai takes turn if not users turn
            return; 
        }

        if (communityCards.size() == 5 && allPlayersHaveActed()) {
            determineWinner(); // determines winner if all community cards are out and all have acted
        }

        if (onStateChange != null) {
            onStateChange.run(); // notifies  change
        }
    }

    // check if all players have taken their action in the current round
    /**
     * checks if all players have acted in the current round
     * @return true if all have acted false otherwise
     */
    private boolean allPlayersHaveActed() {
        for (Player player : players) {
            if (!player.hasFolded() && player.getCurrentBet() == 0) {
                return false; // a player has not acted yet
            }
        }
        return true;
    }

    /**
     * determines the winner of the round based on the players hands and community cards
     */
    public void determineWinner() {
        ArrayList<Player> activePlayers = new ArrayList<>(); // stores active players

        for (Player player : players) {
            if (!player.hasFolded()) {
                activePlayers.add(player); // adds player if not folded
            }
        }
        if (activePlayers.isEmpty()) {
            System.out.println("No active players left."); // prints message if no active players
            return;
        }
        if (activePlayers.size() == 1) {
            Player soleWinner = activePlayers.get(0); // gets the only active player
            System.out.println(soleWinner.getName() + " wins by default (all other players folded)."); // announces default win
            soleWinner.setMoney(soleWinner.getMoney() + pot); // awards pot to winner
            winningPlayer = soleWinner; // sets winning player
            winningCards = new ArrayList<>(soleWinner.getCards()); // stores winning cards
            return;
        }

        Player winner = PokerComparisons.evaluateWinner(activePlayers, communityCards); // evaluates winner using comparisons
        if (winner != null) {
            System.out.println(winner.getName() + " wins the pot of $" + pot + " with the best hand!"); // announces winner
            winner.setMoney(winner.getMoney() + pot); // awards pot to winner
            winningPlayer = winner; // sets winning player
            System.out.print("Winning cards: "); // prints winning cards
            for (Card card : winner.getCards()) {
                System.out.print(card.getValue() + " "); // prints each card value
            }
            
            System.out.println(); // new line after cards
            ArrayList<Card> combinedCards = new ArrayList<>(winner.getCards());
            combinedCards.addAll(communityCards);
            if (combinedCards.size() >= 7) {
                winningCards = PokerLogic.getBestHand(combinedCards, 7); // evaluates best hand with 7 cards
            } else if (combinedCards.size() == 6) {
                winningCards = PokerLogic.getBestHand(combinedCards, 6); // evaluates best hand with 6 cards
            } else {
                winningCards = PokerLogic.getBestHand(combinedCards, 5); // evaluates best hand with 5 cards
            }
        } else {
            System.out.println("It's a draw. Pot remains unchanged."); // announces a draw
            winningPlayer = null; // no winner
            winningCards = null; // no winning cards
        }
    }

    /**
     * handles the ai players turn logic, including fold, call, or raise actions
     */
    public void aiTakeTurn(Player aiPlayer) {
        Timer aiTurnTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = new Random().nextInt(100); // random action for ai
                int amountToCall = getCurrentHighestBet() - aiPlayer.getCurrentBet(); // calculates call amount
                if (action < 5) { // the one in twenty chance it folds
                    aiPlayer.fold(); // ai folds
                    System.out.println(aiPlayer.getName() + " folded."); // prints fold message
                } else if (action >= 5 && action < 70) { // most of the time it will call
                    if (aiPlayer.getMoney() >= amountToCall) {
                        aiPlayer.setMoney(aiPlayer.getMoney() - amountToCall);
                        aiPlayer.setCurrentBet(aiPlayer.getCurrentBet() + amountToCall);
                        pot += amountToCall;
                        System.out.println(aiPlayer.getName() + " called.");
                        if (onAnimateChips != null) {
                            onAnimateChips.run();
                        }
                    } else {
                        aiPlayer.fold();
                        System.out.println(aiPlayer.getName() + " folded due to insufficient funds.");
                    }
                } else { // raises
                    int raiseAmount = 100;
                    if (aiPlayer.getMoney() >= amountToCall + raiseAmount) {
                        aiPlayer.setMoney(aiPlayer.getMoney() - (amountToCall + raiseAmount));
                        aiPlayer.setCurrentBet(aiPlayer.getCurrentBet() + amountToCall + raiseAmount);
                        pot += amountToCall + raiseAmount;
                        System.out.println(aiPlayer.getName() + " raised by $" + raiseAmount);
                        if (onAnimateChips != null) {
                            onAnimateChips.run();
                        }
                    } else {
                        aiPlayer.fold();
                        System.out.println(aiPlayer.getName() + " folded due to insufficient funds.");
                    }
                }
                totalAIBets = 0;
                for (Player player : players) {
                    if (!player.isHuman() && !player.hasFolded()) {
                        totalAIBets += player.getCurrentBet();
                    }
                }
                ((Timer) e.getSource()).stop();
                nextTurn();
                if (onStateChange != null) {
                    onStateChange.run();
                }
            }
        });
        aiTurnTimer.setRepeats(false);
        aiTurnTimer.start();
    }

    /**
     * resets the round, clearing bets and resetting player statuses
     */
    public void resetRound() {
        pot = 0;
        for (Player player : players) {
            player.setCurrentBet(0);
            player.fold();
        }
        currentPlayerIndex = 0;
        gameStarted = false;
    }

    /**
     * calculates the highest bet among players
     * @return the highest current bet
     */
    private int getCurrentHighestBet() {
        int highest = 0;
        for (Player p : players) {
            if (!p.hasFolded() && p.getCurrentBet() > highest) {
                highest = p.getCurrentBet();
            }
        }
        return highest;
    }

    /**
     * draws a card from the deck and adds it to the player's hand
     * @param player the player to receive the card
     * @return the drawn card or null if the deck is empty
     */
    public Card drawCard(Player player) {
        if (deck.size() > 0) {
            Card dealtCard = deck.dealCard();
            player.addCard(dealtCard);
            return dealtCard;
        } else {
            System.out.println("No more cards in the deck.");
            return null;
        }
    }
}
