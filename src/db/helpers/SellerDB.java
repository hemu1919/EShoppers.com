package db.helpers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.Product;
import db.Seller;

public class SellerDB {
	
	public static int insert(Seller seller) throws SQLException {
	
		Connection conn = null;
		PreparedStatement pst = null;
		
		try {
			conn = DBUtil.getConnection();
			pst = conn.prepareStatement("insert into sellers (email, passwd, company_name, brand_name) values "
					+ "(?, ?, ?, ?)");
			pst.setString(1, seller.getEmail());
			pst.setString(2, seller.getPasswd());
			pst.setString(3, seller.getCompany_name());
			pst.setString(4, seller.getBrand_name());
			return pst.executeUpdate();
			
		}
		finally {
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}
	
	public static Seller get(String email, String passwd) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			pst = conn.prepareStatement("select * from sellers where email like ? and passwd like ?");
			pst.setString(1, email);
			pst.setString(2, passwd);
			rs = pst.executeQuery();
			if(!rs.first()) {
				return null;
			}
			Seller seller = new Seller(rs.getString("email"), rs.getString("passwd"), rs.getString("company_name"), rs.getString("brand_name"));
			seller.setId(rs.getInt("id"));
			return seller;
			
		}
		finally {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}
	
	public static boolean check(String email) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			pst = conn.prepareStatement("select count(*) from sellers where email like ?");
			pst.setString(1, email);
			rs = pst.executeQuery();
			rs.first();
			return rs.getInt(1) == 1;
			
		}
		finally {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}

	public static Seller get(Connection conn, long id) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Seller seller = null;
		boolean flag = false;
		if(id == 0) {
			return seller;
		}
		try {
			if(conn == null) { conn = DBUtil.getConnection(); flag = true; }
			pst = conn.prepareStatement("select * from sellers where id = ?");
			pst.setLong(1, id);
			rs = pst.executeQuery();
			if(rs.first()) {
				seller = new Seller(rs.getString("email"), "N/A", rs.getString("company_name"), rs.getString("brand_name"));
				seller.setId(rs.getLong("id"));
			}
			return seller;
			
		}
		finally {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
			if(flag) DBUtil.closeConnection(conn);
		}
	}
}
