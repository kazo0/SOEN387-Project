package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import patterns.CartMapper;
import patterns.OrderMapper;

public class Order extends DomainObject {
	
	private List<OrderItem> orderedItems = new ArrayList<OrderItem>();
	private User user;
	private String orderStatus;
	
	
	public Order(int id, List<OrderItem> orderItems, String orderStatus, User user)
	{
		super(id, DBStatus.CLEAN);
		this.orderedItems = orderItems;
		this.user = user;
		this.orderStatus = orderStatus;
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
	
	public String getOrderStatus() {
		return this.orderStatus;
		
	}
	
	public String setOrderStatus(String _status) {
		return this.orderStatus = _status;
		
	}
	
	public void updateItemPrice(int index, double price)
	{
		
		// Manage Lock Item in DB
		orderedItems.get(index).setPrice(price);
		OrderMapper.getInstance().update(this);
	}
	
	public boolean updateItemQty(int index, int qty)
	{
		if (OrderMapper.getInstance().checkQuantities(qty, this, orderedItems.get(index).getGameID()))
		{
			return true;
		}
		orderedItems.get(index).setQuantity(qty);
		OrderMapper.getInstance().update(this);
		return false;
	}
	
	public void deleteItem(int index)
	{
		OrderMapper.getInstance().deleteOrderItem(this.getID(), orderedItems.get(index));
		orderedItems.remove(index);
		if (orderedItems.isEmpty())
		{
			OrderMapper.getInstance().delete(this.getID());
		}
	}
	
	public void delete()
	{
		Iterator<OrderItem> it = orderedItems.iterator();
		while(it.hasNext())
		{
			OrderItem oi = it.next();
			OrderMapper.getInstance().deleteOrderItem(this.getID(), oi);
			it.remove();
		}
		OrderMapper.getInstance().delete(this.getID());

	}
	
	public void cleanup()
	{
		Iterator<OrderItem> it = orderedItems.iterator();
		while(it.hasNext())
		{
			OrderItem oi = it.next();
			if (oi.getQuantity() == 0)
			{
				OrderMapper.getInstance().deleteOrderItem(this.getID(), oi);
				it.remove();
			}
		}
		if (this.isEmpty())
		{
			OrderMapper.getInstance().delete(this.getID());
		}
	}
	
	public boolean isEmpty()
	{
		return orderedItems.isEmpty();
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
