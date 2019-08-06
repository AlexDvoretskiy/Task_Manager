package TaskManager.AssistantWindow.Buttons;

import TaskManager.Controllers;
import TaskManager.DataBaseService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Кнопка "Выполнено" для окна-ассистента
 */
public class DoneButton extends TaskManager.MainWindow.Buttons.DoneButton {

    public DoneButton(int noteNumber) {
        super(noteNumber);
    }

    /**
     * Инициализируем иконку кнопки
     */
    @Override
    protected void initImage() {
        String imgPath = "images/assistDoneButton.png";
        image = new Image(imgPath);
        imageView = new ImageView(image);
        setGraphic(imageView);
    }

    /**
     * Действие при нажатии - переводим запись в статус "Выполнено"
     * обновляем окно
     */
    @Override
    protected void setDone() {
        DataBaseService.closeNote(noteNumber);
        Controllers.getAssistantController().update();
    }
}
