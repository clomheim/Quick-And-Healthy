CREATE DATABASE QuickAndHealthyMeals;
USE QuickAndHealthyMeals;

CREATE TABLE User (
	username VARCHAR(45) PRIMARY KEY,
	userPassword VARCHAR(45) NOT NULL
);

CREATE TABLE UserFavorites (
	username VARCHAR(45), 
	recipeID INT,
	PRIMARY KEY (username, recipeID)
);

CREATE TABLE Recipe (
	recipeID INT PRIMARY KEY, 
	username VARCHAR(45) NOT NULL,
	recipeTitle VARCHAR(100) NOT NULL, 
	category VARCHAR(65) NOT NULL,
	prepTime TIME NOT NULL, 
	cookTime TIME NOT NULL, 
	directions TEXT NOT NULL
);

CREATE TABLE recipeIngredients (
	recipeID INT, 
	ingredientName VARCHAR(45), 
	optional BIT, 
	PRIMARY KEY (recipeID, ingredientName)
);

CREATE TABLE nutritionalInformation (
	recipeID INT PRIMARY KEY, 
	servingSize FLOAT NOT NULL, 
	servingSizeUnit VARCHAR(8) NOT NULL, 
	calories INT NOT NULL, 
	caloriesFromFat INT NOT NULL,
	saturatedFat INT NOT NULL, 
	cholesterol INT NOT NULL, 
	sodium INT NOT NULL, 
	totalCarbohydrates INT NOT NULL, 
	dietaryFiber INT NOT NULL, 
	sugars INT NOT NULL, 
	protein INT NOT NULL, 
	vitaminA INT, 
	vitaminC INT, 
	calcium INT, 	
	iron INT
);