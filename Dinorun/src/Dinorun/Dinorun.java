package Dinorun;

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
