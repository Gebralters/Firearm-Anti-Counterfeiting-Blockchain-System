
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import acsse.csc03a3.Transaction;
import acsse.csc03a3.BlockChain.GunData;
import acsse.csc03a3.clients.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class responsible for handling firearm selling operations.
 */
public class SellController {
    
    // JavaFX injected fields
    @FXML
    private TextField fserieltxt; // TextField for entering firearm serial number
    @FXML
    private TextField pdatetxt; // TextField for entering purchase date
    @FXML
    private TextField bnamestxt; // TextField for entering buyer's full name
    @FXML
    private TextField bidnotxt; // TextField for entering buyer's ID number
    @FXML
    private TextField bemailtxt; // TextField for entering buyer's email
    @FXML
    private TextField buyerpnumtxt; // TextField for entering buyer's phone number
    @FXML
    private TextField reasonstxt; // TextField for entering reasons for buying
    
    // List to store transactions
    private List<Transaction<GunData>> transactions = new ArrayList<>();

    /**
     * Method to handle selling a firearm.
     */
    public void sell() {
        // Retrieve all transactions from the blockchain network
        List<Transaction<GunData>> alltransactions = Main.bn.getlist();
        
        // Iterate through each transaction
        for (Transaction<GunData> t : alltransactions) {
            GunData gd = t.getData();
            GunData newgundata = null;
            
            // Check if the firearm serial number matches
            if (gd.getFirearmSerielnum().equals(fserieltxt.getText())) {
                // Create a new GunData object with the updated information
                newgundata = new GunData(gd.getFirearmSerielnum(), gd.getFirearmType(), 
                                         gd.getFirearmName(), gd.getFirearmModel(), gd.getDateManufactured(), gd.getManufacName(),
                                         gd.getManufacAddress(), gd.getManufacEmail(), gd.getManufacLicence(), "Purchased");

                // Set buyer information
                newgundata.setBuyerFullnames(bnamestxt.getText());
                newgundata.setBuyerIDNum(bidnotxt.getText());
                newgundata.setBuyerEmail(bemailtxt.getText());
                newgundata.setBuyerPhoneNumber(buyerpnumtxt.getText());
                newgundata.setReasonsForBuying(reasonstxt.getText());
                newgundata.setPurchaseDate(pdatetxt.getText());
            }
            
            // If new GunData object is created
            if (newgundata != null) {
                // Select a client from the blockchain network
                Client c = Main.bn.selectNode();
                
                // Create a new transaction and add it to the transactions list
                Transaction<GunData> trans = new Transaction<>("1", c.getaddress(), newgundata);
                transactions.add(trans);
                
                // Add the block to the client's blockchain
                c.addBlock(transactions);
            }
        }
    }
    
    /**
     * Method to handle returning to the home page.
     */
    public void returnhome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FireArmdealer.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) fserieltxt.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exception appropriately
        }
    }
}