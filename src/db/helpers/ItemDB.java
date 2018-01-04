package db.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

import com.mysql.jdbc.Statement;

import db.Item;
import db.Product;

public class ItemDB {
	
	public static long insert(Connection conn, Item item) throws SQLException {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		long id = 0;
		boolean flag = false;
		
		try {
			if(conn == null) { conn = DBUtil.getConnection(); flag = true; }
			pst = conn.prepareStatement("insert into items (pid, count) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
			pst.setLong(1, item.getProduct().getId());
			pst.setInt(2, item.getCount());
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			while(rs.next())
				id = rs.getLong(1);
			item.setId(id);
			return id;
		}
		finally {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
			if(flag) DBUtil.closeConnection(conn);
		}
	}
	
	public static int update(Connection conn, Item item) throws SQLException {
		
		PreparedStatement pst = null;
		boolean flag = false;
		
		try {
			if(conn == null) { conn = DBUtil.getConnection(); flag = true; }
			pst = conn.prepareStatement("update items set pid=?, count=? where id=?");
			pst.setLong(1, item.getProduct().getId());
			pst.setInt(2, item.getCount());
			pst.setLong(3, item.getId());
			return pst.executeUpdate();
		}
		finally {
			if(pst != null)
				pst.close();
			if(flag) DBUtil.closeConnection(conn);
		}
	}
	
	public static int remove(Item item) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pst = null;
		
		try {
			conn = DBUtil.getConnection();
			pst = conn.prepareStatement("delete from items where id=?");
			pst.setLong(1, item.getId());
			return pst.executeUpdate();
		}
		finally {
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}
	
	public static Item get(Connection conn, long id) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean flag = false;
		Item item = null;
		if(id == 0) {
			return item;
		}
		try {
			if(true) { conn = DBUtil.getConnection(); flag = true; }
			pst = conn.prepareStatement("select * from items where id = ?");
			pst.setLong(1, id);
			rs = pst.executeQuery();
			if(rs.first()) {
				item = new Item(null, rs.getInt("count"));
				item.setId(rs.getLong("id"));
				Product product = ProductDB.getProduct(conn, rs.getLong("pid"));
				item.setProduct(product);
			}
			return item;
		}
		finally {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}
	
}
