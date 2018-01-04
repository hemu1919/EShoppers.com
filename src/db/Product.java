package db;

import java.io.Serializable;
import java.text.NumberFormat;

public class Product implements Serializable {

	private long id;
	private Seller seller;
	private String name;
	private String desc;
	private String type;
	private String imagePath;
	private double price;
	private int count;
	private boolean isPublished;
	
	public Product() {
		super();
	}

	public Product(Seller seller, String name, String desc, String type, double price, int count, boolean isPublished) {
		super();
		this.seller = seller;
		this.name = name;
		this.desc = desc;
		this.type = type;
		this.price = price;
		this.count = count;
		this.isPublished = isPublished;
	}
	
	public boolean isPublished() {
		return isPublished;
	}

	public void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSid() {
		return seller.getId();
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public double getPrice() {
		return price;
	}
	
	public String getPriceFormatted() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(price);
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public Seller getSeller() {
		return seller;
	}
	
}
