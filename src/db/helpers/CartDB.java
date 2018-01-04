package db.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.Cart;
import db.Item;

public class CartDB {
	
	public static Long insert(Cart cart) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		long id = 0;
		
		try {
			conn = DBUtil.getConnection();
			List<Item> items = cart.getItems();
			for(Item item: items) {
				ItemDB.insert(conn, item);
			}
			pst = conn.prepareStatement("insert into cart (itemsList) values (?)", Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, cart.listItems());
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			while(rs.next())
				id = rs.getLong(1);
			cart.setId(id);
			return id;
		}
		finally {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}
	
public static int update(Connection conn, Cart cart) throws SQLException {
	
		PreparedStatement pst = null;
		boolean flag = false;
		
		try {
			if(conn == null) { conn = DBUtil.getConnection(); flag = true; }
			pst = conn.prepareStatement("update cart set itemsList=? where id=?");
			pst.setString(1, cart.listItems());
			pst.setLong(2, cart.getId());
			return pst.executeUpdate();
			
		}
		finally {
			if(pst != null)
				pst.close();
			if(flag) DBUtil.closeConnection(conn);
		}
	}
	
	public static Cart get(Connection conn, long id) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Cart cart = null;
		boolean flag = false;
		if(id == 0) {
			return cart;
		}
		try {
			if(conn == null) { conn = DBUtil.getConnection(); flag = true; }
			pst = conn.prepareStatement("select * from cart where id = ?");
			pst.setLong(1, id);
			rs = pst.executeQuery();
			List<Item> items = new ArrayList<>();
			if(rs.first()) {
				cart = new Cart();
				cart.setId(rs.getLong("id"));
				String[] itemsList = rs.getString("itemsList").split(" ");
				for(int i = 0; i < itemsList.length; i++) {
					if(!itemsList[i].equals("")) items.add(ItemDB.get(conn, Long.valueOf(itemsList[i])));
				}
				cart.setItems(items);
			}
			return cart;
		}
		finally {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
			if(flag) DBUtil.closeConnection(conn);
		}
	}
	
	public static void mergeCarts(Cart cart, Cart temp) throws SQLException {
		Connection conn = null;
		
		try {
			conn = DBUtil.getConnection();
			List<Item> items = temp.getItems();
			for(Item item: items) {
				Item original = cart.getItem(item.getProduct());
				if(original != null) {
					original.setCount(item.getCount());
					ItemDB.update(conn, original);
				}
				else {
					ItemDB.insert(conn, item);
					cart.addItem(item);
				}
			}
			CartDB.update(conn,  cart);
		}
		finally {
			DBUtil.closeConnection(conn);
		}
	}
	
}
