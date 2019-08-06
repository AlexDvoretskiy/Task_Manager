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
 * Кнопка "Выполнено" для основного окна
 */
public class DoneButton extends Button {
    protected Image image;
    protected ImageView imageView;
    protected int noteNumber;

    public DoneButton(int noteNumber){
        this.noteNumber = noteNumber;

        initImage();
        setWidth(image.getWidth());
        setTooltip(new Tooltip("Выполнено"));
        setStyleClass();

        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setDone();
            }
        });
    }

    /**
     * Инициализируем иконку кнопки
     */
    protected void initImage(){
        String imgPath = "images/doneButtonImg.png";
        image = new Image(imgPath);
        imageView = new ImageView(image);
        setGraphic(imageView);
    }

    /**
     * Действие при нажатии - переводим запись в статус "Выполнено"
     * обновляем окно
     */
    protected  void setDone(){
        DataBaseService.closeNote(noteNumber);
        Controllers.getMainWindowController().updateMainWindow();
    }

    protected void setStyleClass(){
        getStyleClass().add("doneButton");
    }
}
