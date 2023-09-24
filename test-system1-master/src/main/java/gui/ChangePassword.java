package gui;

import dao.UserDao;
import entity.User;
import java.awt.EventQueue;

import javax.mail.MessagingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import service.Register;
import service.SendMail;

public class ChangePassword extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public ChangePassword(User user) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("QQ邮箱");
		lblNewLabel.setBounds(38, 39, 54, 15);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(137, 36, 148, 21);
		contentPane.add(textField);
		textField.setColumns(10);



		
		JLabel lblNewLabel_1 = new JLabel("验证码");
		lblNewLabel_1.setBounds(38, 82, 54, 15);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(137, 79, 148, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("发送");
		//获取邮箱
		final String[] email = {""};
		//生成的验证码
		String messageCode=SendMail.generateCode();

		final String[] userCode = {""};  //用户填写的验证码
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				email[0] =textField.getText();
				if(email[0]==null||email[0]==""){
					JOptionPane.showMessageDialog(null, "邮箱不能为空","错误",  JOptionPane.ERROR_MESSAGE);
					return;
				}
				if( !Register.isEmailValid(email[0])){
					JOptionPane.showMessageDialog(null, "邮箱不合法，只支持qq邮箱","错误",  JOptionPane.ERROR_MESSAGE);
					return;
				}
				System.out.println("邮箱验证码发送成功，为："+messageCode);
				//发送验证码

				int res= 0;
				try {
					res = SendMail.sendEmail(email[0],messageCode);
					if(res==0){
						System.out.println("发送成功");
						JOptionPane.showMessageDialog(null, "发送成功","成功",  JOptionPane.PLAIN_MESSAGE);
					}
					else if(res==-1){
						JOptionPane.showMessageDialog(null, "邮箱不存在","错误",  JOptionPane.ERROR_MESSAGE);
						System.out.println("邮箱不存在");
					}
					else {
						JOptionPane.showMessageDialog(null, "邮箱发送出现错误","错误",  JOptionPane.ERROR_MESSAGE);
						System.out.println("邮箱发送出现错误");
					}
				} catch (MessagingException ex) {
					System.out.println("catch里，邮箱发送错误");
					JOptionPane.showMessageDialog(null, "邮箱出现错误","错误",  JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}

			}
		});
		btnNewButton.setBounds(313, 35, 93, 23);
		contentPane.add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(137, 123, 148, 21);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel_2 = new JLabel("输入密码");
		lblNewLabel_2.setBounds(38, 126, 54, 15);
		contentPane.add(lblNewLabel_2);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(137, 164, 148, 21);
		contentPane.add(passwordField_1);
		
		JLabel lblNewLabel_3 = new JLabel("再次输入密码");
		lblNewLabel_3.setBounds(38, 167, 72, 15);
		contentPane.add(lblNewLabel_3);

		//获取用户名
		final String[] username = {""};
		//密码1，2
		final String[] pwd1 = {""};
		final String[] pwd2 = { "" };
		JButton btnNewButton_1 = new JButton("确定");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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

				//修改密码
				int flag=UserDao.updatePassword(user,pwd1[0]);
				if(flag==-1){
					JOptionPane.showMessageDialog(null, "密码不合法","错误",  JOptionPane.ERROR_MESSAGE);
					return;
				}
				else if(flag==-2){
					JOptionPane.showMessageDialog(null, "密码和之前的相同","错误",  JOptionPane.ERROR_MESSAGE);
					return;
				}
				else if(flag==1){
					JOptionPane.showMessageDialog(null, "其它错误","错误",  JOptionPane.ERROR_MESSAGE);
					return;
				}
				else{
					setVisible(false);
					JOptionPane.showMessageDialog(null, "修改成功","成功",  JOptionPane.PLAIN_MESSAGE);
					new selectType1(user).setVisible(true);
				}
			}
		});
		btnNewButton_1.setBounds(313, 230, 93, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("返回");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				new selectType1(user).setVisible(true);
			}
		});
		btnNewButton_2.setBounds(313, 197, 93, 23);
		contentPane.add(btnNewButton_2);
		setVisible(true);
	}
}
