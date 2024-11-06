package pokerGame;


import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

public class MyFrame extends JFrame {
    private GamePanel gamePanel; // creates an instance of GamePanel
    private UserNamePanel usernamePanel; // create an instance of UserNamePanel
    
    
    // used for the screen's height and width
    private int screenWidth;
    private int screenHeight; 
    
    // creates the frame
    public MyFrame() {
        setup();
        initializeComponents();
        setVisible(true);
    }
    
    // sets the layouts and sets the size of the frame 
    private void setup() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) size.getWidth();
        screenHeight = (int) size.getHeight(); 
        setSize(1800, 1000);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); 
    }

    // adds the userNamePanel, and detects if username is empty
    private void initializeComponents() {
        // create the panels
        usernamePanel = new UserNamePanel();

        // adds the username panel to the frame at the center
        add(usernamePanel, BorderLayout.CENTER);

        // sets up the action listener for the username to check if it is empty 
        usernamePanel.setStartGameListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernamePanel.getUsername(); 
                if (!username.isEmpty()) { 
                    System.out.println("Username entered: " + username);
                                  
                    // game can start running
                    switchToGamePanel(username);
                } else {
                    System.out.println("Please enter a username."); 
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
        repaint(); // redraws the frame
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