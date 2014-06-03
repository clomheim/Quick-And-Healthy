import java.util.Date;
import java.util.List;

public class Recipe {
	private int my_recipeID;
	private String my_recipeTitle;
	private String my_category; 
	private Date my_prepTime;
	private Date my_cookTime;
	private List<String> my_ingredients;
	private NutritionalInfo my_nutritionalInfo;
	
	public Recipe(int recipeID, String recipeTitle, String category, Date prepTime, 
					Date cookTime, List<String> ingredients, NutritionalInfo nutritionalInfo) {
		my_recipeID = recipeID; 
		my_category = category;
		my_prepTime = prepTime; 
		my_cookTime = cookTime; 
		
		for (String s : ingredients) {
			my_ingredients.add(s);
		}
		
		my_nutritionalInfo = nutritionalInfo;
	}
}
