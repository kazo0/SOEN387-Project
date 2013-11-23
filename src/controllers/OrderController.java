package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import patterns.GameMapper;
import patterns.OrderMapper;
import models.Order;
import models.OrderItem;
import models.User;

/**
 * Servlet implementation class OrderController
 */
@WebServlet("/OrderController")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession(true).getAttribute("user");
		String opt = request.getParameter("option");
		
		//Was Manage Orders clicked or was My Orders clicked?
		if (opt.equals("manage"))
		{
			if (user.isAdmin())
			{
				//View All Orders
				Order[] allOrders = OrderMapper.getInstance().getAll();
				request.getSession(true).setAttribute("allOrders", allOrders);
				RequestDispatcher rd1=request.getRequestDispatcher("ManageOrders.jsp");
				rd1.forward(request, response);
			
			}
			else
			{
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		}
		else if (opt.equals("my"))
		{
			//View Orders by ID
			Order[] userOrders = (Order[])OrderMapper.getInstance().findByUserId(user.getId());
			List<OrderItem> oi = userOrders[0].getOrderedGames();
			OrderItem o = oi.get(0);
			request.getSession(true).setAttribute("userOrders", userOrders);
			RequestDispatcher rd1=request.getRequestDispatcher("MyOrders.jsp");
			rd1.forward(request, response);
		}
		else if (opt.equals("deleteItem"))
		{
			//Delete Order Item based on the paramaters sent back "orderIndex" and "itemIndex"
			//We need to update the Game quantities for the delete order item.
		}

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 if (request.getParameter("cancel") != null)
		 {
			 //Delete the order, the OrderItems will auto-cascade
			 //We also need to update the Game quantities after cancelling an order
		 }
	}

}
