package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Game;
import models.User;
import patterns.GameMapper;
import patterns.LoginGateway;
import database.DBAccess;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/HomeController")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Init Connection
		DBAccess.getInstance();

		if (request == null)
		{
			RequestDispatcher rd1=request.getRequestDispatcher("index.jsp");
			rd1.forward(request, response);
		}
		String category = request.getParameter("category");
		request.getSession(true).setAttribute("games", GameMapper.getInstance().find(category));
		RequestDispatcher rd1=request.getRequestDispatcher("Home.jsp");
		rd1.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Init Connection
		DBAccess.getInstance();
		RequestDispatcher rd1 = request.getRequestDispatcher("Home.jsp");
		if (request.getParameter("login") != null) {
			String username = request.getParameter("uname");
			String password = request.getParameter("pass");
			if (username.isEmpty() || password.isEmpty())
			{
				request.getSession(true).setAttribute("error", "Username or Password are empty");
				rd1=request.getRequestDispatcher("index.jsp");
			}
			else {
				User user  = LoginGateway.getInstance().Login(username, password);
				if (user == null)
				{
					request.getSession(true).setAttribute("error", "Invalid Login Info");
					rd1=request.getRequestDispatcher("index.jsp");
				}
				if (user.isAdmin()) {
					rd1=request.getRequestDispatcher("InventoryController");
				}
				request.getSession(true).setAttribute("user", user);
				request.getSession(true).setAttribute("games",GameMapper.getInstance().find(null));
			}
		}
		if (request.getParameter("option")!= null && request.getParameter("option").equals("search"))  {
			String search = request.getParameter("search");
			if (search != null) 
				request.getSession(true).setAttribute("games",GameMapper.getInstance().findByName(search));
		}
		rd1.forward(request, response);
	}

}
