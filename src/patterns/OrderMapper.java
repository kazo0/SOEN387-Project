package patterns;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.DBStatus;
import models.DomainObject;
import models.Order;
import models.OrderItem;
import database.DBAccess;
import database.JdbcUtilViaSSH;
import database.SSHjdbcSession;

public class OrderMapper {

	
	private static OrderMapper soleInstance = null;
	
	public static OrderMapper getInstance() {
		if (soleInstance == null) soleInstance =  new OrderMapper();
		
		return soleInstance;
	}
	
	private Map<Integer, Order> order = new HashMap<Integer, Order>(); 
		
	public void addOrderToMap(Order arg) {
		soleInstance.order.put(arg.getID(), arg);
	}
	
	public Order[] getAll() {
		
		List<Order> list = new ArrayList<Order>();
		Order o = null;
		
		String query = "select * from Orders inner join OrderItems on Orders.id = OrderItems.orderID";
		ResultSet rs = DBAccess.getInstance().ExecuteQuery(query);
		try {
			int prevID = -1;
			int prevUserID = -1;
			String prevStatus = "";
			List<OrderItem> oi = new ArrayList<OrderItem>();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				int uid = rs.getInt("userID");
				String status = rs.getString("status");
				
				if (id != prevID)
				{
					if (prevID != -1)
					{
						o = new Order(prevID, new ArrayList<OrderItem>(oi), prevStatus, LoginGateway.getInstance().getUser(prevUserID));
						list.add(o);
						oi.clear();

					}
					prevID = id;
					prevUserID = uid;
					prevStatus = status;
				}
				
				o = order.get(id);
				if (o == null)
				{
					int gid = rs.getInt("gameID");
					int qty = rs.getInt("quantity");
					double price = rs.getDouble("price");
					String name = rs.getString("name");
					
					oi.add(new OrderItem(gid, qty, price, name));
				}
				else
				{
					prevID = id;
					prevUserID = uid;
					prevStatus = status;
					if (!list.contains(o)){
						list.add(o);
					}
					continue;
				}
					

				
			}
			if (prevID != -1 && !oi.isEmpty())
			{
				o = new Order(prevID, new ArrayList<OrderItem>(oi), prevStatus, LoginGateway.getInstance().getUser(prevUserID));
				list.add(o);
			}
			
			// Add All New Orders
			List<DomainObject> addedObjects = UOW.getCurrent().getAllNew();
			for(DomainObject item : addedObjects) {
				list.add((Order)item);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list.toArray(new Order[list.size()]);
	}
	
	public Order findById(int id) {
		
		Order o = null;
		List<OrderItem> oi = new ArrayList<OrderItem>();
		int uid = 0;
		int gid = 0;
		int qty = 0;
		double price = 0;
		String name = "";
		String status = "";
		
		String query = "select * from Orders INNER JOIN OrderItems on Orders.id = OrderItems.OrderID where Orders.id = '"+ id + "'";
		ResultSet rs = DBAccess.getInstance().ExecuteQuery(query);
		try {

			while(rs.next()) {
				uid = rs.getInt("userID");
				status = rs.getString("status");
				gid = rs.getInt("gameID");
				qty = rs.getInt("quantity");
				price = rs.getDouble("price");
				name = rs.getString("name");
				
				oi.add(new OrderItem(gid, qty, price, name));
			}
			o = new Order(id, new ArrayList<OrderItem>(oi), status, LoginGateway.getInstance().getUser(uid));
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	
	public Order[] findByUserId(int uid) {
		
		Order o = null;
		List<Order> list = new ArrayList<Order>();
		String query = "select * from Orders INNER JOIN OrderItems on Orders.id = OrderItems.OrderID where Orders.userID = '" + uid + "'";
		ResultSet rs = DBAccess.getInstance().ExecuteQuery(query);
		try {
			int prevID = -1;
			String prevStatus = "";
			List<OrderItem> oi = new ArrayList<OrderItem>();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String status = rs.getString("status");
				
				if (id != prevID)
				{
					if (prevID != -1)
					{
						o = new Order(prevID, new ArrayList<OrderItem>(oi), prevStatus, LoginGateway.getInstance().getUser(uid));
						list.add(o);
						oi.clear();
						
					}
					prevID = id;
					prevStatus = status;
				}
				

				o = order.get(id);
				if (o == null)
				{
					int gid = rs.getInt("gameID");
					int qty = rs.getInt("quantity");
					double price = rs.getDouble("price");
					String name = rs.getString("name");
					
					oi.add(new OrderItem(gid, qty, price, name));
				}
				else
				{
					prevID = id;
					prevStatus = status;
					if (!list.contains(o)){
						list.add(o);
					}
					
					continue;
				}
			}
			if (prevID != -1 && !oi.isEmpty())
			{
				o = new Order(prevID, new ArrayList<OrderItem>(oi), prevStatus, LoginGateway.getInstance().getUser(uid));
				list.add(o);
			}
			// Add All New Orders
			List<DomainObject> addedObjects = UOW.getCurrent().getAllNew();
			for(DomainObject item : addedObjects) {
				list.add((Order)item);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list.toArray(new Order[list.size()]);
	}
	
	
	private DBStatus GetStatus() {
		
		return null;
	}

	public Order get(int key) {
		Order o =  (Order) soleInstance.order.get(key);
		if (o == null) {
			try{
				o = findById(key);
				this.addOrderToMap(o);
				}
			catch (Exception ex) {
					
			}
		}
		
		return o;
	}
	
	public void insert (DomainObject obj) {
		Order o = (Order) obj;
		//DB Insert 
		String values = "'" + o.getUser().getId() + "'";
		String query = "INSERT INTO Orders (userID) VALUES (" + values + ")";
		int  id = DBAccess.getInstance().ExecuteInsert(query);
		// Set id from DB
		o.setID(id);
		soleInstance.order.put(o.getID(), o);
		
		for (OrderItem oi : o.getOrderedGames())
		{
			String values2 = "'" + o.getID() + "','" + oi.getGameID() + "','" + oi.getQuantity() + "','" + oi.getPrice() + "','" + oi.getName() + "'";
			String query2 = "INSERT INTO OrderItems (orderID, gameID, quantity, price, name) VALUES (" + values2 + ")";
			DBAccess.getInstance().Execute(query2);
		}
		
		
	}
	
	public void update(DomainObject obj) {
		Order o = (Order) obj;
		for (OrderItem oi : o.getOrderedGames())
		{
			String query = "UPDATE OrderItems SET orderID='" + o.getID() + "',gameID='" + oi.getGameID() + "',quantity='" + oi.getPrice() + "', quantity='" + oi.getQuantity() + "',name='" + oi.getName() + "' WHERE orderID='" + o.getID() + "' AND gameID='" + oi.getGameID() + "'";
			DBAccess.getInstance().Execute(query);
		}
	}
	public void delete(int key) {
		String query = "DELETE FROM Orders WHERE id=" + key;
		DBAccess.getInstance().Execute(query);
		soleInstance.order.remove(key);
	}
	public void deleteOrderItem(int oid, int gid)
	{
		String query = "DELETE FROM OrderItems WHERE orderID='" + oid + "' AND gameID='" + gid + "'";
		DBAccess.getInstance().Execute(query);
		soleInstance.order.remove(oid);
		
	}

	public void CleanAll() {
		for (Map.Entry<Integer, Order> entry : order.entrySet()) {
			entry.getValue().setStatus(DBStatus.CLEAN);
		}
		
	}
	
}


