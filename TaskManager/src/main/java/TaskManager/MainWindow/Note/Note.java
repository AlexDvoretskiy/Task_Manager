package TaskManager.MainWindow.Note;

import TaskManager.DataBaseService;
import TaskManager.Main;
import TaskManager.MainWindow.Buttons.DeleteButton;
import TaskManager.MainWindow.Buttons.DoneButton;
import TaskManager.MainWindow.Buttons.EditButton;
import TaskManager.Controllers;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Класс-контейнер для формирования записи
 * Содержит текст записи, кнопки "Редактировать", "Удалить", "Выполнено"
 */
public class Note extends HBox {
    protected NoteTextFlow noteText;
    protected Scene currentScene;

    protected DoneButton doneButton;
    protected DeleteButton deleteButton;
    protected EditButton editButton;

    protected VBox optionButtons;
    protected VBox editButtons;

    protected boolean isEditEnable;
    protected int noteNumber;
    protected double addTaskAreaWidth = 0;

    protected double scroll_width = 20;

    public Note(int noteNumber, String info, String text, String date, int priority, boolean isActive){
        this.noteNumber = noteNumber;
        isEditEnable = Controllers.getMainWindowController().isEditMode();

        setCurrentScene();
        setAddTaskAreaWidth();
        initButtons();

        noteText = new NoteTextFlow(info, text, date, priority, isActive, calcNoteFlowWidth());
        noteText.setPadding(new Insets(0,10,0,10));
        addComponents();
    }

    /**
     * Инициализируем кнопки "Редактировать", "Удалить", "Выполнено"
     */
    public void initButtons(){
        doneButton = new DoneButton(noteNumber);
        deleteButton = new DeleteButton(noteNumber);
        editButton = new EditButton(noteNumber);

        showButtons();

        optionButtons = new VBox();
        optionButtons.getChildren().add(doneButton);
        optionButtons.getChildren().add(deleteButton);

        editButtons = new VBox();
        editButtons.setPadding(new Insets(25,0,0,0));
        editButtons.getChildren().add(editButton);
    }

    /**
     * Настраиваем отображение кнопок для режима редактирования
     * и для неактивных записей
     */
    public void showButtons(){
        if(isEditEnable){
            if(DataBaseService.isNoteActive(noteNumber)) {
                editButton.setVisible(true);
                editButton.setManaged(true);
                doneButton.setVisible(false);
                doneButton.setManaged(false);
                deleteButton.setVisible(false);
                deleteButton.setManaged(false);
            } else {
                editButton.setVisible(false);
                editButton.setManaged(true);
                doneButton.setVisible(false);
                doneButton.setManaged(false);
                deleteButton.setVisible(false);
                deleteButton.setManaged(false);
            }
        } else {
            if (DataBaseService.isNoteActive(noteNumber)) {
                editButton.setVisible(false);
                editButton.setManaged(false);
                doneButton.setVisible(true);
                doneButton.setManaged(true);
                deleteButton.setVisible(true);
                deleteButton.setManaged(true);
            } else {
                editButton.setVisible(false);
                editButton.setManaged(false);
                doneButton.setVisible(false);
                doneButton.setManaged(true);
                deleteButton.setVisible(true);
                deleteButton.setManaged(true);
            }
        }
    }

    /**
     * Добавляем компоненты для отображения
     */
    public void addComponents(){
        getChildren().add(editButtons);
        getChildren().add(noteText);
        getChildren().add(optionButtons);
    }

    /**
     * Рассчитываем ширину контейнера в зависимости от ширины окна
     * @return ширину контейнера записи
     */
    protected int calcNoteFlowWidth(){
        double mainWindowWidth = currentScene.getWidth();

        return (int)(mainWindowWidth - addTaskAreaWidth - scroll_width - doneButton.getWidth());
    }

    /**
     * Устанавливаем Scene для корректного расчета ширины контейнера
     */
    protected void setCurrentScene(){
        currentScene = Main.getPrimaryStage().getScene();
    }

    protected void setAddTaskAreaWidth(){
        addTaskAreaWidth = Controllers.getMainWindowController().getAddTaskArea().getWidth();
    }


    public boolean isEditEnable() {
        return isEditEnable;
    }

    public int getNoteNumber() {
        return noteNumber;
    }
}
