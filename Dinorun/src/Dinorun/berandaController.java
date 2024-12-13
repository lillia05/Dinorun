package Dinorun;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class berandaController implements Initializable {
    @FXML
    private Button button;
    private Parent root;
    private Stage stage;
    private Scene scene;
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        button.setOnMouseEntered(event -> button.setStyle(
            "-fx-background-color: #65962c; " +  
            "-fx-text-fill: white; " + 
            "-fx-font-size: 20px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10 20 10 20; " +
            "-fx-background-radius: 100;"
        ));

        button.setOnMouseExited(event -> button.setStyle(
            "-fx-background-color: #80cc28; " +  
            "-fx-text-fill: #9b6736; " +
            "-fx-font-size: 20px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10 20 10 20; " +
            "-fx-background-radius: 100;"
        ));
    }    
    
     @FXML
    void start(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("mainGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Dinorun");
        stage.setScene(scene);
        stage.show();
    }

    
}
