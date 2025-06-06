package pokerGame;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;


/**
 * The UserNamePanel class represents the initial user GUI for the poker game,
 */
public class SettingsPanel extends JPanel {

    private GradientButton playButton;                  // Button to start the game
    private JRadioButton thousandDollarButton;            // Radio button for $1000 starting amount
    private JRadioButton twoThousandDollarButton;           // Radio button for $2000 starting amount
    private JRadioButton threeThousandDollarButton;         // Radio button for $3000 starting amount

    private ButtonGroup dollarButtonGroup;                // Group that manages the radio buttons
    private static int selectedAmount;                    // The selected starting amount for the player

    /**
     * Sets up the layout and initializes components with styling and gradients.
     */
    public SettingsPanel() {
        // use GridBagLayout for flexible positioning
        setLayout(new GridBagLayout());
        // Make the panel non opaque so our custom gradient shows through
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title label with a casino style look
        JLabel welcomeLabel = new JLabel("Let's Play Poker", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 48));
        welcomeLabel.setForeground(new Color(255, 215, 0)); // Gold text
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(new Color(0, 0, 0, 150)); // Semi transparent black
        welcomeLabel.setBorder(new RoundedBorder(Color.BLACK, 5)); // Rounded border
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(welcomeLabel, gbc);

        

        // Create the "Play" button using   GradientButton
        playButton = new GradientButton("Play");
        playButton.setBackground(new Color(128, 0, 0)); // Maroon color
        playButton.setForeground(Color.WHITE);
        playButton.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(playButton, gbc);

        

        // creates an ActionListener for the radio buttons to update the selected amount.
        ActionListener selectionListener = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                updateSelectedAmount();
            }
        };
        // Radio buttons for starting money options
        dollarButtonGroup = new ButtonGroup();
        thousandDollarButton = new JRadioButton("Start with $1000");
        twoThousandDollarButton = new JRadioButton("Start with $2000");
        threeThousandDollarButton = new JRadioButton("Start with $3000");

        // Add action listeners to update the selected amount when a radio button is chosen
        thousandDollarButton.addActionListener(selectionListener);
        twoThousandDollarButton.addActionListener(selectionListener);
        threeThousandDollarButton.addActionListener(selectionListener);

        // configures the appearance of the radio buttons
        configureRadioButton(thousandDollarButton);
        configureRadioButton(twoThousandDollarButton);
        configureRadioButton(threeThousandDollarButton);

        // Group the radio buttons so only one can be selected at a time
        dollarButtonGroup.add(thousandDollarButton);
        dollarButtonGroup.add(twoThousandDollarButton);
        dollarButtonGroup.add(threeThousandDollarButton);

        // add the radio buttons to the panel
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
     * Configures the appearance of a radio button with updated colors and a rounded border
     * @param button The radio button to change
     */
    private void configureRadioButton(JRadioButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(210, 180, 140)); // Tan color for contrast
        button.setBorder(new RoundedBorder(Color.DARK_GRAY, 3));
        button.setFocusPainted(false);
    }

    /**
     * Updates the selected amount based on the selected radio button
     */
    private void updateSelectedAmount() {
        selectedAmount = thousandDollarButton.isSelected() ? 1000 :
                         (twoThousandDollarButton.isSelected() ? 2000 :
                         (threeThousandDollarButton.isSelected() ? 3000 : 0));
    }

    /**
     * getter for the selected starting amount.
     *
     * @return The selected amount.
     */
    public int getSelectedAmount() {
        return selectedAmount;
    }

    /**
     * sets the action listener for the Play button.
     *
     * @param listener The action listener to add to the Play button.
     */
    public void setStartGameListener(ActionListener listener) {
        playButton.addActionListener(listener);
    }

    

    
    /**
     * Overrides paintComponent to draw a gradient background.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // paints a custom gradient background 
        Graphics2D g2d = (Graphics2D) g.create();
        int w = getWidth();
        int h = getHeight();
        // Green gradient
        Color startColor = new Color(39, 119, 20);
        Color endColor = new Color(60, 179, 113);
        GradientPaint gp = new GradientPaint(0, 0, startColor, 0, h, endColor);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        g2d.dispose();
        super.paintComponent(g);
    }

    /**
     * A  rounded border
     * StackOverflow sample
     */
    private static class RoundedBorder implements Border {
        private Color color;
        private int radius;

        public RoundedBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(color);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    /**
     * custom JButton that paints a gradient background with rounded corners
     * StackOverflow sample
     */
    private static class GradientButton extends JButton {
        public GradientButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            // Enable anti aliasing for smoother graphics
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            Color base = getBackground();
            
            // Create a gradient from a darker version of the base color at the top
            GradientPaint gp = new GradientPaint(0, 0, base.darker(), 0, h, base.brighter());
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, w, h, 20, 20);
            g2.dispose();
            // Let the superclass paint the label (text) over our gradient (inheritance)
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            g2.dispose();
        }
    }
    
    /**
     * Returns a string containing the selected amount and the Play button's enabled status.
     */
    @Override
    public String toString() {
        return "SelectedAmount: " + selectedAmount + ", Play Button Enabled: " + playButton.isEnabled();
    }

    
    
    
}
