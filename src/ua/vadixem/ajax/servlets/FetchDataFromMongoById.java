package ua.vadixem.ajax.servlets;


import java.io.IOException;
import java.security.IdentityScope;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import ua.vadixem.ajax.util.DilemmaUtil;

/**
 * Servlet implementation class FetchDataFromMongo
 */
@WebServlet("/dilemma")
public class FetchDataFromMongoById extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchDataFromMongoById() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Will check existence of session. If session exists, then it returns the reference of that session object,
		// if not, this methods will return null.
		DBCollection coll = DilemmaUtil.getCollectionByName("dilemmas");
//		HttpSession session = request.getSession(false);
//		// Gets id of dilemma.
		String pro = request.getParameter("id");
		boolean isIdEmpty = pro.isEmpty() ? true : false;
		System.out.println("isIdEmpty: " + "." +isIdEmpty + ".");
		System.out.println("Pro: " + "." + pro + ".");
//		boolean showNew = request.getParameter("getNewDilemma") == null ? false : true;
//		System.out.println("ShowNew: " + showNew);
		
		Cookie [] cookies = request.getCookies();
		String [] answeredDilemArray;
		

		// If visitor sends empty request(enters for the very first time).
		if(cookies == null && isIdEmpty){

			System.out.println("There are no cookies yet! Creating one!=======");
			Cookie answeredCookie = new Cookie("answeredIdCookie_" + pro.hashCode(), pro);
			response.addCookie(answeredCookie);
			System.out.println("New cookie " + answeredCookie.getName() + " added!=======");
			answeredDilemArray = new String[1];
			answeredDilemArray[0] = pro;
			System.out.println("Id sent to answeredDilemArray" + answeredDilemArray[0] + " !=======");
		// If id that is got from request is not empty, then
		} else if(!isIdEmpty){
			
			System.out.println("There were cookies already!======");
			// Create a larger by 1 array, then we check whether incoming id is unique in order to add or not.
			int cookiesSize = cookies.length;
			answeredDilemArray = new String[cookiesSize + 1];
			boolean cookieWithSameIdfound = false;
			int i = 0;
			for(Cookie cookie : cookies){
				String name = cookie.getName();
				String value = cookie.getValue();
				
				answeredDilemArray[i] = value;
				System.out.println(i+1 + ") " + " cookie inside loop" + name + " = " + value + "========");
				i++;
				if(pro.equals(value)){
					cookieWithSameIdfound = true;
					System.out.println("Cookie is not null and with same id found!======");
				}
			}
			// When all cookies looped and checked whether requested(pro) id is unique.
			if(!cookieWithSameIdfound){
				
				System.out.println("This id " + pro + "is unique, adding to response new Cookie!======");
				Cookie newCookie = new Cookie("answeredIdCookie_" + pro.hashCode(), pro);
				// Add dilemma id to our array thay will be passed to find elements in db unlike in this array.
				answeredDilemArray[cookiesSize - 1] = pro;
				System.out.println("Cookie id added to answeredDilem array: " + answeredDilemArray[cookiesSize - 1]);
				response.addCookie(newCookie);
				writeNotAnswered(response, answeredDilemArray, coll);
				}
			// Write to the site a new dilemma without adding a new id to the cookies.
			else {
				System.out.println("Cookie already exists, don't add it to request!=+===");
				writeNotAnswered(response, answeredDilemArray, coll);
			}
			
			}
		else if(isIdEmpty){
			System.out.println("Requested id is empty!======\nReturning new Dilemma!======");
			writeNotAnswered(response, coll);
		}
	
	//	writeNotAnswered(response, answeredDilemArray, coll);
	}

	/**
	 * Get all info by id and returns to web-service.
	 * @param response
	 * @param pro
	 * @throws IOException
	 */
	private void write(HttpServletResponse response, String id) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		// Created map for mapping string to json strings("id" - for id, "dilemma - for dilemma JSON")
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		BasicDBObject doc = DilemmaUtil.findById(id);
		String id1 = DilemmaUtil.getId(doc);
		System.out.println(id1);
		map.put("id1", id1);
		map.put("peopleGoodVote", doc.getInt("peopleGoodVote") + "");
		map.put("peopleBadVote", doc.getInt("peopleBadVote") + "");
		map.put("peopleYes", doc.getInt("peopleYes") + "");
		map.put("peopleNo", doc.getInt("peopleNo") + "");
		map.put("youGet", doc.getString("youGet"));
		map.put("but", doc.getString("but"));
		
		response.getWriter().write(gson.toJson(map));	
	}

	/**
	 * Returns new unanswered dilemma depending on cookies.
	 * @param response
	 * @param answeredDilArray
	 * @throws IOException
	 */
	private void writeNotAnswered(HttpServletResponse response, String[] ids, DBCollection coll) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		// Created map for mapping string to json strings("id" - for id, "dilemma - for dilemma JSON")
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		// Get dilemma which was not yed answered.
		BasicDBObject doc = DilemmaUtil.getNewDilemmaExcludingAnswered(coll, ids);
		System.out.println("Inside of writeNotAnswered(3 args) method!======");
		System.out.println("Doc is: ." + doc + ".");
		if(doc != null){
		System.out.println("ANother dilemma got: " + doc);
		String id1 = DilemmaUtil.getId(doc);
		System.out.println(id1);
		Cookie cookieFromWriteNotAnsweredMethod = new Cookie("answeredIdCookie_" + id1.hashCode(), id1);
		map.put("id1", id1);
		map.put("peopleGoodVote", doc.getInt("peopleGoodVote") + "");
		map.put("peopleBadVote", doc.getInt("peopleBadVote") + "");
		map.put("peopleYes", doc.getInt("peopleYes") + "");
		map.put("peopleNo", doc.getInt("peopleNo") + "");
		map.put("youGet", doc.getString("youGet"));
		map.put("but", doc.getString("but"));
		// Add cookie in order to prevent same dilemmas to be loaded.
		response.addCookie(cookieFromWriteNotAnsweredMethod);
		response.getWriter().write(gson.toJson(map));
		}
		else {
			map.put("id1", "Ты дал ответ на все вопросы!");
			response.getWriter().write(gson.toJson(map));
		}
	}
	/**
	 * Used when this is the very first request.
	 * Returns new unanswered dilemma depending on cookies.
	 * @param response
	 * @param answeredDilArray
	 * @throws IOException
	 */
	
	private void writeNotAnswered(HttpServletResponse response,  DBCollection coll) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		// Created map for mapping string to json strings("id" - for id, "dilemma - for dilemma JSON")
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		// Get dilemma which was not yed answered.
		BasicDBObject doc = DilemmaUtil.getNewDilemmaExcludingAnswered(coll);
		System.out.println("Inside of writeNotAnswered(2 args) method!======");
		System.out.println("Doc is: ." + doc + ".");
		if(doc != null){
		System.out.println("ANother dilemma got: " + doc);
		String id1 = DilemmaUtil.getId(doc);
		System.out.println(id1);
		Cookie cookieFromWriteNotAnsweredMethod = new Cookie("answeredIdCookie_" + id1.hashCode(), id1);
		map.put("id1", id1);
		map.put("peopleGoodVote", doc.getInt("peopleGoodVote") + "");
		map.put("peopleBadVote", doc.getInt("peopleBadVote") + "");
		map.put("peopleYes", doc.getInt("peopleYes") + "");
		map.put("peopleNo", doc.getInt("peopleNo") + "");
		map.put("youGet", doc.getString("youGet"));
		map.put("but", doc.getString("but"));
		// Add cookie in order to prevent same dilemmas to be loaded.
		response.addCookie(cookieFromWriteNotAnsweredMethod);
		response.getWriter().write(gson.toJson(map));
		} else {
			map.put("id1", "Ты дал ответ на все вопросы!");
			response.getWriter().write(gson.toJson(map));
		}
	}
	
}