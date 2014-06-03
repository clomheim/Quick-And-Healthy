/** 
 * User information. 
 * @author Erica
 * @version 6/2/2014
 */
public class User {
	private String my_username; 
	private String my_password;
	
	public User(String username, String password) {
		my_username = username; 
		my_password = password; 
	}
	
	public String getUsername() {
		return my_username;
	}
	
	public String getPassword() {
		return my_password;
	}
}
