package Domain;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.FindIterable;

import DataAccess.DataBaseAccess;

public class Search {

	public FindIterable<Document> getByIngredients(String ingredient) {
		DataBaseAccess db = new DataBaseAccess();
		db.establishConnection();
		return (db.getRecepiesByIngredient(ingredient));

	}

	public FindIterable<Document> getByNutrient(ArrayList<String> nutri_list, ArrayList<Double> value_list) {
		DataBaseAccess db = new DataBaseAccess();
		db.establishConnection();
		return db.getRecepies(nutri_list, value_list);

	}

	public Document getByName(String recipeName, FindIterable<Document> recipesDocs) {
		DataBaseAccess db = new DataBaseAccess();
		return db.getRecepie(recipeName, recipesDocs);

	}

}
