package pokerGame;

/**
 * Represents a user with a username, password, and a current money value.
 * The currentMoney field can be set (for example, using another class’s getCurrentMoney method).
 * Also tracks wins and losses.
 * 
 * @author Mrs. Kelly
 */
public class User {
    
    private String user;
    private String password;
    private int currentMoney; // New field to store the current money
    private int wins;         // Number of games won
    private int losses;       // Number of games lost

    /**
     * Constructor that sets the username, password, and current money.
     * 
     * @param user username
     * @param password password
     * @param currentMoney the starting/current money for the user
     * @param wins number of games won
     * @param losses number of games lost
     */
    public User(String user, String password, int currentMoney, int wins, int losses) {
        this.user = user;
        this.password = password;
        this.currentMoney = currentMoney;
        this.wins = wins;
        this.losses = losses;
    }
    
    /**
     * Overloaded constructor that defaults currentMoney to 0, wins and losses to 0.
     * 
     * @param user username
     * @param password password
     */
    public User(String user, String password) {
        this(user, password, 0, 0, 0);
    }
    
    public String getUser() {
        return user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Returns the current money value.
     * 
     * @return the current money as an integer.
     */
    public int getCurrentMoney() {
        return currentMoney;
    }
    
    /**
     * Sets the current money value.
     * 
     * @param currentMoney the new current money value.
     */
    public void setCurrentMoney(int currentMoney) {
        this.currentMoney = currentMoney;
    }

    /**
     * Returns the number of wins.
     * 
     * @return wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * Sets the number of wins.
     * 
     * @param wins the new win count
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * Returns the number of losses.
     * 
     * @return losses
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Sets the number of losses.
     * 
     * @param losses the new loss count
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }
    
    /**
     * Returns a string representation of the user including the current money, wins, and losses.
     */
    @Override
    public String toString() {
        return "[" + user + ", " + password + ", " + currentMoney + ", " + wins + ", " + losses + "]";
    }
} 
