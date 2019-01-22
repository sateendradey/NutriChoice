package LoadData;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.json.*;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.InsertOneModel;


public class LoadRecipes {
	public static void main(String[] args) throws SecurityException, IOException {
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
		mongoLogger.setLevel(Level.OFF);

		try {
			MongoClient mongo = new MongoClient( "localhost" , 27017 );			
			MongoDatabase db = mongo.getDatabase("nutrirecipe_db");		
			loadRecipes(db);
			loadDailyReq(db);
			mongo.close();			
		}catch(Exception e) {
			System.out.println("Error executing   -   " + e.toString());
		}
	}

	public static void loadRecipes(MongoDatabase db) {
		MongoCollection<Document> collection = db.getCollection("recipes_full"); 
		System.out.println("Database Connection Successful");
		//If DB is not empty; drop the collection and load new data
		if (collection.count() != 0) {
			collection.drop();
		}
		//Importing Data 
		System.out.println("*********************Import Recipe Data*************************");
		int count = 0;
		int batch = 100;
		List<InsertOneModel<Document>> docs = new ArrayList<>();
		try {
			JSONArray arr = new JSONArray(new JSONTokener(new FileReader("data/Recipes.json")));		
			for (int i = 0; i<arr.length();i++) {	
				String line = arr.get(i).toString();
				docs.add(new InsertOneModel<>(Document.parse(line)));
				count++;
				if (count == batch) 
				{
					collection.bulkWrite(docs, new BulkWriteOptions().ordered(false));
					docs.clear();
					count = 0;

				}
			}
		}catch(Exception e){
			System.out.println("Error executing "+ e);				
		}

		if (count > 0) {				
			collection.bulkWrite(docs, new BulkWriteOptions().ordered(false));				
		}
		
		try {
			TimeUnit.SECONDS.sleep(5);					
		}catch(InterruptedException ie) {
			System.out.println("Error executing "+ ie);				
		}								
		collection = db.getCollection("recipes_full");
		
	}

	public static void loadDailyReq(MongoDatabase db) {

		MongoCollection<Document> collection = db.getCollection("daily_req"); 
		System.out.println("Database Connection Successful");
		//If DB is not empty; drop the collection and load new data
		if (collection.count() != 0) {
			collection.drop();
		}
		//Importing Data 
		System.out.println("*********************Import Requirements Data*************************");
		int count = 0;
		int batch = 100;
		List<InsertOneModel<Document>> docs = new ArrayList<>();
		try {
			JSONArray arr = new JSONArray(new JSONTokener(new FileReader("data/DRV.json")));		
			for (int i = 0; i<arr.length();i++) {	
				String line = arr.get(i).toString();
				docs.add(new InsertOneModel<>(Document.parse(line)));
				count++;
				if (count == batch) 
				{
					collection.bulkWrite(docs, new BulkWriteOptions().ordered(false));
					docs.clear();
					count = 0;

				}
			}
		}catch(Exception e){
			System.out.println("Error executing "+ e);				
		}

		if (count > 0) {				
			collection.bulkWrite(docs, new BulkWriteOptions().ordered(false));				
		}
		
		try {
			TimeUnit.SECONDS.sleep(5);					
		}catch(InterruptedException ie) {
			System.out.println("Error executing "+ ie);			
		}								
		collection = db.getCollection("daily_req");
		
	}
}
