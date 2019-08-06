package TaskManager.MainWindow.Buttons;

import TaskManager.DataBaseService;
import TaskManager.Controllers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Кнопка "Удалить" для основного окна
 */
public class DeleteButton extends Button {
    protected Image image;
    protected ImageView imageView;
    protected int noteNumber;

    public DeleteButton(int number){
        this.noteNumber = number;

        initImage();
        setWidth(image.getWidth());
        setTooltip(new Tooltip("Удалить"));
        setStyleClass();

        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                delete();
            }
        });
    }

    /**
     * Инициализируем иконку кнопки
     */
    protected void initImage(){
        String imgPath = "images/deleteButtonImg.png";
        image = new Image(imgPath);
        imageView = new ImageView(image);
        setGraphic(imageView);
    }

    /**
     * Действие при нажатии - удаляем выбранную запись
     * обновляем окно
     */
    protected  void delete(){
        DataBaseService.deleteNote(noteNumber);
        Controllers.getMainWindowController().updateMainWindow();
    }

    protected void setStyleClass(){
        getStyleClass().add("deleteButton");
    }
}
