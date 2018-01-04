package db.helpers;

import java.sql.*;

public class DBUtil {

//	public static Connection getConnection() {
//		return ConnectionPool.getInstance().getConnection();
//	}
	
	public static Connection getConnection() {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			con = DriverManager.getConnection("jdbc:mysql://mydb.cqph6opmxi84.us-east-2.rds.amazonaws.com/eshoppers_db","root","rootroot");
		} catch (SQLException e) {
			System.err.println("ERROR : Cannot create the connection.");
			System.err.println("ERROR : \nSQL Error Code : "+e.getErrorCode()+" SQL State : "+e.getSQLState());
		}
		return con;
	}  
	
	
	public static void closeConnection(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			System.err.println("ERROR : Cannot close the connection.");
			System.err.println("ERROR : \nSQL Error Code : "+e.getErrorCode()+" SQL State : "+e.getSQLState());
		}
	}

}