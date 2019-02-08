package pingPong;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class pingPongStarter extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/ping-pong.fxml"));
        primaryStage.setTitle("Ping-Pong");
        primaryStage.setScene(new Scene(root, 1100, 600));
        primaryStage.show();

        root.requestFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
