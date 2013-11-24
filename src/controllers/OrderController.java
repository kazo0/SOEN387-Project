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

import patterns.GameMapper;
import patterns.OrderMapper;
import models.Game;
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
		User user = (User)request.getSession().getAttribute("user");
		String opt = request.getParameter("option");
		
		//Was Manage Orders clicked or was My Orders clicked?
		if (opt.equals("manage"))
		{
			if (user.isAdmin())
			{
				//View All Orders
				Order[] allOrders = OrderMapper.getInstance().getAll();
				request.getSession().setAttribute("allOrders", allOrders);
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
			request.getSession().setAttribute("userOrders", userOrders);
			RequestDispatcher rd1=request.getRequestDispatcher("MyOrders.jsp");
			rd1.forward(request, response);
		}
		else if (opt.equals("deleteItem"))
		{
			//Delete Order Item based on the parameters sent back "orderIndex" and "itemIndex"
			//We need to update the Game quantities for the delete order item.
			int oid = Integer.parseInt(request.getParameter("orderID"));
			int itemIndex = Integer.parseInt(request.getParameter("itemIndex"));
			Order orders = (Order)OrderMapper.getInstance().get(oid);
			
			OrderItem deletedItem = orders.getOrderedGames().get(itemIndex);
			OrderMapper.getInstance().deleteOrderItem(oid, deletedItem.getGameID(), itemIndex);
			
			
			Order[] allOrders = OrderMapper.getInstance().getAll();
			request.getSession().setAttribute("allOrders", allOrders);
			RequestDispatcher rd1=request.getRequestDispatcher("ManageOrders.jsp");
			rd1.forward(request, response);
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
			 int oid = Integer.parseInt(request.getParameter("orderID"));
			 Order o = OrderMapper.getInstance().get(oid);
			 
			 ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>((ArrayList<OrderItem>)o.getOrderedGames()); 
			 
			 //Delete all order items which will then increment the game quantities and delete the order entry
			 for (int i = orderItems.size() - 1; i >= 0 ; i--)
			 {
				 OrderMapper.getInstance().deleteOrderItem(oid, orderItems.get(i).getGameID(), i);
			 }
			 
			 response.sendRedirect("OrderController?option=manage");
		 }
	}

}
