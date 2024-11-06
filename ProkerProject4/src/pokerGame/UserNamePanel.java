package pokerGame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

public class UserNamePanel extends JPanel {
    private JTextField usernameField;
    private JButton startButton;
    private Color pokerGreenColor;
    private JRadioButton thousandDollarButton;
    private JRadioButton twoThousandDollarButton;
    private JRadioButton threeThousandDollarButton;
    private ButtonGroup dollarButtonGroup;
    private int selectedAmount;

    public UserNamePanel() {
        pokerGreenColor = new Color(53, 101, 77);
        setLayout(new BorderLayout());
        setBackground(pokerGreenColor);

        // Create top and bottom navy blue panels
        Color navy = new Color(25, 25, 112);
        JPanel topPanel = new JPanel();
        topPanel.setBackground(navy);
        topPanel.setPreferredSize(new Dimension(0, 100));
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(navy);
        bottomPanel.setPreferredSize(new Dimension(0, 100));
        add(bottomPanel, BorderLayout.SOUTH);

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Pat's Poker Palace", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Egyptienne", Font.BOLD, 60));
        welcomeLabel.setForeground(Color.RED);
        topPanel.add(welcomeLabel);

        // Input panel with GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(pokerGreenColor);
        GridBagConstraints layoutOrder = new GridBagConstraints();
        layoutOrder.insets = new Insets(5, 10, 10, 10);
        layoutOrder.fill = GridBagConstraints.HORIZONTAL;

        // Username label
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

        // Play button
        startButton = new JButton("Play");
        startButton.setFont(new Font("Egyptienne", Font.BOLD, 30));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(Color.BLACK);
        layoutOrder.gridx = 2;
        inputPanel.add(startButton, layoutOrder);

        // Initialize dollar button group and buttons
        dollarButtonGroup = new ButtonGroup();
        thousandDollarButton = new JRadioButton("Start with $1000");
        twoThousandDollarButton = new JRadioButton("Start with $2000");
        threeThousandDollarButton = new JRadioButton("Start with $3000");

        // Configure button appearance
        configureRadioButton(thousandDollarButton);
        configureRadioButton(twoThousandDollarButton);
        configureRadioButton(threeThousandDollarButton);

        // Add buttons to group
        dollarButtonGroup.add(thousandDollarButton);
        dollarButtonGroup.add(twoThousandDollarButton);
        dollarButtonGroup.add(threeThousandDollarButton);

        // Set default selection
     //   thousandDollarButton.setSelected(true); // Set initial selection manually
       // selectedAmount = 1000; // Start with $1000 as default

        // Add ActionListener to the ButtonGroup
        ActionListener selectionListener = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                updateSelectedAmount(); // Ensure update on selection change
                // Debugging log to track the selected amount change
                System.out.println("Selected Amount inside ActionListener (after change): " + selectedAmount);
            }
        };

        // Add ActionListener to each button
        thousandDollarButton.addActionListener(selectionListener);
        twoThousandDollarButton.addActionListener(selectionListener);
        threeThousandDollarButton.addActionListener(selectionListener);

        // Add buttons to input panel
        layoutOrder.gridy = 1;
        inputPanel.add(thousandDollarButton, layoutOrder);
        layoutOrder.gridy++;
        inputPanel.add(twoThousandDollarButton, layoutOrder);
        layoutOrder.gridy++;
        inputPanel.add(threeThousandDollarButton, layoutOrder);

        // Add input panel to the center of UserNamePanel
        add(inputPanel, BorderLayout.CENTER);
    }

    private void configureRadioButton(JRadioButton button) {
        button.setFont(new Font("Egyptienne", Font.BOLD, 30));
        button.setForeground(Color.WHITE);
        button.setBackground(pokerGreenColor);
    }

    // Ensure selectedAmount is up-to-date when requested
    public int getSelectedAmount() {
        // This method will guarantee selectedAmount is updated
        updateSelectedAmount(); // Always update the value before returning
        System.out.println("Selected Amount in getSelectedAmount() (final check): " + selectedAmount);
        return selectedAmount;
    }
    
    
    // DOES NOT WORK! it actually updates the selected amount, however it reverts back to 1000 by the time the panel switches back
    // uses teriary opearted to assign the number to check if the conditions are true, if none then it assigns 0.
    private void updateSelectedAmount() {
    	selectedAmount = thousandDollarButton.isSelected() ? 1000 : (twoThousandDollarButton.isSelected() ? 2000 : (threeThousandDollarButton.isSelected() ? 3000 : 0));

    }
    
    // gets the username text 
    public String getUsername() {
        return usernameField.getText();
    }
    
    // setter for adding  listeners to the startButton
    public void setStartGameListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    @Override
    public String toString() {
        return "UserNamePanel [Username: " + getUsername() +
               ", Start Button Enabled: " + startButton.isEnabled() + "]";
    }
}