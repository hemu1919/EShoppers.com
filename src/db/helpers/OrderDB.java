package db.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.Cart;
import db.Item;
import db.Product;

public class OrderDB {

public static int insert(Cart cart, long bid) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pst = null;
		
		try {
			conn = DBUtil.getConnection();
			List<Item> items = cart.getItems();
			for(Item item: items) {
				Product product = item.getProduct();
				product.setCount(product.getCount() - item.getCount());
				String[] parts = product.getImagePath().split("/");
				product.setImagePath(parts[parts.length - 1]);
				ProductDB.update(conn, product);
			}
			pst = conn.prepareStatement("insert into orders (bid, price, shippedto, items) values (?, ?, ?, ?)");
			pst.setLong(1, bid);
			pst.setDouble(2, cart.getPrice());
			pst.setString(3, BuyerDB.getShippingAddress(conn, bid));
			pst.setString(4, cart.listItems());
			int result = pst.executeUpdate();
			cart.removeAll();
			CartDB.update(conn, cart);
			return result;
		}
		finally {
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}
	
}
