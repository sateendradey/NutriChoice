package DataAccess;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import Domain.Person.Gender;
import LoadData.LoadRecipes;

public class DataBaseAccess {

	MongoClient mongo;
	MongoDatabase db;
	MongoCollection<Document> collection;
	BasicDBObject whereQuery;
	BasicDBObject sortQuery;

	public void establishConnection() {

		try {
			Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
			mongoLogger.setLevel(Level.SEVERE);
			mongo = new MongoClient("localhost", 27017);
			db = mongo.getDatabase("nutrirecipe_db");
		} catch (Exception e) {
			System.out.println("Error in establishConnection Method");
			System.out.println("Error executing   -   " + e.toString());
		}
	}

	public FindIterable<Document> getRecepiesByIngredient(String ingredient) {

		try {
			collection = db.getCollection("recipes_full");
			whereQuery = new BasicDBObject();
			whereQuery.put("ingredients", java.util.regex.Pattern.compile(ingredient));
			FindIterable<Document> recipesDocs = collection.find(whereQuery);
			return recipesDocs;
		} catch (Exception e) {
			System.out.println("Error in getRecepiesByIngredient Method");
			System.out.println("Error executing   -   " + e.toString());
			return null;
		}

	}

	public double getNutrientValues(Gender gender, int age, String nutrient) {

		try {
			double nut_value = 0;
			collection = db.getCollection("daily_req");
			whereQuery = new BasicDBObject();
			whereQuery.put("gender", gender.toString());
			whereQuery.put("age-beg", new BasicDBObject("$lte", age));
			whereQuery.put("age-end", new BasicDBObject("$gte", age));
			FindIterable<Document> recipesDocs = collection.find(whereQuery);
			Document result = recipesDocs.first();
			if (result != null) {
				nut_value = result.getInteger(nutrient);
			}
			closeConnections();
			return nut_value;
		} catch (Exception e) {
			System.out.println("Error in getNutrientValues Method");
			System.out.println("Error executing   -   " + e.toString());
			return 0;
		}
	}

	public FindIterable<Document> getRecepies(ArrayList<String> nutri_list, ArrayList<Double> value_list) {

		try {

			collection = db.getCollection("recipes_full");
			if (collection.count() == 0) {
				// If data is empty, load data
				System.out.println("Loading Data again");
				String args[] = null;
				LoadRecipes.main(args);
			} else {

				System.out.println("Successful connection");

			}

			/*
			 * The next line generates the query from the database (get a value between half
			 * of the value and the value itself, creating a realistic bucket of values
			 */
			// Sort descending in the value of the nutrient

			whereQuery = new BasicDBObject();
			sortQuery = new BasicDBObject();

			for (int index = 0; index < nutri_list.size(); index++) {
				whereQuery.put(nutri_list.get(index),
						new BasicDBObject("$gt", value_list.get(index) / 2).append("$lt", value_list.get(index)));
				sortQuery.put(nutri_list.get(index), -1);
			}

			FindIterable<Document> recipesDocs = collection.find(whereQuery).sort(sortQuery);

			return recipesDocs;
		} catch (Exception e) {
			System.out.println("Error in getRecepies Method");
			System.out.println("Error executing   -   " + e.toString());
			return null;
		}

	}

	public Document getRecepie(String recipeName, FindIterable<Document> recipesDocs) {

		whereQuery = new BasicDBObject();
		whereQuery.put("title", recipeName);
		// Filter the already fetched list with the title query
		recipesDocs.filter(whereQuery);
		Document recipeChosen = recipesDocs.first();
		return recipeChosen;

	}

	public void closeConnections() {
		mongo.close();
	}

}
