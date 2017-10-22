package tcpmail;


import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;

public class QQ extends JFrame{
	
	private static final long serialVersionUID = -6788045638380819221L;
	//用户名
	private JTextField ulName;
	//密码
	private JPasswordField ulPasswd;
	//小容器
	private JLabel j1;
	private JLabel j3;
	private JLabel j4;
	//小按钮
	private JButton b1;
	private JButton b2;
	private JButton b3;

	/**
	 * 初始化QQ登录页面
	 * */
	public QQ(){
		//设置登录窗口标题
		this.setTitle("QQ登录");
		//去掉窗口的装饰(边框)
//		this.setUndecorated(true); 
		//采用指定的窗口装饰风格
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		//窗体组件初始化
		init();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置布局为绝对定位
		this.setLayout(null);
		this.setBounds(0, 0, 355, 265);
		//窗体大小不能改变
		this.setResizable(false);
		//居中显示
		this.setLocationRelativeTo(null);
		//窗体显示
		this.setVisible(true);
	}
	/**
	 * 窗体组件初始化
	 * */
	public void init(){
		//创建一个容器,其中的图片大小和setBounds第三、四个参数要基本一致
		Container container = this.getContentPane();
		j1 = new JLabel();
		//设置背景色
		Image img1 = new ImageIcon("/home/fredia/桌面/timg.jpg").getImage();
		j1.setIcon(new ImageIcon(img1));
		j1.setBounds(0, 0, 355, 265);
		//用户名输入框
		ulName = new JTextField();
		ulName.setBounds(100, 100, 150, 20);
		//注册账号
		j3 = new JLabel("用户名");
		j3.setBounds(260, 100, 70, 20);
		//密码输入框
		ulPasswd = new JPasswordField();
		ulPasswd.setBounds(100, 130, 150, 20);
		//找回密码
		j4= new JLabel("密码");
		j4.setBounds(260, 130, 70, 20);
	
	
		//登陆按钮
		b1 = new JButton("登录");
		//设置字体和颜色和手形指针
		b1.setFont(new Font("宋体", Font.PLAIN, 12));
		b1.setForeground(Color.RED);
		b1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b1.setBounds(280, 200, 65, 20);
		//给按钮添加
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				if("登录".equals(cmd)){
					String username = ulName.getText();
					String userpassword = ulPasswd.getText();
					if(username.equals("tskk") && userpassword.equals("123456")){
						JOptionPane.showConfirmDialog(null, "登录成功");
					}else{
						JOptionPane.showConfirmDialog(null, "登录失败");
					}
				}
			}
		});
		//所有组件用容器装载
		j1.add(j3);
		j1.add(j4);
		j1.add(b1);
		container.add(j1);
		container.add(ulName);
		container.add(ulPasswd);
	}
	public static void main(String[] args) {
		new QQ();
	}
}

