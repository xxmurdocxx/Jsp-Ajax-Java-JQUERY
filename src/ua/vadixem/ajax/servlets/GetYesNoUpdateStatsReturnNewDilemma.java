package ua.vadixem.ajax.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.DBCollection;

import ua.vadixem.ajax.util.DilemmaUtil;

/**
 * Servlet implementation class GetYesNoUpdateStatsReturnNewDilemma
 */
@WebServlet("/GetYesNoUpdateStatsReturnNewDilemma")
public class GetYesNoUpdateStatsReturnNewDilemma extends HttpServlet {


		public DBCollection dilemmas = DilemmaUtil.getCollectionByName("dilemmas");
		
	
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetYesNoUpdateStatsReturnNewDilemma() {
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		// Getting needed info from web-service.
		String sIWill = request.getParameter("iWill");
		String id = request.getParameter("id");
		
		
		boolean bIWill = sIWill.equals("yes") ? true : false;
		
		// Update collection with answer from web-service.
		DilemmaUtil.updateDilemmaYesNoById(this.dilemmas, bIWill, id);
		
		
	}

}
