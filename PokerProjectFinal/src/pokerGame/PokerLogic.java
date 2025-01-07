package pokerGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Patrick Christmas
 * @version January 6 2024 
 * Class simulate's the ENTIRE game's poker logic. It has auxiliary methods that evaluates 5,6, and 7 card hands.
 * in addition to that it has methods that ACTUALLY evaluate those hands, and identify what kind of hands they are.
 * Lastly, it has ALOT of helper methods that help getting information on the deck/cards 
 */
public class PokerLogic {
	
	/**
	 * evaluates a five-card poker hand and determines its rank
	 * @param hand, the list of cards representing the poker hand
	 * @return a string representing the rank of the poker hand
	 */
	private static String evaluateFiveCardHand(ArrayList<Card> hand) {

	    // gets the values and suits of cards in the hand
	    String[] values = getCardValues(hand);
	    String[] suits = getCardTypes(hand);

	    // debugs extracted values and suits for verification
	    System.out.println("Card Values: " + Arrays.toString(values));
	    System.out.println("Card Suits: " + Arrays.toString(suits));

	    if (isRoyalFlush(values, suits)) {
	        System.out.println("Hand is a Royal Flush");
	        return "Royal Flush";
	    } else if (isStraightFlush(values, suits)) {
	        System.out.println("Hand is a Straight Flush");
	        return "Straight Flush";
	    } else if (isFourOfAKind(values) != null) {
	        System.out.println("Hand is Four of a Kind");
	        return "Four of a Kind: " + isFourOfAKind(values);
	    } else if (isFullHouse(values) != null) {
	        System.out.println("Hand is Full House");
	        return "Full House: " + isFullHouse(values);
	    } else if (isFlush(suits)) {
	        System.out.println("Hand is a Flush");
	        return "Flush";
	    } else if (isStraight(values)) {
	        System.out.println("Hand is a Straight");
	        return "Straight";
	    } else if (isThreeOfAKind(values) != null) {
	        System.out.println("Hand is Three of a Kind");
	        return "Three of a Kind: " + isThreeOfAKind(values);
	    } else if (isTwoPair(values) != null) {
	        System.out.println("Hand is a Two Pair");
	        return "Two Pair: " + isTwoPair(values);
	    } else if (isOnePair(values) != null) {
	        System.out.println("Hand is a One Pair");
	        return "One Pair: " + isOnePair(values);
	    } else {
	        System.out.println("Hand is a High Card");
	        return "High Card: " + highCard(values);
	    }
	}
	
	
	/**
	 * determines the best hand from a set of cards and a specified hand size
	 * @param cards, the list of all available cards
	 * @param handSize, the size of the hand to evaluate
	 * @return the best hand as an ArrayList of cards
	 */
	public static ArrayList<Card> getBestHand(ArrayList<Card> cards, int handSize) {
	    
	    // generates all possible combinations of the given hand size
	    ArrayList<ArrayList<Card>> combinations = generateCardCombinations(cards, handSize);
	    ArrayList<Card> bestHand = null;
	    int bestHandValue = 0;

	    for (ArrayList<Card> combination : combinations) {
	        // evaluates the current combination and assigns a value to it
	        String handRank = evaluateFiveCardHand(combination);
	        int handValue = getHandValue(handRank);

	        // updates the best hand if the current one is better
	        if (handValue > bestHandValue) {
	            bestHandValue = handValue;
	            bestHand = new ArrayList<>(combination);
	        }
	    }

	    // returns the best hand found
	    return bestHand;
	}

	/**
	 * generates all possible combinations of a specified hand size from a list of cards
	 * @param cards, the list of cards to generate combinations from
	 * @param handSize, the size of each combination
	 * @return a list of all possible combinations as lists of cards
	 */
	private static ArrayList<ArrayList<Card>> generateCardCombinations(ArrayList<Card> cards, int handSize) {
	    
	    // stores all generated combinations
	    ArrayList<ArrayList<Card>> combinations = new ArrayList<>();
	    
	    // recursively builds combinations
	    combine(cards, 0, new ArrayList<>(), combinations, handSize);
	    
	    // returns the list of combinations
	    return combinations;
	}

	/**
	 * recursively generates combinations of cards
	 * @param cards, the list of available cards
	 * @param start, the starting index for combination generation
	 * @param current, the current combination being constructed
	 * @param combinations, the list to store all generated combinations
	 * @param handSize, the target size of each combination
	 */
	private static void combine(ArrayList<Card> cards, int start, ArrayList<Card> current, ArrayList<ArrayList<Card>> combinations, int handSize) {
	    
	    // adds the current combination to the list if it matches the desired hand size
	    if (current.size() == handSize) {
	        combinations.add(new ArrayList<>(current));
	        return;
	    }

	    // iterates through cards to build combinations recursively
	    for (int i = start; i < cards.size(); i++) {
	        current.add(cards.get(i)); // adds the current card to the combination
	        combine(cards, i + 1, current, combinations, handSize); // recursively builds the next level
	        current.remove(current.size() - 1); // backtracks by removing the last card
	    }
	}
		
	/**
	 * evaluates the best hand from a 6-card hand by considering all possible 5-card combinations
	 * @param cards the list of 6 cards to evaluate
	 * @return the rank of the best 5-card hand from the 6 cards
	 * @throws IllegalArgumentException if the input does not contain exactly 6 cards
	 */
	public static String evaluateSixCardHand(ArrayList<Card> cards) {
	    
	    // ensures the hand contains exactly 6 cards
	    if (cards.size() != 6) {
	        throw new IllegalArgumentException("A 6 card hand must contain exactly 6 cards.");
	    }

	    // generates all possible 5-card combinations from the 6 cards
	    ArrayList<ArrayList<Card>> fiveCardCombinations = generateFiveCardCombinations(cards);
	    String bestHand = "";
	    int bestHandValue = 0;

	    for (ArrayList<Card> hand : fiveCardCombinations) {
	        // evaluates each 5-card combination and determines its value
	        String handRank = evaluateFiveCardHand(hand);
	        int handValue = getHandValue(handRank);

	        // updates the best hand if the current one is better
	        if (handValue > bestHandValue) {
	            bestHandValue = handValue;
	            bestHand = handRank;
	        }
	    }

	    // returns the rank of the best hand
	    return bestHand;
	}
	

	/**
	 * evaluates the best hand from a 7-card hand by considering all possible 5-card combinations
	 * @param cards the list of 7 cards to evaluate
	 * @return the rank of the best 5-card hand from the 7 cards
	 * @throws IllegalArgumentException if the input does not contain exactly 7 cards
	 */
	public static String evaluateSevenCardHand(ArrayList<Card> cards) {

	    // ensures the hand contains exactly 7 cards
	    if (cards.size() != 7) {
	        throw new IllegalArgumentException("A 7 card hand must contain exactly 7 cards.");
	    }

	    // generates all possible 5-card combinations from the 7 cards
	    ArrayList<ArrayList<Card>> fiveCardCombinations = generateFiveCardCombinations(cards);
	    String bestHand = "";
	    int bestHandValue = 0;

	    for (ArrayList<Card> hand : fiveCardCombinations) {
	        // evaluates each 5-card combination and determines its value
	        String handRank = evaluateFiveCardHand(hand);
	        int handValue = getHandValue(handRank);

	        // updates the best hand if the current one is better
	        if (handValue > bestHandValue) {
	            bestHandValue = handValue;
	            bestHand = handRank;
	        }
	    }

	    // returns the rank of the best hand
	    return bestHand;
	}
	
	
	/**
	 * evaluates the best possible hand for a player given their  cards and community cards
	 * @param holeCards the player's  cards
	 * @param communityCards the shared community cards
	 * @return the rank of the best 5-card hand as a string
	 * @throws IllegalArgumentException if the combined cards are fewer than 5
	 */
	public static String evaluateHand(ArrayList<Card> holeCards, ArrayList<Card> communityCards) {
	    ArrayList<Card> combinedHand = new ArrayList<>(holeCards);
	    combinedHand.addAll(communityCards);

	    // debugging Log hand sizes
	    System.out.println("Total cards to evaluate: " + combinedHand.size());
	    if (combinedHand.size() > 7) {
	        System.out.println("Warning: Hand size exceeds 7, truncating to 7 cards.");
	        combinedHand = new ArrayList<>(combinedHand.subList(0, 7));
	    }

	    if (combinedHand.size() < 5) {
	        throw new IllegalArgumentException("At least 5 cards are required to evaluate a hand.");
	    }

	    ArrayList<ArrayList<Card>> fiveCardCombinations = generateFiveCardCombinations(combinedHand);

	    String bestHand = "";
	    int bestHandValue = 0;

	    for (ArrayList<Card> hand : fiveCardCombinations) {
	        String handRank = evaluateFiveCardHand(hand);
	        int handValue = getHandValue(handRank);

	        if (handValue > bestHandValue) {
	            bestHandValue = handValue;
	            bestHand = handRank;
	        }
	    }

	    return bestHand;
	}
	
    
	/**
	 * extracts the values of cards from a list of cards
	 * @param cards the list of cards to extract values from
	 * @return an array of card values as strings
	 */
	public static String[] getCardValues(ArrayList<Card> cards) {

	    // initializes an array to store card values
	    String[] values = new String[cards.size()];

	    // iterates through the cards to extract their values
	    for (int i = 0; i < cards.size(); i++) {
	        values[i] = cards.get(i).getValue(); // gets the value of the current card
	    }

	    // returns the array of card values
	    return values;
	}
    
	/**
	 * generates all possible 5-card combinations from a list of cards without using recursion
	 * @param cards the list of cards to generate combinations from
	 * @return a list of all possible 5-card combinations as lists of cards
	 */
	private static ArrayList<ArrayList<Card>> generateFiveCardCombinations(ArrayList<Card> cards) {
	    ArrayList<ArrayList<Card>> combinations = new ArrayList<>();
	    int n = cards.size();

	    // Ensure there are at least 5 cards
	    if (n < 5) {
	        return combinations;
	    }

	    // Generate combinations using a helper method
	    generateCombinationsHelper(cards, combinations, new ArrayList<>(), 0, 5);
	    
	    return combinations;
	}
	/**
	 *  generates all possible combinations of a specified size from a list of cards.
	 * 
	 * 
	 * @param cards The list of available cards to generate combinations from.
	 * @param combinationsThe list to store all generated combinations.
	 * @param currentCombination The current combination of cards being built.
	 * @param start the starting index for the current recursive call.
	 * @param remaining the number of remaining cards to select for the combination.
	 */
	private static void generateCombinationsHelper(ArrayList<Card> cards, ArrayList<ArrayList<Card>> combinations, ArrayList<Card> currentCombination, int start, int remaining) {
			// adds the current combination to the result if it has 5 cards
			if (remaining == 0) {
				combinations.add(new ArrayList<>(currentCombination)); // adds a copy of currentCombination
				return;
			}

			// loops through the cards to generate combinations recursively
			for (int i = start; i <= cards.size() - remaining; i++) {
				currentCombination.add(cards.get(i)); // adds the current card to the combination
				generateCombinationsHelper(cards, combinations, currentCombination, i + 1, remaining - 1); // recursive call
				currentCombination.remove(currentCombination.size() - 1); // backtracks by removing the last card
			}
		}
    

	/**
	 * extracts the types (suits) of cards from a list of cards
	 * @param cards the list of cards to extract types from
	 * @return an array of card types as strings
	 */
	public static String[] getCardTypes(ArrayList<Card> cards) {

	    // initializes an array to store card types
	    String[] types = new String[cards.size()];

	    // iterates through the cards to extract their types
	    for (int i = 0; i < cards.size(); i++) {
	        types[i] = cards.get(i).getType(); // gets the type (suit) of the current card
	    }

	    // returns the array of card types
	    return types;
	}
	
	/**
	 * extracts the image paths of cards from a list of cards
	 * @param cards the list of cards to extract image paths from
	 * @return an array of card image paths as strings
	 */
	public static String[] getCardImagePaths(ArrayList<Card> cards) {

	    // initializes an array to store card image paths
	    String[] imagePaths = new String[cards.size()];

	    // iterates through the cards to extract their image paths
	    for (int i = 0; i < cards.size(); i++) {
	        imagePaths[i] = cards.get(i).getImagePath(); // gets the image path of the current card
	    }

	    // returns the array of card image paths
	    return imagePaths;
	}
    
    

	/**
	 * checks if the hand is a straight flush
	 * @param values an array of card values in the hand
	 * @param suits an array of card suits in the hand
	 * @return true if the hand is a straight flush, false otherwise
	 */
	private static boolean isStraightFlush(String[] values, String[] suits) {

	    // returns true if the hand is both a straight and a flush
	    return isStraight(values) && isFlush(suits);
	}
	
	
	/**
	 * checks if the hand is a royal flush
	 * @param values an array of card values in the hand
	 * @param suits an array of card suits in the hand
	 * @return true if the hand is a royal flush, false otherwise
	 */
	private static boolean isRoyalFlush(String[] values, String[] suits) {

	    // defines the values required for a royal flush
	    String[] royalValues = {"Ace", "King", "Queen", "Jack", "Ten"};

	    // sorts the card values to match the required order
	    Arrays.sort(values);

	    // checks if the hand is a straight flush and contains the royal values
	    return isStraightFlush(values, suits) && Arrays.equals(values, royalValues);
	}
	

	/**
	 * checks if the hand is a flush
	 * @param suits an array of card suits in the hand
	 * @return true if the hand is a flush, false otherwise
	 */
	private static boolean isFlush(String[] suits) {

	    // initializes counters for each suit
	    int hearts = 0, spades = 0, diamonds = 0, clubs = 0;

	    // counts the occurrences of each suit
	    for (String suit : suits) {
	        switch (suit) {
	            case "Hearts":
	                hearts++;
	                break;
	            case "Spades":
	                spades++;
	                break;
	            case "Diamonds":
	                diamonds++;
	                break;
	            case "Clubs":
	                clubs++;
	                break;
	        }
	    }

	    // checks if any suit has at least 5 cards
	    return hearts >= 5 || spades >= 5 || diamonds >= 5 || clubs >= 5;
	}
	

	/**
	 * checks if the hand is a straight
	 * @param values, an array of card values in the hand
	 * @return true, if the hand is a straight, false otherwise
	 */
	private static boolean isStraight(String[] values) {

	    // defines the rank order of card values
	    String[] rankOrder = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};

	    // initializes an array to store the rank indices of the cards
	    int[] cardRanks = new int[values.length];

	    // maps each card value to its corresponding rank index
	    for (int i = 0; i < values.length; i++) {
	        for (int j = 0; j < rankOrder.length; j++) {
	            if (values[i].equals(rankOrder[j])) {
	                cardRanks[i] = j;
	                break;
	            }
	        }
	    }

	    // sorts the card ranks to check for consecutive values
	    Arrays.sort(cardRanks);

	    // handles the special case for Ace-low straight (Ace, Two, Three, Four, Five)
	    if (cardRanks[0] == 0 && cardRanks[1] == 9) {
	        cardRanks[4] = 12; // treats Ace as the highest rank
	        Arrays.sort(cardRanks);
	    }

	    // checks if the card ranks are consecutive
	    for (int i = 0; i < cardRanks.length - 1; i++) {
	        if (cardRanks[i] + 1 != cardRanks[i + 1]) {
	            return false;
	        }
	    }

	    // returns true if all card ranks are consecutive
	    return true;
	}
    
    
	/**
	 * assigns a numerical value to a poker hand rank
	 * @param handRank, the rank of the hand as a string
	 * @return the numerical value of the hand rank, where higher values indicate stronger hands
	 */
	private static int getHandValue(String handRank) {

	    // checks the hand rank and assigns corresponding values
	    if (handRank.startsWith("Royal Flush")) {
	        return 10;
	    } else if (handRank.startsWith("Straight Flush")) {
	        return 9;
	    } else if (handRank.startsWith("Four of a Kind")) {
	        return 8;
	    } else if (handRank.startsWith("Full House")) {
	        return 7;
	    } else if (handRank.startsWith("Flush")) {
	        return 6;
	    } else if (handRank.startsWith("Straight")) {
	        return 5;
	    } else if (handRank.startsWith("Three of a Kind")) {
	        return 4;
	    } else if (handRank.startsWith("Two Pair")) {
	        return 3;
	    } else if (handRank.startsWith("One Pair")) {
	        return 2;
	    } else if (handRank.startsWith("High Card")) {
	        return 1;
	    } else {
	        return 0; // returns 0 for invalid or unrecognized hand ranks
	    }
	}
    

	/**
	 * checks if the hand contains four of a kind
	 * @param values, an array of card values in the hand
	 * @return the value of the card that forms four of a kind, or null if no four of a kind is present
	 */
	private static String isFourOfAKind(String[] values) {

	    // gets unique values from the hand
	    String[] uniqueValues = Arrays.stream(values).distinct().toArray(String[]::new);

	    // checks each unique value for a count of 4
	    for (String unique : uniqueValues) {
	        int count = 0;
	        for (String value : values) {
	            if (value.equals(unique)) {
	                count++;
	            }
	        }

	        // returns the value if four of a kind is found
	        if (count == 4) {
	            return unique;
	        }
	    }

	    // returns null if no four of a kind is found
	    return null;
	}
	

	/**
	 * checks if the hand is a full house
	 * @param values, an array of card values in the hand
	 * @return a string representing the full house (three of a kind over a pair), or null if no full house is present
	 */
	private static String isFullHouse(String[] values) {

	    // gets unique values from the hand
	    String[] uniqueValues = Arrays.stream(values).distinct().toArray(String[]::new);

	    // initializes variables to store three of a kind and a pair
	    String threeOfAKind = null;
	    String pair = null;

	    // checks each unique value for counts of 3 and 2
	    for (String unique : uniqueValues) {
	        int count = 0;
	        for (String value : values) {
	            if (value.equals(unique)) {
	                count++;
	            }
	        }

	        // assigns the value to three of a kind or pair if applicable
	        if (count == 3) {
	            threeOfAKind = unique;
	        } else if (count == 2) {
	            pair = unique;
	        }
	    }

	    // returns the full house description if both three of a kind and pair are present
	    if (threeOfAKind != null && pair != null) {
	        return threeOfAKind + " over " + pair;
	    }

	    // returns null if no full house is found
	    return null;
	}
	

	/**
	 * checks if the hand contains three of a kind
	 * @param values, an array of card values in the hand
	 * @return the value of the card that forms three of a kind, or null if no three of a kind is present
	 */
	private static String isThreeOfAKind(String[] values) {

	    // gets unique values from the hand
	    String[] uniqueValues = Arrays.stream(values).distinct().toArray(String[]::new);

	    // checks each unique value for a count of 3
	    for (String unique : uniqueValues) {
	        int count = 0;
	        for (String value : values) {
	            if (value.equals(unique)) {
	                count++;
	            }
	        }

	        // returns the value if three of a kind is found
	        if (count == 3) {
	            return unique;
	        }
	    }

	    // returns null if no three of a kind is found
	    return null;
	}
	

	/**
	 * checks if the hand contains two pairs
	 * @param values, an array of card values in the hand
	 * @return a string representing the two pairs (highest pair first), or null if no two pairs are present
	 */
	private static String isTwoPair(String[] values) {

	    // creates a list of unique values in the hand
	    ArrayList<String> uniqueValues = new ArrayList<>();
	    for (String value : values) {
	        if (!uniqueValues.contains(value)) {
	            uniqueValues.add(value);
	        }
	    }

	    // initializes a list to store pairs found in the hand
	    ArrayList<String> pairs = new ArrayList<>();

	    // checks each unique value for a count of 2
	    for (String unique : uniqueValues) {
	        int count = 0;
	        for (String value : values) {
	            if (value.equals(unique)) {
	                count++;
	            }
	        }

	        // adds the value to pairs if a pair is found
	        if (count == 2) {
	            pairs.add(unique);
	        }
	    }

	    // checks if exactly two pairs are found
	    if (pairs.size() == 2) {
	        pairs.sort(Comparator.reverseOrder()); // sorts pairs in descending order
	        return pairs.get(0) + " and " + pairs.get(1);
	    }

	    // returns null if no two pairs are found
	    return null;
	}

	/**
	 * checks if the hand contains one pair
	 * @param values, an array of card values in the hand
	 * @return the value of the card that forms the pair, or null if no pair is present
	 */
	private static String isOnePair(String[] values) {

	    // creates a list of unique values in the hand
	    ArrayList<String> uniqueValues = new ArrayList<>();
	    for (String value : values) {
	        if (!uniqueValues.contains(value)) {
	            uniqueValues.add(value);
	        }
	    }

	    // checks each unique value for a count of 2
	    for (String unique : uniqueValues) {
	        int count = 0;
	        for (String value : values) {
	            if (value.equals(unique)) {
	                count++;
	            }
	        }

	        // returns the value if a pair is found
	        if (count == 2) {
	            return unique;
	        }
	    }

	    // returns null if no pair is found
	    return null;
	}

	/**
	 * determines the high card in the hand
	 * @param values, an array of card values in the hand
	 * @return the value of the highest-ranked card as a string
	 */
	private static String highCard(String[] values) {

	    // defines the rank order of card values
	    String[] rankOrder = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};

	 // sorts the card values in descending order of their ranks
	    Arrays.sort(values, new Comparator<String>() {
	        @Override
	        public int compare(String a, String b) {
	            int rankA = Arrays.asList(rankOrder).indexOf(a); // gets the rank of card a
	            int rankB = Arrays.asList(rankOrder).indexOf(b); // gets the rank of card b
	            return Integer.compare(rankB, rankA); // compares the ranks in descending order
	        }
	    });

	    // returns the highest-ranked card
	    return values[0];
	}
    
	
	
    
	/**
	 * evaluates the rank of a given poker hand
	 * @param hand, the list of cards representing the poker hand
	 * @return a string representing the rank of the poker hand
	 */
	public static String evaluateHandRank(ArrayList<Card> hand) {
	    return evaluateFiveCardHand(hand);
	}
	
	@Override
	public String toString() {
	    return "PokerLogic class has utility methods for evaluating poker hands, determining best hands, and comparing poker hands between players.";
	}
	  
    
}
