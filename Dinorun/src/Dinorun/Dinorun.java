/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

public class Dinorun extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
