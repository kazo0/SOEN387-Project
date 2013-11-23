package models;

import java.util.ArrayList;
import java.util.List;

public class Cart extends DomainObject {
	private List<OrderItem> games;
	
	public Cart(int userID)
	{
		super(userID);
		games = new ArrayList<OrderItem>();
	}
	
	public Cart(int userID, ArrayList<OrderItem> items)
	{
		super(userID);
		this.games = items;
	}
	
	public int getItemCount()
	{
		return games.size();
	}
	
	public void deleteItem(int index)
	{
		// Manage unLock Item in DB
		games.remove(index);
	}
	
	public void updateItem(int index, int quantity)
	{
		
		// Manage Lock Item in DB
		if (quantity == 0)
		{
			deleteItem(index);
			return;
		}
		games.get(index).setQuantity(quantity);
	}
	
	public void addItem(int gameID, double price, String name)
	{
		
		// Manage Lock Item in DB
		for (OrderItem oi : games)
		{
			if (oi.getGameID() == gameID)
			{
				oi.setQuantity(oi.getQuantity() + 1);
				return;
			}
		}
		games.add(new OrderItem(gameID, 1, price, name));
	}
	
	public double getTotal()
	{
		double total = 0;
		for(OrderItem oi : games)
		{
			total += oi.getPrice() * oi.getQuantity();
		}
		return total;
	}
	
	public OrderItem[] getItems()
	{
		return games.toArray(new OrderItem[games.size()]);
	}
	
	public List<OrderItem> getOrderItems()
	{
		return games;
	}

}
