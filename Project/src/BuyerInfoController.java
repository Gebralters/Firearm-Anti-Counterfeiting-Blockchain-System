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

public class BuyerInfoController {
    // FXML annotation for JavaFX component
    @FXML
    private TextField reasonstext;
    // List to hold transactions
    private List<Transaction<GunData>> transactions = new ArrayList<>();

    // Method to apply for a firearm
    public void apply() {
        // Retrieve all transactions
        List<Transaction<GunData>> alltransactions = Main.bn.getlist();
        for (Transaction<GunData> t : alltransactions) {
            GunData gd = t.getData();
            GunData newgundata = null;
            // Check if firearm is not sold and buyer's email matches
            if (!"Not Sold".equals(gd.getStatus()) && gd.getBuyerEmail().equals(Main.tempemail)) {
                // Create a new GunData object with updated information
                newgundata = new GunData(gd.getFirearmSerielnum(), gd.getFirearmType(),
                        gd.getFirearmName(), gd.getFirearmModel(), gd.getDateManufactured(), gd.getManufacName(),
                        gd.getManufacAddress(), gd.getBuyerEmail(), gd.getManufacLicence(), "License Application Pending");

                // Set additional information
                newgundata.setBuyerFullnames(gd.getBuyerFullnames());
                newgundata.setBuyerIDNum(gd.getBuyerIDNum());
                newgundata.setBuyerEmail(gd.getBuyerEmail());
                newgundata.setBuyerPhoneNumber(gd.getBuyerPhoneNumber());
                newgundata.setReasonsForBuying(reasonstext.getText());
                newgundata.setPurchaseDate(gd.getPurchaseDate());
            }
            // If new data is available, add it to transactions list and update the block
            if (newgundata != null) {
                Client c = Main.bn.selectNode();
                Transaction<GunData> trans = new Transaction<>("1", c.getaddress(), newgundata);
                transactions.add(trans);
                c.addBlock(transactions);
            }

        }
    }

    // Method to return to the buyer's home screen
    public void returnhome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Buyer.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) reasonstext.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exception appropriately
        }
    }
}
