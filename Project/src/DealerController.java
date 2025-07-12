import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import acsse.csc03a3.Transaction;
import acsse.csc03a3.BlockChain.GunData;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Controller class for managing dealer interface functionality.
 */
public class DealerController {

    // Injecting FXML elements
    @FXML
    private ImageView imagetxt;
    @FXML
    private Label nametxt;
    @FXML
    private TextArea infotext;
    
    /**
     * Method called upon initializing the dealer interface.
     * Retrieves and displays buyer information.
     */
    public void initialize() {
        // Retrieve buyer information using the ClientHandler
        String info = Main.ch.setupbuyer(Main.tempemail);
        StringTokenizer st = new StringTokenizer(info, "#");
        
        // Extract buyer information from the retrieved data
        String fname = st.nextToken();
        String lname = st.nextToken();
        String surname = st.nextToken();
        String email = st.nextToken();
        String type = st.nextToken();
        String image = st.nextToken();
        String idnum = st.nextToken();
        String phonenum = st.nextToken();
        
        // Display buyer's image
        File f = new File(image);
        Image img = new Image(f.toURI().toString());
        imagetxt.setImage(img);
        
        // Display buyer's name
        String name = lname + " " + fname;
        nametxt.setText(name);
    }
    
    /**
     * Method to navigate to the sell firearm interface.
     */
    public void sellfirearms() {
        try {
            // Load SellFireArm.fxml file to navigate to the sell firearm interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SellFireArm.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nametxt.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exception appropriately
        }
    }
    
    /**
     * Method to navigate to the capture new firearm interface.
     */
    public void capturenew() {
        try {
            // Load NewFireArm.fxml file to navigate to the capture new firearm interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewFireArm.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nametxt.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exception appropriately
        }
    }
    
    /**
     * Method to view all sold firearms.
     */
    public void viewsoldfirearms() {
        infotext.clear();
        // Retrieve all transactions from the data structure
        List<Transaction<GunData>> alltransactions = Main.bn.getlist();
        // Iterate through each transaction and display sold firearms
        for (Transaction<GunData> t : alltransactions) {
            GunData gd = t.getData();
            if (!"Not Sold".equals(gd.getStatus())) {
                // Display firearm information
                String display = "Firearm Serial Number: " + gd.getFirearmSerielnum() + "\n" +
                        "Firearm Type: " + gd.getFirearmType() + "\n" +
                        "Firearm Model: " + gd.getFirearmModel() + "\n" +
                        "Firearm Name: " + gd.getFirearmName() + "\n" +
                        "Date Manufactured: " + gd.getDateManufactured() + "\n" +
                        "Manufacturer Name: " + gd.getManufacName() + "\n" +
                        "Manufacturer Address: " + gd.getManufacAddress() + "\n" +
                        "Manufacturer Email: " + gd.getManufacEmail() + "\n" +
                        "Manufacturer Licence: " + gd.getManufacLicence() + "\n";
                infotext.appendText(display);
            }
            infotext.appendText("\n");
        }
    }
    
    /**
     * Method to view all unsold firearms.
     */
    public void viewnotsoldfirearms() {
        infotext.clear();
        // Retrieve all transactions from the data structure
        List<Transaction<GunData>> alltransactions = Main.bn.getlist();
        // Iterate through each transaction and display unsold firearms
        for (Transaction<GunData> t : alltransactions) {
            GunData gd = t.getData();
            if ("Not Sold".equals(gd.getStatus()) && gd.getPurchaseDate() == null) {
                // Display firearm information
                String display = "Firearm Serial Number: " + gd.getFirearmSerielnum() + "\n" +
                        "Firearm Type: " + gd.getFirearmType() + "\n" +
                        "Firearm Model: " + gd.getFirearmModel() + "\n" +
                        "Firearm Name: " + gd.getFirearmName() + "\n" +
                        "Date Manufactured: " + gd.getDateManufactured() + "\n" +
                        "Manufacturer Name: " + gd.getManufacName() + "\n" +
                        "Manufacturer Address: " + gd.getManufacAddress() + "\n" +
                        "Manufacturer Email: " + gd.getManufacEmail() + "\n" +
                        "Manufacturer Licence: " + gd.getManufacLicence() + "\n";
                infotext.appendText(display);
            }
            infotext.appendText("\n");
        }
    }
    
    /**
     * Method to quit and return to the main login interface.
     */
    public void quit() {
        try {
            // Load Mainlog.fxml file to return to the main login interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainlog.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nametxt.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exception appropriately
        }
    }
    
}