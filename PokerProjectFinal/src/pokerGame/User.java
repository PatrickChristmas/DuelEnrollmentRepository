package pokerGame;

/**
 * Represents a user with a username, password, and a current money value.
 * The currentMoney field can be set (for example, using another class’s getCurrentMoney method).
 * Also tracks wins and losses.
 * 
 * @author Mrs. Kelly
 */
public class User {
    
	/**
	 * The username  with the player
	 */
	private String user;

	/**
	 * The password  with the player
	 */
	private String password;

	/**
	 * current amount of money the player has in game
	 */
	private int currentMoney;

	/**
	 *  total number of games  player has won
	 */
	private int wins;

	/**
	 * total number of games the player  lost
	 */
	private int losses;

	/**
	 * players current elo rating, used for ranking on the leaderboard.
	 */
	private int elo;

	/**
	 *  players inventory, containing owned items .
	 */
	private Inventory inventory; 

    /**
     * Constructor that sets the username, password, and current money.
     * 
     * @param user username
     * @param password password
     * @param currentMoney the starting/current money for the user
     * @param wins number of games won
     * @param losses number of games lost
     */
    public User(String user, String password, int currentMoney, int wins, int losses, int elo) {
        this.user = user;
        this.password = password;
        this.currentMoney = currentMoney;
        this.wins = wins;
        this.losses = losses;
        this.elo = elo;
        this.inventory = new Inventory(); 
    }
    
    /**
     * Constructs a new User object with the username, password, money, wins, and losses.
     * Sets the initial elo rating to 1000 by default
     * @param user         the username of the user
     * @param password     the users password
     * @param currentMoney  amount of money the user  has
     * @param wins          number of games the user has won
     * @param losses        number of games the user has lost
     */
    public User(String user, String password, int currentMoney, int wins, int losses) {
        this(user, password, currentMoney, wins, losses, 1000);
    }

    /**
     * returns the users inventory, which includes owned items 
     * @return the inventory of the user
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Sets the users inventory to the specified Inventory object.
     * @param inventory the inventory to assign to the user
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    
    
    
    /**
     * Overloaded constructor that defaults currentMoney to 0, wins and losses to 0.
     * @param user username
     * @param password password
     */
    public User(String user, String password) {
        this(user, password, 0, 0, 0);
    }
    
    /**
     * returns the username associated with this user
     * @return the username
     */
    public String getUser() {
        return user;
    }

    /**
     * sets the username for this user
     * @param user the new username to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * returns the password associated with this user.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets the password for this user.
     * @param password the new password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * returns the current money value.
     * @return the current money as an integer.
     */
    public int getCurrentMoney() {
        return currentMoney;
    }
    
    /**
     * sets the current money value
     * @param currentMoney the new current money value
     */
    public void setCurrentMoney(int currentMoney) {
        this.currentMoney = currentMoney;
    }

    /**
     * returns the number of wins
     * @return wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * sets the number of wins
     * @param wins the new win count
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * returns the number of losses
     * @return losses
     */
    public int getLosses() {
        return losses;
    }

    /**
     * sets the number of losses.
     * @param losses the new loss count
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }
    
    
    
    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
    
    
    
    /**
     * Returns a string representation of the user including the current money, wins, and losses.
     */
    @Override
    public String toString() {
        return "[" + user + ", " + password + ", " + currentMoney + ", " + wins + ", " + losses + "]";
    }
} 
