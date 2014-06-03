import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.List;

/**
 * Connects the recipe object to the recipe table in the database.
 * @author Erica
 * @version 06/2/2014
 */
public class RecipeDB {
	private static String userName = "root";
	private static String password = "";
	private static String serverName = "localhost";
	private static Connection conn;
	private List<Recipe> recipes;
	private static int recipe_id = 1000;
	
	/**
	 * Creates a connection to the local database.
	 * @throws SQLException
	 */
	public static void createConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
	
		conn = DriverManager.getConnection("jdbc:" + "mysql" + "://"
				+ serverName + "/", connectionProps);
	
		System.out.println("Connected to database");
	}
	
	public static void addRecipe(User user, Recipe recipe) {
		String sql = "INSERT INTO Recipe VALUES (?, ?, ?, ?, ?, ?, ?, NULL)";
		
		PreparedStatement prepStatement = null; 
		
		try {
			prepStatement = conn.prepareStatement(sql);
			prepStatement.setInt(1, recipe_id);
			incrementRecipeID();
			prepStatement.setString(2, user.getUsername());
			prepStatement.setString(3, recipe.getRecipeTitle());
			prepStatement.setString(4,  recipe.getCategory());
			// TODO finish this.
		} catch (SQLException e) {
			
		}
	}
	
	public static void incrementRecipeID() {
		recipe_id++;
	}
}