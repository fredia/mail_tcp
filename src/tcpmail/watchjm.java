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
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import tcpmail.User;
import tcpmail.Mail;
import tcpmail.Send;
public class watchjm extends JDialog{
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
	
	Mail mail=null;
	public watchjm(rejm par,Mail mail) {
		super(par,"查看邮件",true);
		this.mail=mail;
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
		jt1.setText(mail.getFrom());
		jt2.setText(mail.getTo());
		jt3.setText(mail.getSubj());
		jta.setText(mail.getContent());
		jt1.setEditable(false);
		jt2.setEditable(false);
		jt3.setEditable(false);
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
		container.add("North",j4);
		container.add("Center",jsc);
	}
	
}
