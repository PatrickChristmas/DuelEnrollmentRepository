package pokerGame;


/**
 * stores a username and password
 * @author Mrs. Kelly
 */
public class User {
	
	/**
	 * username
	 */
	
	private String user;
	/**
	 * password
	 */private String password;

	/**
	 * standard constructor
	 * @param user username
	 * @param password password
	 */
	public User(String user, String password) {
		this.user = user;
		this.password = password;
	}

	/**
	 * standard getter
	 * @return username
	 */
	public String getUser() {
		return user;
	}

	/**
	 * standard setter
	 * @param user username
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * standard getter
	 * @return username
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * standard setter
	 * @param password password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the username and the password
	 */
	@Override
	public String toString() {
		return "[" + user + ", " + password + "]";
	}
}