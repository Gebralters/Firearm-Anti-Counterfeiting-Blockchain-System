
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
 * Controller class responsible for handling the registration of a new firearm.
 */
public class NewController {

    // JavaFX injected fields
    @FXML
    private TextField serieltxt; // TextField for entering firearm serial number
    @FXML
    private TextField fnametxt; // TextField for entering firearm name
    @FXML
    private TextField fmodeltxt; // TextField for entering firearm model
    @FXML
    private TextField manulicnum; // TextField for entering manufacturer license number
    @FXML
    private TextField manuemailtxt; // TextField for entering manufacturer email
    @FXML
    private TextField typetxt; // TextField for entering firearm type
    @FXML
    private TextField manunametxt; // TextField for entering manufacturer name
    @FXML
    private TextField manuaddresstxt; // TextField for entering manufacturer address
    @FXML
    private TextField datetxt; // TextField for entering date

    private List<Transaction<GunData>> transactions = new ArrayList<>(); // List to store firearm registration transactions

    /**
     * Method to register a new firearm.
     */
    public void registernew() {
        // Create GunData object with input values
        GunData gd = new GunData(serieltxt.getText(), typetxt.getText(), fnametxt.getText(), fmodeltxt.getText(), datetxt.getText(),
                manunametxt.getText(), manuaddresstxt.getText(), manuemailtxt.getText(), manulicnum.getText(), "Not Sold");

        // Get the client associated with the selected node from the blockchain network
        Client c = Main.bn.selectNode();
        
        // Create a new transaction for registering the firearm
        Transaction<GunData> t = new Transaction<>("1", c.getaddress(), gd);
        
        // Add the transaction to the list of transactions
        transactions.add(t);
        
        // Add a block containing the transaction to the client's blockchain
        c.addBlock(transactions);
    }

    /**
     * Method to return to the main interface for firearm dealers.
     */
    public void returnhome() {
        try {
            // Load the FireArmdealer.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FireArmdealer.fxml"));
            // Load the root node from the FXML file
            Parent root = loader.load();
            // Get the stage from the TextField's scene and close it
            Stage stage = (Stage) serieltxt.getScene().getWindow();
            // Set the scene with the loaded root node
            stage.setScene(new Scene(root));
            // Show the stage
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Print stack trace if an exception occurs during the operation
        }
    }
}