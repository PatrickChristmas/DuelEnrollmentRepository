package PokerGame;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import pokerGame.Drawing;


public class UserNamePanel extends JPanel {
    private JTextField usernameField; // create a JTextField variable 
    private JButton startButton; // create a JButton variable 
    private Color pokerGreenColor; // create a custom pokerGreen color. 

    public UserNamePanel() {
        pokerGreenColor = new Color(53, 101, 77); // custom poker green color 
        // change the layout manager
        setLayout(new BorderLayout());
        setBackground(pokerGreenColor); // sets the background to that custom color

        // creates the welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Pat's Poker Palace", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Egyptienne", Font.BOLD, 40));
        welcomeLabel.setForeground(Color.RED); // sets the label color to red
        add(welcomeLabel, BorderLayout.NORTH); // adds the label at the top

        // creates a panel to hold the username field and button, and center it
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setBackground(pokerGreenColor);
        
        // creates a label for username input
        JLabel inputLabel = new JLabel("Input Username: ");
        inputLabel.setFont(new Font("Egyptienne", Font.BOLD, 20)); // create a new font
        inputLabel.setForeground(Color.WHITE); // set the color to white
        inputPanel.add(inputLabel); // add the inputLabel to the inputPanel

        // creates the Jtextfield for username input
        usernameField = new JTextField(15); // 15 columns wide
        usernameField.setToolTipText("Enter your username"); // tooltip, so the user knows that to write
        inputPanel.add(usernameField); // adds the username toolTipText to the panel

        // creates the start button
        startButton = new JButton("Play");
        startButton.setFont(new Font("Egyptienne", Font.BOLD, 30));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(Color.BLACK);
        inputPanel.add(startButton); // adds the button to input panel

        // adds the input panel to the center of the UserNamePanel
        add(inputPanel, BorderLayout.CENTER);
    }

    public String getUsername() {
        return usernameField.getText(); // returns (String) text from the user inputed text box
    }

    public void setStartGameListener(ActionListener listener) {
        startButton.addActionListener(listener); // this is so the program knows that the user pressed the button and wants to start playing. 
    }
    //toString 
    @Override
    public String toString() {
        return "UserNamePanel [Username: " + getUsername() + 
               ", Start Button Enabled: " + startButton.isEnabled() + "]";
    }
    
    
}