package TaskManager.StatisticWindow.Charts;

import TaskManager.DataBaseService;
import TaskManager.StatisticWindow.ChartInterface.Chartable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 * Серия данных для формирования линейного графика
 */
public class AreaChartSeries implements Chartable {

    private ObservableList<XYChart.Series<String, Integer>> data = FXCollections.observableArrayList();
    private LinkedHashMap<String, Integer> statisticMap;

    public AreaChartSeries(){
        initStatisticMap();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Активность");

        for(String key: statisticMap.keySet()){
            series.getData().add(new XYChart.Data<>(key, statisticMap.get(key)));
        }
        data.add(series);
    }

    /**
     * Получаем данные из БД и записываем их в Map
     */
    public void initStatisticMap(){
        statisticMap = new LinkedHashMap<>();
        ResultSet resultSet = DataBaseService.getStatisticPerDay();

        try {
            while (resultSet.next()){
                statisticMap.put(resultSet.getString("date_startactive"), java.lang.Integer.parseInt(resultSet.getString("count")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<XYChart.Series<String, Integer>> getData() {
        return data;
    }
}
