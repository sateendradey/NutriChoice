package Domain;


import DataAccess.DataBaseAccess;

public class Person {

	public enum Gender{
		male, 
		female
	}

	private int age; 
	private Gender	gender; 

	public Person(int a, Gender g) {
		age = a; gender = g; 
	}

	public double getNutrientValue(String nutrient) {
		
		DataBaseAccess db = new DataBaseAccess();
		db.establishConnection();
		return db.getNutrientValues(gender,age,nutrient);
	
	}
}
