package tcpmail;

import java.awt.Container;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import tcpmail.sendjm;
import tcpmail.watchjm;
import tcpmail.Receive;
public class rejm extends JFrame{
	private JScrollPane j1 =new JScrollPane();
	private JPanel j2;
	private JScrollPane j3 =new JScrollPane();
	private JPanel j4;
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("我的邮箱");
	DefaultMutableTreeNode recvBox = new DefaultMutableTreeNode("收件箱");
	DefaultMutableTreeNode postBox = new DefaultMutableTreeNode("发件箱");
	private DefaultTableModel table = null;
	private JTable jtable1;
	private JTree tree;
	private JButton xie = new JButton("写邮件");
	private JButton shou = new JButton("收邮件");
	private JTextArea textyh = new JTextArea();
	private Mail mail;
	private User user;
	String lastSelectedBox = "";
	List<Mail> mailList = new ArrayList<Mail>();
	public rejm(User user)
	{
		this.user=user;
		//设置登录窗口标题
		this.setTitle("MAILCLIENT");
		//采用指定的窗口装饰风格
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		//窗体组件初始化
		init();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(1,1));
		this.setBounds(0, 0, 600, 400);
		//窗体大小不能改变
		this.setResizable(false);
		//居中显示
		this.setLocationRelativeTo(null);
		//窗体显示
		this.setVisible(true);
	}
	public void init()
	{
		root.add(recvBox);
		root.add(postBox);
		tree = new JTree(root);
		tree.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				treeMouseClicked(evt);
			}
		});
		Container container = this.getContentPane();
		j2=new JPanel();
		j4=new JPanel();
		j4.add(tree);
		textyh.setText(user.getName()+"\n"+user.getSmtp()+"\n"+user.getPop()+"\n");
		j1.setViewportView(textyh);
		jtable1 = new JTable(){
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		};
		jtable1.setEditingColumn(0);
		jtable1.setEditingRow(0);
		jtable1.setFocusable(false);
		jtable1.setShowGrid(false);
		jtable1.setShowHorizontalLines(true);
		jtable1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jTable1MouseClicked(evt);
			}
		});
		xie.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				JDialog dia=  new sendjm(rejm.this,user);
	        	dia.setVisible(true);
			}
		});
		shou.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Receive rec = new Receive();
	        	try {
	        		mailList.clear();
					String tmp=rec.receive(user,mailList);
					
					JOptionPane.showConfirmDialog(null, "您的邮箱共有"+tmp.substring(4)+"现在接收前三封邮件", "提示",
							JOptionPane.OK_OPTION);
					table = new DefaultTableModel();
					jtable1.setModel(table);
					table.addColumn("主题");
					table.addColumn("发件人");

					jtable1.getColumnModel().getColumn(0).setPreferredWidth(250);
					jtable1.getColumnModel().getColumn(1).setPreferredWidth(100);
					Mail aMail;
					Iterator<Mail> it = mailList.iterator();
					while (it.hasNext()) {
						aMail = it.next();
						table.addRow(new Object[] { 
								aMail.getSubj(), aMail.getFrom(),
							 });
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}});
		j2.add(xie);
		j2.add(shou);
		
		j3.setViewportView(jtable1);
		JSplitPane hSplitPane = new JSplitPane();  
        hSplitPane.setDividerLocation(100);  
        container.add(hSplitPane);  
        JSplitPane vvSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);  
        vvSplitPane.setLeftComponent(j1);  
        vvSplitPane.setRightComponent(j4);  
        vvSplitPane.setDividerLocation(150);  
        vvSplitPane.setDividerSize(8); 
        hSplitPane.setLeftComponent(vvSplitPane);  
  
        JSplitPane vSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);  
        vSplitPane.setLeftComponent(j2);  
        vSplitPane.setRightComponent(j3);  
        vSplitPane.setDividerLocation(50);  
        vSplitPane.setDividerSize(8);  
        vSplitPane.setOneTouchExpandable(true);  
        vSplitPane.setContinuousLayout(true);  
        hSplitPane.setRightComponent(vSplitPane);  
		
	}
	private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        int index = jtable1.getSelectedRow();
		mail = mailList.get(index);
	   if (evt.getClickCount() == 2) {
			new watchjm(this,mail);
		}
	}
	private void treeMouseClicked(java.awt.event.MouseEvent evt) {
		if (evt.getClickCount() >= 1 && evt.getButton() == 1) {
			if (!"".equals(lastSelectedBox)) {
				try {
					Receive.save(mailList, "/home/fredia/桌面/" + user.getName()
							+ "/" + lastSelectedBox);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			mailList.clear();
			table = new DefaultTableModel();
			jtable1.setModel(table);
			if (tree.getLastSelectedPathComponent().equals(recvBox)) {
				//双击收件箱的动作
				table.addColumn("主题");
				table.addColumn("发件人");

				jtable1.getColumnModel().getColumn(0).setPreferredWidth(250);
				jtable1.getColumnModel().getColumn(1).setPreferredWidth(100);

				try {

					Receive.regain(mailList, "/home/fredia/桌面/" + user.getName()
							+ "/recvBox.dat");
					Mail aMail;
					Iterator<Mail> it = mailList.iterator();
					while (it.hasNext()) {
						aMail = it.next();
						table.addRow(new Object[] { 
								aMail.getSubj(), aMail.getFrom(),
							 });
					}
				} catch (IOException e) {
					System.out.println("无记录");
					e.printStackTrace();
				}
				lastSelectedBox = "recvBox.dat";
			} else if (tree.getLastSelectedPathComponent().equals(postBox)) {
				//双击发件箱的动作
				table.addColumn("主题");
				table.addColumn("收件人");

				jtable1.getColumnModel().getColumn(0).setPreferredWidth(250);
				jtable1.getColumnModel().getColumn(1).setPreferredWidth(100);

				try {
					Receive.regain(mailList, "/home/fredia/桌面/" + user.getName()
							+ "/postBox.dat");
					Mail aMail;
					Iterator<Mail> it = mailList.iterator();
					while (it.hasNext()) {
						aMail = it.next();
						table.addRow(new String[] { aMail.getSubj(),
								aMail.getTo()});
					}
				} catch (IOException e) {
					System.out.println("无记录");
					e.printStackTrace();
				}
				lastSelectedBox = "postBox.dat";
			} 
	    }
	}
	
	
}
