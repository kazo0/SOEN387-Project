package models;

import java.util.ArrayList;
import java.util.List;

import patterns.CartMapper;

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
		CartMapper.getInstance().deleteCartItem(this.getID(), games.get(index).getGameID());
		games.remove(index);
		if (games.isEmpty())
		{
			CartMapper.getInstance().delete(this.getID());
		}
		
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
		CartMapper.getInstance().update(this);
	}
	
	public void addItem(int gameID, double price, String name)
	{
		
		// Manage Lock Item in DB
		for (OrderItem oi : games)
		{
			if (oi.getGameID() == gameID)
			{
				oi.setQuantity(oi.getQuantity() + 1);
				CartMapper.getInstance().update(this);
				return;
			}
		}
		games.add(new OrderItem(gameID, 1, price, name));
		//If the list was just empty then create a new Order
		if (games.size() == 1)
		{
			CartMapper.getInstance().insert(this);
		}
		else //otherwise, add this orderitem row to the Cart table for the existing Cart
		{
			CartMapper.getInstance().insertCartItem(this, games.size()-1);
		}
		
		
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
	
	public boolean isEmpty()
	{
		return games.isEmpty();
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
