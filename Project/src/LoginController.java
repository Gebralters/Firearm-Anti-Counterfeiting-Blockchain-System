



import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class for managing login functionality.
 */
public class LoginController {

    // Injecting FXML elements
    @FXML
    private TextField emailtext;
    @FXML
    private PasswordField passtext;
    @FXML
    private Label lbltext;

    /**
     * Method called when the login button is clicked.
     */
    public void loginButtonAction(ActionEvent e) {
        // Check if email or password fields are empty
        if (emailtext.getText().equals("") || passtext.getText().equals("")) {
            // If empty, clear fields and display error message
            emailtext.setText("");
            passtext.setText("");
            lbltext.setText("!TRY AGAIN");
            lbltext.setStyle("-fx-text-fill: red;");
        } else {
            // If fields are not empty, attempt login
            String email = emailtext.getText();
            String pass = passtext.getText();

            // Set temporary email for future reference
            Main.tempemail = email;

            // Initialize connection if not already done
            if (Main.ch == null) {
                Main.initconnection();
            }

            // Attempt login with provided credentials
            if (Main.ch.Login(email, pass)) {
                // If login successful, navigate to appropriate interface based on user type
                if (Main.ch.typeuser == 1) { // Buyer
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Buyer.fxml"));
                        Parent root = loader.load();
                        BuyerController buyerController = loader.getController();
                        buyerController.initialize();
                        Stage stage = (Stage) emailtext.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace(); // Handle exception appropriately
                    }
                } else if (Main.ch.typeuser == 2) { // Firearm Dealer
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FireArmdealer.fxml"));
                        Parent root = loader.load();
                        DealerController dealerController = loader.getController();
                        dealerController.initialize();
                        Stage stage = (Stage) emailtext.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace(); // Handle exception appropriately
                    }
                } else if (Main.ch.typeuser == 3) { // Authorities
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Authorities.fxml"));
                        Parent root = loader.load();
                        AuthoritiesController authoController = loader.getController();
                        authoController.initialize();
                        Stage stage = (Stage) emailtext.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace(); // Handle exception appropriately
                    }
                }
            } else {
                // If login failed, clear fields and display error message
                emailtext.setText("");
                passtext.setText("");
                lbltext.setText("!TRY AGAIN");
                lbltext.setStyle("-fx-text-fill: red;");
            }
        }
    }

    /**
     * Method called when the register link is clicked.
     * Navigates to the register page.
     */
    public void registerLinkAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root = loader.load();
            RegisterController regController = loader.getController();
            Stage stage = (Stage) emailtext.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle exception appropriately
        }
    }
}