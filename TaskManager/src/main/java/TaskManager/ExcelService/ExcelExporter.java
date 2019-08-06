package TaskManager.ExcelService;

import TaskManager.DataBaseService;
import TaskManager.MainWindow.Note.NotePriorities;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс для выгрузки записей в Excel
 */
public class ExcelExporter {
    private static Workbook workbook;
    private static ResultSet resultSet;
    private static Sheet sheet;

    private static final int COLUMN_QUANTITY = 6;

    public static void exportAllRecords() throws IOException {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Notes");
        setAutoSizeAllColumns();

        resultSet = DataBaseService.getNotes();

        // Получаем данные из БД
        try {
            int i = 0;
            while(resultSet.next()){
                Row row = sheet.createRow(i);

                Cell id = row.createCell(0);
                Cell noteText = row.createCell(1);
                Cell priority = row.createCell(2);
                Cell dateStart = row.createCell(3);
                Cell dateEnd = row.createCell(4);
                Cell status = row.createCell(5);

                id.setCellValue(resultSet.getString("id"));
                noteText.setCellValue(resultSet.getString("note_text"));
                priority.setCellValue(NotePriorities.getPriorityName(Integer.parseInt(resultSet.getString("priority"))));
                dateStart.setCellValue(resultSet.getString("date_startactive"));
                dateEnd.setCellValue(resultSet.getString("date_endactive"));
                status.setCellValue(Integer.parseInt(resultSet.getString("status")) == 0 ? "Актуальна" : "Закрыта");

                i++;
            }

            // Формируем исходящий excel файл и записываем в него данные
            workbook.write(new FileOutputStream("Task_Manager.xlsx"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Автоматическое выравнивание колнок
     */
    private static void setAutoSizeAllColumns(){
        for(int i = 1; i <= COLUMN_QUANTITY; i++){
            sheet.autoSizeColumn(i, true);
        }
    }
}
