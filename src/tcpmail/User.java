package tcpmail;

public class User {
	private String name="";
	private String pass="";
	private String smtpse = "";
	private String popse = "";
	public User()
	{
		super();
	}
	public User(String name,String pass,String smtpse,String popse)
	{
		this.name=name;
		this.pass=pass;
		this.smtpse=smtpse;
		this.popse=popse;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return name;
	}
	public void setPass(String pass)
	{
		this.pass=pass;
	}
	public String getPass()
	{
		return pass;
	}
	public void setSmtp(String smtpse)
	{
		this.smtpse=smtpse;
	}
	public String getSmtp()
	{
		return smtpse;
	}
	public void setPop(String popse)
	{
		this.popse=popse;
	}
	public String getPop()
	{
		return popse;
	}
}
