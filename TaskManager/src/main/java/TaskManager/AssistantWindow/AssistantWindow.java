package TaskManager.AssistantWindow;

import TaskManager.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.IOException;

/**
 * Окно-ассистент
 */
public class AssistantWindow {
    private static Stage stage;

    public AssistantWindow(Stage stage) throws IOException {
        String fxmlFile = "/fxml/assistantWindow.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = (Parent) loader.load();

        AssistantWindow.stage = stage;
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Ассистент");
        stage.getIcons().add(new Image("/images/main.png"));

        Scene scene = new Scene(root, 350, 600);
        stage.setScene(scene);
        stage.setX(getX());
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Main.getPrimaryStage().close();
            }
        });
    }

    /**
     * Определяем координату появления окна в зависимости от разрешения экрана
     * @return Х-координату появлениея окна
     */
    public static double getX(){
        return Toolkit.getDefaultToolkit().getScreenSize().getWidth() - getStage().getScene().getWidth() - 5;
    }

    public static void closeStage(){
        getStage().close();
    }

    public static Stage getStage(){
        return stage;
    }
}
