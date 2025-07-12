package acsse.csc03a3.BlockChain;





import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import acsse.csc03a3.Block;

import acsse.csc03a3.Transaction;
import acsse.csc03a3.clients.AuthoritiesClient;
import acsse.csc03a3.clients.BuyerClient;
import acsse.csc03a3.clients.Client;
import acsse.csc03a3.clients.FirearmDealers;

/**
 * Represents the blockchain network.
 */
public class BlockchainNetwork {
    private int nodesize = 0; // Size of the network
    private Client selectedNode = null; // Selected node
    private ArrayList<Client> ClientNodes; // List of participating nodes in the network
    
    /**
     * Constructor to initialize the blockchain network.
     */
    public BlockchainNetwork() {
        this.ClientNodes = new ArrayList<Client>(); // Initialize the list of client nodes
    }
    
    /**
     * Method to get a list of all transactions from the blockchain.
     * @return List of transactions
     */
    public List<Transaction<GunData>> getlist() {
        List<Transaction<GunData>> returnlist = new ArrayList<>(); // Initialize the return list
        
        // Get the blockchain from the selected node
        MyBlockchain<GunData> mb = selectNode().getblockchain();
        List<Block<GunData>> lib = mb.getChain(); // Get the blocks from the blockchain
        
        // Iterate through each block and add its transactions to the return list
        for (Block<GunData> b : lib) {
            returnlist.addAll(b.getTransactions()); // Use addAll() to add all transactions from the block
        }
        
        return returnlist; // Return the list of transactions
    }
    
    /**
     * Method to add a node to the network.
     * @param node The node to be added
     */
    public void addNode(Client node) {
        ClientNodes.add(node); // Add the node to the list of client nodes
    }
    
    /**
     * Method to get the size of the network.
     * @return Size of the network
     */
    public int getSize() {
        return nodesize; // Return the size of the network
    }
    
    /**
     * Method to set the size of the network.
     * @param size Size of the network
     */
    public void setSize(int size) {
        this.nodesize = size; // Set the size of the network
    }
    
    /**
     * Method to randomly select a node from the network.
     * @return Selected node
     */
    public Client selectNode() {
        // Randomly select a node from the list
        if (ClientNodes.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(ClientNodes.size());
        return ClientNodes.get(index); // Return the selected node
    }
    
    /**
     * Method to broadcast a block to all nodes in the network.
     * @param block The block to be broadcasted
     */
    public void broadcastBlock(Block<GunData> block) {
        // Broadcast the block to all nodes except the one that added the block
        for (Client node : ClientNodes) {
            if (!node.equals(selectedNode)) {
                if (node instanceof BuyerClient) {
                    BuyerClient bc = (BuyerClient) node;
                    bc.addBlock(block.getTransactions());
                } else if (node instanceof FirearmDealers) {
                    FirearmDealers fd = (FirearmDealers) node;
                    fd.addBlock(block.getTransactions());
                } else if (node instanceof AuthoritiesClient) {
                    AuthoritiesClient ad = (AuthoritiesClient) node;
                    ad.addBlock(block.getTransactions());
                }
            }
        }
    }
}

