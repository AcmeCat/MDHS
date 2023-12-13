package client;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MDHS extends Application{
    
    //static variables
    private static MDHSClient client;
    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));
        scene = new Scene(parent, 600, 400);
        primaryStage.setTitle("Maleny Diary To Home System (MDHS)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //main to launch
    public static void main(String[] args) {
        client = new MDHSClient();
        launch(args);
    }

    public static MDHSClient getClient() {
        return client;
    }
    
    static void loadScene(String fxml){
        Parent root;
        try {
            root = FXMLLoader.load(MDHS.class.getResource(fxml + ".fxml"));
            scene.setRoot(root);
        } catch (IOException e) {
            System.out.println("cannot load scene: IOException - " + e.getLocalizedMessage());
        }
    }
}
