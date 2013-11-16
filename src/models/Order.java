package models;

import java.util.ArrayList;
import java.util.List;

public class Order extends DomainObject {
	
	private List<OrderItem> orderedItems = new ArrayList<OrderItem>();
	private User user;
	
	public Order(int id, List<OrderItem> orderItems, User user)
	{
		super(id, DBStatus.CLEAN);
		this.orderedItems = orderItems;
		this.user = user;
	}
	
	public List<OrderItem> getOrderedGames() {
		return orderedItems;
	}

	public void setOrderedGames(List<OrderItem> orderedItems) {
		this.orderedItems = orderedItems;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public double getTotal() {
		double total = 0;
		for ( OrderItem oi : orderedItems)
		{
			total += oi.getPrice() * oi.getQuantity();
		}
		return total;
	}
	
}