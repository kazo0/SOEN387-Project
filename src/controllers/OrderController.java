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
			Order order = (Order)OrderMapper.getInstance().get(oid);
			order.deleteItem(itemIndex);
			//OrderItem deletedItem = order.getOrderedGames().get(itemIndex);
			//OrderMapper.getInstance().deleteOrderItem(oid, deletedItem);
			
			
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
			 
			 o.delete();
			 
			 response.sendRedirect("OrderController?option=manage");
		 }
		 else if (request.getParameter("update") != null) {
			 String[] quantities = request.getParameterValues("Quantity[]");
			 String[] prices = request.getParameterValues("Price[]");
			 boolean error = false;
			 
			 int key = Integer.parseInt(request.getParameter("orderID"));
			 
			 Order o = OrderMapper.getInstance().get(key);
			 
			 if (prices != null) 
			 {
					for(int i= 0; i< prices.length; i++) {
						double qty = Double.parseDouble(prices[i]);
						o.updateItemPrice(i, qty);
					}
			 }
			 
			 
			 if (quantities != null)
			 {
					for(int i= 0; i< quantities.length; i++) {
						int qty = Integer.parseInt(quantities[i]);
						if(o.updateItemQty(i, qty))
						{
							error = true;
						}
					}
			 }
			 o.cleanup();
			 
			 if (error)
			 {
				 request.getSession().setAttribute("ManageError", "Not enough stock to update quantities of some items");
			 }
			 response.sendRedirect("OrderController?option=manage");
		 
		 }
	}

}
