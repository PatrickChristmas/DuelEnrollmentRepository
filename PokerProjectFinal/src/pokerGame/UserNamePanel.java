package pokerGame;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Image;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author PatrickChristmas
 * @version December 20 2024.
 * The UserNamePanel class represents the initial user GUI for the poker game, where the player selects their
 * starting amount of money and starts the game. It has radio buttons for selecting the amount and a button to 
 * initiate the game. It also creates a title, "Let's Play Poker" and an image of a king card.
 * 
 * This panel is used to gather the starting money choice before starting the poker game.
 */
public class UserNamePanel extends JPanel {
    
    private JButton startButton; // Button to start the game
    private JRadioButton thousandDollarButton; // Radio button for $1000 starting amount
    private JRadioButton twoThousandDollarButton; // Radio button for $2000 starting amount
    private JRadioButton threeThousandDollarButton; // Radio button for $3000 starting amount
    
    private ButtonGroup dollarButtonGroup; // Group that manages the radio buttons
    private static int selectedAmount; // The selected starting amount for the player
    
    private ImageIcon kingCardImage; // ImageIcon for the king card image

    /**
     * Constructor for the UserNamePanel class.
     * Sets up the layout, loads images, and initializes components like radio buttons and the start button.
     */
    public UserNamePanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(34, 49, 63));  // Set background color to navy blue

        GridBagConstraints gbc = new GridBagConstraints(); 
        gbc.insets = new Insets(10, 10, 10, 10); // Adds padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally

        // Load and scale the king card image
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("Images/KingCardImage.png")));
            Image imageS = icon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            kingCardImage = new ImageIcon(imageS);
        } catch (IOException e) {
            System.out.println("Failed to load King Card Image");
        }

        // Welcome label
        JLabel welcomeLabel = new JLabel("Let's Play Poker", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Cursive", Font.BOLD, 36));
        welcomeLabel.setForeground(new Color(189, 195, 199)); // Light gray text
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(new Color(0, 0, 0, 150)); // Semi-transparent black background
        welcomeLabel.setBorder(new LineBorder(Color.BLACK, 5)); // Black border around label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(welcomeLabel, gbc);

        // Start button
        startButton = new JButton("Play");
        configureButton(startButton);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(startButton, gbc);

        // Add king card image to the panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel(kingCardImage), gbc);

        // ActionListener to update the selected amount based on the radio button selection
        ActionListener selectionListener = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                updateSelectedAmount();
            }
        };

        // Radio buttons for dollar options
        dollarButtonGroup = new ButtonGroup();
        thousandDollarButton = new JRadioButton("Start with $1000");
        twoThousandDollarButton = new JRadioButton("Start with $2000");
        threeThousandDollarButton = new JRadioButton("Start with $3000");
        
        // Add action listener to each radio button
        thousandDollarButton.addActionListener(selectionListener);
        twoThousandDollarButton.addActionListener(selectionListener);
        threeThousandDollarButton.addActionListener(selectionListener);

        // Configure radio buttons
        configureRadioButton(thousandDollarButton);
        configureRadioButton(twoThousandDollarButton);
        configureRadioButton(threeThousandDollarButton);

        // Add radio buttons to the group
        dollarButtonGroup.add(thousandDollarButton);
        dollarButtonGroup.add(twoThousandDollarButton);
        dollarButtonGroup.add(threeThousandDollarButton);

        // Add radio buttons to the panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(thousandDollarButton, gbc);

        gbc.gridy++;
        add(twoThousandDollarButton, gbc);

        gbc.gridy++;
        add(threeThousandDollarButton, gbc);
    }

    /**
     * Configures the appearance of a radio button.
     * 
     * @param button The radio button to configure
     */
    private void configureRadioButton(JRadioButton button) {
        button.setFont(new Font("Arial Rounded", Font.BOLD, 24)); // Set font
        button.setForeground(Color.WHITE); // Set text color (white)
        button.setBackground(new Color(52, 152, 219)); // Set background color (light blue)
        button.setBorder(new LineBorder(Color.BLACK, 3, true)); // Add a border
    }

    /**
     * Configures the appearance of the start button.
     * 
     * @param button, The button to configure
     */
    private void configureButton(JButton button) {
        button.setFont(new Font("Arial Rounded", Font.BOLD, 24)); // Set font
        button.setForeground(Color.WHITE); // Set text color (white)
        button.setBackground(new Color(52, 152, 219)); // Set background color (light blue)
        button.setBorder(new LineBorder(Color.BLACK, 3, true)); // Add a border
    }

    /**
     * Updates the selected amount based on the selected radio button.
     */
    private void updateSelectedAmount() {
        selectedAmount = thousandDollarButton.isSelected() ? 1000 :
                         (twoThousandDollarButton.isSelected() ? 2000 :
                         (threeThousandDollarButton.isSelected() ? 3000 : 0));
    }

    /**
     * Getter for the seleceted amount based on the radiobuttons 
     * 
     * @return The selected amount
     */
    public int getSelectedAmount() {
        return selectedAmount;
    }

    /**
     * This listener is used to start the game when the button is clicked.
     * 
     * @param listener, The action listener to add to the start button
     */
    public void setStartGameListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    /**
     * 
     * @return A string of the selected amount and if the startbutton is enabled. 
     */
    @Override
    public String toString() {
        return "SelectedAmount: " + selectedAmount +
               ", Start Button Enabled: " + startButton.isEnabled() + "]";
    }
}