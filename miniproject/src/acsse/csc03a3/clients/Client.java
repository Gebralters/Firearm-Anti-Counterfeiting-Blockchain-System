package acsse.csc03a3.clients;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Client {
    protected String password;
    protected String username;
	
    
    //client side socket
	protected Socket s=null;
	protected DataOutputStream dout=null;
	protected DataInputStream din=null;
	protected PrintWriter pw=null;
	protected BufferedReader br=null;
	
	
	
	
	public Client()
	{
		try {
			s=new Socket("localhost",2000);
            System.out.println("Connected................");
			
			pw=new PrintWriter(s.getOutputStream(),true);
			br=new BufferedReader(new InputStreamReader(s.getInputStream()));
			dout=new DataOutputStream(s.getOutputStream());
			din=new DataInputStream(s.getInputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//A method for encrypting the password of client
	protected static String calculateSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input.getBytes());

        // Convert byte array to hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
	
	 protected void send(String message)
 	{
 		
 		pw.println(message);
 		pw.flush();
 	}
	 protected String response()
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
	 public String getpass()
	 {
		 String pass=null;
		 try {
			pass= calculateSHA256(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return pass;
	 }
	 public String getusername()
	 {
		 return username;
	 }
}
