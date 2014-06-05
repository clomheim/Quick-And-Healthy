
public class NutritionalInfo {
	private float my_servingSize;
	private String my_servingSizeUnit;
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
	
	public NutritionalInfo(float servingSize, String servingSizeUnit, int calories, int caloriesFromFat, int saturatedFat, 
							int cholesterol, int sodium, int totalCarbs, int fiber, int sugars, 
							int protein, int vitA, int vitC, int calcium, int iron) {
		my_servingSize = servingSize;
		my_servingSizeUnit = servingSizeUnit;
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
	
	public float getServingSize(){
		return my_servingSize;
	}
	
	public String getServingSizeUnit(){
		return my_servingSizeUnit;
	}

	public int getCalories() {
		return my_calories;
	}

	public int getCaloriesFromFat() {
		return my_caloriesFromFat;
	}

	public int getSaturatedFat() {
		return my_saturatedFat;
	}

	public int getCholesterol() {
		return my_cholesterol;
	}

	public int getSodium() {
		return my_sodium;
	}

	public int getTotalCarbohydrates() {
		return my_totalCarbohydrates;
	}

	public int getDietaryFiber() {
		return my_dietaryFiber;
	}

	public int getSugars() {
		return my_sugars;
	}

	public int getProtein() {
		return my_protein;
	}

	public int getVitaminA() {
		return my_vitaminA;
	}

	public int getVitaminC() {
		return my_vitaminC;
	}

	public int getCalcium() {
		return my_calcium;
	}

	public int getIron() {
		return my_iron;
	}
}
