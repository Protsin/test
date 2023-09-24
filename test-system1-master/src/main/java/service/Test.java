package service;

import static service.SendMail.sendEmail;

import com.mysql.cj.log.Log;
import dao.UserDao;
import entity.Question;
import entity.User;
import entity.impl.QuestionImpl;
import java.util.Scanner;
import javax.mail.MessagingException;

/**
 * @Author : Ted
 * @create 2023/9/20 10:52 类功能:
 */
public class Test {


  public static void registerTest(String email){
    //1.模拟注册
    String code=SendMail.generateCode(); //验证码生成
    Scanner sc=new Scanner(System.in);
    int registerRes= 0;
    try {
      registerRes = SendMail.sendEmail(email,code);  //发送验证码
    } catch (MessagingException e) {
      e.printStackTrace();
    }

    System.out.println("请输入验证码：");
    String usercode=sc.next();  //用户填的验证码
    //下面对返回值处理，这里就不仔细处理了
    if(registerRes!=0){
      System.out.println("邮箱出现错误 "+registerRes);
    }
    //注册初中老师
    int res=UserDao.register("tedddyy",email,"Gjh123456",1,code,usercode);
    if(res!=0){
      System.out.println("注册错误 "+res);
      return;
    }
    System.out.println("注册成功");
  }

  /**
   * 登录测试
   */
  public static User loginTest(String username,String pwd){
    //直接调用dao的login方法
    User user=UserDao.login(username,pwd);
    if(user!=null){
      System.out.println("登录成功");
    }
    else{
      System.out.println("登录失败");
      return null;
    }
    return user;
  }

  /**
   * 修改密码测试
   */
  public static void changePwdTest(User user,String newPwd){
    //直接调用dao
    int res1=UserDao.updatePassword(user,newPwd);
    if(res1==0){
      System.out.println("修改密码成功");
    }else{
      System.out.println("修改密码失败");
    }
  }

  /**
   * 模拟出题
   * @param user：当前用户
   */
  public static void generateTest(User user){
    int num=15;  //出题数目
    QuestionImpl question=new QuestionImpl(num);

    //生成题目和选项
    question.generateQuestion(0,user,num);
    //比较结果
    String result=question.getScore(question.getCorrectPosition());

    System.out.println("总分数为: "+result);
    System.out.println("*******完成*******");
  }

  public static void main(String[] args) throws MessagingException {
    //1.模拟注册
    //registerTest("1375809659@qq.com");

    //2.登录测试
    User user=loginTest("tedddy","Gjh123");

    //3.修改密码
    //changePwdTest(user,"Gjh123456");

    //4.模拟出题
    generateTest(user);

    //5.改变为高中，继续出题：
    //QuestionImpl question=new QuestionImpl();
    //user.setState(2);

    //generateTest(user);


  }
}
