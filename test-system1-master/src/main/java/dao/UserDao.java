package dao;

import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import service.Register;

/**
 * @Author : Ted
 * @create 2023/9/19 20:08 类功能:
 */
public class UserDao {

  //获取所有用户的email；
  public static Set<String> getAllEmail(){
    Set<String> set=new HashSet<>();
    Connection conn = null;//连接
    PreparedStatement psmt = null;
    ResultSet rs = null;//结果集
    String password = new String();//查找后的密码
    String sql = "select email from t_user";//查找该用户名的用户语句
    try {
      conn = MyConnection.getConn();//获得连接
      PreparedStatement ptmt = conn.prepareStatement(sql);
      rs = ptmt.executeQuery();//获得结果集
      String email=null;
      while(rs.next()){
        email= rs.getString("email");//得到密码
        set.add(email);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return set;
  }
  //获取所有用户的email；
  public static Set<String> getAllName(){ 
    Set<String> set=new HashSet<>();
    Connection conn = null;//连接
    PreparedStatement psmt = null;
    ResultSet rs = null;//结果集
    String password = new String();//查找后的密码
    String sql = "select username from t_user";//查找该用户名的用户语句
    try {
      conn = MyConnection.getConn();//获得连接
      PreparedStatement ptmt = conn.prepareStatement(sql);
      rs = ptmt.executeQuery();//获得结果集
      String name=null;
      while(rs.next()){
        name= rs.getString("username");//得到密码
        set.add(name);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return set;
  }

  public static int getUserState(String username){
    Connection conn = null;//连接
    PreparedStatement psmt = null;
    ResultSet rs = null;//结果集
    String password = new String();//查找后的密码
    String sql = "select state from t_user where username=?";//查找该用户名的用户语句
    try {
      conn = MyConnection.getConn();//获得连接
      PreparedStatement ptmt = conn.prepareStatement(sql);
      ptmt.setString(1,username);
      rs = ptmt.executeQuery();//获得结果集
      int state=0;
      if(rs.next()){
        state= rs.getInt("state");//得到state
      }
      return state;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  public static User login(String username,String pwd){//用于对用户是否能够登录进行判断
    Connection conn = null;//连接
    PreparedStatement psmt = null;
    ResultSet rs = null;//结果集
    String password = new String();//查找后的密码
    String sql = "select * from t_user where  username=?";//查找该用户名的用户语句
    try {
      conn = MyConnection.getConn();//获得连接
      PreparedStatement ptmt = conn.prepareStatement(sql);
      ptmt.setString(1,  username);
      rs = ptmt.executeQuery();//获得结果集
      while(rs.next()){
        password = rs.getString("password");//得到密码
      }
      if(pwd!=null&&pwd.equals(password)){//判断查找数据库的密码是否与用户输入的密码相等
        return new User(username,password);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String getPassword(String name){//获得对应用户名的密码
    Connection conn = null;
    PreparedStatement psmt = null;
    ResultSet rs = null;
    String password = new String();
    String sql = "select * from t_user where name=?";//查找相应用户名的用户
    try {
      conn = MyConnection.getConn();
      PreparedStatement ptmt = conn.prepareStatement(sql);
      ptmt.setString(1, name);
      rs = ptmt.executeQuery();
      while(rs.next()){
        password = rs.getString("password");//获得密码
      }
      return password;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static int updatePassword(User user,String newPwd){//用于改变相应用户名的密码
    Connection conn = null;
    PreparedStatement psmt = null;
    if(newPwd==null||!Register.isPwdValid(newPwd)){
      return -1;
    }
    else if(newPwd.equals(user.getPassword())){
      return -2;
    }
    String sql = "update t_user set password=? where username=?";//更新相应用户名的密码
    try {
      conn = MyConnection.getConn();
      PreparedStatement ptmt = conn.prepareStatement(sql);
      ptmt.setString(2, user.getUsername());
      ptmt.setString(1, newPwd);
      ptmt.execute();
    } catch (Exception e) {
      e.printStackTrace();
      return 1;
    }
    user.setPassword(newPwd);
    return 0;
  }

  public static int register(String username,String email,String password,int state,String originCode,String authCode){//用于注册用户
    Connection conn = null;
    PreparedStatement psmt = null;

    //如果邮箱不合法
    if(!Register.isEmailValid(email)){
      return -1;
    }
    //如果邮箱已经注册
    else if(Register.isEmailRepeated(email)){
      return -2;
    }
    //用户名不合法或用户名已经被注册
    else if(!Register.isNameValid(username)||Register.isNameRepeated(username)){
      return -5;
    }
    //密码不合法 （6-12位，包含大小写字母）
    else if(!Register.isPwdValid(password)){
      return -6;
    }


    //如果code不存在，或者验证码不匹配
    else if(originCode==null||!originCode.equals(authCode)){
      return -3;
    }

    //向数据库增加新用户信息
    String sql = "insert into t_user(username,email,password,state) values(?,?,?,?)";
    try {
      conn = MyConnection.getConn();
      PreparedStatement ptmt = conn.prepareStatement(sql);
      ptmt.setString(1, username);
      ptmt.setString(2, email);
      ptmt.setInt(4, state);
      ptmt.setString(3, password);
      ptmt.execute();
    } catch (Exception e) {
      e.printStackTrace();
      return -4;
    }
    //添加email信息
    Register.allUserEmails.add(email);
    Register.allUserName.add(username);
    return 0;
  }


}
