package TaskManager.StatisticWindow;

import TaskManager.DataBaseService;
import TaskManager.StatisticWindow.Charts.AreaChartSeries;
import TaskManager.StatisticWindow.Charts.PieChartSlices;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Класс-контроллер для окна статистики
 */
public class StatisticController {

    @FXML
    Label quantityLabel;

    @FXML
    PieChart pieChart;

    @FXML
    AreaChart<String, Integer> areaChart;

    @FXML
    Button closeButton;

    /**
     * Инициализируем объекты окна статистики
     */
    @FXML
    public void initialize() {
        initCloseButton();
        initQuantityLabel();

        AreaChartSeries areaChartSeries = new AreaChartSeries();
        PieChartSlices pieChartSlices = new PieChartSlices();

        final ObservableList<XYChart.Series<String, Integer>> seriesAreaChart = areaChartSeries.getData();
        final ObservableList<PieChart.Data> pieChartData = pieChartSlices.getData();

        areaChart.setData(seriesAreaChart);
        pieChart.setData(pieChartData);
    }

    public void initCloseButton(){
        ImageView cancelBut= new ImageView(new Image("images/cancelButtonImg.png"));
        closeButton.setGraphic(cancelBut);
    }

    public void closeWindow(){
        StatisticWindow.getStage().close();
    }

    public void initQuantityLabel(){
        quantityLabel.setText("Всего задач: " + DataBaseService.getNoteCount());
    }
}
