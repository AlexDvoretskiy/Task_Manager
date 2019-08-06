package TaskManager.AssistantWindow;

import TaskManager.AssistantWindow.Buttons.DeleteButton;
import TaskManager.AssistantWindow.Buttons.DoneButton;
import TaskManager.MainWindow.Note.Note;
import javafx.scene.layout.VBox;

/**
 * Класс-контейнер для формирования записи в окне-Ассистенте
 * Содержит текст записи, кнопки редактирования ("Выполнено","Удалить")
 */
public class AssistantNote extends Note {

    public AssistantNote(int noteNumber, String info, String text, String date, int priority, boolean isActive) {
        super(noteNumber, info, text, date, priority, isActive);
    }

    /**
     * Инициализируем кнопки окна
     * В окне-ассистенте присутствуют только кнопки "Выполнено", "Удалить"
     */
    @Override
    public void initButtons() {
        doneButton = new DoneButton(noteNumber);
        deleteButton = new DeleteButton(noteNumber);

        optionButtons = new VBox();
        optionButtons.getChildren().add(doneButton);
        optionButtons.getChildren().add(deleteButton);
    }

    /**
     * Добавляем компоненты: текст, кнопки
     */
    @Override
    public void addComponents() {
        getChildren().add(noteText);
        getChildren().add(optionButtons);
    }

    /**
     * Настраиваем видимость кнопок
     */
    @Override
    public void showButtons() {
        doneButton.setVisible(true);
        doneButton.setManaged(true);
        deleteButton.setVisible(true);
        deleteButton.setManaged(true);
    }

    /**
     * Устанавливаем Scene для корректного расчета ширины контейнера
     */
    @Override
    protected void setCurrentScene() {
        currentScene = AssistantWindow.getStage().getScene();
        scroll_width = 0;
    }

    /**
     * Устанавливаем ширину компонента
     */
    @Override
    protected void setAddTaskAreaWidth() {
        addTaskAreaWidth = 0;
    }
}
