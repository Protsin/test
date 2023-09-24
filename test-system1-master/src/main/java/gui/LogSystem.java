package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import entity.User;
import dao.UserDao;

public class LogSystem extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogSystem frame = new LogSystem();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LogSystem() {
		//初始化数据库
        //new Database();
        //判断数据库是否连接成功
        //if(Database.getCon() == null){
          //  JOptionPane.showMessageDialog(null, "数据库连接失败!", "提示", JOptionPane.ERROR_MESSAGE); 
           // System.exit(0);
        //}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(157, 34, 171, 44);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(157, 121, 171, 44);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel = new JLabel("用户名");
		lblNewLabel.setFont(new Font("楷体", Font.PLAIN, 20));
		lblNewLabel.setBounds(49, 31, 95, 44);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("密码");
		lblPassword.setFont(new Font("楷体", Font.PLAIN, 20));
		lblPassword.setBounds(49, 118, 111, 44);
		contentPane.add(lblPassword);
		
		JButton btnNewButton = new JButton("登录");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//点击登录后进行账号密码验证，成功登录后，进入到题目选择界面，“小初高”
				LogIn(e);
			}
		});
		btnNewButton.setBounds(90, 204, 90, 35);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("注册");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//点击注册后跳转到注册界面
				setVisible(false);
				new Regester();
			}
		});
		btnNewButton_1.setBounds(238, 204, 90, 35);
		contentPane.add(btnNewButton_1);
	}

	

	@SuppressWarnings("deprecation")
	protected void LogIn(MouseEvent e) {
		// TODO 自动生成的方法存根
				String username = textField.getText().toString();
				String password = passwordField.getText().toString();
				//判断字符串是否为空，这里是如果不为空，继续。
				if(!textField.getText().isEmpty() && !passwordField.getText().isEmpty())
				{ 
					//当点击登录按钮时，首先与数据库建立连接
					//DataBase.getCon();
					 //判断username和	password
					//boolean result = DataBase.logIn(textField.getText().trim(), passwordField.getText().trim());
					User user=UserDao.login(username,password);
					if(user!=null){
						//登录成功
						JOptionPane.showMessageDialog(null, "登录成功","成功",  JOptionPane.PLAIN_MESSAGE);
						new selectType1(user).setVisible(true); //打开新界面selectType
						this.setVisible(false);//关闭本界面
					}else{
						//登录失败
						JOptionPane.showMessageDialog(null, "登录名或密码错误，请重新登录!", "提示", JOptionPane.ERROR_MESSAGE);
					}
				
				}else if(textField.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null,"请输入USERNAME","提示消息",JOptionPane.WARNING_MESSAGE);
				}else if(passwordField.getText().isEmpty())	
				{
					JOptionPane.showMessageDialog(null,"请输入PASSWORD","提示消息",JOptionPane.WARNING_MESSAGE);
				}
	}
}


