package acsse.csc03a3.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerBackend implements Runnable{

	
	private Socket s=null;
	private boolean running=false;
	private DataOutputStream dout=null;
	private DataInputStream din=null;
	private BufferedReader br=null;
	private PrintWriter pw=null;
	public ServerBackend(Socket s)
	{
		try
		{
			this.s=s;
			System.out.println("Connected");
			running=true;
			
			dout=new DataOutputStream(s.getOutputStream());
			din=new DataInputStream(s.getInputStream());
			pw=new PrintWriter(s.getOutputStream(),true);
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
		}
		catch(IOException ex)
		{
			ex.getStackTrace();
			
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(running)
		{
			
			if(response().equals("HI"))
			{
				send("received");
			}
			running=false;
		}
		
		if(running==false)
		{
			try {
				
				dout.close();
				din.close();
				pw.close();
				br.close();
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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

}
