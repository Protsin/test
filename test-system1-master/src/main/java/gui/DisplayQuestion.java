package gui;

import entity.User;
import entity.impl.QuestionImpl;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DisplayQuestion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static int answer;
	public static int order = 0;
	String s;
	int num = 3;
	static int k = 0;
	static int l = 0;
	static int option[];

	public static int getOrder() {
		return order;
	}

	public static int getAnswer() {
		return answer;
	}

	/**
	 * Create the frame.
	 */
	public DisplayQuestion(User user,String questions[], String answers[][], int i, int num,int op[],
			QuestionImpl question) {
		k = (i + 1);
		l = (i - 1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextArea textArea = new JTextArea(questions[i]);
		textArea.setEditable(false);
		textArea.setBounds(66, 30, 292, 31);
		contentPane.add(textArea);

		JRadioButton rdbtnNewRadioButton = new JRadioButton(answers[i][0],(op[i]>0?true:false));
		rdbtnNewRadioButton.setBounds(58, 110, 121, 23);
		contentPane.add(rdbtnNewRadioButton);

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton(answers[i][1],(op[i]>0?true:false));
		rdbtnNewRadioButton_1.setBounds(276, 110, 121, 23);
		contentPane.add(rdbtnNewRadioButton_1);

		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton(answers[i][2],(op[i]>0?true:false));
		rdbtnNewRadioButton_2.setBounds(58, 162, 121, 23);
		contentPane.add(rdbtnNewRadioButton_2);

		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton(answers[i][3],(op[i]>0?true:false));
		rdbtnNewRadioButton_3.setBounds(276, 162, 121, 23);
		contentPane.add(rdbtnNewRadioButton_3);

		ButtonGroup Group = new ButtonGroup();
		Group.add(rdbtnNewRadioButton);
		Group.add(rdbtnNewRadioButton_1);
		Group.add(rdbtnNewRadioButton_2);
		Group.add(rdbtnNewRadioButton_3);

		
		JButton btnNewButton = new JButton("上");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (l >= 0) {
					setVisible(false);
					new DisplayQuestion(user,questions, answers, l, num,op,question);
				}
			}
		});
		btnNewButton.setBounds(171, 217, 93, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("下");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(rdbtnNewRadioButton.isSelected()) {
					op[i]=1;
					rdbtnNewRadioButton.setSelected(true);
					System.out.println(1+"ok");
				}
				if(rdbtnNewRadioButton_1.isSelected()) {
					op[i]=2;
					rdbtnNewRadioButton_1.setSelected(true);
					System.out.println(2+"ok");
				}
				if(rdbtnNewRadioButton_2.isSelected()) {
					op[i]=3;
					rdbtnNewRadioButton_2.setSelected(true);
					System.out.println(3+"ok");
				}
				if(rdbtnNewRadioButton_3.isSelected()) {
					op[i]=4;
					rdbtnNewRadioButton_3.setSelected(true);
					System.out.println(4+"ok");
				}
				if (k <= num - 1) {
					setVisible(false);
					new DisplayQuestion(user,questions, answers, k, num,op,question);
				} else {
					setVisible(false);
					new ResultScore(op, user,question).setVisible(true);;
				}
			}
		});
		btnNewButton_1.setBounds(306, 217, 93, 23);
		contentPane.add(btnNewButton_1);

		ButtonGroup Groupp = new ButtonGroup();
		Groupp.add(btnNewButton_1);
		Groupp.add(btnNewButton);

		setVisible(true);
		
	}
	
	int[] getOption() {
		return option;
	}
}
