package pokerGame;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

/**
 * @author PatrickChristmas
 * @version June 2 2025.
 * 
 * This class represents the main frame of the poker game application, where users can log in and 
 * interact with the game. It manages the login process, error handling, and switching between panels.
 */
public class MyFrame extends JFrame {
    
	/**
	 * The main panel where the poker game is played
	 */
	private GamePanel gamePanel;

	/**
	 * The settings panel used for username setup and validation before entering the game.
	 */
	private SettingsPanel usernamePanel;

	/**
	 * The panel for user login
	 */
	private UserSetUp userSetUp;

	/**
	 * The panel for user registration
	 */
	private UserRegistration userRegistration;

	/**
	 * A panel used to display error messages when login or registration fails
	 */
	private JPanel errorPanel;

	/**
	 * Stores the currently validated and active username 
	 */
	private String currentUsername;

	/**
	 * The home screen panel where users can navigate to play the game
	 */
	private HomeScreen homeScreen;

	/**
	 * The panel that allows users to browse and purchase badges
	 */
	private ShopPanel shopPanel;

	/**
	 * The panel displaying user and AI rankings, sorted by elo 
	 */
	private LeaderboardPanel leaderboardPanel;

	/**
	 * The playes inventory, which stores owned badges
	 */
	private Inventory userInventory;


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

        JLabel errorLabel = new JLabel("Invalid password: Must contain at least one capital letter and one number.", JLabel.CENTER);
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
     // if password does not contain capital letter or number
        if (!containsCapitalLetter(password) || !containsNumber(password)) {
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
        userInventory = new Inventory();
        switchToHomeScreen();
    }
    
    
    /**
     * Removes all the other panels and goes back to HomeScreen
     */
    public void switchToHomeScreen() {
        // Remove all other panels
        if (gamePanel != null) 
        	remove(gamePanel);
        if (shopPanel != null)
        	remove(shopPanel);
        if (leaderboardPanel != null)
        	remove(leaderboardPanel);
        if (usernamePanel != null) 
        	remove(usernamePanel);
        if (userSetUp != null) 
        	remove(userSetUp);
        if (errorPanel != null) 
        	remove(errorPanel);

        homeScreen = new HomeScreen(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                switch (cmd) {
                    case "play":
                        switchToUsernamePanel();
                        break;
                    case "shop":
                        showShopPanel();
                        break;
                    case "leaderboard":
                        showLeaderboardPanel();
                        break;
                    case "quit":
                        System.exit(0);
                        break;
                }
            }
        });

        add(homeScreen, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    
    
    
    /**
     * opens up the shop panel with all the badges 
     **/
    private void showShopPanel() {
        if (shopPanel != null) {
            remove(shopPanel);
        }

        ArrayList<Badge> shopBadges = new ArrayList<>();
        shopBadges.add(new Badge("first_win", "First Win!", 50, new ImageIcon("images/badges/First_Win.png")));
        shopBadges.add(new Badge("high_roller", "High Roller", 300, new ImageIcon("images/badges/High_Roller.png")));
        shopBadges.add(new Badge("comeback_king", "Comeback King", 250, new ImageIcon("images/badges/Comeback_King.png")));
        shopBadges.add(new Badge("lucky_draw", "Lucky Draw", 100, new ImageIcon("images/badges/Lucky_Draw.png")));
        shopBadges.add(new Badge("cold_bluff", "Cold Bluff", 200, new ImageIcon("images/badges/Cold_Bluff.png")));
        shopBadges.add(new Badge("all_in_maniac", "All-In Maniac", 400, new ImageIcon("images/badges/All_In_Maniac.png")));
        shopBadges.add(new Badge("poker_legend", "Poker Legend", 500, new ImageIcon("images/badges/Poker_Legend.png")));
        shopBadges.add(new Badge("chatty_ai", "Chatted with AI", 150, new ImageIcon("images/badges/Chatted_With_AI.png")));

        shopPanel = new ShopPanel(userInventory, shopBadges);

        shopPanel.setBackButtonListener(e -> {
            remove(shopPanel);
            add(homeScreen, BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        remove(homeScreen);
        add(shopPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    /**
     * hows the leaderboard panel  and loads the user data 
     */
    private void showLeaderboardPanel() {
        try {
            UserRegistration.registerUsers(); // Load user data
            ArrayList<User> users = UserRegistration.getAllUsers();
            ArrayList<AIPlayer> aiPlayers = AIProfileManager.loadAIPlayers();

            leaderboardPanel = new LeaderboardPanel(users, aiPlayers, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    remove(leaderboardPanel);                // remove leaderboard panel
                    add(homeScreen, BorderLayout.CENTER);    // return to home screen
                    revalidate();
                    repaint();
                }
            });

            remove(homeScreen);
            add(leaderboardPanel, BorderLayout.CENTER);
            revalidate();
            repaint();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
        remove(homeScreen);
        
        // Always create new SettingsPanel to avoid duplicate listeners
        usernamePanel = new SettingsPanel();

        usernamePanel.setStartGameListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToGamePanel();
            }
        });

        add(usernamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Switches from the usernamePanel to the gamePanel, starting the game
     */
    private void switchToGamePanel() {
        if (gamePanel != null) {
            remove(gamePanel);
            gamePanel = null;
        }

        remove(usernamePanel);

        ArrayList<AIPlayer> aiPlayers = AIProfileManager.loadAIPlayers();
        AIPlayer targetAI = aiPlayers.get(0);

        AIPlayer llmTarget = new AIPlayer(
            targetAI.getName(),
            targetAI.getBio(),
            targetAI.getMoney(),
            targetAI.getWins(),
            targetAI.getLosses(),
            targetAI.getElo()
        );

        gamePanel = new GamePanel(currentUsername, llmTarget, userInventory);

        gamePanel.setBackToHomeListener(e -> {
            remove(gamePanel);
            add(homeScreen, BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        add(gamePanel, BorderLayout.CENTER);
        gamePanel.setParentFrame(this);
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