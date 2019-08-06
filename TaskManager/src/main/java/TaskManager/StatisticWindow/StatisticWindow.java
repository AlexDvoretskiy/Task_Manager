package TaskManager.StatisticWindow;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Окно для формирования статистики
 */
public class StatisticWindow {
    private static Stage stage;

    public StatisticWindow(Stage stage) throws IOException {
        String fxmlFile = "/fxml/statisticWindow.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = (Parent) loader.load();
        StatisticWindow.stage = stage;
        stage.setTitle("Окно редактирования");
        Scene scene = new Scene(root, 900, 400);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage(){
        return stage;
    }
}
