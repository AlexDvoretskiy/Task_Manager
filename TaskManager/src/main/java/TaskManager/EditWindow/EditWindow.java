package TaskManager.EditWindow;

import TaskManager.Controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Окно редактирвоания выбранной записи
 */
public class EditWindow {

    public EditWindow(Stage stage) throws IOException {
        String fxmlFile = "/fxml/editWindow.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = (Parent) loader.load();
        Controllers.getEditWindowController().setStage(stage);

        stage.setTitle("Окно редактирования");
        stage.getIcons().add(new Image("/images/main.png"));
        Scene scene = new Scene(root, 700, 300);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(scene);
        stage.show();
    }
}
