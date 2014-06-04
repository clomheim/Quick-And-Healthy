import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
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
		try{
			 Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e){
			System.err.println(e);
		}
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
	
		conn = DriverManager.getConnection("jdbc:" + "mysql" + "://"
				+ serverName + "/", connectionProps);
	
		System.out.println("Connected to database");
	}
	
	/**
	 * Gets a list of all Recipes from the Database.
	 * 
	 * @author Claire
	 * @return
	 * @throws SQLException
	 */
	public List<Recipe> getRecipes() throws SQLException {
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select recipeID, recipeTitle, category, prepTime, cookTime, directions "
				+ "from quickandhealthymeals.recipe ";

		recipes = new ArrayList<Recipe>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = rs.getInt("recipeID");
				String title = rs.getString("recipeTitle");
				String cat = rs.getString("category");
				Time prep = rs.getTime("prepTime");
				Time cook = rs.getTime("cookTime");
				List<String> ingr = getIngredients(id);
				String dir = rs.getString("directions");
				NutritionalInfo ni = getNutInfo(id);
				Recipe rec = new Recipe(id, title, cat, prep, cook, ingr, dir, ni);
				recipes.add(rec);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return recipes;
	}
	
	/**
	 * Gets the list of ingredients associated with a particular Recipe.
	 * 
	 * @author Claire
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	private List<String> getIngredients(int id) throws SQLException{
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select ingredientName "
				+ "from quickandhealthymeals.recipeingredients "
				+ "where recipeID = " + id;
		
		List<String> ingr = new  ArrayList<String>();
		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				ingr.add(rs.getString("ingredientName"));
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}
		
		return ingr;
	}
	/**
	 * Gets the Nutritional Information for a particular recipe.
	 * 
	 * @author Claire
	 * @param id
	 * @return
	 * @throws SQLException 
	 */
	private NutritionalInfo getNutInfo(int id) throws SQLException{
		if (conn == null) {
			createConnection();
		}
		Statement stmt = null;
		String query = "select * "
				+ "from quickandhealthymeals.nutritionalinformation "
				+ "where recipeID = " + id;
		NutritionalInfo ni = null;
		try{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			float servSiz = rs.getFloat("servingSize");
			String uni = rs.getString("servingSizeUnit");
			int cal = rs.getInt("calories");
			int calFromFat = rs.getInt("caloriesFromFat");
			int satFat = rs.getInt("saturatedFat");
			int chol = rs.getInt("cholesteral");
			int sod = rs.getInt("sodium");
			int carb = rs.getInt("totalCarbohydrates");
			int fib = rs.getInt("dietaryFiber");
			int sug = rs.getInt("sugars");
			int prot = rs.getInt("proein");
			int vitA = rs.getInt("vitaminA");
			int vitC = rs.getInt("vitaminC");
			int calc = rs.getInt("calcium");
			int iron = rs.getInt("iron");
			ni = new NutritionalInfo(servSiz, uni, cal, calFromFat, satFat, chol, sod, carb, fib, sug, prot, vitA, vitC, calc, iron);
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}
		
		return ni;
	}
	
	/** 
	 * Adds a recipe to the database.
	 * @param user The user to whom the recipe belongs.
	 * @param recipe The recipe to be added.
	 * @throws SQLException
	 * @author Erica
	 */
	public static void addRecipe(User user, Recipe recipe) throws SQLException {
		if (conn == null) {
			createConnection();
		}
		String sql = "INSERT INTO Recipe VALUES (?, ?, ?, ?, ?, ?, ?, NULL)";
		
		PreparedStatement prepStatement = null; 
		
		try {
			prepStatement = conn.prepareStatement(sql);
			prepStatement.setInt(1, recipe.getRecipeID());
			incrementRecipeID();
			prepStatement.setString(2, user.getUsername());
			prepStatement.setString(3, recipe.getRecipeTitle());
			prepStatement.setString(4,  recipe.getCategory());
			prepStatement.setTime(5, recipe.getPrepTime());
			prepStatement.setTime(6, recipe.getCookTime());
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds an ingredient to the recipe.
	 * @param recipe The recipe that the ingredient is being added to.
	 * @param ingredient The ingredient to add to the recipe.
	 * @throws SQLException
	 * @author Erica
	 */
	public static void addIngredientToRecipe(Recipe recipe, String ingredient) throws SQLException {
		if (conn == null) {
			createConnection();
		}
		String sql = "INSERT INTO recipeIngredients VALUES (?, ?, NULL)";
		
		PreparedStatement prepStatement = null; 
		
		try {
			prepStatement = conn.prepareStatement(sql);
			prepStatement.setInt(1,  recipe.getRecipeID());
			prepStatement.setString(2, ingredient);
 		} catch (SQLException e) {
 			System.out.println(e);
			e.printStackTrace();	
 		}
	}
	
	private static void incrementRecipeID() {
		recipe_id++;
	}
	
	/**
	 * Checks whether or not a user exists in the database.
	 * @param user The user to check for.
	 * @return True if the user was found, false otherwise.
	 */
	public static boolean userExists(User user) throws SQLException {
		if (conn == null) {
			createConnection();
		}
		boolean found = false;
		
		String query = "SELECT username FROM User WHERE username = " 
						+  user.getUsername() + ";";
 
		Statement statement = null;
		
		try {
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			String name = rs.getString("username");
			
			if (name != null) {
				found = true;
			}
			
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		return found;
	}
	
	/**
	 * Compares the user-input password against the password that user has on their account.
	 * @param user The user who is trying to login.
	 * @param password The password the user entered at login.
	 * @return True if the user successfully logs in, false otherwise.
	 * @throws SQLException
	 * @author Erica
	 */
	public boolean loginValidation(User user, String password) throws SQLException {
		if (conn == null) {
			createConnection();
		}
		
		boolean matchFound = false;
		
		String query = "SELECT userPassword FROM User WHERE username = " 
					+ user.getUsername() +";";
		
		Statement statement = null;
		
		try {
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			String pass = rs.getString("userPassword");
			
			if (pass.equals(user.getPassword())) {
				matchFound = true;
			}
			
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		return matchFound;
	}
}