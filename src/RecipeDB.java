import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
				Date prep = rs.getDate("prepTime");
				Date cook = rs.getDate("cookTime");
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