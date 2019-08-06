package TaskManager.StatisticWindow.ChartInterface;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 * Интерфейс для реализации графиков
 */
public interface Chartable {

    public void initStatisticMap();

    public ObservableList getData();

}
