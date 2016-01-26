package ua.vadixem.ajax.util;

import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * Consists of methods that interact with MongoDB.
 * @author Vadim
 *
 */
public class DilemmaUtil {

	public static DBCollection getCollectionByName(String collectionName){
		
		try{
		MongoClient client = new MongoClient();
		DB db = client.getDB("test");
		return db.getCollection(collectionName);
		} catch(Exception e) {e.printStackTrace();}
		System.err.println("Couldnt get collection!==========");
		return null;
		}
	
	/**
	 * Finds dilemma which contents String pro and returns it.
	 * @param pro
	 * @return
	 */
	public static BasicDBObject findByPro(String pro){
		
		try{
		MongoClient client = new MongoClient();
		DB db = client.getDB("test");
		
		DBCollection coll = db.getCollection("dilemmas");
		
		DBObject doc = coll.findOne(new BasicDBObject("youGet", pro));
		return (BasicDBObject)doc;
		}
		catch(Exception e){e.printStackTrace();}
		return new BasicDBObject("youGet", "nothing found!");
	}
	
	/**
	 * Returns BasicDBObject found by id
	 * @param id
	 * @return
	 */
	public static BasicDBObject findById(String id){

		try{
			MongoClient client = new MongoClient();
			DB db = client.getDB("test");
			
			DBCollection coll = db.getCollection("dilemmas");
			
		BasicDBObject bas  = new BasicDBObject();
		BasicDBObject query = (BasicDBObject) new BasicDBObject();
		query.put("_id", new ObjectId(id));
		bas = (BasicDBObject)coll.findOne(query);
		System.out.println("Found by id: " + bas.toString());
		return bas;
		}
		catch(Exception e){ e.printStackTrace();}
	System.err.println("====Did not manage to find required data by ID!======");
		return null;	
	}
	
	/**
	 * Returns id of documens as string.
	 */
	public static String getId(BasicDBObject ob){
		return ob.get("_id").toString();
	}
	
	/**
	 * Updates peopleVotedYesNo counter nad rateYesNo
	 */
	public static void updateDilemmaYesNoById(DBCollection dilemma, boolean iWill, String id){
		
		// Get doc by id in order to use it's parametres.
		BasicDBObject original = findById(id);
		int people;
		
		if(iWill){
			people = original.getInt("peopleYes") + 1;
		} else{
			people = original.getInt("peopleNo") + 1;
		}


		// Query to find needed doc by id.
		BasicDBObject query = original;
		// Update details.
		BasicDBObject update =  new BasicDBObject().append("$set", new BasicDBObject()
				.append(iWill? "peopleYes" : "peopleNo", people));
		dilemma.update(query, update, false, false);
	}
	
	public static int getNotNullArrayCount(Object []arr){
		int i = 0;
		for(Object o : arr)
		{
			if(o == null) return i;
			i++;
		}
		System.out.println("Count items in array " + arr.toString() + " :" + i);
		return i;
	}	
	
	/**
	 * 
	 * @param coll
	 * @param answeredDilemArray
	 * @return returns <b>null</b> when all questions are answered.
	 */
	public static BasicDBObject getNewDilemmaExcludingAnswered(DBCollection coll,
			String[] answeredDilemArray){
		// Select all documents in collection and get a cursor to the selected documents.
		Cursor cursor = coll.find();
		boolean dilemmaFound;
		// Find and return element whose id is not in answeredDilemArray.
		DBObject ob;
		while(cursor.hasNext()){
			dilemmaFound = false;
			ob = cursor.next();
			String id = DilemmaUtil.getId((BasicDBObject)ob);
			for(String str : answeredDilemArray){
				if(str.equals(id)){ 
					dilemmaFound = true;
					break;
				}
			}
			if(!dilemmaFound){ 
				return (BasicDBObject)ob;
				}
		}
		System.out.println("All answers have been given!=====");
		return null;
	}
	
	public static void main(String[] args) {
		// Update test.
//		DBCollection coll = getCollectionByName("dilemmas");
//		System.out.println("Before: " + findById("56a4a046c930ac1424357b87"));
//		updateDilemmaYesNoById(coll, false, "56a4a046c930ac1424357b87");
//		System.out.println("After: " + findById("56a4a046c930ac1424357b87"));
		findById("56a4b143c930ac1700bf6e30");
		DBCollection coll = getCollectionByName("dilemmas");
		BasicDBObject doc = getNewDilemmaExcludingAnswered(coll, new String[]{"56a4d808c930ac14e00bd546","56a4aaf3c930ac1b08ab3a52", "56a4ab0bc930ac13fc02fc9b"});
		System.out.println(doc);

	}
}
