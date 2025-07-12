/**
 * 
 */
import java.io.IOException;

import acsse.csc03a3.BlockChain.BlockchainNetwork;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Main class responsible for launching the application and initializing essential components.
 */
public class Main extends Application {
    // Instance variables
    public static ClientHandler ch = null; // ClientHandler instance for handling client-server communication
    public static BlockchainNetwork bn = null; // BlockchainNetwork instance for managing blockchain data
    public static String tempemail = null; // Temporary email used for user identification

    /**
     * Main method to start the application.
     */
    public static void main(String[] args) {
        // Initialize the BlockchainNetwork
        bn = new BlockchainNetwork();
        // Set temporary email to empty string
        tempemail = "";
        // Launch JavaFX application
        launch(args);
    }

    /**
     * Start method overridden from Application class to initialize JavaFX UI.
     *
     * @param primaryStage The primary stage of the application
     * @throws Exception Throws an exception if there is an error during initialization
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the main login interface (Mainlog.fxml)
            Parent root = FXMLLoader.load(getClass().getResource("Mainlog.fxml"));
            // Create a new scene with the loaded UI
            Scene scene = new Scene(root);
            // Set the scene to the primary stage
            primaryStage.setScene(scene);
            // Display the primary stage
            primaryStage.show();
        } catch (Exception ex) {
            ex.printStackTrace(); // Print stack trace if an exception occurs during initialization
        }
    }

    /**
     * Method to initialize the client-server connection.
     */
    public static void initconnection() {
        try {
            // Initialize the ClientHandler for client-server communication
            ch = new ClientHandler();
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if an exception occurs during initialization
        }
    }
}