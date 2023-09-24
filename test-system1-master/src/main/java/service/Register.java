package service;

import dao.MyConnection;
import dao.UserDao;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : Ted
 * @create 2023/9/19 10:50 类功能:
 */

/**
 * 每次注册前，需要发送验证码；没发送，则原始code=null，肯定不通过；
 * 如果邮箱错误，在sendEmail.sendEmail时就会提醒；
 * 只有email正确，且发送了验证码、且匹配，并且邮箱不重复，密码合规，才能成功
 */

public class Register {
  String authCode=null;
  public static Set<String> allUserEmails=null;
  public static Set<String> allUserName=null;


  public static boolean isEmailValid(String email){
    String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    Pattern regex = Pattern.compile(check);

    Matcher matcher = regex.matcher(email);
    boolean isMatched = matcher.matches();
    if(isMatched==false){
      return false;
    }

    if(email.length()<=7){
      return false;
    }
    else if("@qq.com".equals(email.substring(email.length()-7))){
      return true;
    }
    return false;
  }

  public static boolean isEmailRepeated(String email){
    if(allUserEmails==null){
      allUserEmails=UserDao.getAllEmail();
    }
    if(allUserEmails.contains(email)){
      return true;
    }
    return false;
  }
  public static boolean isNameRepeated(String username){
    if(allUserName==null){
      allUserName=UserDao.getAllName();
    }
    if(allUserName.contains(username)){
      return true;
    }
    return false;
  }
  public static boolean isPwdValid(String pwd){
    if(pwd!=null&&pwd.length()>=6&&pwd.length()<=12){
      if(pwd.matches(".*[A-Z].*") && pwd.matches(".*[a-z].*")){
        return true;
      }
    }
    return false;
  }
  public static boolean isNameValid(String username){
    if(username!=null&&username.length()>=6&&username.length()<=12){
      return true;
    }
    return false;
  }
}
