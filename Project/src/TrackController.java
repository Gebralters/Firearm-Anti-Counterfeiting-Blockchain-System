
import java.io.IOException;
import java.util.List;

import acsse.csc03a3.Transaction;
import acsse.csc03a3.BlockChain.GunData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class responsible for tracking firearm information.
 */
public class TrackController {

    // JavaFX injected fields
    @FXML
    private TextField serieltxt; // TextField for entering firearm serial number
    
    @FXML
    private TextArea viewtext; // TextArea for displaying firearm information
    
    /**
     * Method to track firearm information based on the provided serial number.
     */
    public void Track() {
        // Retrieve all transactions from the blockchain network
        List<Transaction<GunData>> alltransactions = Main.bn.getlist();
        
        // Iterate through each transaction
        for (Transaction<GunData> t : alltransactions) {
            GunData gd = t.getData();
            
            // Check if the firearm has been sold and if the serial number matches
            if (!"Not Sold".equals(gd.getStatus()) && gd.getFirearmSerielnum().equals(serieltxt.getText())) {
                // Construct the information to be displayed
                String data = "Buyer Full Name: " + gd.getBuyerFullnames() + "\n" +
                              "Buyer ID Number: " + gd.getBuyerIDNum() + "\n" +
                              "Buyer Email: " + gd.getBuyerEmail() + "\n" +
                              "Buyer Phone Number: " + gd.getBuyerPhoneNumber() + "\n" +
                              "Firearm Serial Number: " + gd.getFirearmSerielnum() + "\n" +
                              "Firearm Type: " + gd.getFirearmType() + "\n" +
                              "Firearm Model: " + gd.getFirearmModel() + "\n" +
                              "Firearm Name: " + gd.getFirearmName() + "\n" +
                              "Date Manufactured: " + gd.getDateManufactured() + "\n" +
                              "Manufacturer Name: " + gd.getManufacName() + "\n" +
                              "Manufacturer Address: " + gd.getManufacAddress() + "\n" +
                              "Manufacturer Email: " + gd.getManufacEmail() + "\n" +
                              "Manufacturer Licence: " + gd.getManufacLicence() + "\n" +
                              "Reasons for Buying: " + gd.getReasonsForBuying() + "\n" +
                              "Purchase Date: " + gd.getPurchaseDate() + "\n" +
                              "Status: " + gd.getStatus() + "\n";
                
                // Append the information to the viewtext TextArea
                viewtext.appendText(data);
            }
            
            // Add a new line for readability
            viewtext.appendText("\n");
        }
    }
    
    /**
     * Method to return to the home page.
     */
    public void returnhome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Authorities.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) serieltxt.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exception appropriately
        }
    }
}