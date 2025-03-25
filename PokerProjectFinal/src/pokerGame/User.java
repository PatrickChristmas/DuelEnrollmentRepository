package pokerGame;

/**
 * Represents a user with a username, password, and a current money value.
 * The currentMoney field can be set (for example, using another class’s getCurrentMoney method).
 * 
 * @author Mrs. Kelly
 */
public class User {
    
    private String user;
    private String password;
    private int currentMoney; // New field to store the current money

    /**
     * Constructor that sets the username, password, and current money.
     * 
     * @param user username
     * @param password password
     * @param currentMoney the starting/current money for the user
     */
    public User(String user, String password, int currentMoney) {
        this.user = user;
        this.password = password;
        this.currentMoney = currentMoney;
    }
    
    /**
     * Overloaded constructor that defaults currentMoney to 0.
     * 
     * @param user username
     * @param password password
     */
    public User(String user, String password) {
        this(user, password, 0);
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
     * Returns a string representation of the user including the current money.
     */
    @Override
    public String toString() {
        return "[" + user + ", " + password + ", " + currentMoney + "]";
    }
}
