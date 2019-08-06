package TaskManager.MainWindow.Buttons;

import TaskManager.Controllers;
import TaskManager.EditWindow.EditWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Кнопка "Редактирвоать" для оновного окна
 */
public class EditButton extends Button {

    public EditButton(int noteNumber){
        Image image = new Image("images/editNoteButtonImg.png");
        ImageView imageView = new ImageView(image);
        setGraphic(imageView);
        setWidth(image.getWidth());
        setTooltip(new Tooltip("Редактировать"));
        getStyleClass().add("editButton");

        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage stage = new Stage();
                    new EditWindow(stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Controllers.getEditWindowController().setNoteNumber(noteNumber);
                Controllers.getEditWindowController().initEditWindow();
            }
        });
    }
}
