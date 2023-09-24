package gui;

import entity.User;
import entity.impl.QuestionImpl;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Group;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class QuestionGenerate extends JFrame {

  private static final long serialVersionUID = 1L;
  private JPanel contentPane;
  private JTextField textField;
  static int i;
  int []x=new int[41];
  static String questions[];
  static String answers[][];
  User user;
  int state;
  int num;
  QuestionImpl question;

  public QuestionGenerate(int num, User user,int state) {
    this.user=user;
    this.state=state;
    this.num=num;
    question=new QuestionImpl(num);
  }


  public void manage() {//生成前端对应的页面和用户交互
    //this.generateQuestion(num);

    //生成对应的页面,传入的参数依次为题目，选项1-4
    //DisaplayQuestion page=new DisplayQuestion(user,String question,String selections[i][0],String selections[i][1],String selections[i][2],String selections[i][3]);
    //调用DisplayQuestion的函数得到order（返回值为1表示下一题，-1表示上一题），answer（1-4表示选择abcd四个选项）

    //生成问题
    question.generateQuestion(state,user,num);
    int []op=new int[num];
    DisplayQuestion page=new DisplayQuestion(user,question.getTests(),question.getSelections(),0,num,op,question);
    int order=DisplayQuestion.order;

    if(order==-1) {
      i-=2;//
    }

    page.setVisible(true);

  }


}