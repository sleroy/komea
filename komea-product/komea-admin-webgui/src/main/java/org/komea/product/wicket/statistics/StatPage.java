
package org.komea.product.wicket.statistics;



import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.esper.IAlertStatisticsService;
import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.AlertTypeStatistic;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.DataTableBuilder;

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.AxisType;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.DateTimeLabelFormat;
import com.googlecode.wickedcharts.highcharts.options.DateTimeLabelFormat.DateTimeProperties;
import com.googlecode.wickedcharts.highcharts.options.Function;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.Tooltip;
import com.googlecode.wickedcharts.highcharts.options.series.Coordinate;
import com.googlecode.wickedcharts.highcharts.options.series.CustomCoordinatesSeries;
import com.googlecode.wickedcharts.wicket6.highcharts.Chart;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class StatPage extends LayoutPage
{
    
    
    @SpringBean
    private IAlertStatisticsService statService;
    
    
    
    public StatPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        add(new Label("alert_number", new LoadableDetachableModel<Long>()
        {
            
            
            @Override
            protected Long load() {
            
            
                return statService.getReceivedAlertsIn24LastHours();
                
            }
            
        }));
        generateLabelForAlertsWithCriticity(Criticity.BLOCKING);
        generateLabelForAlertsWithCriticity(Criticity.CRITICAL);
        generateLabelForAlertsWithCriticity(Criticity.MAJOR);
        generateLabelForAlertsWithCriticity(Criticity.MINOR);
        generateLabelForAlertsWithCriticity(Criticity.INFO);
        
        final ChartOptions chartOptions = new ChartOptions();
        chartOptions.setType(SeriesType.SPLINE);
        chartOptions.setBorderRadius(1);
        chartOptions.setBorderColor(Color.GRAY);
        
        final Options options = new Options();
        options.setChartOptions(chartOptions);
        
        options.setTitle(new Title("Number of alerts received per day"));
        //
        
        final Axis xAxis = new Axis();
        xAxis.setType(AxisType.DATETIME);
        
        final DateTimeLabelFormat dateTimeLabelFormat =
                new DateTimeLabelFormat().setProperty(DateTimeProperties.MONTH, "%e. %b")
                        .setProperty(DateTimeProperties.YEAR, "%b");
        xAxis.setDateTimeLabelFormats(dateTimeLabelFormat);
        options.setxAxis(xAxis);
        
        final Axis yAxis = new Axis();
        yAxis.setTitle(new Title("Number of alerts"));
        yAxis.setMin(0);
        options.setyAxis(yAxis);
        
        final Tooltip tooltip = new Tooltip();
        tooltip.setFormatter(new Function(
                "return '<b>'+ this.series.name +'</b><br/>'+Highcharts.dateFormat('%e. %b', this.x) +': '+ this.y +' alerts';"));
        options.setTooltip(tooltip);
        
        // Define the data points. All series have a dummy year
        // of 1970/71 in order to be compared on the same x axis. Note
        // that in JavaScript, months start at 0 for January, 1 for February etc.
        
        final List<Coordinate<String, Integer>> seriesData1 =
                new ArrayList<Coordinate<String, Integer>>();
        
        
        for (final Measure measure : statService.getAllMeasures()) {
            seriesData1.add(new Coordinate<String, Integer>(measure.getId().toString(), measure
                    .getValue().intValue()));
        }
        
        
        final CustomCoordinatesSeries<String, Integer> series1 =
                new CustomCoordinatesSeries<String, Integer>();
        series1.setName("Number of alerts per day");
        series1.setData(seriesData1);
        options.addSeries(series1);
        
        add(new Chart("chart", options));
        
        
        final DataTable<AlertTypeStatistic, String> table =
                DataTableBuilder.<AlertTypeStatistic, String> newTable("table")
                        .addColumn("Alert type", "type").addColumn("Provider", "provider")
                        .addColumn("Number", "number").displayRows(10)
                        .withListData(statService.getReceivedAlertTypesIn24LastHours()).build();
        add(table);
        
    }
    
    
    private void generateLabelForAlertsWithCriticity(final Criticity criticity) {
    
    
        add(new Label(criticity.name().toLowerCase(), new LoadableDetachableModel<Long>()
        {
            
            
            @Override
            protected Long load() {
            
            
                return statService.getNumberOfAlerts(criticity);
                
            }
            
        }));
    }
}
