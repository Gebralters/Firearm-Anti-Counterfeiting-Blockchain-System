import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import acsse.csc03a3.Transaction;
import acsse.csc03a3.BlockChain.GunData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BuyerController {
 
    // FXML annotations for JavaFX components
    @FXML
    private ImageView imgtext;
    @FXML
    private Label nametext;
    @FXML
    private Button applybtn;
    @FXML
    private TextArea firetextarea;
    @FXML
    private TextArea detailtextarea;
    @FXML
    private Button trackbtn;
    @FXML
    private Button detailbtn;

    // String to hold output text
    public String outputText;

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
        imgtext.setImage(img);

        // Setting the full name
        String name = fname + " " + lname;
        nametext.setText(name);

        // Constructing the output text
        outputText = "First Name: " + fname + "\n" +
                     "Last Name:  " + lname + "\n" +
                     "Surname:    " + surname + "\n" +
                     "Email:      " + email + "\n" +
                     "Type:       " + type + "\n" +
                     "Image:      " + image + "\n" +
                     "ID Number:  " + idnum + "\n" +
                     "Phone Number:" + phonenum + "\n";

        // Clearing the firetextarea
        firetextarea.clear();

        // Retrieving all transactions and displaying relevant firearm details
        List<Transaction<GunData>> alltransactions = Main.bn.getlist();
        for (Transaction<GunData> t : alltransactions) {
            GunData gd = t.getData();
            if (!"Not Sold".equals(gd.getStatus()) && gd.getBuyerEmail().equals(email)) {
                firetextarea.appendText("FIREARM DETAILS:\n");
                String fdetails = "FireArm Name:" + gd.getFirearmName() + "\n" +
                                  "FireArm Type:" + gd.getFirearmType() + "\n" +
                                  "FireArm Seriel Number:" + gd.getFirearmSerielnum() + "\n" +
                                  "FireArm Purchase Date::" + gd.getPurchaseDate() + "\n" +
                                  "FireArm Manufacturer Name:" + gd.getManufacName() + "\n" +
                                  "FireArm Manufacturer License:" + gd.getManufacLicence() + "\n";
                firetextarea.appendText(fdetails);
            }
        }
    }

    // Method to navigate to tracking page
    public void track() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientTracking.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) applybtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exception appropriately
        }
    }

    // Method to navigate to apply page
    public void apply() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BuyerInfo.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) applybtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exception appropriately
        }
    }

    // Method to view personal details
    public void viewdata() {
        detailtextarea.appendText("PERSONAL DETAILS:\n");
        detailtextarea.appendText(outputText);
    }

    // Method to quit and return to the main login page
    public void quit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainlog.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) applybtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exception appropriately
        }
    }
}
