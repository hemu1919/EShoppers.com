package db.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.Product;
import db.Seller;
import helpers.Utils;

public class ProductDB {

	public static int insert(Product product) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pst = null;
		
		try {
			conn = DBUtil.getConnection();
			pst = conn.prepareStatement("insert into products (sid, prod_name, description, price, count, imagepath, prod_type) values "
					+ "(?, ?, ?, ?, ?, ?, ?)");
			pst.setLong(1, product.getSid());
			pst.setString(2, product.getName());
			pst.setString(3, product.getDesc());
			pst.setDouble(4, product.getPrice());
			pst.setInt(5, product.getCount());
			pst.setString(6, product.getImagePath());
			pst.setString(7, product.getType());
			return pst.executeUpdate();
			
		}
		finally {
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}
	
	public static int update(Connection conn, Product product) throws SQLException {
		
		PreparedStatement pst = null;
		boolean flag = false;
		
		try {
			if(conn == null) { conn = DBUtil.getConnection(); flag = true; }
			pst = conn.prepareStatement("update products set sid=?, prod_name=?, description=?, price=?, count=?, imagepath=?, prod_type=? where id=?");
			pst.setLong(1, product.getSid());
			pst.setString(2, product.getName());
			pst.setString(3, product.getDesc());
			pst.setDouble(4, product.getPrice());
			pst.setInt(5, product.getCount());
			pst.setString(6, product.getImagePath());
			pst.setString(7, product.getType());
			pst.setLong(8, product.getId());
			return pst.executeUpdate();
			
		}
		finally {
			if(pst != null)
				pst.close();
			if(flag) DBUtil.closeConnection(conn);
		}
	}
	
	public static int setPublished(long id, boolean isPublished) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pst = null;
		
		try {
			conn = DBUtil.getConnection();
			pst = conn.prepareStatement("update products set ispublished=? where id=?");
			pst.setInt(1, isPublished ? 1 : 0);
			pst.setLong(2, id);
			return pst.executeUpdate();
			
		}
		finally {
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}

	public static List<Product> get(long sid) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Product> products = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			pst = conn.prepareStatement("select * from products where sid = ?");
			pst.setLong(1, sid);
			rs = pst.executeQuery();
			while(rs.next()) {
				Product product = new Product(null, rs.getString("prod_name"), rs.getString("description"), rs.getString("prod_type"), rs.getDouble("price"), rs.getInt("count"), rs.getInt("ispublished") == 1);
				product.setImagePath(rs.getString("imagepath"));
				product.setId(rs.getLong("id"));
				Seller seller = SellerDB.get(conn, rs.getLong("sid"));
				product.setSeller(seller);
				products.add(product);
			}
			return products;
			
		}
		finally {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}
	
	public static List<Product> get() throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Product> products = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			pst = conn.prepareStatement("select * from products where ispublished = 1");
			rs = pst.executeQuery();
			while(rs.next()) {
				Product product = new Product(null, rs.getString("prod_name"), rs.getString("description"), rs.getString("prod_type"), rs.getDouble("price"), rs.getInt("count"), rs.getInt("ispublished") == 1);
				product.setImagePath(Utils.getStorageURL(rs.getString("imagepath")));
				product.setId(rs.getLong("id"));
				Seller seller = SellerDB.get(conn, rs.getLong("sid"));
				product.setSeller(seller);
				products.add(product);
			}
			return products;
			
		}
		finally {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}
	
	public static String remove(long id) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		Product product;
		
		try {
			product = getProduct(conn, id);
			String path = product.getImagePath();
			conn = DBUtil.getConnection();
			pst = conn.prepareStatement("delete from products where id=?");
			pst.setLong(1, id);
			pst.executeUpdate();
			return path;
		} finally {
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}

	public static Product getProduct(Connection conn, long id) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Product product = null;
		boolean flag = false;
		if(id == 0) {
			return product;
		}
		try {
			if(conn == null) { conn = DBUtil.getConnection(); flag = true; }
			pst = conn.prepareStatement("select * from products where id = ?");
			pst.setLong(1, id);
			rs = pst.executeQuery();
			if(rs.first()) {
				product = new Product(null, rs.getString("prod_name"), rs.getString("description"), rs.getString("prod_type"), rs.getDouble("price"), rs.getInt("count"), rs.getInt("ispublished") == 1);
				product.setImagePath(Utils.getStorageURL(rs.getString("imagepath")));
				product.setId(rs.getLong("id"));
				Seller seller = SellerDB.get(conn, rs.getLong("sid"));
				product.setSeller(seller);
			}
			return product;
			
		}
		finally {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
			if(flag) DBUtil.closeConnection(conn);
		}
	}
	
	public static List<List<Object>> getFilters() throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Object> types = new ArrayList<>();
		List<Object> sellers = new ArrayList<>();
		List<List<Object>> filters = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			pst = conn.prepareStatement("select distinct prod_type from products where ispublished = 1");
			rs = pst.executeQuery();
			while(rs.next()) {
				types.add(rs.getString("prod_type"));
			}
			rs.close();
			rs = pst.executeQuery("select distinct sid from products where ispublished = 1");
			while(rs.next()) {
				sellers.add(SellerDB.get(conn, rs.getLong("sid")));
			}
			filters.add(types);
			filters.add(sellers);
			return filters;
			
		}
		finally {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}

	public static String preCheck(Product product) throws SQLException {
		Product old = getProduct(null, product.getId());
		String path = null;
		if(old!= null && product.getImagePath() == null) {
			String[] parts = old.getImagePath().split("/");
			product.setImagePath(parts[parts.length - 1]);
			path = old.getImagePath();
		}
		return path;
	}
	
	public static List<Product> applyFilters(List<String> type, List<String> seller) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		if(seller.size() == 0 && type.size() == 0) return get();
		
		List<Product> products = new ArrayList<>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from products where isPublished = 1");
		
		if(type.size() != 0) {
			sql.append(" and prod_type in (");
			for(int i=0; i < type.size() - 1; i++) {
				sql.append("?, ");
			}
			sql.append("?)");
		}
		
		if(seller.size() != 0) {
			sql.append(" and sid in (");
			for(int i=0; i < seller.size() - 1; i++) {
				sql.append("?, ");
			}
			sql.append("?)");
		}
		
		try {
			conn = DBUtil.getConnection();
			pst = conn.prepareStatement(sql.toString());
			int count = 1;
			for(int i=0; i < type.size(); i++) {
				pst.setString(count++, type.get(i));
			}
			for(int i=0; i < seller.size(); i++) {
				pst.setLong(count++, Long.valueOf(seller.get(i)));
			}
			rs = pst.executeQuery();
			while(rs.next()) {
				Product product = new Product(SellerDB.get(conn, rs.getLong("sid")), rs.getString("prod_name"), rs.getString("description"), rs.getString("prod_type"), rs.getDouble("price"), rs.getInt("count"), rs.getInt("ispublished") == 1);
				product.setImagePath(Utils.getStorageURL(rs.getString("imagepath")));
				product.setId(rs.getLong("id"));
				Seller seller_obj = SellerDB.get(conn, rs.getLong("sid"));
				product.setSeller(seller_obj);
				products.add(product);
			}
			return products;
			
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
