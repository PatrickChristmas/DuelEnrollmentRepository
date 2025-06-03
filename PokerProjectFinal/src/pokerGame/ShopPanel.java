package pokerGame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * ShopPanel displays a list of purchasable badges and the player's current coin balance.
 * allows the user to buy badges if they have enough coins and dont  own them.
 */
public class ShopPanel extends JPanel {

    /** 
     * The users inventory, used to check coins and badge ownership
     **/
    private Inventory inventory;

    /** 
     * List of available badges in the shop
     **/
    private ArrayList<Badge> shopBadges;

    /** 
     * Label displaying the users current coin total 
     **/
    private JLabel coinsLabel;

    /** 
     * Button to go back to the home screen 
     **/
    private JButton backButton;

    /**
     * Constructs a ShopPanel with the given inventory and list of badges for sale.
     * @param inventory   the user's current inventory
     * @param shopBadges  the list of badges available to purchase
     */
    public ShopPanel(Inventory inventory, ArrayList<Badge> shopBadges) {
        this.inventory = inventory;
        this.shopBadges = shopBadges;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); 

        //Title and back button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 144, 255)); 

        backButton = new JButton("← Back");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(25, 25, 112)); 
        backButton.setFocusPainted(false);
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel title = new JLabel("Badge Shop", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        topPanel.add(title, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Coins Display
        coinsLabel = new JLabel("Coins: " + inventory.getCoins(), SwingConstants.CENTER);
        coinsLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        coinsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(coinsLabel, BorderLayout.SOUTH);

        // Shop Items
        JPanel itemsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        itemsPanel.setBackground(new Color(245, 245, 245));
        for (Badge badge : shopBadges) {
            JPanel badgePanel = createBadgePanel(badge);
            itemsPanel.add(badgePanel);
        }

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Creates a panel for a single badge, including image, name, and buy button.
     * @param badge the badge to create a panel for
     * @return JPanel representing the badge in the shop
     */
    private JPanel createBadgePanel(Badge badge) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel nameLabel = new JLabel(badge.getDisplayName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(nameLabel, BorderLayout.NORTH);

        if (badge.getImageIcon() != null) {
            JLabel imageLabel = new JLabel(badge.getImageIcon());
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(imageLabel, BorderLayout.CENTER);
        }

        JButton buyButton = new JButton("Buy (" + badge.getPrice() + " coins)");
        buyButton.setBackground(new Color(60, 179, 113)); 
        buyButton.setForeground(Color.WHITE);
        buyButton.setFocusPainted(false);
        buyButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        // I know, JOptionpane is lazy :(
        buyButton.addActionListener(e -> {
            if (inventory.owns(badge.getId())) {
                JOptionPane.showMessageDialog(this, "You already own this badge!");
            } else if (inventory.getCoins() < badge.getPrice()) {
                JOptionPane.showMessageDialog(this, "Not enough coins!");
            } else {
                inventory.spendCoins(badge.getPrice());
                inventory.addBadge(badge);
                updateCoinDisplay();
                JOptionPane.showMessageDialog(this, "Purchased " + badge.getDisplayName() + "!");
            }
        });

        panel.add(buyButton, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Updates the coin display label after a purchase is made.
     */
    private void updateCoinDisplay() {
        coinsLabel.setText("Coins: " + inventory.getCoins());
    }

    /**
     * Adds an action listener to the back button.
     * @param listener the listener to execute when the back button is clicked
     */
    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    /**
     * Returns a String of the current ShopPanel 
     * @return string showing current coin count and number of badges in shop
     */
    @Override
    public String toString() {
        return "ShopPanel [Coins: " + inventory.getCoins() +
               ", Badges Available: " + shopBadges.size() + "]";
    }
}