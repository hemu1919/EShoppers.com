package db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
	
	private long id;
	private long bid;
	private List<Item> items;
	private String shippedTo;
	private double price;
	
	public Order() {}

	public Order(long bid, String shippedTo, double price) {
		super();
		this.bid = bid;
		this.items = new ArrayList<>();
		this.shippedTo = shippedTo;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getShippedTo() {
		return shippedTo;
	}

	public void setShippedTo(String shippedTo) {
		this.shippedTo = shippedTo;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
