package tcpmail;
import java.io.*;
import java.net.Socket;
import tcpmail.Receive;
import Decoder.BASE64Encoder;
import tcpmail.Mail;
import tcpmail.User;
public class Send {
	public String outin(String s,BufferedReader in,PrintWriter out) throws IOException
	{
		out.println(s);
		out.flush();
		try{
			return in.readLine();
		}catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public String send(User user,Mail mail) throws IOException{
		String pass=user.getPass();
		String name=user.getName();
		name = new BASE64Encoder().encode(name.substring(0,name.indexOf("@")).getBytes());
		pass = new BASE64Encoder().encode(pass.getBytes());
		System.out.println(name);
		System.out.println(pass);
		System.out.println(mail.getContent());
		System.out.println(mail.toString());
		try{
			Socket socket = new Socket(user.getSmtp(), 25);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			PrintWriter writter = new PrintWriter(outputStream, true);
			String res = reader.readLine();
			System.out.println(res);
			if(res.indexOf("220")==-1) {return "连接失败";}
			res=outin("HELO fredia",reader,writter);
			System.out.println(res);
			if(res.indexOf("250")==-1) return "无法连接服务器";
			res=outin("AUTH LOGIN",reader,writter);
			System.out.println(res);
			if(res.indexOf("334")==-1) return "无法验证";	
			res=outin(name,reader,writter);
			System.out.println("3.名字"+res);  
			if(res.indexOf("334")==-1) return "用户名非法";
			writter.println(pass);
			res=reader.readLine();
			System.out.println("4.密码"+res);
			if(res.indexOf("235")==-1) return "密码错误";
			res=outin("mail from:<" + mail.getFrom() + ">",reader,writter);
			System.out.println("5.发件人"+res);
			if(res.indexOf("250")==-1) return "发件人非法";
			res=outin("rcpt to:<" + mail.getTo() + ">",reader,writter);
			System.out.println("6.收件人:"+res);
			if(res.indexOf("250")==-1) return "收件人非法";
			res=outin("data",reader,writter);
			System.out.println("7.data:"+res);
			if(res.indexOf("354")==-1) return "无法发送邮件";
			writter.println("subject:"+mail.getSubj());
			writter.println("from:" + mail.getFrom());
			writter.println("to:" + mail.getTo());
			writter.println("Content-Type: text/plain;charset=\"utf-8\"");
			writter.println();
			writter.println(mail.getContent());
			writter.println(".");
			writter.println("");
			res=reader.readLine();
			System.out.println(res);
			if(res.indexOf("250")==-1) return "发送失败";
			System.out.println("8.content:"+res);
			res=outin("rset",reader,writter);
			System.out.println("9.rset:"+res);
			res=outin("quit",reader,writter);
			System.out.println("10.quit:"+res);
			Receive.save(mail, "/home/fredia/桌面/"+user.getName()+"/postBox.dat");
			socket.close();
			return "发送成功";	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
    
}
