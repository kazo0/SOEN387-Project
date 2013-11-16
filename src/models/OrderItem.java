package models;

public class OrderItem {
	
	private int gameID;
	private int quantity;
	private double price;
	
	
	public OrderItem(int gameID, int quantity, double price) {
		super();
		this.gameID = gameID;
		this.quantity = quantity;
		this.price = price;
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
	
	

}
