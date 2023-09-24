package dao;

import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author : Ted
 * @create 2023/9/18 18:58 类功能:
 */

public class MyConnection {
  public static final String driver = "com.mysql.cj.jdbc.Driver";
  public static final String url = "jdbc:mysql://localhost:3306/questions";
  public static final String user = "root";
  public static final String password = "gjh1375809659";
  
  static {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException var1) {
      var1.printStackTrace();
    }

  }

  public MyConnection() {
  }

  public static Connection getConn() {
    try {
      return DriverManager.getConnection("jdbc:mysql://localhost:3306/questions?userUnicode=true&characterEnconding=UTF-8&serverTimezone=UTC", "root", "gjh1375809659");
    } catch (SQLException var1) {
      var1.printStackTrace();
      return null;
    }
  }

  public static void close(ResultSet rs, PreparedStatement ps, Connection conn) {
    try {
      if (rs != null) {
        rs.close();
      }

      if (ps != null) {
        ps.close();
      }

      if (conn != null) {
        conn.close();
      }
    } catch (SQLException var4) {
      var4.printStackTrace();
    }

  }

  public static void main(String[] args) {
    String sql = "insert into t_user values(3,'刘备','xxxxxxx')";
    Connection conn = getConn();
    PreparedStatement ps = null;

    try {
      ps = conn.prepareStatement(sql);
      int count = ps.executeUpdate();
      if (count > 0) {
        System.out.print("保存成功");
      }
    } catch (SQLException var8) {
      var8.printStackTrace();
    } finally {
      close((ResultSet)null, ps, conn);
    }

  }
}
