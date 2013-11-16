package models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private List<OrderItem> games;
	
	public Cart()
	{
		games = new ArrayList<OrderItem>();
	}
	
	public int getItemCount()
	{
		return games.size();
	}
	
	public void deleteItem(int index)
	{
		games.remove(index - 1);
	}
	
	public void updateItem(int index, int quantity)
	{
		if (quantity == 0)
		{
			deleteItem(index);
			return;
		}
		games.get(index).setQuantity(quantity);
	}
	
	public void addItem(int gameID, double price)
	{
		games.add(new OrderItem(gameID, 1, price));
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

}
