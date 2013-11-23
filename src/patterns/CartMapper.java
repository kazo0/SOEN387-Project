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
import models.Game;
import models.Cart;
import models.Order;
import models.OrderItem;
import database.DBAccess;
import database.JdbcUtilViaSSH;
import database.SSHjdbcSession;

public class CartMapper {

	
	private static CartMapper soleInstance = null;
	
	public static CartMapper getInstance() {
		if (soleInstance == null) soleInstance =  new CartMapper();
		
		return soleInstance;
	}
	
	private Map<Integer, Cart> cart = new HashMap<Integer, Cart>(); 
		
	public void addCartToMap(Cart arg) {
		soleInstance.cart.put(arg.getID(), arg);
	}

	public Cart findById(int userId) {
		
		Cart o = null;
		List<OrderItem> oi = new ArrayList<OrderItem>();
		int gid = 0;
		int qty = 0;
		double price = 0;
		String name = "";
		
		String query = "select * from Carts where Carts.userID = '"+ userId + "'";
		ResultSet rs = DBAccess.getInstance().ExecuteQuery(query);
		try {

			while(rs.next()) {
				gid = rs.getInt("gameID");
				qty = rs.getInt("quantity");
				price = rs.getDouble("price");
				name = rs.getString("name");
				
				oi.add(new OrderItem(gid, qty, price, name));
			}
			if (!oi.isEmpty())
			{
				o = new Cart(userId, new ArrayList<OrderItem>(oi));
			}
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	
	public Cart get(int key) {
		Cart c =  (Cart) soleInstance.cart.get(key);
		if (c == null) {
			try{
				c = findById(key);
				this.addCartToMap(c);
				}
			catch (Exception ex) {
					
			}
		}
		
		return c;
	}
	
	public void insert (DomainObject obj) {
		Cart c = (Cart) obj;

		
		// Set id from DBs

		String values = "";
		String query = "";
		
		for (OrderItem oi : c.getOrderItems())
		{
			values = "'" + c.getID() + "','" + oi.getGameID() + "','" + oi.getQuantity() + "','" + oi.getPrice() + "','" + oi.getName() + "'";
			query = "INSERT INTO Carts (userID, gameID, quantity, price, name) VALUES (" + values + ")";
			DBAccess.getInstance().Execute(query);
			soleInstance.cart.put(c.getID(), c);
		}
		
		
		
	}
	
	public void update(DomainObject obj) {
		Cart c = (Cart) obj;
		for (OrderItem oi : c.getOrderItems())
		{
			String query = "UPDATE Carts SET cartID='" + c.getID() + "',gameID='" + oi.getGameID() + "',quantity='" + oi.getQuantity() + "', price='" + oi.getPrice() + "',name='" + oi.getName() + "' WHERE userID='" + c.getID() + "' AND gameID='" + oi.getGameID() + "'";
			DBAccess.getInstance().Execute(query);
		}
	}
	public void delete(int key) {
		String query = "DELETE FROM Carts WHERE userID=" + key;
		DBAccess.getInstance().Execute(query);
		soleInstance.cart.remove(key);
	}
	
	public void deleteCartItem(int userID, int gid, int oiIndex)
	{
		String query = "DELETE FROM Carts WHERE userID='" + userID + "' AND gameID='" + gid + "'";
		DBAccess.getInstance().Execute(query);
		
		Cart c = soleInstance.get(userID);
		c.getOrderItems().remove(oiIndex);
		
		
		if (c.getOrderItems().size() == 0)
		{
			soleInstance.delete(userID);
		}
		
	}
	
}


