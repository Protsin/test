package entity;

import dao.UserDao;
import javax.print.DocFlavor.READER;

/**
 * @Author : Ted
 * @create 2023/9/19 10:42 类功能:
 */
public class User {
  String username;
  String email;
  String password;
  int state;

  public User(String name,String pwd){
    username=name;
    password=pwd;
    state= UserDao.getUserState(name);
  }
  public String getUsername(){
    return username;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String name) {
    email = name;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public int getState() {
    return state;
  }
  public void setState(int state) {
    this.state = state;
  }

}
