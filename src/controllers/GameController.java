package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Game;
import patterns.GameMapper;
import patterns.UOW;

/**
 * Servlet implementation class GameController
 */
@WebServlet("/GameController")
public class GameController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String opt = request.getParameter("option");
		if (opt.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Game gm =GameMapper.getInstance().get(id);
			gm.markRemoved();
		}
		
		request.getSession(true).setAttribute("items", GameMapper.getInstance().getAll(null));
		RequestDispatcher rd1=request.getRequestDispatcher("Inventory.jsp");
		rd1.forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String opt = request.getParameter("option");
		
		if (opt.equals("edit")){
			//get ID from session
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("Name");
			String desc = request.getParameter("Description");
			double price = Double.parseDouble(request.getParameter("Price"));
			int qty = Integer.parseInt(request.getParameter("Quantity"));
			String[] cat = request.getParameterValues("Category");
			String image = request.getParameter("Image");

			
			Game gm =GameMapper.getInstance().get(id);
			gm.setName(name);
			gm.setDescription(desc);
			gm.setPrice(price);
			gm.setQty(qty);
			gm.setCategory(cat[0]);
			gm.setImage(image);
			gm.markDirty();
			
	
			request.getSession(true).setAttribute("items", GameMapper.getInstance().getAll(null));
			RequestDispatcher rd1=request.getRequestDispatcher("Home.jsp");
			rd1.forward(request, response);
		}
		else if (opt.equals("add")){
			//add game details
			
			String name = request.getParameter("Name");
			String desc = request.getParameter("Description");
			double price = Double.parseDouble(request.getParameter("Price"));
			int qty = Integer.parseInt(request.getParameter("Quantity"));
			String[] cat = request.getParameterValues("Category");
			String image = request.getParameter("Image");
			
			Game gm = new Game(-1, name, desc, price, qty, cat[0], image);
			gm.markNew();
			
			request.getSession(true).setAttribute("items", GameMapper.getInstance().getAll(null));
			RequestDispatcher rd1=request.getRequestDispatcher("Inventory.jsp");
			rd1.forward(request, response);
			
		}
		else if (opt.equals("commit")){
			UOW.getCurrent().commit();
			
			request.getSession(true).setAttribute("items", GameMapper.getInstance().getAll(null));
			RequestDispatcher rd1=request.getRequestDispatcher("Inventory.jsp");
			rd1.forward(request, response);
			
		}
	}

}
