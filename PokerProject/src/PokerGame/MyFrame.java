package PokerGame;


import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

public class MyFrame extends JFrame {
    private GamePanel gamePanel; // used to create instances of GamePanel
    private UserNamePanel usernamePanel; // used to create instances of UserNamePanel
    // calls set up and initializeComponents, actually 'creates' the frame
    public MyFrame() {
        setup();
        initializeComponents();
        setVisible(true);
    }

    private void setup() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(1200, 1000);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // uses border layout 
    }

    private void initializeComponents() {
        // create the panels
        usernamePanel = new UserNamePanel();

        // adds the username panel to the frame at the center
        add(usernamePanel, BorderLayout.CENTER);

        // sets up the action listener for the username textbox (to make sure a username is filled out)
        usernamePanel.setStartGameListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernamePanel.getUsername(); 
                if (!username.isEmpty()) { // if the username is NOT empty
                    System.out.println("Username entered: " + username);
                    
                  

                    // game can start running
                    switchToGamePanel(username);
                } else {
                    System.out.println("Please enter a username."); // prints if the user does not put in a username
                }
            }
        });
    }

    private void switchToGamePanel(String username) {
        // removes the username panel and add the game panel
        remove(usernamePanel);       
        gamePanel = new GamePanel(username);

        add(gamePanel, BorderLayout.CENTER); // adds the game panel in the center
        revalidate(); // refreshes the frame 
        repaint(); // redraw the frame
        gamePanel.requestFocusInWindow(); // needed so the betAmount works
    }
    // toString 
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
               ", User: " + usernamePanel.getUsername() + 
               ", Game Panel: " + gamePanelStatus + "]";
    }
}