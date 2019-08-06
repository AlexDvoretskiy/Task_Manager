package TaskManager.AssistantWindow.Buttons;

import TaskManager.Controllers;
import TaskManager.DataBaseService;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Кнопка "Удалить" для окна-ассистента
 */
public class DeleteButton extends TaskManager.MainWindow.Buttons.DeleteButton {

    public DeleteButton(int number) {
        super(number);
    }

    /**
     * Инициализируем иконку кнопки
     */
    @Override
    protected void initImage() {
        String imgPath = "images/assistDeleteButton.png";
        image = new Image(imgPath);
        imageView = new ImageView(image);
        setGraphic(imageView);
    }

    /**
     * Действие при нажатии - удаляем выбранную запись
     * обновляем окно
     */
    @Override
    protected void delete() {
        DataBaseService.deleteNote(noteNumber);
        Controllers.getAssistantController().update();
    }
}
