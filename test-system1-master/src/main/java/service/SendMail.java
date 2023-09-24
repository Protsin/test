package service;

/**
 * @Author : Ted
 * @create 2023/9/19 17:26 类功能:
 */

import entity.User;
import java.net.UnknownHostException;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;


// 网站三秒原则
public class SendMail extends Thread {

  // 发送邮件的邮箱
  private  static String fromEmail = "137589659@qq.com";
  // 授权码
  private static String pwd = "hjdvqyrdulqdgbag";
  // 发送邮件的服务器地址
  private  static String host = "smtp.qq.com";

  private User user;

  public static String generateCode(){
    StringBuffer sb=new StringBuffer();
    Random random=new Random();
    random.setSeed(System.currentTimeMillis());
    for(int i=0;i<4;i++){
      sb.append(random.nextInt(10));
    }
    return sb.toString();
  }

  public static void main(String[] args) throws MessagingException {
    //SendMail.sendQQEmail("1375809659@qq.com","1234");
    //SendMail.sendMail("1375809659@qq.com","你好，这是一封测试邮件，无需回复。","测试邮件");
    String code=generateCode();
    sendEmail("1375@qq.com",code);
    //System.out.println("发送成功"+code);
    //System.out.println(Register.isEmailValid("1375809659@qq.com"));
    //System.out.println(Register.isEmailValid("137580965@q.com"));
    //System.out.println(Register.isEmailValid("13758096qq.com"));
  }

  /**
   * 返回值0表示发送成功，-1表示号码不存在，1表示其它错误
   * @param email
   * @param authCode
   * @return
   * @throws MessagingException
   */
  public static int sendEmail(String email,String authCode) throws MessagingException {
    // 创建Properties 类用于记录邮箱的一些属性
    Properties props = new Properties();
    // 表示SMTP发送邮件，必须进行身份验证
    props.put("mail.smtp.auth", "true");
    //此处填写SMTP服务器
    props.put("mail.smtp.host", "smtp.qq.com");
    //端口号，QQ邮箱端口587
    props.put("mail.smtp.port", "587");
    // 此处填写，写信人的账号
    props.put("mail.user", "1375809659@qq.com");
    // 此处填写16位STMP口令
    props.put("mail.password", "hjdvqyrdulqdgbag");

    // 构建授权信息，用于进行SMTP进行身份验证
    Authenticator authenticator = new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        // 用户名、密码，都不用改直接copy
        String userName = props.getProperty("mail.user");
        String password = props.getProperty("mail.password");
        return new PasswordAuthentication(userName, password);
      }
    };
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    String str =
        "<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body><p style='font-size: 20px;font-weight:bold;'>"+
            "您好！</p>"
            + "<p style='text-indent:2em; font-size: 20px;'>欢迎注册湖南大学考试平台，您本次的注册码是 "
            + "<span style='font-size:30px;font-weight:bold;color:red'>" + authCode
            + "</span>，3分钟之内有效，请尽快使用！</p>"
            + "<p style='text-align:right; padding-right: 20px;'"
            + "<a href='http://www.hyycinfo.com' style='font-size: 18px'>湖南大学文化有限公司</a></p>"
            + "<span style='font-size: 18px; float:right; margin-right: 60px;'>" + sdf.format(
            new Date()) + "</span></body></html>";
    // 使用环境属性和授权信息，创建邮件会话
    Session mailSession = Session.getInstance(props, authenticator);
    // 创建邮件消息
    MimeMessage message = new MimeMessage(mailSession);
    // 设置发件人，
    InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
    message.setFrom(form);
    // 设置收件人的邮箱
    InternetAddress to = new InternetAddress(email);
    message.setRecipient(MimeMessage.RecipientType.TO, to);
    // 设置邮件标题
    message.setSubject("验证码信息");
    // 设置邮件的内容体
    message.setContent(str, "text/html;charset=UTF-8");
    // 最后当然就是发送邮件啦
    //Transport.send(message);

    try{
      Transport.send(message);
    } catch (Exception e) {
      // 判断异常类型是否为UnknownHostException
      if (e instanceof com.sun.mail.smtp.SMTPSendFailedException) {
        System.out.println("该邮箱不存在");
        return -1;
      } else {
        System.out.println("出现了其它错误");
        return 1;
      }
    }
    System.out.println("发送验证码成功");
    return 0;

  }



}

