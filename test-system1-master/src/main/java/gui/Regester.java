package gui;

import dao.UserDao;
import java.awt.EventQueue;

import javax.mail.MessagingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import service.Register;
import service.SendMail;

public class Regester extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public Regester() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(108, 36, 149, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(108, 83, 149, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(108, 127, 149, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(108, 167, 149, 21);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(108, 208, 149, 21);
		contentPane.add(passwordField_1);
		
		JLabel lblNewLabel = new JLabel("QQ邮箱");
		lblNewLabel.setBounds(29, 39, 54, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("验证码");
		lblNewLabel_1.setBounds(29, 86, 54, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("用户名");
		lblNewLabel_2.setBounds(29, 130, 54, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("输入密码");
		lblNewLabel_3.setBounds(29, 170, 54, 15);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("再次输入密码");
		lblNewLabel_4.setBounds(29, 211, 98, 15);
		contentPane.add(lblNewLabel_4);
		
		JButton btnNewButton = new JButton("发送");

		//获取邮箱
		final String[] email = {""};
		//获取用户名
		final String[] username = {""};
		//生成验证码
		String messageCode=SendMail.generateCode();
		//密码1，2
		final String[] pwd1 = {""};
		final String[] pwd2 = { "" };
		final String[] userCode = {""};  //用户填写的验证码

		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//发送验证码
				email[0] =textField.getText().toString();

				//如果邮箱不合法
				if(!Register.isEmailValid(email[0])){
					JOptionPane.showMessageDialog(null, "邮箱不合法，目前只支持QQ邮箱","错误",  JOptionPane.ERROR_MESSAGE);
					return;
				}
				//如果邮箱已经注册
				else if(Register.isEmailRepeated(email[0])){
					JOptionPane.showMessageDialog(null, "邮箱已经注册","错误",  JOptionPane.ERROR_MESSAGE);
					return;
				}
				System.out.println("邮箱验证码获取成功，为："+userCode[0]);
				//发送验证码
				int res= 0;
				try {
					res = SendMail.sendEmail(email[0],messageCode);
					if(res==0){
						System.out.println("发送成功");
					}
					else if(res==-1){
						System.out.println("邮箱不存在");
						JOptionPane.showMessageDialog(null, "邮箱不存在", "错误", JOptionPane.ERROR_MESSAGE);
					}
					else {
						System.out.println("邮箱发送出现错误");
						JOptionPane.showMessageDialog(null, "邮箱发送出现错误","错误",  JOptionPane.ERROR_MESSAGE);
					}
				} catch (MessagingException ex) {
					System.out.println("catch里，邮箱发送错误");
					JOptionPane.showMessageDialog(null, "邮箱错误", "错误", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(298, 35, 93, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("确认");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//比对是否正确
				//textField_1为验证码
				//passwordField_1和passwordField为两次密码
				username[0] =textField_2.getText();
				pwd1[0] =passwordField.getText();
				pwd2[0] =passwordField_1.getText();
				userCode[0] = textField_1.getText().toString();
				//判断验证码是否正确

				if(pwd1[0] ==null){
					JOptionPane.showMessageDialog(null, "密码不能为空","错误",  JOptionPane.ERROR_MESSAGE);
				}
				else if(!pwd1[0].equals(pwd2[0])){
					JOptionPane.showMessageDialog(null, "两次密码不一致","错误",  JOptionPane.ERROR_MESSAGE);
				}
				int res=UserDao.register(username[0], email[0], pwd1[0],0,messageCode,userCode[0]);
				switch (res){
					case -1:
						JOptionPane.showMessageDialog(null, "邮箱不合法，目前只支持QQ邮箱","错误",  JOptionPane.ERROR_MESSAGE);
						break;
					case -2:
						JOptionPane.showMessageDialog(null, "邮箱已经注册", "错误", JOptionPane.ERROR_MESSAGE);
						break;
					case -3:
						JOptionPane.showMessageDialog(null, "验证码错误","错误",  JOptionPane.ERROR_MESSAGE);
						break;
					case -4:
						JOptionPane.showMessageDialog(null, "数据库错误","错误",  JOptionPane.ERROR_MESSAGE);
						break;
					case -5:
						JOptionPane.showMessageDialog(null, "用户名不合法或已经被注册（范围6位-12位）","错误",  JOptionPane.ERROR_MESSAGE);
						break;
					case -6:
						JOptionPane.showMessageDialog(null, "密码不合法 （范围6位-12位，包含大小写）","错误",  JOptionPane.ERROR_MESSAGE);
						break;
					case 0:
						JOptionPane.showMessageDialog(null, "注册成功","成功",  JOptionPane.PLAIN_MESSAGE);
						setVisible(false);
						new LogSystem().setVisible(true);
				}
//				if(textField==  &&passwordField==passwordField_1) {      修改成功 密码符合规定，且两次密码相等，验证码正确
//					new selectType1();
//				}
//				if(textField_1) {验证码不正确
//					JOptionPane.showMessageDialog(null, "错误", "验证码错误", JOptionPane.ERROR_MESSAGE);
//				}
//				if(passwordField_1 !=passwordField) {  两次密码不匹配
//					JOptionPane.showMessageDialog(null, "错误", "两次密码不匹配", JOptionPane.ERROR_MESSAGE);
//				}
//				if() {  两次密码相等但不符合规定在6-10位
//					JOptionPane.showMessageDialog(null, "错误", "密码设置不符合规定，请设置6-10位", JOptionPane.ERROR_MESSAGE);
//				}
			}
		});
		btnNewButton_1.setBounds(298, 230, 93, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("返回");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				new LogSystem().setVisible(true);
			}
		});
		btnNewButton_2.setBounds(298, 188, 93, 23);
		contentPane.add(btnNewButton_2);
		setVisible(true);
	}

}
