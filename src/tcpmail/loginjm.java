package tcpmail;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import tcpmail.Receive;
import tcpmail.rejm;
public class loginjm extends JFrame implements ActionListener{
	//用户名
	private JTextField ulName=new JTextField("fredialee@163.com",20);
	private JTextField ulsmtp=new JTextField("smtp.163.com",20);
	private JTextField ulpop=new JTextField("pop.163.com",20);
	//密码
	private JPasswordField ulPasswd=new JPasswordField("206167l",20);
	
	private  JLabel jzh=new JLabel("<HTML><font color=white>帐号: </font></html>");
	private JLabel jma=new JLabel("<HTML><font color=white>密码： </font></html>");
	private JLabel jsmtp=new JLabel("<HTML><font color=white>smtp： </font></html>");
	private JLabel jpop=new JLabel("<HTML><font color=white>pop： </font></html>");
	private JButton dl = new JButton("登录");
	private JPanel bg;
	private JPanel jp1;
	private JPanel jp2;
	private JPanel jp3;
	private JPanel jp4;
	private JPanel jp5;
	public loginjm(){
		//设置登录窗口标题
		this.setTitle("登录");
		//采用指定的窗口装饰风格
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		//窗体组件初始化
		init();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(1,1));
		this.setBounds(0, 0, 355, 265);
		//窗体大小不能改变
		this.setResizable(false);
		//居中显示
		this.setLocationRelativeTo(null);
		//窗体显示
		this.setVisible(true);
	}
	public void init()
	{
		Container container = this.getContentPane();
		bg = new JPanel(){  
            @Override    
              protected void paintComponent(Graphics g) {    
                  ImageIcon icon = new ImageIcon("/home/fredia/桌面/timg.jpg");   
                  Image img = icon.getImage();    
                  g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());    
              }    
      };
      dl.setFont(new Font("宋体", Font.PLAIN, 12));
	  dl.setForeground(Color.RED);
	  dl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	  dl.addActionListener(this);
      bg.setLayout(new GridLayout(5,1));
      jp1=new JPanel();
      jp1.setBackground(null);  
      jp1.setOpaque(false);  
      jp1.add(jzh);
      jp1.add(ulName);
      jp2=new JPanel();
      jp2.setBackground(null);  
      jp2.setOpaque(false);  
      jp2.add(jma);
      jp2.add(ulPasswd);
      jp3=new JPanel();
      jp3.setBackground(null);  
      jp3.setOpaque(false);  
      jp3.add(dl);
      jp4=new JPanel();
      jp4.setBackground(null);  
      jp4.setOpaque(false);  
      jp4.add(jsmtp);
      jp4.add(ulsmtp);
      jp5=new JPanel();
      jp5.setBackground(null);  
      jp5.setOpaque(false);  
      jp5.add(jpop);
      jp5.add(ulpop);
      bg.add(jp1);
      bg.add(jp2);
      bg.add(jp4);
      bg.add(jp5);
      bg.add(jp3);
      container.add(bg);
      
	}
	public void actionPerformed(ActionEvent e) {  
        String cmd = e.getActionCommand();  
        if("登录".equals(cmd)){
        	String username = ulName.getText();
        	String userpassword = ulPasswd.getText();
        	String smtp = ulsmtp.getText();
        	String pop=ulpop.getText();
        	User user=new User();
        	user.setName(username);
        	user.setPass(userpassword);
        	user.setSmtp(smtp);
        	user.setPop(pop);
        	System.out.println(user.getPop());
        	Receive re=new Receive();
        	try{
	        	int tmp=re.test(user);
	        	System.out.print(tmp);
	        	if(tmp==-1)
	        	{
	        		JOptionPane.showConfirmDialog(null, "登录失败");
	        	}
	        	else if(tmp==1){
	        		//TODO
	        		rejm rej=new rejm(user);
	        		rej.setVisible(true);
	        		this.dispose();
	        	}
        	}catch(IOException ee)
        	{
        		ee.printStackTrace();
        	}

        }
    }  
	public static void main(String[] args) {
		new loginjm();
	}
}


