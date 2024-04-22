package acsse.csc03a3.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	//boolean for keeping the server running
	boolean serverrunning=false;
	ServerSocket sc=null;
	
	public Server(int portnum)
	{
		
		try {
			//binding the server to socket
			sc=new ServerSocket(portnum);
			System.out.println("Waiting for connection........");
			serverrunning=true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//method for running the server
	public void run() throws IOException
	{
		while(serverrunning)
		{
			System.out.println("running the server");
			Thread t=new Thread(new ServerBackend(sc.accept()));
			t.run();
			
		}
		
	}
    public static void main(String[] args)
    {
    	
    	try {
    		Server s=new Server(2000);
			s.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
