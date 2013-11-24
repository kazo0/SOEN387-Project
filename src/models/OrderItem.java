package models;

public class OrderItem {
	
	private int gameID;
	private int quantity;
	private double price;
	private String name;
	
	
	public OrderItem(int gameID, int quantity, double price, String name) {
		super();
		this.gameID = gameID;
		this.quantity = quantity;
		this.price = price;
		this.name = name;
	}
	
	public OrderItem(OrderItem oi) {
		super();
		this.gameID = oi.getGameID();
		this.quantity = oi.getQuantity();
		this.price = oi.getPrice();
		this.name = oi.getName();
	}

	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
