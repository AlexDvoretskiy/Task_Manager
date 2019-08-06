package TaskManager;

import java.sql.*;

public class DataBaseService {
    private static Statement statement;
    private static Connection connection;
    private static int noteCount;
    private static boolean isActive = false;
    private static final int STATUS_CLOSED = 1;
    private static final int STATUS_OPEN = 0;

    /**
     * Устанавливаем соединение с БД
     * @throws SQLException
     */
    public static void connect() throws SQLException{
        if(!isActive) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:Task_Manager.db");
                statement = connection.createStatement();
                isActive = true;
                initTable();
                initNoteCount();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Разрываем соединение с БД
     * @throws SQLException
     */
    public static void disconnect() throws SQLException {
        connection.close();
        isActive = false;
    }

    /**
     * Инициализируем таблицу notes если она не создана
     * @throws SQLException
     */
    public static void initTable() throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS notes (\n" +
                "    ID               INTEGER UNIQUE\n" +
                "                             PRIMARY KEY AUTOINCREMENT,\n" +
                "    NOTE_TEXT        TEXT    NOT NULL,\n" +
                "    PRIORITY         INTEGER NOT NULL,\n" +
                "    DATE_STARTACTIVE TEXT,\n" +
                "    DATE_ENDACTIVE   TEXT,\n" +
                "    STATUS           INTEGER NOT NULL\n" +
                ");";

        statement.execute(sql);
    }

    /**
     * Добавляем запись в БД
     * @param text текст записи
     * @param priority приоритет записи
     * @param startDate дата начала действия
     * @param endDate дата окончания действия
     */
    public static void addNote(String text, int priority, String startDate, String endDate){
        noteCount++;
        String sql = String.format("Insert into notes values ('%d', '%s', '%d', '%s', '%s', '%d' ) ", noteCount, text, priority, startDate, endDate, STATUS_OPEN);

        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Закрываем (проставляем статус "Закрыто") всем записям в БД
     */
    public static void closeAllNotes(){
        String sql = String.format("update notes set status = '%d' ", STATUS_CLOSED);

        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Закрываем (проставляем статус "Закрыто") для записи по номеру
     * @param number номер записи
     */
    public static void closeNote(int number){
        String sql = String.format("update notes set status = '%d' where id = '%d' ", STATUS_CLOSED, number);

        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляем все записи из БД
     */
    public static void deleteAllNotes(){
        String sql = "delete from notes";

        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляем запись из БД по номеру
     * @param number номер записи
     */
    public static void deleteNote(int number){
        String sql = String.format("delete from notes where id = '%d'", number);

        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обновление полей БД записи по номеру
     * @param text новый текст записи
     * @param priority новый приоритет
     * @param startDate новая дата начала
     * @param endDate новая дата окончания
     * @param number номер записи в БД
     * @throws SQLException
     */
    public static void updateNote(String text, int priority, String startDate, String endDate, int number) throws SQLException {
        String sql = String.format("update notes set note_text = '%s', priority = '%d', date_startactive = '%s', date_endactive = '%s' where id = '%d'", text, priority, startDate, endDate, number);

        statement.execute(sql);
    }

    /**
     * Инициализируем свободный порядковый номер для новой записи
     */
    public static void initNoteCount(){
        noteCount = 0;
        String sql = "select max(id) from notes";

        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next())
                if(rs.getString(1) != null) {
                    noteCount = Integer.parseInt(rs.getString(1));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получение всех записей
     * @return ResultSet
     */
    public static ResultSet getNotes(){
        ResultSet resultSet = null;
        String sql = "select * from notes order by status, priority desc";

        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Получение записи по номеру
     * @param number номер записи в БД
     * @return ResultSet
     */
    public static ResultSet getNote(int number) {
        ResultSet resultSet = null;
        String sql = String.format("select * from notes where id = '%d'", number);

        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Определяем активна запись или нет по номеру
     * @param number номер записи в БД
     * @return
     */
    public static boolean isNoteActive(int number){
        String sql = String.format("Select * from notes where id = '%d' and status = '%d'", number, STATUS_OPEN);

        try {
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Получение данных для формирования статистики по дням
     * @return ResultSet
     */
    public static ResultSet getStatisticPerDay(){
        ResultSet resultSet= null;
        String sql = "select count(*) as count, date_startactive, substr(date_startactive, 1,2) as day, substr(date_startactive, 4,2) as month, " +
                     "substr(date_startactive, -4) as year  from notes group by date_startactive order by year, month, day";

        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Получение данных для формирования статистики по приоритетам
     * @return ResultSet
     */
    public static ResultSet getStatisticByPriority(){
        ResultSet resultSet= null;
        String sql = "select count(*) as count, priority from notes group by priority order by priority";

        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static int getNoteCount() {
        return noteCount;
    }
}
