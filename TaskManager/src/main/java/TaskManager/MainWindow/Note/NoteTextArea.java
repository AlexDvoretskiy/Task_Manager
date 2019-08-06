package TaskManager.MainWindow.Note;

import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Класс-компонент для формирования записи
 */
public class NoteTextArea extends TextArea {

    private String text;
    private String info;
    private String date;
    private int maxWidth = 700;
    private int defaultWidth = 25;
    private double fontSize = 18;
    private int priority;
    private boolean isActive;

    public NoteTextArea(String info, String text, String date, int priority, boolean isActive){
        this.text = text;
        this.date = date;
        this.info = info;
        this.priority = priority;
        this.isActive = isActive;

        setWrapText(true);
        setEditable(false);
        getTextAreaBounds(this, text);
    }

    /**
     * Устанавливаем границы компонента
     * @param textArea для установки границ
     * @param message текст для передачи в компоненту
     */
    private void getTextAreaBounds(TextArea textArea, String message){
        Text text = new Text(message);
        text.setFont(new Font(fontSize));
        StackPane pane = new StackPane(text);
        pane.layout();
        double width = text.getLayoutBounds().getWidth();
        textArea.setPrefWidth(maxWidth - defaultWidth);
        textArea.setMaxWidth(maxWidth);
        textArea.setPrefRowCount(getTextAreaHeight(width));
        textArea.setText(message);
    }

    /**
     * Определение высоты компоненты
     * @param width ширина текста
     * @return количество рядов
     */
    private int getTextAreaHeight(double width){
        int rows = (int) (width / maxWidth) + 3;
        int rowLimit = 10;

        if(rows < rowLimit){
            return rows;
        } else {
            return rowLimit;
        }
    }
}
