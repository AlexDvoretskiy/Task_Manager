package TaskManager.StatisticWindow.Charts;

import TaskManager.DataBaseService;
import TaskManager.MainWindow.Note.NotePriorities;
import TaskManager.StatisticWindow.ChartInterface.Chartable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Класс, формирующий данные для круговой диаграммы
 */
public class PieChartSlices implements Chartable {

    private ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
    private HashMap<String, Integer> statisticMap;

    public PieChartSlices(){
        initStatisticMap();

        for(String key: statisticMap.keySet()){
            data.add(new PieChart.Data(key, statisticMap.get(key)));
        }
    }

    /**
     * Получаем данные из БД и записываем их в Map
     */
    public void initStatisticMap(){
        statisticMap = new HashMap<>();
        ResultSet resultSet = DataBaseService.getStatisticByPriority();

        try {
            while (resultSet.next()){
                String priority = NotePriorities.getPriorityName(Integer.parseInt(resultSet.getString("priority")));
                statisticMap.put(priority, Integer.parseInt(resultSet.getString("count")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<PieChart.Data>  getData() {
        return data;
    }
}
