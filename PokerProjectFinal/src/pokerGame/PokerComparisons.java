package pokerGame;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Compares poker hands between players 
 * @author Patrick Christmas
 * @version January 4 2025
 */
public class PokerComparisons {

    /**
     * finds the winner among players using their cards and community cards
     *
     * @param players, players in the game
     * @param communityCards, the community cards
     * @return the player with the best hand
     */
    public static Player evaluateWinner(ArrayList<Player> players, ArrayList<Card> communityCards) {
        Player winner = null;
        String bestHandRank = "";
        ArrayList<Card> bestHand = new ArrayList<>();

        for (Player player : players) {
            if (player.hasFolded()) {
                continue; // skip if player folded
            }

            // combine player cards and community cards
            ArrayList<Card> combinedHand = new ArrayList<>(player.getCards());
            combinedHand.addAll(communityCards);

            // get best 5 cards
            ArrayList<Card> bestFiveCardHand = PokerLogic.getBestHand(combinedHand, 5);
            String handRank = PokerLogic.evaluateHandRank(bestFiveCardHand);

            // check if this hand beats the current best
            if (winner == null || isHandRankHigher(handRank, bestHandRank) ||
                (handRank.equals(bestHandRank) && compareKickers(bestFiveCardHand, bestHand) > 0)) {
                winner = player;
                bestHandRank = handRank;
                bestHand = bestFiveCardHand;
            }
        }

        // print the winner
        if (winner != null) {
            System.out.println(winner.getName() + " wins with " + bestHandRank + "!");
        } else {
            System.out.println("No winner could be determined.");
        }

        return winner;
    }

    /**
     * checks if one hand rank is better than another
     *
     * @param currentHand, current player's hand rank
     * @param bestHand, best hand rank so far
     * @return true if currentHand is better
     */
    private static boolean isHandRankHigher(String currentHand, String bestHand) {
        String[] handRankings = {
            "High Card",
            "One Pair",
            "Two Pair",
            "Three of a Kind",
            "Straight",
            "Flush",
            "Full House",
            "Four of a Kind",
            "Straight Flush",
            "Royal Flush"
        };

        int currentRankIndex = -1;
        int bestRankIndex = -1;

        for (int i = 0; i < handRankings.length; i++) {
            if (handRankings[i].equals(currentHand)) {
                currentRankIndex = i;
            }
            if (handRankings[i].equals(bestHand)) {
                bestRankIndex = i;
            }
        }

        return currentRankIndex > bestRankIndex;
    }

    /**
     * compares the kickers of two hands
     *
     * @param hand1, first hand
     * @param hand2, second hand
     * @return positive if hand1 is better, negative if hand2, zero if tie
     */
    private static int compareKickers(ArrayList<Card> hand1, ArrayList<Card> hand2) {
        ArrayList<Integer> ranks1 = getCardRanks(hand1);
        ArrayList<Integer> ranks2 = getCardRanks(hand2);

        for (int i = 0; i < Math.min(ranks1.size(), ranks2.size()); i++) {
            if (!ranks1.get(i).equals(ranks2.get(i))) {
                return ranks1.get(i) - ranks2.get(i);
            }
        }

        return 0; // equal hands
    }

    /**
     * gets numerical card ranks sorted in descending order
     *
     * @param hand, the hand of cards
     * @return sorted list of card ranks
     */
    private static ArrayList<Integer> getCardRanks(ArrayList<Card> hand) {
        ArrayList<Integer> ranks = new ArrayList<>();
        for (Card card : hand) {
            ranks.add(getCardRank(card.getValue()));
        }
        Collections.sort(ranks, Collections.reverseOrder());
        return ranks;
    }

    /**
     * maps card value to a numerical rank
     *
     * @param value, card value as string
     * @return numerical rank of card
     */
    private static int getCardRank(String value) {
        switch (value) {
            case "Two": return 2;
            case "Three": return 3;
            case "Four": return 4;
            case "Five": return 5;
            case "Six": return 6;
            case "Seven": return 7;
            case "Eight": return 8;
            case "Nine": return 9;
            case "Ten": return 10;
            case "Jack": return 11;
            case "Queen": return 12;
            case "King": return 13;
            case "Ace": return 14;
            default: return 0; // invalid card
        }
    }
    
    @Override
    public String toString() {
        return "PokerComparisons class uses utility methods for evaluating the winner among players, " +
               "comparing poker hands, and analyzing hand strengths and kickers.";
    }
    
    
}