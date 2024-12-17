package pokerGame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
// import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class UserNamePanel extends JPanel {
    private JTextField usernameField;
    private JButton startButton;
    private Color pokerGreenColor;
    private JRadioButton thousandDollarButton;
    private JRadioButton twoThousandDollarButton;
    private JRadioButton threeThousandDollarButton;
    private ButtonGroup dollarButtonGroup;
    private static int selectedAmount;
    private Image PokerPanelBackground;

    public UserNamePanel() {
       // pokerGreenColor = new Color(53, 101, 77);
      //  setLayout(new BorderLayout());
        
        // creates top and bottom navy blue panels
       // Color navy = new Color(25, 25, 112);
        
        JPanel topPanel = new JPanel();
        // adds the panel to the top of the border layout 
        add(topPanel, BorderLayout.NORTH);

        // creates the bottom as navy
        JPanel bottomPanel = new JPanel();
         add(bottomPanel, BorderLayout.SOUTH);

        // Welcome label
      //  JLabel welcomeLabel = new JLabel("Welcome to Pat's Poker Palace", JLabel.CENTER);
      //  welcomeLabel.setFont(new Font("Egyptienne", Font.BOLD, 60));
       // welcomeLabel.setForeground(Color.RED);

        // Input panel using GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(pokerGreenColor);
        GridBagConstraints layoutOrder = new GridBagConstraints();
        
     // specifies the margins around the component
        layoutOrder.insets = new Insets(10, 10, 10, 10); 
        layoutOrder.fill = GridBagConstraints.HORIZONTAL;

        // creates the Username label
     JLabel inputLabel = new JLabel("Input Username: ");
       inputLabel.setFont(new Font("Egyptienne", Font.BOLD, 40));
       inputLabel.setForeground(Color.WHITE);
        layoutOrder.gridx = 0;
       layoutOrder.gridy = 0;
        inputPanel.add(inputLabel, layoutOrder);

        // Username text field
        usernameField = new JTextField(15);
        usernameField.setToolTipText("Enter your username");
        layoutOrder.gridx = 1;
        inputPanel.add(usernameField, layoutOrder);

        // starts the game, 'Play' button
       startButton = new JButton("Play");
        startButton.setFont(new Font("Egyptienne", Font.BOLD, 30));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(Color.BLACK);
        layoutOrder.gridx = 2;
        inputPanel.add(startButton, layoutOrder);

        // Initializes dollar button group and buttons
        dollarButtonGroup = new ButtonGroup();
        thousandDollarButton = new JRadioButton("Start with $1000");
        twoThousandDollarButton = new JRadioButton("Start with $2000");
       threeThousandDollarButton = new JRadioButton("Start with $3000");

        // configure button appearance
        configureRadioButton(thousandDollarButton);
       configureRadioButton(twoThousandDollarButton);
        configureRadioButton(threeThousandDollarButton);

        // Adds the buttons to group
       dollarButtonGroup.add(thousandDollarButton);
        dollarButtonGroup.add(twoThousandDollarButton);
       dollarButtonGroup.add(threeThousandDollarButton);

        //load the background image
        imageLoader();
      
        // Add ActionListener to the ButtonGroup
        ActionListener selectionListener = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                updateSelectedAmount();
            }
        };

        // Adds the ActionListener to each button
        thousandDollarButton.addActionListener(selectionListener);
        twoThousandDollarButton.addActionListener(selectionListener);
        threeThousandDollarButton.addActionListener(selectionListener);

        // Adds the buttons to input panel
       layoutOrder.gridy = 1;
        inputPanel.add(thousandDollarButton, layoutOrder);
        layoutOrder.gridy++;
        inputPanel.add(twoThousandDollarButton, layoutOrder);
        layoutOrder.gridy++;
        inputPanel.add(threeThousandDollarButton, layoutOrder);

        // Adds the input panel to the center of UserNamePanel
        add(inputPanel, BorderLayout.CENTER);
    }

    private void imageLoader() {
        // matches each file with their image
        File file1 = new File("Images/PokerPanelBackground.PNG");
        System.out.println("Images Loaded");

        try {
            // reads the actual image into the File variable
            PokerPanelBackground = ImageIO.read(file1).getScaledInstance(1500, 1500, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("PokerPanelBackground not found.");
            PokerPanelBackground = null;
        }
    }
    // checks if the pokerPanelBackground is null, if not, it can be drawn
    private void drawPokerPanelBackground(Graphics g) {
        if (PokerPanelBackground != null) {
            g.drawImage(PokerPanelBackground, 0, 0, this);           
        }             
    }
    // paint component to actually draw the PokerPanelBackground
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
      
        drawPokerPanelBackground(g);

    }

    private void configureRadioButton(JRadioButton button) {
        button.setFont(new Font("Egyptienne", Font.BOLD, 30));
        button.setForeground(Color.WHITE);
        button.setBackground(pokerGreenColor);
    }

    // getter for selected amount 
    public int getSelectedAmount() {
        return selectedAmount;
    }
    // updates selected amount when chosen
    private void updateSelectedAmount() {
        selectedAmount = thousandDollarButton.isSelected() ? 1000 :
                         (twoThousandDollarButton.isSelected() ? 2000 :
                         (threeThousandDollarButton.isSelected() ? 3000 : 0));
    }

    // gets the username text
    public String getUsername() {
        return usernameField.getText();
    }

    // setter for adding listeners to the startButton
    public void setStartGameListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

 //   @Override
    public String toString() {
       return "UserNamePanel [Username: " + getUsername() +
               ", Start Button Enabled: " + startButton.isEnabled() + "]";
    }
}