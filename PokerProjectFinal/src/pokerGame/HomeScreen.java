package pokerGame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

/**
 * HomeScreen is the main menu panel for the Poker Game
 * It has  buttons to access other parts of the game
 * buttons include, play game, card shop, leaderboard, and quit.
 * buttons is wired to a shared ActionListener passed in the constructor.
 * 
 * @author Patrick Christmas 
 * @version June 1 2025
 */
public class HomeScreen extends JPanel {

    /**
     * constructs the home screen panel with all menu buttons and layout setup
     * @param listener the ActionListener to handle button actions
     */
    public HomeScreen(ActionListener listener) {
        setLayout(new BorderLayout());
        setBackground(new Color(34, 40, 49));  // dark background 

        // Title label setup
        JLabel title = new JLabel("Welcome to Pat's Poker Game", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        // Panel for holding buttons
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 15, 15));
        buttonPanel.setBackground(new Color(57, 62, 70));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 150, 50, 150));

        // Create and style buttons
        String[] buttonLabels = { "Play Game", "Card Shop", "Leaderboard", "Quit" };
        String[] commands = { "play", "shop", "leaderboard", "quit" };

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            button.setActionCommand(commands[i]);
            button.setFocusPainted(false);
            button.setFont(new Font("SansSerif", Font.BOLD, 20));
            button.setBackground(new Color(0, 128, 0)); // POKER GREEN
            button.setForeground(Color.WHITE);
            button.setPreferredSize(new Dimension(200, 50));
            button.addActionListener(listener);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * returns a string description of the HomeScreen panel.
     * @return string representation of the home screen
     */
    @Override
    public String toString() {
        return "HomeScreen[Main menu with navigation buttons: Play, Shop, Leaderboard, Quit]";
    }
}
