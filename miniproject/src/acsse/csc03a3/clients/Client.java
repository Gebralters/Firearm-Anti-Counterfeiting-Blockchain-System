package acsse.csc03a3.clients;


import java.io.Serializable;

import java.util.List;

import acsse.csc03a3.Blockchain;
import acsse.csc03a3.Transaction;
import acsse.csc03a3.BlockChain.GunData;
import acsse.csc03a3.BlockChain.MyBlockchain;

public abstract class Client implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7691185054818054700L;
	private String nodeaddress="0";
	private MyBlockchain<GunData> blockchain;

    public Client(String nodeaddress) {
    	blockchain=new MyBlockchain<>();
    	this.nodeaddress=nodeaddress;
    }

    public void addBlock(List<Transaction<GunData>> transactions) {
        blockchain.addBlock(transactions);
    }
    public MyBlockchain<GunData> getblockchain()
    {
    	return blockchain;
    }
	
    public void displayChain() {
        System.out.println(blockchain.toString());
    }
    public String getaddress()
    {
 	   return nodeaddress;
    }

    
}
