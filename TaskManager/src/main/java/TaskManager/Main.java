package TaskManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Основное окно
 */
public class Main extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        String fxmlFile = "/fxml/mainWindow.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        primaryStage.setTitle("TaskManager");
        Scene scene = new Scene(root, 1050, 600);
        primaryStage.getIcons().add(new Image("/images/main.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }
}

