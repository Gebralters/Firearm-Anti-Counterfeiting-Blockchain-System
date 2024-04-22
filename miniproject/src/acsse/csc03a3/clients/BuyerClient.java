package acsse.csc03a3.clients;




import java.io.IOException;


public class BuyerClient extends Client{
	
	
		
		public BuyerClient()
		{
			super();	
		}
        public void login()
        {
        	send("HI");
        	System.out.println(response());
        	
        	try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
       
}

