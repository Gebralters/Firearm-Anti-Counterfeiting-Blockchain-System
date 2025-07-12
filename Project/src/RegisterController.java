import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller class responsible for handling user registration.
 */
public class RegisterController {

    // JavaFX injected fields
    @FXML
    private TextField lnametext; // TextField for entering last name
    @FXML
    private TextField fnametext; // TextField for entering first name
    @FXML
    private TextField snametext; // TextField for entering surname
    @FXML
    private TextField emtext; // TextField for entering email
    @FXML
    private TextField pnumtext; // TextField for entering phone number
    @FXML
    private TextField idnumtext; // TextField for entering ID number
    @FXML
    private PasswordField conpasstext; // PasswordField for entering password confirmation
    @FXML
    private PasswordField passtext; // PasswordField for entering password
    @FXML
    private Label regtext; // Label for displaying registration status

    /**
     * Method to handle registration button action.
     */
    public void registerButtonAction() {
        // Check if password and password confirmation match
        if (conpasstext.getText().equals(passtext.getText())) {
            // Check if the client handler is null and initialize connection if needed
            if (Main.ch == null) {
                Main.initconnection();
            }

            // Register user using client handler
            if (Main.ch.register(lnametext.getText(), fnametext.getText(), snametext.getText(), emtext.getText(), pnumtext.getText(), idnumtext.getText(), passtext.getText())) {
                // If registration is successful, load the login page
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainlog.fxml"));
                    Parent root = loader.load();
                    LoginController logController = loader.getController();
                    Stage stage = (Stage) emtext.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace(); // Print stack trace if an exception occurs during the operation
                }
            } else {
                // If registration fails, display a failure message
                conpasstext.setText("");
                passtext.setText("");
                regtext.setText("!FAILED");
                regtext.setStyle("-fx-text-fill: red;");
            }
        } else {
            // If password and password confirmation do not match, clear the fields
            conpasstext.setText("");
            passtext.setText("");
        }
    }

    /**
     * Method to handle back to login link action.
     */
    public void backToLoginLinkAction() {
        // This method can be implemented to handle navigating back to the login page if needed
    }
}