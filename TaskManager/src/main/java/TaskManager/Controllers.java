package TaskManager;

import TaskManager.AssistantWindow.AssistantController;
import TaskManager.EditWindow.EditController;
import TaskManager.MainWindow.MainController;

/**
 * Контроллеры всех действующих окон
 */
public class Controllers {

    private static MainController mainWindowController;
    private static EditController editWindowController;
    private static AssistantController assistantController;


    public static MainController getMainWindowController() {
        return mainWindowController;
    }

    public static void setMainWindowController(MainController controller) {
        Controllers.mainWindowController = controller;
    }

    public static EditController getEditWindowController() {
        return editWindowController;
    }

    public static void setEditWindowController(EditController controller) {
        Controllers.editWindowController = controller;
    }

    public static AssistantController getAssistantController() {
        return assistantController;
    }

    public static void setAssistantController(AssistantController controller) {
        Controllers.assistantController = controller;
    }
}
