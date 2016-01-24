package ua.vadixem.ajax.util;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DilemmaUtil {

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
	
	public static void main(String[] args) {
		
		System.out.println(findByPro("Моника Белуччи будет твоей женой"));
	}
}
