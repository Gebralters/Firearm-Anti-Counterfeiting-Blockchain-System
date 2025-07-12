/**
 * 
 */


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import acsse.csc03a3.clients.AuthoritiesClient;
import acsse.csc03a3.clients.BuyerClient;
import acsse.csc03a3.clients.FirearmDealers;

/**
 * 
 */
public class ClientHandler {
	//client side socket
		protected Socket s=null;
		protected DataOutputStream dout=null;
		protected DataInputStream din=null;
		protected PrintWriter pw=null;
		protected BufferedReader br=null;
		
		public int typeuser;
		public boolean running=false;
		

	public ClientHandler()
	{
		try {
			s=new Socket("localhost",2000);
            System.out.println("Connected................");
			
			pw=new PrintWriter(s.getOutputStream(),true);
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			dout=new DataOutputStream(s.getOutputStream());
			din=new DataInputStream(s.getInputStream());
			running=true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public boolean Login(String email, String password)
	{
		boolean logstatus=false;
		send("LOGIN");
		
		String status1=response();
		
		String em=email;
		String pass= password;
		
		send(em+"#"+pass);
		
		String resp=response();
		
		
		if(resp.contains("101"))
		{
			StringTokenizer st=new StringTokenizer(resp,"#");
			String status=st.nextToken();
			String usertype=st.nextToken();
			
			logstatus=true;
			if(usertype.equals("1"))
			{
				System.out.println("Buyer has logged in");
				typeuser=1;
			}
			else if(usertype.equals("2"))
			{
				System.out.println("Manufacturer has logged in");
				typeuser=2;
			}
			else if(usertype.equals("3"))
			{
				System.out.println("Manufacturer has logged in");
				typeuser=3;
			}
		}
		else if(resp.contains("102"))
		{
			System.out.println("Unable to log in");
			logstatus=false;
		}
		return logstatus;
	}
	public String setupbuyer(String getemail)
	{
		send("BUYER");
		String status1=response();
		
		send("GETINFO#"+getemail);
		
		String info=response();
		
		return info;
	}
	public String getdealerinfo(String getemail)
	{
		send("MANUFAC");
		String status1=response();
		
		send("GETINFO#"+getemail);
		
		String info=response();
		
		return info;
	}
	public String getauthoinfo(String getemail)
	{
		
		send("AUTH");
		String status1=response();
		
		send("GETINFO#"+getemail);
		
		String info=response();
		
		return info;
	}
	public boolean register(String fname,String lname, String surname, String email,String idnum,String phonenum,String pass)
	{
		send("REGISTER");
		String status1=response();
		
		
		
		String type="1";
		String image="data/images/nouser.jpeg";
		
		
	
		
		String sendstr=fname+"#"+lname+"#"+surname+"#"+email+"#"+type+"#"+image+"#"+idnum+"#"+phonenum+"#"+pass;
		send(sendstr);
		String res=response();
		if(res.contains("101"))
		{
			StringTokenizer st=new StringTokenizer(res,"#");
			String re=st.nextToken();
			String uid=st.nextToken();
			String usernum=st.nextToken();
			int utype=Integer.parseInt(usernum);
			
			if(utype==1)
			{
				BuyerClient bc=new BuyerClient(uid);
				Main.bn.addNode(bc);
			}
			else if(utype==2)
			{
				FirearmDealers fs=new FirearmDealers(uid);
				Main.bn.addNode(fs);
			}
			else if(utype==3)
			{
				AuthoritiesClient ac=new AuthoritiesClient(uid);
				Main.bn.addNode(ac);
			}
			
			
			System.out.println("Resistration success");
			return true;
			
		}
		else if(res.contains("102"))
		{
			System.out.println("Registration failed");
			return false;
		}
		
		return false;
	}
	public void quit()
	{
		send("EXIT");
		String receive=response();
		closeconnection();
	}
	private void send(String message)
 	{
 		
 		pw.println(message);
 		pw.flush();
 	}
	private String response()
 	{
 		String res=null;
 		try {
 			 res=br.readLine();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		return res;
 	}
	public void closeconnection()
	{
		try {
			s.close();
			pw.close();
			dout.close();
			din.close();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
