package pokerGame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

/**
 * @author PatrickChristmas
 * @version December 20 2024.
 * 
 * This class represents the main frame of the poker game application, where users can log in and 
 * interact with the game. It manages the login process, error handling, and switching between panels.
 */
public class MyFrame extends JFrame {
    
    private GamePanel gamePanel;
    private SettingsPanel usernamePanel;
    private UserSetUp userSetUp;
    private UserRegistration userRegistration;
    private JPanel errorPanel;
    private String currentUsername; // Store validated username
    
    /**
     * Constructs a new MyFrame instance and initializes the frame settings and components.
     */
    public MyFrame() {
        // create instance of UserRegistation, used later on in the class
        userRegistration = new UserRegistration(); 
        setup();
        initializeComponents();
        setVisible(true);
    }

    /**
     * Sets up the default close operation, window size, and layout for the frame.
     */
    private void setup() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        // creates variables for the screen size to 60% of size of the user's screen
        int screenWidth = (int) (size.getWidth() * 0.6);
        int screenHeight = (int) (size.getHeight() * 0.6);
        // sets the size to those custom screen sizes
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    /**
     * Initializes and sets up the components of the frame such as panels and listeners.
     */
    private void initializeComponents() {
        userSetUp = new UserSetUp();
        usernamePanel = new SettingsPanel();
        initializeErrorPanel();

        // adds login panel to the frame
        add(userSetUp, BorderLayout.CENTER);

        // sets up the action listener to check if the login is valid 
        userSetUp.setLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }
    
    /**
     * Initializes the error panel, which will be shown if the username does not meet requirements.
     */
    private void initializeErrorPanel() {
        errorPanel = new JPanel();
        errorPanel.setBackground(Color.RED);
        errorPanel.setLayout(new BorderLayout());

        JLabel errorLabel = new JLabel("Invalid username: Must contain at least one capital letter and one number.", JLabel.CENTER);
        errorLabel.setFont(new Font("Egyptienne", Font.BOLD, 20));
        errorLabel.setForeground(Color.WHITE);
        errorPanel.add(errorLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Egyptienne", Font.BOLD, 20));
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        // when pressed, back button switches back to the userSetUpPanel
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToUserSetUpPanel();
            }
        });
        errorPanel.add(backButton, BorderLayout.SOUTH);
    }

    /**
     * Handles the login process by validating the username and password entered by the user.
     */
    private void handleLogin() {
        String username = userSetUp.getUsername();
        String password = userSetUp.getPassword();
        // if the username or password are not entered.
        if (username.isEmpty() || password.isEmpty()) {
            userSetUp.setErrorMessage("Please enter both username and password.");
            return;
        }
        // if no caps or nums
        if (!containsCapitalLetter(username) || !containsNumber(username)) {
            switchToErrorPanel();
            return;
        }
        // if username is taken (found in textfile)
        if (userRegistration.isUsernameTaken(username)) {
            userSetUp.setErrorMessage("Username is already taken. Please try another one.");
            return;
        }

        // saves valid username and switches panel
        currentUsername = username;
        // clears the error message once error is fixed 
        userSetUp.setErrorMessage(""); 
        
        switchToUsernamePanel();
    }

    /**
     * Checks if a string contains at least one capital letter.
     * @param str, the string to check
     * @return true if the string contains a capital letter, false otherwise
     */
    private boolean containsCapitalLetter(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a string contains at least one number.
     * @param str, the string to check
     * @return true if the string contains a number, false otherwise
     */
    private boolean containsNumber(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Switches from the userSetUp panel to the error panel to display an error message
     */
    private void switchToErrorPanel() {
        remove(userSetUp);
        add(errorPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Switches from the error panel back to the userSetUp panel
     */
    private void switchToUserSetUpPanel() {
        remove(errorPanel);
        add(userSetUp, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Switches from the userSetUp panel to the usernamePanel after a correct login
     */
    private void switchToUsernamePanel() {
        remove(userSetUp);
        add(usernamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        // If the play button has been clicked in the Username panel, starts the game 
        usernamePanel.setStartGameListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToGamePanel();
            }
        });
    }

    /**
     * Switches from the usernamePanel to the gamePanel, starting the game
     */
    private void switchToGamePanel() {
        remove(usernamePanel);
        gamePanel = new GamePanel(currentUsername);

        add(gamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        gamePanel.requestFocusInWindow(); 
    }

    /**
     * 
     * @return a string of width, height, currentUsername, and GamepelStatus 
     */
    @Override
    public String toString() {
        String gamePanelStatus = ""; 
        if (gamePanel != null) {
            gamePanelStatus = "Initialized";
        } else {
            gamePanelStatus = "Not Initialized";
        }

        return "MyFrame [Width: " + getWidth() +
               ", Height: " + getHeight() +
               ", Current User: " + currentUsername +
               ", Game Panel: " + gamePanelStatus + "]";
    }
}