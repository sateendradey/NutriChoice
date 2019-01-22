package Presentation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import DataAccess.DataBaseAccess;
import Domain.Person;
import Domain.Search;

public class UIForm {

	private double value;
	private Person user;
	private String nutrient;
	static int threshold = 0;
	static ArrayList<Integer> thresh_list = new ArrayList<Integer>();
	static ArrayList<String> nutri_list = new ArrayList<String>();
	static ArrayList<Double> value_list = new ArrayList<Double>();
	private Scanner inputScan = new Scanner(System.in);

	public String getNutrient() {
		return nutrient;
	}

	public void setNutrient(String nutrient) {
		this.nutrient = nutrient;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public static void starline(int width) {
		// Utility method to draw a line full of stars
		for (int i = 0; i < width; i++)
			System.out.print("*");
		System.out.println("");
	}

	// Program Starts Here
	public void entry() {
		try (BufferedReader br = new BufferedReader(new FileReader("data/disclaimer.txt"))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Disclaimer File Not found in Data folder");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error in reading Disclaimer File");
			e.printStackTrace();
		}
		// Welcome Greeting
		System.out.println("Welcome to NutriRecipe");
		System.out.println("Your one stop shop for tasty nutritious recipes");
		System.out.println("Powered by Epicurious.com");
		starline(50);
		System.out.println("Would you like to Search by 1.Nutrient or by 2.Ingredient.(Enter 1 or 2)");
		boolean selectFlag = false;
		int selection = 1;// default selection is by Nutrient
		do {
			selection = inputScan.nextInt();
			if (selection != 1 && selection != 2) {
				System.out.println("Invalid input. Please input appropriate Choice");
				selectFlag = true;
			} else {
				selectFlag = false;
			}
		} while (selectFlag != false);
		if (selection == 1) {
			getPersonalInfo();
		} else {
			inputIngredient();
		}

	}

	public void inputIngredient() {
		System.out.println("Please enter any ingredient you would like to search by ");
		// To Skip Current Line and read from New Line
		inputScan.nextLine();
		boolean inputFlag = false;
		String ingredient = "";// default selection
		do {
			ingredient = inputScan.nextLine();
			if (ingredient == null || ingredient == "") {
				System.out.println("Invalid input. Please input appropriate Choice");
				inputFlag = true;
			} else {
				inputFlag = false;
			}
		} while (inputFlag != false);

		Search byIngredient = new Search();
		FindIterable<Document> recipesDocs = byIngredient.getByIngredients(ingredient);
		printRecepies(recipesDocs, false);

	}

	public void getPersonalInfo() {

		starline(50);
		System.out.println("Please enter your age");
		boolean ageFlag = false;
		int age = 20;// default age set to 20
		do {
			age = inputScan.nextInt();
			// A person class is created that holds the Age and Gender. It is further
			// described below

			if (age < 1 || age > 110) {
				System.out.println("Invalid input. Please enter an age value between 1-110");
				ageFlag = true;
			} else {
				ageFlag = false;
			}
		} while (ageFlag != false);

		System.out.println("Please enter your Gender: (M/F)");
		boolean genderFlag = false;
		char gender = 'f'; // default set to f
		do {
			gender = inputScan.next().charAt(0);
			// A person class is created that holds the Age and Gender. It is further
			// described below
			gender = Character.toUpperCase(gender);
			if (gender != 'M' && gender != 'F') {
				System.out.println("Invalid input. Please enter appropriate option");
				genderFlag = true;
			} else {
				genderFlag = false;
			}
		} while (genderFlag != false);

		switch (gender) {
		case 'M':
			user = new Person(age, Person.Gender.male);
			break;
		case 'F':
			user = new Person(age, Person.Gender.female);
			break;
		default:
			System.out.println("Error in input.Program will exit");
			System.exit(0);
		}
		starline(50);
		getNutrientInfo();
	}

	public void getNutrientInfo() {

		boolean repeat;
		int itr = 1;
		int inp1 = 0;

		do {
			repeat = false;
			System.out.println(
					"Please select the nutrient on which you want to search:\n1 Calories   2 Protein   3 Fat   4 Sodium");
			System.out.println("Enter number before the nutrient (1,2,3,4):");
			int input = 1;// set to 1 by default
			boolean nutrientFlag = false;
			do {
				input = inputScan.nextInt();
				// A person class is created that holds the Age and Gender. It is further
				// described below

				if (input != 1 && input != 2 && input != 3 && input != 4) {
					System.out.println("Invalid input. Please enter appropriate option");
					nutrientFlag = true;
				} else {
					nutrientFlag = false;
				}
			} while (nutrientFlag != false);

			if (itr == 1) {
				inp1 = input;
			} else if (itr == 2) {
				while (inp1 == input) {
					System.out.println("Nutrient Already Entered, Enter new nutrient");
					input = inputScan.nextInt();
				}
			}

			starline(50);
			System.out.println("Enter value threshold: 1 High 2 Medium 3 Low:");
			int threshold = 2;// Set to 2 by default
			boolean threshodFlag = false;
			do {
				threshold = inputScan.nextInt();
				// A person class is created that holds the Age and Gender. It is further
				// described below

				if (threshold != 1 && threshold != 2 && threshold != 3) {
					System.out.println("Invalid input. Please enter appropriate option");
					threshodFlag = true;
				} else {
					threshodFlag = false;
				}
			} while (threshodFlag != false);

			/*
			 * All the values are passed to the Person class which computes the recommended
			 * value for each nutrient based on age and gender
			 */
			switch (input) {
			case 1:
				nutrient = "calories";
				break;
			case 2:
				nutrient = "protein";
				break;
			case 3:
				nutrient = "fat";
				break;
			case 4:
				nutrient = "sodium";
				break;
			}
			value = user.getNutrientValue(nutrient);
			if (value == 0) {
				System.out.println("Error in reading nutrient value. System will exit. Please try again");
				System.exit(0);
			}
			// As protein values are too low, we need a higher value to create realistic
			// buckets
			if (nutrient.equals("protein"))
				value = value * 2;

			switch (threshold) {
			case 1:
				value = value / 2; // High
				break;
			case 2:
				value = value / 4; // Medium
				break;
			case 3:
				value = value / 6; // Low
				break;
			}
			nutri_list.add(nutrient);
			thresh_list.add(threshold);
			value_list.add(value);
			if (itr == 1) {
				itr++;
				System.out.println("Do you want to add another nutrient? (y/n)");
				char again = inputScan.next().charAt(0);
				again = Character.toUpperCase(again);
				if (again == 'Y')
					repeat = true;
			}
		} while (repeat);

		getRecepiesByNutrient();

	}

	public void getRecepiesByNutrient() {

		Search byNutrient = new Search();
		FindIterable<Document> recipesDocs = byNutrient.getByNutrient(nutri_list, value_list);
		printRecepies(recipesDocs, true);

	}

	public void printRecepies(FindIterable<Document> recipesDocs, boolean byNutrientFlag) {

		if (recipesDocs != null) {
			MongoCursor<Document> recipeCurs = recipesDocs.iterator();
			boolean runFlag = recipeCurs.hasNext();
			// The run flag is used to run the program in increments of 5 recipes
			while (runFlag) {
				int i = 0;
				// This array list will hold the current 5 recipes being shown so that in can be
				// queried later
				ArrayList<String> recipeList = new ArrayList<String>();
				while (recipeCurs.hasNext()) {
					Document recipeName = recipeCurs.next();
					System.out.print((i + 1) + "  " + "Title: " + recipeName.get("title"));
					if (byNutrientFlag) {
						for (String nutri : nutri_list) {
							System.out.print(", " + nutri + ": " + recipeName.get(nutri));
						}
					}
					System.out.println("");
					recipeList.add(recipeName.getString("title"));
					i++;
					if (i == 5)
						break;
					if (!recipeCurs.hasNext())
						runFlag = false;
				}
				System.out.println("Please Enter Recipe Number, Press 0 for next");
				int recipeNo = inputScan.nextInt();
				// Repeat for invalid entry
				while (recipeNo > 5 || recipeNo < 0) {
					System.out.println("Invalid recipe number. Please enter recipe again");
					recipeNo = inputScan.nextInt();
				}
				// If there is no next page, ask user one last time.
				if (recipeNo == 0 && runFlag == false) {
					System.out.println(
							"These were the last options for the input, Input recipe number or press 0 to exit");
					recipeNo = inputScan.nextInt();
				}
				// For a valid recipe chosen
				else if (recipeNo != 0) {
					String recipeName = recipeList.get(recipeNo - 1);
					Search byName = new Search();
					Document recipeChosen = byName.getByName(recipeName, recipesDocs);
					closeConnections();
					DisplayRecepie(recipeChosen);
				}
			}
		} else {
			System.out.println("No Data Returned. Please Start Again");
			System.exit(0);
		}

	}

	public void DisplayRecepie(Document recipeChosen) {

		System.out.print("Recipe: " + recipeChosen.get("title"));
		System.out.print("\tCalories:" + recipeChosen.get("calories"));
		System.out.print("\tProtein:" + recipeChosen.get("protein"));
		System.out.print("\tFat:" + recipeChosen.get("fat"));
		System.out.println("\tSodium:" + recipeChosen.get("sodium"));
		starline(150);
		System.out.println("Ingredients: ");
		@SuppressWarnings("unchecked")
		ArrayList<String> ingList = (ArrayList<String>) recipeChosen.get("ingredients");
		for (String ing : ingList) {
			System.out.println(ing);
		}
		starline(150);
		System.out.println("Directions: ");
		@SuppressWarnings("unchecked")
		ArrayList<String> dirList = (ArrayList<String>) recipeChosen.get("directions");
		for (String dir : dirList) {
			System.out.println(dir);
		}
		starline(150);
		System.exit(0);

	}

	public void closeConnections() {

		inputScan.close();
	}
}
