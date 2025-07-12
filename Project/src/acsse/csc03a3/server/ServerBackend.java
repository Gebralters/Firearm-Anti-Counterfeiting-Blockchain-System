package acsse.csc03a3.server;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;
import java.io.InputStreamReader;

import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;


//ServerBackend class implementing Runnable for handling server-side operations
public class ServerBackend implements Runnable {
 
 // Socket for client connection
 private Socket s=null;
 private boolean running=false;
 // Streams for communication
 private DataOutputStream dout=null;
 private DataInputStream din=null;
 private BufferedReader br=null;
 private PrintWriter pw=null;
 // Database connection
 private Connection connection=null;

 // Constructor for ServerBackend class
 public ServerBackend(Socket s) {
     try {
         // Initialize socket and streams
         this.s=s;
         System.out.println("Connected");
         running=true;
         // MySQL Database connection 
         try {
             // Register JDBC driver
             Class.forName("com.mysql.cj.jdbc.Driver");
             // Connect to database
             connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ProjectDB", "root", "12345");
         } catch (SQLException | ClassNotFoundException e) {
             // Print error message if connection fails
             System.err.println("Unable to connect to Database");
             e.printStackTrace();
         }
         // Initialize streams
         dout=new DataOutputStream(s.getOutputStream());
         din=new DataInputStream(s.getInputStream());
         pw=new PrintWriter(s.getOutputStream(),true);
         br=new BufferedReader(new InputStreamReader(s.getInputStream()));
     } catch(IOException ex) {
         ex.getStackTrace();
     }
 }
	@Override
	public void run() {
	    // The main loop that runs while the server is running
	    while(running)
	    {
	        // Read the client's request
	        String reply=response();
	        
	        // Check the type of request received
	        if(reply.equals("LOGIN"))
	        {
	            // Notify the client that the server is ready to receive information
	            send("OK 100");
	            
	            // Receive the email and password from the client
	            String emailpass=response();
	            
	            // Tokenize the received string to extract email and password
	            StringTokenizer st=new StringTokenizer(emailpass,"#");
	            String email=st.nextToken();
	            String password=st.nextToken();
	            
	            // Hash the received password for comparison
	            try {
	                password=calculateSHA256(password);
	            } catch (NoSuchAlgorithmException e) {
	                e.printStackTrace();
	            }
	            
	            // Validate the user's credentials
	            if(isValidUser(email,password))
	            {
	                // If login is successful, send OK 101 along with the user type
	                int usertype=getUsertype(email);
	                String type=Integer.toString(usertype);
	                send("OK 101#"+type);
	                
	            }
	            else
	            {
	                // If login fails, send OK 102
	                send("OK 102");
	            }
	        }
	        else if(reply.equals("REGISTER"))
	        {
	            // Notify the client that the server is ready to receive information
	            send("OK 100");
	            
	            // Receive the registration information from the client
	            String res=response();
	            try {
	                try {
	                    // Attempt to register the user
	                    int utype=register(res);
	                    
	                    // Tokenize the received string to extract user information
	                    StringTokenizer st=new StringTokenizer(res,"#");
	                    String fname=st.nextToken();
	                    String lname=st.nextToken();
	                    String surname=st.nextToken();
	                    String email=st.nextToken();
	                    
	                    // Get the user ID corresponding to the registered email
	                    int userid=getuserid(email);
	                    if(utype!=-1)
	                    {
	                        // If registration is successful, send OK 101 along with the user ID and type
	                        send("OK 101#"+userid+"#"+utype);
	                    }
	                    else
	                    {
	                        // If registration fails, send OK 102 with user ID 0
	                        send("OK 102#0");
	                    }
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            } catch (NoSuchAlgorithmException e) {
	                e.printStackTrace();
	            }
	            
	        }
	        else if(reply.equals("BUYER"))
	        {
	            // Notify the client that the server is ready to receive information
	            send("OK 100");
	            
	            // Receive the buyer's request from the client
	            String buyresponse=response();
	            StringTokenizer st=new StringTokenizer(buyresponse,"#");
	            if(buyresponse.startsWith("GETINFO"))
	            {
	                // If the request is to get user information, extract the email and send the information back
	                String st1=st.nextToken();
	                String em=st.nextToken();
	                send(getuserinfo(em));
	            }
	        }
	        else if(reply.equals("MANUFAC"))
	        {
	            // Notify the client that the server is ready to receive information
	            send("OK 100");
	            
	            // Receive the manufacturer's request from the client
	            String manuresponse=response();
	            StringTokenizer st=new StringTokenizer(manuresponse,"#");
	            if(manuresponse.startsWith("GETINFO"))
	            {
	                // If the request is to get user information, extract the email and send the information back
	                String st1=st.nextToken();
	                String em=st.nextToken();
	                send(getuserinfo(em));
	            }
	        }
	        else if(reply.equals("AUTH"))
	        {
	            // Notify the client that the server is ready to receive information
	            send("OK 100");
	            
	            // Receive the authorization request from the client
	            String auth=response();
	            StringTokenizer st=new StringTokenizer(auth,"#");
	            if(auth.startsWith("GETINFO"))
	            {
	                // If the request is to get user information, extract the email and send the information back
	                String st1=st.nextToken();
	                String em=st.nextToken();
	                send(getuserinfo(em));
	            }
	        }
	        else if(reply.equals("EXIT"))
	        {
	            // Notify the client that the server is exiting
	            send("EXIT OK");
	            running=false; // Set the running flag to false to exit the loop
	        }
	    }
	    
	    // Cleanup resources if the server is no longer running
	    if(running==false)
	    {
	        try {
	            // Close all streams and the socket
	            dout.close();
	            din.close();
	            pw.close();
	            br.close();
	            s.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	private void send(String message)
 	{
 		
 		pw.println(message);
 		pw.flush();
 	}
	// Method to register a new user
	private int register(String res) throws NoSuchAlgorithmException, SQLException {
	    // Tokenize the input string
	    StringTokenizer st=new StringTokenizer(res,"#");
	    // Extract information from the tokenized string
	    String fname=st.nextToken();
	    String lname=st.nextToken();
	    String surname=st.nextToken();
	    String email=st.nextToken();
	    int type=Integer.parseInt(st.nextToken());
	    String image=st.nextToken();
	    String idnum=st.nextToken();
	    String phonenum=st.nextToken();
	    String pass=st.nextToken();
	    pass=calculateSHA256(pass); // Encrypt the password using SHA-256 algorithm
	    
	    // Check if the email is unique in the database
	    boolean emailunique=false;
	    String query = "SELECT * FROM user WHERE EMAIL = ?";
	    try {
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, email);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        emailunique=resultSet.next();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        emailunique=false;
	    }
	    
	    if(emailunique==false) { // If email is unique
	        // SQL query to insert user data into the database
	        String sql = "INSERT INTO user (F_NAME, L_NAME, SURNAME, EMAIL, U_TYPE, U_IMGURL, ID_NUMBER, P_NUMBER, PASS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	        // Create a PreparedStatement object to execute the query
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, fname);
	        preparedStatement.setString(2, lname);
	        preparedStatement.setString(3, surname);
	        preparedStatement.setString(4, email);
	        preparedStatement.setInt(5, type);
	        preparedStatement.setString(6, image);
	        preparedStatement.setString(7, idnum);
	        preparedStatement.setString(8, phonenum);
	        preparedStatement.setString(9, pass);
	       
	        // Execute the PreparedStatement to insert data
	        int rowsAffected = preparedStatement.executeUpdate();

	        // Check if data insertion was successful
	        if (rowsAffected > 0) {
	            System.out.println("Data inserted successfully.");
	            return type; // Return the user type
	        } else {
	            System.out.println("Failed to insert data.");
	            return -1; // Return -1 to indicate failure
	        }
	    } else {
	        System.out.println("duplicate"); // Log duplicate email
	        return -1; // Return -1 to indicate failure due to duplicate email
	    }
	}

	// Method to read response from client
	private String response() {
	    String res=null;
	    try {
	         res=br.readLine(); // Read a line from BufferedReader
	    } catch (IOException e) {
	        e.printStackTrace(); // Print stack trace in case of IOException
	    }
	    return res; // Return the read response
	}

	// Method to validate user credentials
	private boolean isValidUser(String email, String password) {
	    String query = "SELECT * FROM user WHERE EMAIL = ? AND PASS = ?";
	    try {
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, email);
	        preparedStatement.setString(2, password);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        return resultSet.next(); // Return true if user exists with the given email and password
	    } catch (SQLException e) {
	        e.printStackTrace(); // Print stack trace in case of SQL exception
	        return false; // Return false in case of SQL exception
	    }
	}

	// Method to get user type based on email
	private int getUsertype(String email) {
	    String query = "SELECT U_TYPE FROM user WHERE EMAIL = ?";
	    try {
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, email);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            return resultSet.getInt("U_TYPE"); // Return the user type if user exists
	        } else {
	            return -1; // Return -1 if user does not exist
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Print stack trace in case of SQL exception
	        return -1; // Return -1 in case of SQL exception
	    }
	}

	// Method to get user ID based on email
	private int getuserid(String email) {
	    String query = "SELECT U_ID FROM user WHERE EMAIL = ?";
	    try {
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, email);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            return resultSet.getInt("U_ID"); // Return the user ID if user exists
	        } else {
	            return -1; // Return -1 if user does not exist
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Print stack trace in case of SQL exception
	        return -1; // Return -1 in case of SQL exception
	    }
	}
	// Method to retrieve user information based on email
	private String getuserinfo(String email) {
	    String infostr=""; // Initialize empty string to hold user information
	    String query = "SELECT * FROM user WHERE EMAIL = ?"; // SQL query to select user data by email
	    try {
	        // Prepare SQL statement
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, email); // Set email parameter in the prepared statement
	        // Execute SQL query
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) { // If user with given email is found
	            // Retrieve user data from the result set
	            String firstName = resultSet.getString("F_NAME");
	            String lastName = resultSet.getString("L_NAME");
	            String surname = resultSet.getString("SURNAME");
	            String uemail = resultSet.getString("EMAIL");
	            String userType = resultSet.getString("U_TYPE");
	            String imageURL = resultSet.getString("U_IMGURL");
	            String idNumber = resultSet.getString("ID_NUMBER");
	            String phoneNumber = resultSet.getString("P_NUMBER");
	            // Concatenate user data into a single string with '#' as delimiter
	            infostr=firstName+"#"+lastName+"#"+surname+"#"+uemail+"#"+userType+"#"+imageURL+"#"+idNumber+"#"+phoneNumber;
	            return infostr; // Return the concatenated user information
	        } else {
	            // No user found with the given email
	            return null; // Return null to indicate no user found
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Print stack trace for any SQL exceptions
	        return null; // Return null in case of SQL exception
	    }
	}

	// Method for encrypting the password of client using SHA-256 algorithm
	protected static String calculateSHA256(String input) throws NoSuchAlgorithmException {
	    MessageDigest digest = MessageDigest.getInstance("SHA-256"); // Create SHA-256 message digest instance
	    byte[] hashBytes = digest.digest(input.getBytes()); // Generate hash bytes for the input string

	    // Convert byte array to hexadecimal string
	    StringBuilder hexString = new StringBuilder();
	    for (byte hashByte : hashBytes) {
	        String hex = Integer.toHexString(0xff & hashByte); // Convert byte to hexadecimal
	        if (hex.length() == 1) {
	            hexString.append('0'); // Append '0' if single character hexadecimal
	        }
	        hexString.append(hex); // Append hexadecimal to the string builder
	    }
	    return hexString.toString(); // Return the hexadecimal string representing the hashed input
	}
    

}
