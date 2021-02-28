package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import models.Country;
import models.Coviddata;
import service.AppQueries;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import utils.Constants;

public class CovidChart extends JFrame {
    public CovidChart(final String title, String countryName, Boolean confirmedDataNeeded, Boolean deathsDataNeeded, Boolean recoveredDataNeeded, Boolean cumulativeDataNeeded, Date startDate, Date endDate) {
        super(title);
        final CategoryDataset dataset = createDataset(countryName, confirmedDataNeeded, deathsDataNeeded, recoveredDataNeeded, cumulativeDataNeeded, startDate, endDate);
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        
        chartPanel.setPreferredSize(new Dimension(800, 400));
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setMouseZoomable(true);
        chartPanel.setHorizontalAxisTrace(true);
        chartPanel.setVerticalAxisTrace(true);
        
        this.setLayout(new BorderLayout(0, 5));
        this.add(chartPanel, BorderLayout.CENTER);
                
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(createZoom(chartPanel));
        panel.add(createTrace(chartPanel));
        this.add(panel, BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

//        setContentPane(chartPanel);
    }
    
    private JButton createZoom(ChartPanel chartPanel) {
        final JButton auto = new JButton(new AbstractAction("Reset") {

            @Override
            public void actionPerformed(ActionEvent e) { chartPanel.restoreAutoBounds(); }
        });
        return auto;
    }
    
    private JComboBox createTrace(ChartPanel chartPanel) {
        final JComboBox trace = new JComboBox();
        final String[] traceCmds = {"Rulers On", "Rulers Off"};
        trace.setModel(new DefaultComboBoxModel(traceCmds));
        trace.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (traceCmds[0].equals(trace.getSelectedItem())) {
                    chartPanel.setHorizontalAxisTrace(true);
                    chartPanel.setVerticalAxisTrace(true);
                    chartPanel.repaint();
                } else {
                    chartPanel.setHorizontalAxisTrace(false);
                    chartPanel.setVerticalAxisTrace(false);
                    chartPanel.repaint();
                }
            }
        });
        return trace;
    }

    private CategoryDataset createDataset(String countryName, Boolean confirmedDataNeeded, Boolean deathsDataNeeded, Boolean recoveredDataNeeded, Boolean cumulativeDataNeeded, Date startDate, Date endDate) {
        
        // Date formater for the chart
        SimpleDateFormat dateFormater = new SimpleDateFormat("MM-dd-yy");
        
        // row keys...
        final String series1 = "Confirmed";
        final String series2 = "Deaths";
        final String series3 = "Recovered";
        
        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // column keys...       
        Country country = AppQueries.fetchCountryByName(countryName);        
        if(confirmedDataNeeded){
            List<Coviddata> fetchedConfirmeddata = AppQueries.fetchCoviddata(country, Constants.DATA_KIND_CONFIRMED, startDate, endDate);
            for (Coviddata coviddata : fetchedConfirmeddata) {
                dataset.addValue(cumulativeDataNeeded ? coviddata.getProodqty() : coviddata.getQty(), series1, dateFormater.format(coviddata.getTrndate()));
            }
        }
        if(deathsDataNeeded){
            List<Coviddata> fetchedConfirmeddata = AppQueries.fetchCoviddata(country, Constants.DATA_KIND_DEATHS, startDate, endDate);
            for (Coviddata coviddata : fetchedConfirmeddata) {
                dataset.addValue(cumulativeDataNeeded ? coviddata.getProodqty() : coviddata.getQty(), series2, dateFormater.format(coviddata.getTrndate()));
            }
        }
        if(recoveredDataNeeded){
            List<Coviddata> fetchedConfirmeddata = AppQueries.fetchCoviddata(country, Constants.DATA_KIND_RECOVERED, startDate, endDate);
            for (Coviddata coviddata : fetchedConfirmeddata) {
                dataset.addValue(cumulativeDataNeeded ? coviddata.getProodqty() : coviddata.getQty(), series3, dateFormater.format(coviddata.getTrndate()));  
            }
        }

        return dataset;
    }


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
        renderer.setSeriesStroke(0, new BasicStroke( 2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[]{10.0f, 6.0f}, 0.0f ));
        renderer.setSeriesStroke(1, new BasicStroke( 2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[]{6.0f, 6.0f}, 0.0f));
        renderer.setSeriesStroke(2, new BasicStroke( 2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[]{2.0f, 6.0f}, 0.0f));
        return chart;
    }
}
