package tcpmail;

import java.io.Serializable;

public class Mail implements Serializable{
	private String from="";
	private String to="";
	private String subj="";
	private String content="";
	public Mail()
	{
		super();
	}
	public Mail(String from,String to,String subj,String content)
	{
		this.from=from;
		this.to=to;
		this.subj=subj;
		this.content=content;
	}
	public String getFrom()
	{
		return from;
	}
	public void setFrom(String from)
	{
		this.from = from;
	}
	public String getTo()
	{
		return to;
	}
	public void setTo(String to)
	{
		this.to=to;
	}
	public String getSubj()
	{
		return subj;
	}
	public void setSubj(String subj)
	{
		this.subj=subj;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content=content;
	}
	public String toString()
	{
		return "From:"+from+"\nTo:"+to+"\n"+"Subject:"+subj+"\n"+"Content-Type: text/plain;charset=\"utf-8\"\r\n"+content+"\n";
	}

}
