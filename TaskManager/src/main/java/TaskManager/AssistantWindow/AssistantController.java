package TaskManager.AssistantWindow;

import TaskManager.Controllers;
import TaskManager.DataBaseService;
import TaskManager.MainWindow.Note.NotePriorities;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс - контроллер для окна-ассистента
 */
public class AssistantController {

    @FXML
    Button closeAssist;

    @FXML
    VBox AssistWindow;

    @FXML
    ScrollPane scrollPane;

    /**
     * Инициализация объектов окна-ассистента
     */
    @FXML
    public void initialize(){
        Controllers.setAssistantController(this);
        AssistWindow.setSpacing(5);

        ImageView closeBut = new ImageView(new Image("images/minimizeButtonImg.png"));
        closeAssist.setGraphic(closeBut);

        update();
    }

    /**
     * Получение активных записей из БД и
     * добавление их в окно-ассистент
     */
    public void update(){
        AssistWindow.getChildren().clear();

        ResultSet resultSet = DataBaseService.getNotes();

        try {
            while (resultSet.next()){
                int noteNumber = Integer.parseInt(resultSet.getString(1));
                String info = "Приоритет: " + NotePriorities.getPriorityName(Integer.parseInt(resultSet.getString(3)));
                String text = resultSet.getString(2);
                String date = "Отчетная дата: " + resultSet.getString(5);
                int priority = Integer.parseInt(resultSet.getString(3));
                boolean isActive = Integer.parseInt(resultSet.getString(6)) == 0;

                //Доавляем записи
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        AssistantNote note = new AssistantNote(noteNumber, info, text, date, priority, isActive);

                        if(isActive) {
                            AssistWindow.getChildren().add(note);
                        }
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * При закрытии окна - закрываем само окно-ассистент,
     * обновляем основное окно в случае внесения изменений,
     * вызываем основное окно.
     */
    public void closeAssistantWindow(){
        Controllers.getMainWindowController().updateMainWindow();
        Controllers.getMainWindowController().showMainWindow();
        AssistantWindow.closeStage();
    }
}
