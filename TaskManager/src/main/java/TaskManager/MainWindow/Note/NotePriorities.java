package TaskManager.MainWindow.Note;

import java.util.HashMap;

/**
 * Класс настроек приоритетов:
 * стиль оформления
 * номер приоритета
 * наименование приоритета
 */
public class NotePriorities {
    private static HashMap<Integer, String> noteColors;
    private static HashMap<Integer, String> noteNames;
    private final static String veryHighPriorityColor = "veryHighPriority";  //"#dbb1b1, #fff0f0";
    private final static String highPriorityColor = "highPriority";  //"#ffce2e, #ffeaa3";
    private final static String mediumPriorityColor = "mediumPriority";  // "#3895ff, #c7e1ff";
    private final static String lowPriorityColor = "lowPriority";  //"#47fc56, #c7ffcc";
    private final static String closedNoteColor = "closedNoteColor";  //"#828282, #e3e3e3";
    private final static String veryHighPriorityName = "очень высокий";
    private final static String highPriorityName = "высокий";
    private final static String mediumPriorityName = "средний";
    private final static String lowPriorityName = "низкий";
    private final static Integer veryHighPriority = 4;
    private final static Integer highPriority = 3;
    private final static Integer mediumPriority = 2;
    private final static Integer lowPriority = 1;

    public NotePriorities(){
        noteColors = new HashMap<>();
        noteColors.put(veryHighPriority, veryHighPriorityColor);
        noteColors.put(highPriority, highPriorityColor);
        noteColors.put(mediumPriority, mediumPriorityColor);
        noteColors.put(lowPriority, lowPriorityColor);

        noteNames = new HashMap<>();
        noteNames.put(veryHighPriority, veryHighPriorityName);
        noteNames.put(highPriority, highPriorityName);
        noteNames.put(mediumPriority, mediumPriorityName);
        noteNames.put(lowPriority, lowPriorityName);
    }

    public static String getColorByPriority(int priority){
        NotePriorities priorities = new NotePriorities();
        return noteColors.get(priority);
    }

    public static String getPriorityName(int priority){
        NotePriorities priorities = new NotePriorities();
        return noteNames.get(priority);
    }

    public static String getClosedNoteColor() {
        return closedNoteColor;
    }

    public static Integer getVeryHighPriority() {
        return veryHighPriority;
    }

    public static Integer getHighPriority() {
        return highPriority;
    }

    public static Integer getMediumPriority() {
        return mediumPriority;
    }

    public static Integer getLowPriority() {
        return lowPriority;
    }
}
