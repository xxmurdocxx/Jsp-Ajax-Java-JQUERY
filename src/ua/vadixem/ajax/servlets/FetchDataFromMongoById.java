package ua.vadixem.ajax.servlets;

import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;

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
		
		String pro = request.getParameter("id");
		
		write(response, pro);
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


}
