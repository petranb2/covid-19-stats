package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import models.Country;
import models.Coviddata;
import service.AppQueries;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import service.LoadCovidData;
import utils.Constants;

/**
 *
 * @author kalogeros
 */
public class CovidChart extends JFrame {
    public CovidChart(final String title, String countryName, Boolean confirmedDataNeeded, Boolean deathsDataNeeded, Boolean recoveredDataNeeded, Boolean cumulativeDataNeeded, Date startDate, Date endDate) {
        super(title);
        final CategoryDataset dataset = createDataset(countryName, confirmedDataNeeded, deathsDataNeeded, recoveredDataNeeded, cumulativeDataNeeded, startDate, endDate);
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400));
        chartPanel.setMouseWheelEnabled(true);

        setContentPane(chartPanel);
    }

    /**
     * Creates a sample dataset.
     *
     * @return The dataset.
     */
    private CategoryDataset createDataset(String countryName, Boolean confirmedDataNeeded, Boolean deathsDataNeeded, Boolean recoveredDataNeeded, Boolean cumulativeDataNeeded, Date startDate, Date endDate) {
        
        // row keys...
        final String series1 = "Confirmed";
        final String series2 = "Deaths";
        final String series3 = "Recovered";
        
        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // column keys...       
        // FIX THIS:
        Country country = AppQueries.fetchCountryByName(countryName);        
        if(confirmedDataNeeded){
            List<Coviddata> fetchedConfirmeddata = AppQueries.fetchCoviddata(country, Constants.DATA_KIND_CONFIRMED, startDate, endDate);
            for (Coviddata coviddata : fetchedConfirmeddata) {
                dataset.addValue(cumulativeDataNeeded ? coviddata.getProodqty() : coviddata.getQty(), series1, coviddata.getTrndate().toString());
            }
        }
        if(deathsDataNeeded){
            List<Coviddata> fetchedConfirmeddata = AppQueries.fetchCoviddata(country, Constants.DATA_KIND_DEATHS, startDate, endDate);
            for (Coviddata coviddata : fetchedConfirmeddata) {
                dataset.addValue(cumulativeDataNeeded ? coviddata.getProodqty() : coviddata.getQty(), series2, coviddata.getTrndate().toString());
            }
        }
        if(recoveredDataNeeded){
            List<Coviddata> fetchedConfirmeddata = AppQueries.fetchCoviddata(country, Constants.DATA_KIND_RECOVERED, startDate, endDate);
            for (Coviddata coviddata : fetchedConfirmeddata) {
                dataset.addValue(cumulativeDataNeeded ? coviddata.getProodqty() : coviddata.getQty(), series3, coviddata.getTrndate().toString());  
            }
        }
                
//        List<Coviddata> fetchedCoviddata = AppQueries.fetchCoviddata(country, 1);                               
//        for (int i = 0; i < 2; i++) {
//            System.out.println(fetchedCoviddata.get(i).getQty());
//            dataset.addValue(fetchedCoviddata.get(i).getQty(), series1, fetchedCoviddata.get(i).getTrndate().toString());
//        }

        return dataset;
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset a dataset.
     *
     * @return The chart.
     */
    private JFreeChart createChart(final CategoryDataset dataset) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createLineChart(
            "Covid-19 Data Chart",     // chart title
            "Time",                    // domain axis label
            "Quantity (Persons)",      // range axis label
            dataset,                   // data
            PlotOrientation.VERTICAL,  // orientation
            true,                      // include legend
            true,                      // tooltips
            false                      // urls
        );

       
        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        // customise the range axis...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);

        // customise the renderer...
        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        renderer.setDrawShapes(true);

        renderer.setSeriesStroke(
                0, new BasicStroke(
                        2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                        1.0f, new float[]{10.0f, 6.0f}, 0.0f
                )
        );
        renderer.setSeriesStroke(
                1, new BasicStroke(
                        2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                        1.0f, new float[]{6.0f, 6.0f}, 0.0f
                )
        );
        renderer.setSeriesStroke(
                2, new BasicStroke(
                        2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                        1.0f, new float[]{2.0f, 6.0f}, 0.0f
                )
        );
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;
    }
}
