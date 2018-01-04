package db;

import java.io.Serializable;
import java.text.NumberFormat;

public class Item implements Serializable{
	
	private long id;
	private Product product;
	private int count;
	
	public Item() {}

	public Item(Product product) {
		this.id = -1;
		this.product = product;
		this.count = 1;
	}
	
	public Item(Product product, int count) {
		this.product = product;
		this.count = count;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public String getPriceFormatted() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(count * product.getPrice());
		
	}
	
}
