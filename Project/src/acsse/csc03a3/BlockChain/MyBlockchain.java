package acsse.csc03a3.BlockChain;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import acsse.csc03a3.Block;

import acsse.csc03a3.Transaction;


public class MyBlockchain<T>{
	/**
	 * 
	 */

	private List<Block<T>> chain;
    private List<String> stakes; // Contains information about stakeholders

    public MyBlockchain() {
        this.chain = new ArrayList<>();
        this.stakes = new ArrayList<>();
        createGenesisBlock();
    }

    public void addBlock(List<Transaction<T>> transactions) {
        Block<T> block = new Block<>(getLatestBlock().getHash(), transactions);
        chain.add(block);
    }

 // Method to calculate Proof of Stake (PoS) nonce for the given block
    public long calculatePoSNonce(Block<T> block) {
        long nonce = 0;
        while (!validatePoSNonce(block, nonce)) {
            nonce++;
        }
        return nonce;
    }

    public Block<T> createGenesisBlock() {
        Block<T> genesisBlock = new Block<>("0", new ArrayList<>());
        chain.add(genesisBlock);
        return genesisBlock;
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block<T> currentBlock = (Block<T>) chain.get(i);
            Block<T> previousBlock = (Block<T>) chain.get(i - 1);

            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    
 // Method to apply SHA-256 hashing algorithm to the given input
    private String applySHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    private boolean validatePoSNonce(Block<T> block, long nonce) {
        // Combine nonce with block data and calculate hash
        String data = block.getPreviousHash() + block.getTransactions() + nonce;
        String hash = applySHA256(data);

        // Check if hash satisfies Proof of Stake (PoS) conditions
        return hash.startsWith("0000"); 
    }

 // Method to register a stake for a given address with a specified amount
    public void registerStake(String address, int amount) {
        stakes.add(address + ":" + amount);
    }

    // Method to select a validator from the stakeholders based on some criteria
    public String selectValidator() {
        Random random = new Random();
        int index = random.nextInt(stakes.size());
        return stakes.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Block<T> block : chain) {
            sb.append(block.toString()).append("\n");
        }
        return sb.toString();
    }

    public List<Block<T>> getChain() {
        return chain;
    }

    public List<String> getStakes() {
        return stakes;
    }

    

    public Block<T> getLatestBlock() {
        return (Block<T>) chain.get(chain.size() - 1);
    }

    public String getLatestBlockHash() {
        return getLatestBlock().getHash();
    }
}
