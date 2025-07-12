import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import acsse.csc03a3.Transaction;
import acsse.csc03a3.BlockChain.GunData;
import acsse.csc03a3.clients.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AuthoritiesController {

    // FXML annotations for JavaFX components
    @FXML
    private ImageView imagetxt;
    @FXML
    private Label nametext;
    @FXML
    private TextField serieltext;
    @FXML
    private TextArea viewtext;
    @FXML
    private TextField commenttext;
    @FXML
    private TextField licensetext;

    // List to hold transactions
    private List<Transaction<GunData>> transactions = new ArrayList<>();

    // Method to initialize the controller
    public void initialize() {
        // Retrieving buyer information and setting up the view
        String info = Main.ch.setupbuyer(Main.tempemail);
        StringTokenizer st = new StringTokenizer(info, "#");

        // Extracting information from the string
        String fname = st.nextToken();
        String lname = st.nextToken();
        String surname = st.nextToken();
        String email = st.nextToken();
        String type = st.nextToken();
        String image = st.nextToken();
        String idnum = st.nextToken();
        String phonenum = st.nextToken();

        // Loading image and setting it to the ImageView
        File f = new File(image);
        Image img = new Image(f.toURI().toString());
        imagetxt.setImage(img);

        // Setting the full name
        String name = fname + " " + lname;
        nametext.setText(name);
    }

    // Method to navigate to tracking page
    public void TrackFireArm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AuthoritiesTracking.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) viewtext.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exception appropriately
        }
    }

    // Method to approve a firearm application
    public void Approve() {
        // Retrieving list of all transactions
        List<Transaction<GunData>> alltransactions = Main.bn.getlist();
        for (Transaction<GunData> t : alltransactions) {
            GunData gd = t.getData();
            GunData newgundata = null;
            // Checking if the application is pending and serial number matches
            if ("License Application Pending".equals(gd.getStatus()) && gd.getFirearmSerielnum().equals(serieltext.getText())) {

                // Creating a new GunData object with updated information
                newgundata = new GunData(gd.getFirearmSerielnum(), gd.getFirearmType(),
                        gd.getFirearmName(), gd.getFirearmModel(), gd.getDateManufactured(), gd.getManufacName(),
                        gd.getManufacAddress(), gd.getBuyerEmail(), gd.getManufacLicence(), "Approved");

                // Setting additional information
                newgundata.setBuyerFullnames(gd.getBuyerFullnames());
                newgundata.setBuyerIDNum(gd.getBuyerIDNum());
                newgundata.setBuyerEmail(gd.getBuyerEmail());
                newgundata.setBuyerPhoneNumber(gd.getBuyerPhoneNumber());
                newgundata.setReasonsForBuying(gd.getReasonsForBuying());
                newgundata.setPurchaseDate(gd.getPurchaseDate());
                newgundata.setAuthorityApprovalData(commenttext.getText());
                newgundata.setLicenseNum(licensetext.getText());

            }
            // If new data is available, add it to transactions list and update the block
            if (newgundata != null) {
                Client c = Main.bn.selectNode();
                Transaction<GunData> trans = new Transaction<GunData>("1", c.getaddress(), newgundata);
                transactions.add(trans);
                c.addBlock(transactions);
            }

        }
        // Clearing the view text area
        viewtext.clear();
    }

    // Method to reject a firearm application
    public void Reject() {
        // Similar process to Approve method but changing status to 'Reject'
        List<Transaction<GunData>> alltransactions = Main.bn.getlist();
        for (Transaction<GunData> t : alltransactions) {
            GunData gd = t.getData();
            GunData newgundata = null;
            if ("License Application Pending".equals(gd.getStatus()) && gd.getFirearmSerielnum().equals(serieltext.getText())) {
                newgundata = new GunData(gd.getFirearmSerielnum(), gd.getFirearmType(),
                        gd.getFirearmName(), gd.getFirearmModel(), gd.getDateManufactured(), gd.getManufacName(),
                        gd.getManufacAddress(), gd.getBuyerEmail(), gd.getManufacLicence(), "Reject");

                newgundata.setBuyerFullnames(gd.getBuyerFullnames());
                newgundata.setBuyerIDNum(gd.getBuyerIDNum());
                newgundata.setBuyerEmail(gd.getBuyerEmail());
                newgundata.setBuyerPhoneNumber(gd.getBuyerPhoneNumber());
                newgundata.setReasonsForBuying(gd.getReasonsForBuying());
                newgundata.setPurchaseDate(gd.getPurchaseDate());
                newgundata.setAuthorityApprovalData(commenttext.getText());
                newgundata.setLicenseNum(licensetext.getText());

            }
            if (newgundata != null) {
                Client c = Main.bn.selectNode();
                Transaction<GunData> trans = new Transaction<GunData>("1", c.getaddress(), newgundata);
                transactions.add(trans);
                c.addBlock(transactions);
            }

        }
        viewtext.clear();
    }

    // Method to view pending firearm applications
    public void viewapplication() {
        viewtext.clear();
        List<Transaction<GunData>> alltransactions = Main.bn.getlist();
        for (Transaction<GunData> t : alltransactions) {
            GunData gd = t.getData();

            if ("License Application Pending".equals(gd.getStatus())) {
                String data =
                        "Buyer Full Name: " + gd.getBuyerFullnames() + "\n" +
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
                                "Purchase Date: " + gd.getPurchaseDate() + "\n";

                viewtext.appendText(data);
            }
            viewtext.appendText("\n");
        }
    }

    // Method to view approved firearm applications
    public void viewapprovedapplication() {
        viewtext.clear();
        List<Transaction<GunData>> alltransactions = Main.bn.getlist();
        for (Transaction<GunData> t : alltransactions) {
            GunData gd = t.getData();

            if ("Approved".equals(gd.getStatus())) {
                String data =
                        "Buyer Full Name: " + gd.getBuyerFullnames() + "\n" +
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
                                "Purchase Date: " + gd.getPurchaseDate() + "\n";

                viewtext.appendText(data);
            }
            viewtext.appendText("\n");

        }
    }

    // Method to quit and return to the main login page
    public void quit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainlog.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) imagetxt.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exception appropriately
        }
    }
}
