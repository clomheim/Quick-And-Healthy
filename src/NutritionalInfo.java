
public class NutritionalInfo {
	private float my_servingSize;
	private int my_calories;
	private int my_caloriesFromFat; 
	private int my_saturatedFat; 
	private int my_cholesterol;
	private int my_sodium;
	private int my_totalCarbohydrates; 
	private int my_dietaryFiber;
	private int my_sugars; 
	private int my_protein; 
	private int my_vitaminA; 
	private int my_vitaminC; 
	private int my_calcium; 
	private int my_iron;
	
	public NutritionalInfo(float servingSize, int calories, int caloriesFromFat, int saturatedFat, 
							int cholesterol, int sodium, int totalCarbs, int fiber, int sugars, 
							int protein, int vitA, int vitC, int calcium, int iron) {
		my_servingSize = servingSize; 
		my_calories = calories;
		my_caloriesFromFat = caloriesFromFat; 
		my_saturatedFat = saturatedFat; 
		my_cholesterol = cholesterol; 
		my_sodium = sodium;
		my_totalCarbohydrates = totalCarbs; 
		my_dietaryFiber = fiber; 
		my_sugars = sugars; 
		my_protein = protein;
		my_vitaminA = vitA;
		my_vitaminC = vitC;
		my_calcium = calcium; 
		my_iron = iron;
	}
}
