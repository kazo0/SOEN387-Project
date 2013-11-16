package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBAccess;
import patterns.GameMapper;
import patterns.UOW;
import models.*;


/**
 * Servlet implementation class Test
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
		// TODO Auto-generated method stub
		
		DBAccess.getInstance();
		request.getSession(true).setAttribute("items", GameMapper.getInstance().getAll());
		RequestDispatcher rd1=request.getRequestDispatcher("Home.jsp");
		rd1.forward(request, response);
		//Game gm = GameMapper.getInstance().get(1);
		//unit.registerClean(gm);
		
		//ChangeName(gm, "Hello world!!");
		//Game newGame = new Game(0, "Title", "Desc" , 15.4 , 24);
		//AddGame(newGame);
		//Commit();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
