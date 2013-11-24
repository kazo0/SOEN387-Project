package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Cart;
import models.Game;
import models.Order;
import models.OrderItem;
import models.User;
import patterns.CartMapper;
import patterns.GameMapper;
import patterns.OrderMapper;

/**
 * Servlet implementation class CartController
 */
@WebServlet("/CartController")
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String opt = request.getParameter("option");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		User user = (User) request.getSession().getAttribute("user");
		
		if (cart == null)
		{
			cart = new Cart(user.getId());
		}
		
		if (opt.equals("add")) {
			int gameID = Integer.parseInt(request.getParameter("gameID"));
			double price = Double.parseDouble(request.getParameter("price"));
			String name = request.getParameter("name");

			cart.addItem(gameID, price, name);
	
			
			request.getSession().setAttribute("cart", cart);
			RequestDispatcher rd1=request.getRequestDispatcher("Home.jsp");
			rd1.forward(request, response);
			
		}
		if (opt.equals("delete")) {
			int index = Integer.parseInt(request.getParameter("index"));
			
			cart.deleteItem(index);
			if (cart.isEmpty())
			{
				cart = null;
			}
			request.getSession().setAttribute("cart", cart);
			
			RequestDispatcher rd1=request.getRequestDispatcher("Cart.jsp");
			rd1.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		User user = (User) request.getSession().getAttribute("user");

		if (cart == null) 
			return;
		
		 if (request.getParameter("update") != null) {
			String[] items = request.getParameterValues("Quantity[]");
			if (items != null) {
				for(int i= 0; i< items.length; i++) {
					int qty = Integer.parseInt(items[i]);
					cart.updateItem(i, qty);
				}
				cart.cleanup();
				if (cart.isEmpty())
				{
					cart = null;
				}
				request.getSession().setAttribute("cart", cart);
			}
			RequestDispatcher rd1=request.getRequestDispatcher("Cart.jsp");
			rd1.forward(request, response);
		}
		 else if (request.getParameter("checkout") != null) {
			 
			 List<OrderItem> validCart = new ArrayList<OrderItem>();
			 
			 validCart = CartMapper.getInstance().checkQuantities(cart);
			 
			 if (!validCart.isEmpty())
			 {
				 Order order = new Order(-1, validCart, "Processing", user);
				 OrderMapper.getInstance().insert(order);
				
			 }
			 
			 request.getSession().setAttribute("cart", cart);
			 
			 
			 if (cart.isEmpty())
			 { 
				 response.sendRedirect("HomeController");
			 }
			 else
			 {
				 request.getSession().setAttribute("error", "We do not have enough stock to order some of your items!");
				 RequestDispatcher rd1 = request.getRequestDispatcher("Cart.jsp");
				 rd1.forward(request, response);
			 }

		 }

	}

}
