package TaskManager.MainWindow.Note;

import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Класс-компонент для формирования текста записи
 */
public class NoteTextFlow extends TextFlow {
    private Text text;
    private Text info;
    private Text date;
    private String close;
    private String noteInfo;

    private final int defaultWidth = 25;

    private  int flowWidth;
    private int priority;
    private boolean isActive;

    public NoteTextFlow(String info, String text, String date, int priority, boolean isActive, int flowWidth){
        this.priority = priority;
        this.isActive = isActive;
        this.flowWidth = flowWidth;

        close = "Запись закрыта" + "\n";
        noteInfo = info + "\n";
        this.text = new Text(text + "\n");
        this.date = new Text(date + "\n");

        // Если запись закрыта - добавляем строку close
        if(isActive) {
            this.getStyleClass().add(NotePriorities.getColorByPriority(priority));
            this.info = new Text(noteInfo);
        } else {
            this.getStyleClass().add(NotePriorities.getClosedNoteColor());
            this.info  = new Text(close + noteInfo);
        }

        setStyleClass();

        getTextFlowBounds(this, this.text);
        setLineSpacing(2.0);

        ObservableList list = this.getChildren();
        list.addAll(this.info, this.text, this.date);
        getStyleClass().add("textFlow");
    }

    /**
     * Устанавливаем размерность в зависимости от количества текста
     * @param textFlow изменяемый компонент
     * @param text основной текст записи
     */
    private void getTextFlowBounds(TextFlow textFlow, Text text){
        text.getStyleClass().add("noteText");
        StackPane pane = new StackPane(text);
        pane.layout();
        double width = text.getLayoutBounds().getWidth();
        textFlow.setPrefWidth(flowWidth - defaultWidth);
        textFlow.setPrefHeight(getTextFlowHeight(width));
    }

    /**
     * Определение высоты компоненты
     * @param width ширина текста
     * @return количество рядов
     */
    private int getTextFlowHeight(double width){
        int rows = (int) (width / flowWidth) + 3;
        int rowLimit = 10;

        if(rows < rowLimit){
            return rows;
        } else {
            return rowLimit;
        }
    }

    /**
     * Устанавливаем CSS для каждого текстового блока
     */
    private void setStyleClass(){
        this.info.getStyleClass().add("info");
        this.text.getStyleClass().add("noteText");
        this.date.getStyleClass().add("date");
    }
}
