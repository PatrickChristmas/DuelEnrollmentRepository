package pokerGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 * contains methods for rendering  poker game 
 */
public class GamePanelRenderer {
    /**
     * the spacing used for drawing chips
     */
    public static final int CHIP_SPACING = 15;

    /**
     * draws the table using the background texture
     * @param g the graphics object used for drawing
     * @param backgroundTexture the image used as the background
     */
    public static void drawTable(Graphics g, Image backgroundTexture) {
        if (backgroundTexture != null) {
            g.drawImage(backgroundTexture, 0, 0, null);
        }
    }

    /**
     * renders the table with gradient effect and card placeholders in the middle
     * @param g2d the graphics2d object used for rendering
     * @param panelWidth the width of the panel
     * @param panelHeight the height of the panel
     */
    public static void renderTable(Graphics2D g2d, int panelWidth, int panelHeight) {
        int tableWidth = 1600;
        int tableHeight = 1000;
        int x = (panelWidth - tableWidth) / 2;
        int y = (panelHeight - tableHeight) / 2;

        GradientPaint tableGradient = new GradientPaint(
            x + 50, y + 50, new Color(30, 120, 30),
            x + 50, y + tableHeight - 100, new Color(20, 90, 20)
        );
        g2d.setPaint(tableGradient);
        g2d.fillOval(x, y, tableWidth, tableHeight);

        g2d.setColor(new Color(139, 69, 19));
        g2d.setStroke(new BasicStroke(15));
        g2d.drawOval(x, y, tableWidth, tableHeight);

        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawOval(x + 30, y + 30, tableWidth - 60, tableHeight - 60);

        // Draw card placeholders in the middle:
        int cardWidth = 120;
        int cardHeight = 160;
        int spacing = 40;
        int startX = panelWidth / 2 - 5 * (cardWidth + spacing) / 2;
        int centerY = panelHeight / 2 - cardHeight / 2;

        for (int i = 0; i < 5; i++) {
            int xCard = startX + i * (cardWidth + spacing);
            g2d.setColor(new Color(0, 0, 0, 50));
            g2d.fillRoundRect(xCard + 5, centerY + 5, cardWidth, cardHeight, 10, 10);
            g2d.setColor(new Color(34, 139, 34));
            g2d.fillRoundRect(xCard, centerY, cardWidth, cardHeight, 10, 10);
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(xCard, centerY, cardWidth, cardHeight, 10, 10);
        }
    }

    /**
     * moves the chips towards the center along the x axis
     * @param animatedRedChipX the current x coordinate of the red chip
     * @param animatedGreenChipX the current x coordinate of the green chip
     * @param animatedBlueChipX the current x coordinate of the blue chip
     * @param panelWidth the width of the panel
     * @param chipMoveSpeed the speed at which chips move
     * @return an array of new x coordinates for red, green and blue chips
     */
    public static int[] moveChipsTowardsCenterX(int animatedRedChipX, int animatedGreenChipX, int animatedBlueChipX,int panelWidth, int chipMoveSpeed) {
        int centerX = panelWidth / 2;

        // Red chip
        if (animatedRedChipX < centerX) {
            animatedRedChipX += chipMoveSpeed;
            if (animatedRedChipX > centerX) animatedRedChipX = centerX;
        } else if (animatedRedChipX > centerX) {
            animatedRedChipX -= chipMoveSpeed;
            if (animatedRedChipX < centerX) animatedRedChipX = centerX;
        }

        // Green chip
        if (animatedGreenChipX < centerX) {
            animatedGreenChipX += chipMoveSpeed;
            if (animatedGreenChipX > centerX) animatedGreenChipX = centerX;
        } else if (animatedGreenChipX > centerX) {
            animatedGreenChipX -= chipMoveSpeed;
            if (animatedGreenChipX < centerX) animatedGreenChipX = centerX;
        }

        // Blue chip
        if (animatedBlueChipX < centerX) {
            animatedBlueChipX += chipMoveSpeed;
            if (animatedBlueChipX > centerX) animatedBlueChipX = centerX;
        } else if (animatedBlueChipX > centerX) {
            animatedBlueChipX -= chipMoveSpeed;
            if (animatedBlueChipX < centerX) animatedBlueChipX = centerX;
        }

        return new int[] {animatedRedChipX, animatedGreenChipX, animatedBlueChipX};
    }

    /**
     * moves the chips towards the center along the y axis
     * @param animatedRedChipY the current y coordinate of the red chip
     * @param animatedGreenChipY the current y coordinate of the green chip
     * @param animatedBlueChipY the current y coordinate of the blue chip
     * @param panelHeight the height of the panel
     * @param chipMoveSpeed the speed at which chips move
     * @return an array of new y coordinates for red, green and blue chips
     */
    public static int[] moveChipsTowardsCenterY(int animatedRedChipY, int animatedGreenChipY, int animatedBlueChipY, int panelHeight, int chipMoveSpeed) {
        int centerY = panelHeight / 2 - 200;

        // Red chip
        if (animatedRedChipY < centerY) {
            animatedRedChipY += chipMoveSpeed;
            if (animatedRedChipY > centerY) animatedRedChipY = centerY;
        } else if (animatedRedChipY > centerY) {
            animatedRedChipY -= chipMoveSpeed;
            if (animatedRedChipY < centerY) animatedRedChipY = centerY;
        }

        // Green chip
        if (animatedGreenChipY < centerY) {
            animatedGreenChipY += chipMoveSpeed;
            if (animatedGreenChipY > centerY) animatedGreenChipY = centerY;
        } else if (animatedGreenChipY > centerY) {
            animatedGreenChipY -= chipMoveSpeed;
            if (animatedGreenChipY < centerY) animatedGreenChipY = centerY;
        }

        // Blue chip
        if (animatedBlueChipY < centerY) {
            animatedBlueChipY += chipMoveSpeed;
            if (animatedBlueChipY > centerY) animatedBlueChipY = centerY;
        } else if (animatedBlueChipY > centerY) {
            animatedBlueChipY -= chipMoveSpeed;
            if (animatedBlueChipY < centerY) animatedBlueChipY = centerY;
        }

        return new int[] {animatedRedChipY, animatedGreenChipY, animatedBlueChipY};
    }

    /**
     * draws the chips on the screen based on the current bet and chip positions
     * @param g the graphics object used for drawing
     * @param currentBet the current bet amount
     * @param animatedRedChipX the x coordinate of the red chip
     * @param animatedGreenChipX the x coordinate of the green chip
     * @param animatedBlueChipX the x coordinate of the blue chip
     * @param animatedRedChipY the y coordinate of the red chip
     * @param animatedGreenChipY the y coordinate of the green chip
     * @param animatedBlueChipY the y coordinate of the blue chip
     * @param redChip the image for the red chip
     * @param greenChip the image for the green chip
     * @param blueChip the image for the blue chip
     */
    public static void drawChips(Graphics g, int currentBet, int animatedRedChipX, int animatedGreenChipX, int animatedBlueChipX,int animatedRedChipY, int animatedGreenChipY, int animatedBlueChipY, Image redChip, Image greenChip, Image blueChip) {
        int numOfRed = currentBet / 1000;
        int placeHolderNum = currentBet;
        int numOfBlue = placeHolderNum / 100;
        placeHolderNum %= 100;
        int numOfGreen = placeHolderNum / 10;

        int chipsPerRow = 4;

        // draw red chips
        if (redChip != null) {
            for (int i = 0; i < numOfRed; i++) {
                int row = i / chipsPerRow;
                int col = i % chipsPerRow;
                int x = animatedRedChipX + col * (CHIP_SPACING + 50);
                int y = animatedRedChipY + row * (CHIP_SPACING + 15);
                g.drawImage(redChip, x, y, null);
            }
        }
        // draw green chips
        if (greenChip != null) {
            for (int i = 0; i < numOfGreen; i++) {
                int row = i / chipsPerRow;
                int col = i % chipsPerRow;
                int x = animatedGreenChipX + col * (CHIP_SPACING + 50);
                int y = animatedGreenChipY + row * (CHIP_SPACING + 15);
                g.drawImage(greenChip, x, y, null);
            }
        }
        // draw blue chips
        if (blueChip != null) {
            for (int i = 0; i < numOfBlue; i++) {
                int row = i / chipsPerRow;
                int col = i % chipsPerRow;
                int x = animatedBlueChipX + col * (CHIP_SPACING + 50);
                int y = animatedBlueChipY + row * (CHIP_SPACING + 15);
                g.drawImage(blueChip, x, y, null);
            }
        }
    }

    /**
     * draws the ai chips on the screen based on the total bets from ai players
     * @param g the graphics object used for drawing
     * @param totalAIBets the total bets made by ai players
     * @param aiRedChipX the x coordinate for the ai red chip
     * @param aiGreenChipX the x coordinate for the ai green chip
     * @param aiBlueChipX the x coordinate for the ai blue chip
     * @param aiRedChipY the y coordinate for the ai red chip
     * @param aiGreenChipY the y coordinate for the ai green chip
     * @param aiBlueChipY the y coordinate for the ai blue chip
     * @param redChip the image for the red chip
     * @param blueChip the image for the blue chip
     * @param greenChip the image for the green chip
     */
    public static void drawAIChips(Graphics g, int totalAIBets, int aiRedChipX, int aiGreenChipX, int aiBlueChipX,int aiRedChipY, int aiGreenChipY, int aiBlueChipY,Image redChip, Image blueChip, Image greenChip) {
        int remainingAmount = totalAIBets;
        int numRed = remainingAmount / 1000;
        remainingAmount %= 1000;
        int numBlue = remainingAmount / 100;
        remainingAmount %= 100;
        int numGreen = remainingAmount / 10;

        // convert large chip counts if needed
        if (numBlue >= 10) {
            numRed += numBlue / 10;
            numBlue %= 10;
        }
        if (numGreen >= 10) {
            numBlue += numGreen / 10;
            numGreen %= 10;
        }

        int chipsPerRow = 4;

        // red chips
        if (redChip != null) {
            for (int i = 0; i < numRed; i++) {
                int row = i / chipsPerRow;
                int col = i % chipsPerRow;
                int x = aiRedChipX + col * (CHIP_SPACING + 50);
                int y = aiRedChipY + row * (CHIP_SPACING + 15);
                g.drawImage(redChip, x, y, null);
            }
        }
        // blue chips
        if (blueChip != null) {
            for (int i = 0; i < numBlue; i++) {
                int row = i / chipsPerRow;
                int col = i % chipsPerRow;
                int x = aiBlueChipX + col * (CHIP_SPACING + 50);
                int y = aiBlueChipY + row * (CHIP_SPACING + 15);
                g.drawImage(blueChip, x, y, null);
            }
        }
        // green chips
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
     * draws the user hand description at the bottom of the panel
     * @param g the graphics object used for drawing
     * @param userHandDescription the description of the user hand
     * @param panelHeight the height of the panel
     */
    public static void drawUserHand(Graphics g, String userHandDescription, int panelHeight) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        if (userHandDescription != null && !userHandDescription.isEmpty()) {
            g.drawString("Your Hand: " + userHandDescription, 50, panelHeight - 50);
        } else {
            g.drawString("Your Hand: Evaluating...", 50, panelHeight - 50);
        }
    }

    /**
     * draws the community cards in the center of the panel
     * @param g2d the graphics2d object used for drawing
     * @param communityCards the list of community cards
     * @param panelWidth the width of the panel
     * @param panelHeight the height of the panel
     */
    public static void drawCommunityCards(Graphics2D g2d, ArrayList<Card> communityCards, int panelWidth, int panelHeight) {
        if (communityCards == null || communityCards.isEmpty()) {
            return;
        }
        int cardWidth = 75;
        int cardHeight = 100;
        int spacing = 20;
        int startX = (panelWidth - (communityCards.size() * cardWidth + (communityCards.size() - 1) * spacing)) / 2;
        int y = panelHeight / 2 - cardHeight / 2;

        for (int i = 0; i < communityCards.size(); i++) {
            Card card = communityCards.get(i);
            if (card != null) {
                Image cardImage = Toolkit.getDefaultToolkit().getImage(card.getImagePath());
                g2d.drawImage(cardImage, startX + i * (cardWidth + spacing), y,
                              cardWidth, cardHeight, null);
            }
        }
    }

    /**
     * draws the player's cards at the bottom left of the panel
     * @param g2d the graphics2d object used for drawing
     * @param player the player whose cards are drawn
     * @param panelHeight the height of the panel
     */
    public static void drawPlayerCards(Graphics2D g2d, Player player, int panelHeight) {
        if (player == null || player.getCards().isEmpty()) return;

        int cardWidth = 75;
        int cardHeight = 100;
        int spacing = 20;
        int startX = 50;
        int y = panelHeight - cardHeight - 150;

        ArrayList<Card> playerCards = player.getCards();

        g2d.setColor(Color.YELLOW);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Your Cards", startX, y - 20);

        for (int i = 0; i < playerCards.size(); i++) {
            Card card = playerCards.get(i);
            if (card != null) {
                Image cardImage = Toolkit.getDefaultToolkit().getImage(card.getImagePath());
                g2d.drawImage(cardImage, startX + i * (cardWidth + spacing),
                              y, cardWidth, cardHeight, null);
            }
        }
    }

    /**
     * draws the other players cards using the back of card image at  positions
     * @param g2d the graphics2d object used for drawing
     * @param otherPlayersHands the list of other players hands
     * @param panelWidth the width of the panel
     * @param panelHeight the height of the panel
     * @param backOfCard the image for the back of a card
     * @param players the list of players (user is at index 0)
     */
    public static void drawOtherPlayersCards(Graphics2D g2d, ArrayList<ArrayList<Card>> otherPlayersHands, int panelWidth, int panelHeight, Image backOfCard, ArrayList<Player> players) {
        if (otherPlayersHands == null || otherPlayersHands.isEmpty()) {
            return;
        }
        int cardWidth = 75;
        int cardHeight = 100;
        int spacing = 20;
        int[][] positions = {{panelWidth / 2 - 1600 / 3, panelHeight / 8},{panelWidth / 8, panelHeight / 2 - 1000 / 8},{panelWidth - panelWidth / 8 - cardWidth, panelHeight / 2 - 1000 / 8}};
        for (int i = 0; i < otherPlayersHands.size(); i++) {
            ArrayList<Card> hand = otherPlayersHands.get(i);
            int[] pos = positions[i];
            int x = pos[0];
            int y = pos[1];

            // The i+1th player (since i=0 is user)
            Player player = players.get(i + 1);

            g2d.setColor(Color.YELLOW);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString(player.getName(),
                           x + (hand.size() * (cardWidth + spacing)) / 4,
                           y - 20);

            for (int j = 0; j < hand.size(); j++) {
                if (backOfCard != null) {
                    g2d.drawImage(backOfCard,
                                  x + j * (cardWidth + spacing),
                                  y, cardWidth, cardHeight, null);
                }
            }
        }
    }

    /**
     * draws the players information on the screen
     * @param g the graphics object used for drawing
     * @param players the list of players
     * @param currentPlayerIndex the index of the current player
     * @param panelWidth the width of the panel
     */
    public static void drawPlayers(Graphics g, ArrayList<Player> players,
                                   int currentPlayerIndex, int panelWidth) {
        int x = panelWidth - 550;
        int y = 50;
        int spacing = 40;
        g.setFont(new Font("Arial", Font.PLAIN, 20));

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            // default color is white, highlight yellow if current
            g.setColor((i == currentPlayerIndex) ? Color.YELLOW : Color.WHITE);

            String status = player.hasFolded() ? "Folded" : "Active";
            g.drawString(
                player.getName() + " | Money: $" + player.getMoney() + " | Status: " + status,
                x, y
            );
            y += spacing;
        }
    }

    /**
     * draws the total pot text on the screen
     * @param g the graphics object used for drawing
     * @param pot the total pot amount
     * @param panelWidth the width of the panel
     * @param panelHeight the height of the panel
     */
    public static void drawPotText(Graphics g, int pot, int panelWidth, int panelHeight) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String potText = "Total Pot: $" + pot;
        int x1 = (panelWidth - 100) / 2;
        int x = (panelWidth - x1) / 2;
        int y = panelHeight / 2 - 200;
        g.drawString(potText, x, y);
    }

    /**
     * clears the list of drawn cards to prevent invalid extra drawings
     * @param drawnCards the list of drawn card objects
     */
    public static void clearInvalidDrawnCards(ArrayList<Drawing> drawnCards) {
        if (drawnCards != null) {
            drawnCards.clear();
        }
    }

    
}
