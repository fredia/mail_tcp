package tcpmail;
import java.io.*;
import java.net.Socket;
import java.util.*;

import tcpmail.Mail;
import tcpmail.User;
import tcpmail.Decoder;
public class Receive {
	
	public String receive(User user,List<Mail> mailList)throws IOException{
		int cnt=0;
		String res="";
		try{
			Socket socket = new Socket(user.getPop(),110);
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			PrintWriter writter = new PrintWriter(outputStream, true);
			res=reader.readLine();
			System.out.println(res);
			if(res.indexOf("+OK")==-1) return  "无法连接服务器";
			writter.println("user "+user.getName());
			res=reader.readLine();
			System.out.println(res);
			if(res.indexOf("+OK")==-1) return  "用户名不存在";
			writter.println("pass "+user.getPass());
			res=reader.readLine();
			System.out.println(res);
			if(res.indexOf("+OK")==-1) return  "密码错误";
			writter.println("list");
			for(cnt=0;!".".equals(res=reader.readLine());cnt++){
				System.out.println(cnt);
			}
			System.out.println("现在下载前三封邮件");
			Decoder mDecoder = new Decoder();
			List<Mail> keptList = new ArrayList<Mail>();
			regain(keptList, "/home/fredia/桌面/"+user.getName()+"/recvBox.dat");
			for(int i=cnt-1;i>=cnt-3;i--)
			{
				StringBuffer each=new StringBuffer();
				writter.println("retr "+i);
				res=reader.readLine();
				if(res.indexOf("+OK")==-1) {
					System.out.println("下载第"+i+"封失败");
					continue;
				}
				res=reader.readLine();
				while(!".".equals(res)){
					each.append(res+"\n");
					res = reader.readLine();
				}
				String neach ="";
				Mail amail = null;
				amail=new Mail();
				mDecoder.packMail(amail, each.toString());
				Iterator<Mail> it = keptList.iterator();
				int j=0;
				for(;it.hasNext();j++){
					if(amail.equals(it.next())) break;
				}
				if(j==keptList.size()){//文件中无该记录
					save(amail,"/home/fredia/桌面/"+user.getName()+"/recvBox.dat");
					mailList.add(amail);
				}
				System.out.print(amail.toString());
				
			}
			socket.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		String tmp=String.valueOf(cnt);;
         return "接收成功"+tmp;
	}
	public static void save(Mail mail,String filename) throws IOException{
		if(mail==null) return;
		File mfile = new File(filename);
		if(!mfile.exists()){
			mfile.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(mfile));
			oos.writeObject(mail);
			oos.flush();
			oos.close();
		}else{
			ObjectWriter oos = new ObjectWriter(new FileOutputStream(mfile,true));
			oos.writeObject(mail);
			oos.flush();
			oos.close();
		}		
	}
	public static void save(List<Mail> mailList,String filename) throws IOException{
		if(!(mailList.size()>0)) return ;
		File mfile = new File(filename);
		if(!mfile.exists()) mfile.createNewFile();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(mfile));
		for(Iterator<Mail> it=mailList.iterator();it.hasNext();)
			oos.writeObject(it.next());
		oos.flush();
		oos.close();
	}
	public static String regain(List<Mail> mailList,String filename) throws IOException{
		File mfile = new File(filename);	
		if(!mfile.exists()) return "无记录";

		Mail aMail = null;
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(mfile));		
		try{
			while((aMail=(Mail)ois.readObject())!=null){
				mailList.add(0, aMail);
			}
		}catch(EOFException e){
			System.out.println("文件读取完毕");
		} catch (ClassNotFoundException e) {
			System.out.println("格式错误");
			e.printStackTrace();
		}
		ois.close();
		return "查询完毕";
	}

	public int test(User user)throws IOException{
		System.out.println("aaa");
		String res="";
		try{
			Socket sockett = new Socket(user.getPop(),110);
			InputStream inputStream = sockett.getInputStream();
			OutputStream outputStream = sockett.getOutputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			PrintWriter writter = new PrintWriter(outputStream, true);
			res=reader.readLine();
			System.out.println(res);
			sockett.close();
			if(res.indexOf("+OK")==-1) return  -1;
			else return 1;
	     }catch(IOException e){
	    	 e.printStackTrace();
		}
		return 0;
	}

}
class ObjectWriter extends ObjectOutputStream{
	public ObjectWriter(OutputStream out) throws IOException {
		super(out);
	}
	/**
	 * 以追加方式写入对象不需要重新写StreamHeader，故重写此方法
	 */
	@Override
	protected void writeStreamHeader() throws IOException {
		super.reset();
	}		
}
