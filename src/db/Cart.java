package db;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
	
	private long id;
	private List<Item> items;
	
	public Cart() {
		items = new ArrayList<>();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public String listItems() {
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < items.size(); i++) result.append(String.valueOf(items.get(i).getId()) + (i == items.size() - 1 ? "" : " "));
		return result.toString();
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public void addItem(Item item) {
		if(item.getId() == -1) item.setId(items.size());
		items.add(item);
	}
	
	public void removeItem(Item item) {
		items.remove(item);
	}
	
	public boolean search(Product product) {
		for(Item item: items) {
			if(item.getProduct().getId() == product.getId()) return true;
		}
		return false;
	}
	
	public Item getItem(Product product) {
		for(Item item: items) {
			if(item.getProduct().getId() == product.getId()) return item;
		}
		return null;
	}

	public Item removeItem(long id) {
		for(Item item: items) {
			if(item.getId() == id) {
				removeItem(item);
				return item;
			}
		}
		return null;
	}
	
	public Item updateItem(int id, int count) {
		for(Item item: items) {
			if(item.getId() == id) {
				item.setCount(count);
				return item;
			}
		}
		return null;
	}
	
	public double getPrice() {
		double total = 0;
		for(Item item: items) total += item.getCount() * item.getProduct().getPrice();
		return total;
	}
	
	public String getPriceFormatted() {
		double total = 0;
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		for(Item item: items) total += item.getCount() * item.getProduct().getPrice();
		return formatter.format(total);
	}

	public void removeAll() {
		items.clear();
		items = new ArrayList<>();
	}
	
}
