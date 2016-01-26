package ua.vadixem.ajax.servlets;


import java.io.IOException;
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
		boolean showNew = request.getParameter("getNewDilemma") == null ? false : true;
		System.out.println("ShowNew: " + showNew);
		// ****  Cookies sector  ****
		//		Cookie [] cookies = request.getCookies();
//		String [] answeredDilamArr;
//		// If no cookies yet then add a new cookie with dilemma id to response. 
//		if(cookies == null){
//			Cookie cookie = new Cookie("dilemma1", pro);
//			response.addCookie(cookie);
//			answeredDilamArr = new String[1];
//			answeredDilamArr[0] = pro;
//			System.out.println("Id of incoming dilemma: " + answeredDilamArr[0]);
//		}
//		 // If cookies exist then add a new cookie with dilemma 
//		 // id relying on total cookies count.
//		else{
//			// Log all cookies values(to be deleted).
//			int i = 0;
//			answeredDilamArr = new String[cookies.length + 1];
//			System.out.println("Cookies are not empty: ");
//			for(Cookie c : cookies){
//				// Add id of answered dilemmas to array.
//				answeredDilamArr[i] = c.getValue();
//				System.out.println(c.getValue());
//		}
//			// Add last requested dilemma id to array.
//			answeredDilamArr[answeredDilamArr.length - 1] = pro;
//			
//		int cookieIndetifier = cookies.length;
//		response.addCookie(new Cookie("dilemma" + cookieIndetifier, pro));
//		}

		
		
		//		// There must be two cookies: 1) id of session; 2) answeredDilemArray.
//		Cookie [] cookies = request.getCookies();
//		Cookie answeredDilemArrayCookie = null;
//
//		for(Cookie c : cookies){
//			if(c.getName() == "answeredDilemArray")
//				answeredDilemArrayCookie = c;
//		}
//		response.addCookie(answeredDilemArrayCookie);
//		
//		// If no session created.
//		if(session == null){
//		System.out.println("Create new session!======");
//		HttpSession newSession = request.getSession();
//		String answeredDilemArray[] = new String[50];
//		answeredDilemArray[0] = pro;
//		newSession.setAttribute("answeredDilemArray", answeredDilemArray);
//		// If session exists.
//		} else {
//			System.out.println("Working with existing session: " + session.getId());
//			// Getting array of IDs of already answered questions.
//			String arr[] = (String[])session.getAttribute("answeredDilemArray");
//			// Get quantity of not null items in array.
//			int next = DilemmaUtil.getNotNullArrayCount(arr);
//			// Add new id to answeredDilemArray.
//			arr[next] = pro;
//			// Set attrib to session.
//			session.setAttribute("answeredDilemArray", arr);
//		}
		// Get results of dilemma by requested id.
//		int charArrLength = pro.toCharArray().length;
//		if(pro.trim().toLowerCase().toCharArray()[charArrLength - 1] == 'o')
		if(!showNew)
		write(response, pro);
//		// Get results of another dilemma that  was not answered yet.
//		else
//		System.out.println("Checking answeredDilamArr");
//		int i = 0;
//		for(String s : answeredDilamArr){
//			System.out.println(i++ + " )" + s);
//		}
		else
		writeNotAnswered(response, pro, coll);
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
	private void writeNotAnswered(HttpServletResponse response, String id, DBCollection coll) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		// Created map for mapping string to json strings("id" - for id, "dilemma - for dilemma JSON")
		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		// Get dilemma which was not yed answered.
		BasicDBObject doc = DilemmaUtil.getNewDilemmaExcludingAnswered(coll, new String[]{id});
		System.out.println("ANother dilemma got: " + doc);
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
}