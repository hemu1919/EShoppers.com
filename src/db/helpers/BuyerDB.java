package db.helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.Buyer;
import db.Cart;

public class BuyerDB {
	
public static int insert(Buyer buyer) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pst = null;
		
		try {
			conn = DBUtil.getConnection();
			pst = conn.prepareStatement("insert into buyers (fname, lname, email, passwd, addr, pincode, mobile, cid) values "
					+ "(?, ?, ?, ?, ?, ?, ?, ?)");
			pst.setString(1, buyer.getFname());
			pst.setString(2, buyer.getLname());
			pst.setString(3, buyer.getEmail());
			pst.setString(4, buyer.getPasswd());
			pst.setString(5, buyer.getAddr());
			pst.setInt(6, buyer.getPincode());
			pst.setString(7, buyer.getMobile());
			pst.setLong(8, buyer.getCart());
			return pst.executeUpdate();
			
		}
		finally {
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
			pst = conn.prepareStatement("select count(*) from buyers where email like ?");
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
	
	public static Buyer get(String email, String passwd) throws SQLException {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			pst = conn.prepareStatement("select * from buyers where email like ? and passwd like ?");
			pst.setString(1, email);
			pst.setString(2, passwd);
			rs = pst.executeQuery();
			if(!rs.first()) {
				return null;
			}
			Buyer buyer = new Buyer(rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("passwd"), rs.getString("addr"), rs.getInt("pincode"),
					rs.getString("mobile"), null);
			buyer.setId(rs.getInt("id"));
			Cart cart = CartDB.get(conn, rs.getLong("cid"));
			buyer.setCart(cart);
			return buyer;
		}
		finally {
			if(rs != null)
				rs.close();
			if(pst != null)
				pst.close();
			DBUtil.closeConnection(conn);
		}
	}

	public static String getShippingAddress(Connection conn, long bid) throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean flag = false;
		
		try {
			if(conn == null) { conn = DBUtil.getConnection(); flag = true; }
			pst = conn.prepareStatement("select addr, pincode, mobile from buyers where id=?");
			pst.setLong(1, bid);
			rs = pst.executeQuery();
			if(!rs.first()) {
				return null;
			}
			StringBuffer result = new StringBuffer();
			result.append(rs.getString("addr") + " - ");
			result.append(rs.getInt("pincode") + "<br/>");
			result.append("Mobile: " + rs.getString("mobile"));
			return result.toString();
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
