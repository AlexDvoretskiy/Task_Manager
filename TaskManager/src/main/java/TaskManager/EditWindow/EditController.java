package TaskManager.EditWindow;

import TaskManager.Controllers;
import TaskManager.DataBaseService;
import TaskManager.MainWindow.Note.NotePriorities;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс-контроллер для окна редактирования записи
 */

public class EditController {

    @FXML
    RadioButton VeryHighPriority;

    @FXML
    RadioButton HighPriority;

    @FXML
    RadioButton MediumPriority;

    @FXML
    RadioButton LowPriority;

    @FXML
    TextArea editTextArea;

    @FXML
    DatePicker startDate;

    @FXML
    DatePicker endDate;

    @FXML
    Button applyButton;

    @FXML
    Button cancelButton;

    private int noteNumber = 0;
    private Stage stage;

    /**
     * Инициализация графических объектов окна редактирования
     */
    @FXML
    public void initialize(){
        Controllers.setEditWindowController(this);

        ToggleGroup group = new ToggleGroup();
        VeryHighPriority.setToggleGroup(group);
        HighPriority.setToggleGroup(group);
        MediumPriority.setToggleGroup(group);
        LowPriority.setToggleGroup(group);

        ImageView applyeBut= new ImageView(new Image("images/applyButtonImg.png"));
        applyButton.setGraphic(applyeBut);

        ImageView cancelBut= new ImageView(new Image("images/cancelButtonImg.png"));
        cancelButton.setGraphic(cancelBut);
    }

    /**
     * Инициализация данных выбранной записи в окно редактирование
     * и заполнение ключевых полей
     */
    public void initEditWindow(){
        String noteText = "";
        int priority = 0;
        String startDate = "";
        String endDate = "";

        ResultSet resultSet = DataBaseService.getNote(noteNumber);

        try {
            while(resultSet.next()){
                noteText = resultSet.getString(2);
                startDate = resultSet.getString(4);
                endDate = resultSet.getString(5);
                priority = Integer.parseInt(resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        editTextArea.setText(noteText);
        this.startDate.getEditor().setText(startDate);
        this.endDate.getEditor().setText(endDate);
        initPriorityButton(priority);
    }

    /**
     * Инициализируем выбор RadioButton в соответствии приоритету записи
     * @param priority - приоритет записи
     */
    public void initPriorityButton(int priority){
        if(NotePriorities.getVeryHighPriority() == priority) VeryHighPriority.setSelected(true);
        if(NotePriorities.getHighPriority() == priority) HighPriority.setSelected(true);
        if(NotePriorities.getMediumPriority() == priority) MediumPriority.setSelected(true);
        if(NotePriorities.getLowPriority() == priority) LowPriority.setSelected(true);
    }

    public void applyNoteChanges(){
        if(!editTextArea.getText().isEmpty() || !startDate.getEditor().getText().isEmpty()) {
            try {
                DataBaseService.updateNote(editTextArea.getText(), getNotePriority(), startDate.getEditor().getText(), endDate.getEditor().getText(), noteNumber);
                Controllers.getMainWindowController().updateMainWindow();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Не удалось обновить запись");
            }
            stage.close();
        } else {
            JOptionPane.showMessageDialog(null, "Заполните описание задачи и дату заведения");
        }
    }

    /**
     * При отмене изменений закрываем окно редактирования
     */
    public void cancelChanges(){
        stage.close();
    }

    /**
     * Получение нового параметра группы RadioButton
     * @return - возвращает номер приоритета
     */
    private int getNotePriority(){
        int defaultPriority = 2;
        if(VeryHighPriority.isSelected()) return NotePriorities.getVeryHighPriority();
        if(HighPriority.isSelected()) return NotePriorities.getHighPriority();
        if(MediumPriority.isSelected()) return NotePriorities.getMediumPriority();
        if(LowPriority.isSelected()) return NotePriorities.getLowPriority();
        return defaultPriority;
    }

    public void setNoteNumber(int noteNumber) {
        this.noteNumber = noteNumber;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public int getNoteNumber() {
        return noteNumber;
    }
}
