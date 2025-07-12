/**
 * 
 */
package acsse.csc03a3.clients;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Controller class for handling scene transitions.
 */
public class SceneController {
    private Stage stage; // The primary stage of the application
    private Scene scene; // The scene to be displayed
    private Parent parent; // The parent node of the scene
    
    /**
     * Method to switch to the login scene.
     * @param event The action event triggering the scene switch
     * @throws IOException If an I/O exception occurs
     */
    public void switchtologin(ActionEvent event) throws IOException {
        // Load the login scene
        Parent root = FXMLLoader.load(getClass().getResource("/Mainlog.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the stage from the event source
        scene = new Scene(root); // Create a new scene with the loaded root
        stage.setScene(scene); // Set the scene to the stage
        stage.show(); // Show the stage
    }
    
    /**
     * Method to switch to the register scene.
     * @param event The action event triggering the scene switch
     * @throws IOException If an I/O exception occurs
     */
    public void switchtoRegister(ActionEvent event) throws IOException {
        // Load the register scene
        Parent root = FXMLLoader.load(getClass().getResource("/Register.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow(); // Get the stage from the event source
        scene = new Scene(root); // Create a new scene with the loaded root
        stage.setScene(scene); // Set the scene to the stage
        stage.show(); // Show the stage
    }
}