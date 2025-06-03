package pokerGame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JPasswordField; 
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author PatrickChristmas
 * @version May 30 2025.
 * The UserSetUp class provides a user interface for user login. It allows users to input
 * a username and password, and saving it to a file.
 * This panel includes fields for the username, password, an error message label, and a login button.
 * It ensures that the passowrd contains at least one capital letter and one number, and it prevents 
 * duplicate usernames by checking against existing usernames in the text file .
 */
public class UserSetUp extends JPanel {

    private JTextField usernameField; // TextField for username input
    private JPasswordField passwordField; // PasswordField for password input
    private JButton loginButton; // Button to trigger the login action
    private JLabel errorLabel; // Label to display error messages
    private JButton togglePasswordButton; // Button to toggle password visibility

    /**
     * Constructor that sets up the UserSetUp panel with all the components.
     * The panel includes fields for entering a username and password, and a login button.
     */
    public UserSetUp() {
        setLayout(new GridBagLayout()); 
        setBackground(new Color(34, 49, 63)); // navy blue 

        GridBagConstraints gbc = new GridBagConstraints(); 
        gbc.insets = new Insets(10, 10, 10, 10); // Sets border around components
        gbc.fill = GridBagConstraints.HORIZONTAL; 

        // Title label
        JLabel titleLabel = new JLabel("Welcome to Patrick's Poker Palace", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36)); 
        titleLabel.setForeground(new Color(236, 240, 241)); // light gray
        gbc.gridx = 0; // 0th column
        gbc.gridy = 0; // 0th row
        gbc.gridwidth = 2; 
        add(titleLabel, gbc); // Adds the title to the panel

        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Serif", Font.BOLD, 24));
        usernameLabel.setForeground(new Color(189, 195, 199)); // light gray
        gbc.gridx = 0; // 0th column
        gbc.gridy = 1; // 1st row
        gbc.gridwidth = 1; 
        add(usernameLabel, gbc); // Adds the username label to the panel

        // Username text field
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Serif", Font.PLAIN, 20)); 
        gbc.gridx = 1; // 1st column
        add(usernameField, gbc); // Adds the username text field to the panel

        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Serif", Font.BOLD, 24)); 
        passwordLabel.setForeground(new Color(189, 195, 199)); // light gray
        gbc.gridx = 0; // 0th column
        gbc.gridy = 2; // 2nd row
        add(passwordLabel, gbc); // Adds the password label to the panel
         // password visibility toggle button
        togglePasswordButton = new JButton("Show");
        togglePasswordButton.setFont(new Font("Serif", Font.PLAIN, 14));
        togglePasswordButton.setFocusPainted(false);
        gbc.gridx = 2; // next to the password field
        gbc.gridy = 2;
        add(togglePasswordButton, gbc);

        togglePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordField.getEchoChar() != '\u0000') {
                    passwordField.setEchoChar('\u0000'); // Show password
                    togglePasswordButton.setText("Hide");
                } else {
                    passwordField.setEchoChar((Character) UIManager.get("PasswordField.echoChar")); // Hide password
                    togglePasswordButton.setText("Show");
                }
            }
        });
        
        // Password field
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Serif", Font.PLAIN, 20)); 
        gbc.gridx = 1; // 1st column
        add(passwordField, gbc); // Adds password field to the panel

        // Error label
        errorLabel = new JLabel(" ", JLabel.CENTER);
        errorLabel.setFont(new Font("Serif", Font.ITALIC, 18)); 
        errorLabel.setForeground(Color.RED); 
        gbc.gridx = 0; // 0th column
        gbc.gridy = 3; // 3rd row
        gbc.gridwidth = 2; 
        add(errorLabel, gbc); // Adds error label to the panel

        // Login button setup
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Serif", Font.BOLD, 24)); 
        loginButton.setBackground(new Color(52, 152, 219)); // light blue 
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false); // Removes the focus outline on button press
        gbc.gridx = 0; // 0th column
        gbc.gridy = 4; // 4th row
        gbc.gridwidth = 2; 
        add(loginButton, gbc); // Adds the login button to the panel

        // ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin(); // Checks if it is a valid username
            }
        });
    }

    /**
     * Handles the login action. It validates the username and password entered by the user, 
     * and if the username is valid, writes them to a text file.
     */
    private void handleLogin() {
        String username = usernameField.getText(); 
        String password = new String(passwordField.getPassword()); 
        UserRegistration userReg = new UserRegistration(); 

        // Checks if username or password is empty
        if (username.isEmpty() || password.isEmpty()) {
            setErrorMessage("Please enter both a username and password."); // Show error message
            return;
        }

        // Checks if it is a valid password (must contain a capital letter and a number)
        if (!isPasswordValid(password)) {
            setErrorMessage("Password must contain at least one capital letter and one number.");
            return;
        }

        // Checks if the username is already taken (in the text file)
        if (userReg.isUsernameTaken(username)) {
            setErrorMessage("Username already taken. Please enter a new username.");
            return;
        }

        // Writes the user information into the text file if valid
        printUserInfoIntoTextFile(username, password);
        setErrorMessage(""); // Clears error message 
    }

    /**
     *  Checks if the username contains at least one capital letter and one number.
     * 
     * @param username The username to be checked.
     * @return True if the username contains a capital letter and a number, false if not.
     */
    private boolean isPasswordValid(String password) {
        boolean hasCapital = false;
        boolean hasNumber = false;

        // Checks if the username contains at least one uppercase letter and one digit
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true; 
            }
            if (Character.isDigit(c)) {
                hasNumber = true; 
            }
            if (hasCapital && hasNumber) {
                return true;
            }
        }
        return false; 
    }

    /**
     * Writes the username and password onto a text file.
     * 
     * @param username
     * @param password 
     */
    private void printUserInfoIntoTextFile(String username, String password) {
        File file = new File("TextFileFolder/Text.txt"); 
        try { 
            FileWriter fileWriter = new FileWriter(file, true); // Open file in append mode
            PrintWriter writer = new PrintWriter(fileWriter); // Creates a PrintWriter object for writing

            writer.println(username); // Writes the username to file
            writer.println(password); // Writes the password to file
            writer.println("1000"); // starting money
            writer.println("0");    // wins
            writer.println("0");    // losses
            writer.println("1000"); // starting Elo
            writer.close(); // Closes and saves the file
            System.out.println("User data has been written to the file."); 
        } catch (IOException e) {
            System.out.println("Error writing to file."); 
        }
    }

    /**
     * Gets the entered username.
     * 
     * @return The username 
     */
    public String getUsername() {
        return usernameField.getText(); 
    }

    /**
     * Sets the error message to be displayed
     * 
     * @param message, The error message
     */
    public void setErrorMessage(String message) {
        errorLabel.setText(message); 
    }

    /**
     * Gets the entered password
     * 
     * @return The password 
     */
    public String getPassword() {
        return new String(passwordField.getPassword()); // Returns the password as a string
    }

    /**
     * Sets a listener for the login button
     * 
     * @param listener, the ActionListener to be added to the login button.
     */
    public void setLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener); 
    }

    /**
     * 
     * @return A string of the username, password, and error message (if there is one) 
     */
    @Override
    public String toString() {
        return "UserSetUp{" +
               "username: " + usernameField.getText() + ", " +
               "password: " + new String(passwordField.getPassword()) + ", " +
               "errorMessage: " + errorLabel.getText() +
               '}';
    }
}