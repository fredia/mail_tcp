package tcpmail;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import tcpmail.User;
import tcpmail.Mail;
import tcpmail.Send;
public class sendjm extends JDialog implements ActionListener{
	private JScrollPane jsc =new JScrollPane();
	private JPanel j1;
	private JPanel j2;
	private JPanel j3;
	private JPanel j4;
	private JLabel jl1=new JLabel("        from:    ");
	private JLabel jl2=new JLabel("        to:      ");
	private JLabel jl3=new JLabel("        subject: ");
	private JTextField jt1=new JTextField(20);
	private JTextField jt2=new JTextField(20);;
	private JTextField jt3=new JTextField(20);
	private JTextArea jta = new JTextArea(5,30);
	private JButton jb=new JButton("发送");
	
	User user=null;
	Mail mail=null;
	public sendjm(rejm par,User user) {
		super(par,"写邮件",true);
		this.user=user;
		this.setLayout(new BorderLayout());
		init();
		this.setBounds(0, 0, 600, 400);
		//窗体大小不能改变
		this.setResizable(false);
		//居中显示
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	public void init()
	{
		Container container= getContentPane();
		jsc.setViewportView(jta);
		j1=new JPanel();
		j1.setLayout(new FlowLayout(FlowLayout.LEFT));
		j1.add(jl1);
		j1.add(jt1);
		j2=new JPanel();
		j2.setLayout(new FlowLayout(FlowLayout.LEFT));
		j2.add(jl2);
		j2.add(jt2);
		j3=new JPanel();
		j3.setLayout(new FlowLayout(FlowLayout.LEFT));
		j3.add(jl3);
		j3.add(jt3);
		j4 = new JPanel();
		j4.setLayout(new GridLayout(3,1));
		j4.add(j1);j4.add(j2);j4.add(j3);
		jb.addActionListener(this);
		container.add("North",j4);
		container.add("Center",jsc);
		container.add("South",jb);
	}
	public void actionPerformed(ActionEvent e) {  
        String cmd = e.getActionCommand();
        System.out.println(cmd);
        if("发送".equals(cmd))
        {
        	mail =new Mail();
        	String from = jt1.getText();
        	String to=jt2.getText();
        	String subj=jt3.getText();
        	String content=jta.getText();
        	mail.setFrom(from);
        	mail.setTo(to);
        	mail.setSubj(subj);
        	mail.setContent(content);
        	System.out.println(mail.toString());
        	Send send=new Send();
        	try {
        		String s=send.send(user, mail);
        		JOptionPane.showConfirmDialog(null, s+"返回上一界面请按X","提示",JOptionPane.OK_OPTION);
        		
				System.out.print(s);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        	
        }
	}
}
