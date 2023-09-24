package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
	String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//首先要在数据库中新建一个叫 USER 的数据库
	String conStr = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=USER";
	// 数据库连接用户名
	String dbUserName = "id"; 
	// 数据库连接密码
	String dbPassword = "123456"; 
	private static Connection con = null;
	public DataBase() {
		try {
			Class.forName(driverName);
			System.out.println("[加载驱动成功]");
		}  
		catch (Exception sqlEx) {
			sqlEx.printStackTrace();
			System.out.println("[加载驱动失败]");
		}
		try{
			con=DriverManager.getConnection(conStr,dbUserName,dbPassword);
			System.out.println("SQL Server连接成功！");
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.out.print("SQL Server连接失败！"); 
		}
		
	}

	public static Connection getCon() {
		return con;
	}
	
	public void closeCon(Connection con) throws SQLException{
		if(con!= null) {
			con.close();
		}	
	}
	
	public static boolean logIn(String username, String password) {
		String sql = "select Username from hl_admin where Username=? and Password=?";
		boolean flag = false;
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				flag = true;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	String primaryQuestion() {
		return "";
	}
	
	String middleQuestion() {
		return "";
	}
	
	String highQuestion() {
		return "";
	}
	void check() {
		
	}
}
