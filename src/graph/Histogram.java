package graph;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;

public class Histogram {

    private final double[] data;

    private JFreeChart histogramChart;

    private HistogramDataset dataset;

    public Histogram(double[] data){
        this.data = data;

        createHistogram();
    }

    private void createHistogram(){
        dataset = new HistogramDataset();
        dataset.addSeries("key", data, data.length);

        histogramChart = ChartFactory.createHistogram("Histogram", "Liczby", "Ilosc", dataset, PlotOrientation.VERTICAL,
                false, false, false);
    }

    public JFreeChart getHistogramChart() {
        return histogramChart;
    }

}
