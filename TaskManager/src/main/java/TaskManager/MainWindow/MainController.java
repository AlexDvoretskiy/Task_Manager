package TaskManager.MainWindow;

import TaskManager.AssistantWindow.AssistantWindow;
import TaskManager.DataBaseService;
import TaskManager.Controllers;
import TaskManager.ExcelService.ExcelExporter;
import TaskManager.Main;
import TaskManager.MainWindow.Note.Note;
import TaskManager.MainWindow.Note.NotePriorities;
import TaskManager.StatisticWindow.StatisticWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Класс-контроллер для основного окна
 */
public class MainController {

    @FXML
    TextArea taskNameArea;

    @FXML
    RadioButton VeryHighPriority;

    @FXML
    RadioButton HighPriority;

    @FXML
    RadioButton MediumPriority;

    @FXML
    RadioButton LowPriority;

    @FXML
    Button AddTaskButton;

    @FXML
    Button updateButton;

    @FXML
    Button deleteAllButton;

    @FXML
    Button statisticButton;

    @FXML
    Button closeAllButton;

    @FXML
    Button editButton;

    @FXML
    Button assistantWindow;

    @FXML
    Button exportButton;

    @FXML
    DatePicker StartDate;

    @FXML
    DatePicker EndDate;

    @FXML
    VBox MainWindow;

    @FXML
    VBox AddTaskArea;

    @FXML
    CheckBox showClosedNotes;

    private Stage primaryStage;
    private int defaultPriority = 2;
    private HashMap<Integer, Note> notes;
    private boolean editMode = false;
    private Date startDate;

    /**
     * Инициализируем объекты основного окна
     */
    @FXML
    private void initialize(){
        Controllers.setMainWindowController(this);
        primaryStage = Main.getPrimaryStage();

        ToggleGroup group = new ToggleGroup();
        VeryHighPriority.setToggleGroup(group);
        HighPriority.setToggleGroup(group);
        MediumPriority.setToggleGroup(group);
        LowPriority.setToggleGroup(group);

        MainWindow.setSpacing(10);
        notes = new HashMap<>();

        ImageView addButton = new ImageView(new Image("images/addButtonImg.png"));
        AddTaskButton.setGraphic(addButton);
        AddTaskButton.getStyleClass().add("addButton");

        ImageView updateBut= new ImageView(new Image("images/updateButtonImg.png"));
        updateButton.setGraphic(updateBut);

        ImageView deleteAll= new ImageView(new Image("images/deleteAllButtonImg.png"));
        deleteAllButton.setGraphic(deleteAll);

        ImageView closeAll= new ImageView(new Image("images/closeAllButtonImg.png"));
        closeAllButton.setGraphic(closeAll);

//        ImageView clearBut= new ImageView(new Image("images/clearButtonImg.png"));
//        clearButton.setGraphic(clearBut);

        ImageView statistics= new ImageView(new Image("images/statisticsButtonImg.png"));
        statisticButton.setGraphic(statistics);

        ImageView editBut= new ImageView(new Image("images/editButtonImg.png"));
        editButton.setGraphic(editBut);

        ImageView export= new ImageView(new Image("images/exportButtonImg.png"));
        exportButton.setGraphic(export);

        ImageView minimizeBut= new ImageView(new Image("images/minimizeButtonImg.png"));
        assistantWindow.setGraphic(minimizeBut);

        connectDB();
        updateMainWindow();
        updateStartDate();
    }

    /**
     * Добавление записи
     * действие при нажатии на кнопку "Добавить"
     */
    public void addNote(){
        MainWindow.getChildren().clear();

        int priority = getNotePriority();
        String startDate = StartDate.getEditor().getText();
        String endDate = EndDate.getEditor().getText();

        if (!taskNameArea.getText().isEmpty()) {
            DataBaseService.addNote(taskNameArea.getText(), priority, startDate, endDate);
        } else {
            JOptionPane.showMessageDialog(null, "Заполните описание задачи");
        }

        clearNoteAddFields();
        updateMainWindow();
        updateStartDate();
        MainWindow.setSpacing(10);
    }

    /**
     * Обновление основного окна
     */
    public void updateMainWindow(){
        MainWindow.getChildren().clear();

        ResultSet resultSet = DataBaseService.getNotes();

        // Получаем данные из БД
        try {
            while (resultSet.next()){
                int noteNumber = Integer.parseInt(resultSet.getString(1));
                String info = "Номер записи: " + resultSet.getString(1) +
                              "   Приоритет: " + NotePriorities.getPriorityName(Integer.parseInt(resultSet.getString(3)));
                String text = resultSet.getString(2);
                String date = "Отчетная дата: " + resultSet.getString(5);
                int priority = Integer.parseInt(resultSet.getString(3));
                boolean isActive = Integer.parseInt(resultSet.getString(6)) == 0;

                // Добавляем запись в окно
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Note note = new Note(noteNumber, info, text, date, priority, isActive);

                        if(showClosedNotes.isSelected()) {
                            if(isActive) {
                                MainWindow.getChildren().add(note);
                                addNoteToMap(note);
                            }
                        } else {
                            MainWindow.getChildren().add(note);
                        }
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateStartDate();
    }

    /**
     * Закрываем все записи
     * действие при нажатии на кнопку "Закрыть все записи"
     */
    public void closeAllNotes(){
        int confirmResult = JOptionPane.showConfirmDialog(null, "Все записи будут закрыты");

        if(confirmResult == 0) {
            DataBaseService.closeAllNotes();
        }
        clearNoteAddFields();
        updateMainWindow();
        updateStartDate();
    }

    /**
     * Удаляем все записи
     * действие при нажатии на кнопку "Удалить все записи"
     */
    public void deleteAllNotes(){
        int confirmResult = JOptionPane.showConfirmDialog(null, "Все записи будут удалены");

        if(confirmResult == 0) {
            DataBaseService.deleteAllNotes();
        }
        clearNoteAddFields();
        updateMainWindow();
        updateStartDate();
    }

    /**
     * Добавление новых записей в Map
     * @param note
     */
    public void addNoteToMap(Note note){
        if(!notes.containsKey(note.getNoteNumber())){
            notes.put(note.getNoteNumber(), note);
        }
    }

    /**
     * Переключениие в режим редактирования
     * действие при нажатии кнопки "Редактировать"
     */
    public void editNotes(){
        if(editMode){
            editMode = false;
        } else {
            editMode = true;
        }
        updateMainWindow();
    }

    /**
     * Инициализация окна-ассистента
     */
    public void initAssistantWindow(){
        Stage stage = new Stage();

        try {
            new AssistantWindow(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hideMainWindow();
    }

    /**
     * Инициализация окна статистики
     * действие при нажатии на кнопку "Статистика"
     */
    public void getStatistics(){
        Stage stage = new Stage();

        try {
            new StatisticWindow(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Экспорт записей в эксель
     * действие при нажатии кнопки "Выгрузить"
     */
    public void exportToExcel(){
        try {
            ExcelExporter.exportAllRecords();
            JOptionPane.showMessageDialog(null, "Файл успешно выгружен");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ошибка при выгрузке в excel");
            e.printStackTrace();
        }
    }

    /**
     * Установка подключения к БД
     */
    public void connectDB(){
        try {
            DataBaseService.connect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ошибка подключения к базе данных");
            e.printStackTrace();
        }
    }

    /**
     * Определение приоритета записи
     * @return номер приоритета
     */
    private int getNotePriority(){
        if(VeryHighPriority.isSelected()) return NotePriorities.getVeryHighPriority();
        if(HighPriority.isSelected()) return NotePriorities.getHighPriority();
        if(MediumPriority.isSelected()) return NotePriorities.getMediumPriority();
        if(LowPriority.isSelected()) return NotePriorities.getLowPriority();
        return defaultPriority;
    }

    /**
     * Очищаем поля добавления записи
     */
    private void clearNoteAddFields(){
        taskNameArea.clear();
        StartDate.getEditor().clear();
        EndDate.getEditor().clear();
    }

    /**
     * Проставляем актуальную дату формирования записи
     */
    private void updateStartDate(){
        startDate = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        StartDate.getEditor().setText(formatForDateNow.format(startDate));
    }

    /**
     * Очищаем окно с записями
     */
    public void clearMainWindow(){
        MainWindow.getChildren().clear();
    }

    /**
     * Опеределяем включен ли режим редактирования
     * @return boolean
     */
    public boolean isEditMode() {
        return editMode;
    }

    /**
     * Прячем основное окно
     * Используется при инициализации окна-ассистента
     */
    public void hideMainWindow(){
        primaryStage.setOpacity(0);
    }

    /**
     * Возвращаем основное окно
     * Используется при закрытии окна-ассистента
     */
    public void showMainWindow(){
        primaryStage.setOpacity(1);
    }

    public VBox getAddTaskArea() {
        return AddTaskArea;
    }
}
