
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

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for tracking firearms and managing the user interface.
 */
public class ClientTrackingController {

    // Injecting FXML elements
    @FXML
    private TextField serieltext;
    @FXML
    private TextArea trackinfotext;
    
    /**
     * Method to track a specific firearm.
     */
    public void TrackFireArm() {
        // Retrieve all transactions from the data structure
        List<Transaction<GunData>> alltransactions = Main.bn.getlist();
        
        // Iterate through each transaction
        for (Transaction<GunData> t : alltransactions) {
            GunData gd = t.getData();
            
            // Check if the firearm is sold, matches the serial number, and belongs to the current user
            if (!"Not Sold".equals(gd.getStatus()) && gd.getFirearmSerielnum().equals(serieltext.getText())
                    && gd.getBuyerEmail().equals(Main.tempemail)) {
                // Constructing data to display
                String data = "Firearm Serial Number: " + gd.getFirearmSerielnum() + "\n" +
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
                        "Firearm License: " + gd.getLicenseNum() + "\n" +
                        "Rejection Notice: " + gd.getAuthorityApprovalData() + "\n" +
                        "Status: " + gd.getStatus() + "\n";
                
                // Append the data to the text area
                trackinfotext.appendText(data);
            }       
        }
    }
    
    /**
     * Method to return to the home screen.
     */
    public void returnhome() {
        try {
            // Load the Buyer.fxml file to navigate back to the buyer interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Buyer.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) serieltext.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exception appropriately
        }
    }
}
