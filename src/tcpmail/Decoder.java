package tcpmail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.*;
import java.util.*;

import Decoder.BASE64Decoder;

import tcpmail.Mail;


public class Decoder {
	private BASE64Decoder decoder = new BASE64Decoder();
	
	
	private String findFrom(String original){
		String s = original.substring(original.indexOf("From:")+5);		
		s = s.substring(0,s.indexOf("\n"));
		if(s.indexOf("<")==-1||s.indexOf(">")==-1){
			return s;
		}else{
			return s.substring(s.indexOf("<")+1, s.indexOf(">"));
		}
	}
	
	
	private String findTo(String original){
		String s = original.substring(original.indexOf("To:")+3);		
		s = s.substring(0,s.indexOf("\n"));
		if(s.indexOf("<")==-1||s.indexOf(">")==-1){
			return s;
		}else{
			return s.substring(s.indexOf("<")+1, s.indexOf(">"));
		}
	}
	

	private String findSubject(String original){
		if(original.indexOf("Subject:")==-1) return "";
		String s = original.substring(original.indexOf("Subject:")+8);	
		s = s.substring(0,s.indexOf("\n"));
		if(s.indexOf("?B?")!=-1){
			s = s.substring(s.indexOf("?B?")+3);
			s = s.substring(0,s.indexOf("?="));
			try {
				s = new String(decoder.decodeBuffer(s));
			} catch (IOException e1) {}
		}
		return s;
	}
	
	private String findContent(String original){
		boolean isBase64 = true;
		String plain = "";
		String html = "";
		if(original.indexOf("Content-Type: text/plain")!=-1){
			plain = original.substring(original.indexOf("Content-Type: text/plain")+24);
			String code = plain.substring(0,plain.indexOf("\n\n"));
			if(code.indexOf("Content-Transfer-Encoding: 7bit")!=-1) isBase64 = false;
			plain = plain.substring(plain.indexOf("\n\n")+2);
			
			if(plain.indexOf("Content-Type: text/html; charset=UTF-8")!=-1) plain = plain.substring(0,plain.indexOf("Content-Type: text/html; charset=UTF-8")-43);
			
			if(isBase64){
				try {
					plain = new String(decoder.decodeBuffer(plain));
				} catch (IOException e1) {}
			}
		}
		if(original.indexOf("Content-Type: text/html")!=-1){
			html = original.substring(original.indexOf("Content-Type: text/html")+23);
			String code = html.substring(0,html.indexOf("\n\n"));
			if(code.indexOf("Content-Transfer-Encoding: 7bit")!=-1) isBase64 = false;
			else isBase64 = true;
			html = html.substring(html.indexOf("\n\n")+2);
			if(html.indexOf("\n\n")!=-1) html = html.substring(0,html.indexOf("\n\n"));
			
			if(isBase64){
				try {
					html = new String(decoder.decodeBuffer(html));
				} catch (IOException e1) {}
			}
			
		}
		
		//System.out.println("iiii"+plain);
		//System.out.println("llll"+html);
		String content = plain;  //+"\n"+html
		if("\n".equals(content)) content = original.substring(original.indexOf("\n\n")+2);
		return content;
	}
	

	
	
	/**
	 * 将邮件报文解码打包成Mail实例
	 * @param mail Mail实例
	 * @param original 邮件报文
	 */
	public void packMail(Mail mail,String original){
		mail.setFrom(findFrom(original));
		mail.setTo(findTo(original));
		//mail.setCc(findCc(original));
		mail.setSubj(findSubject(original));
		mail.setContent(findContent(original));
		
	}
	
}