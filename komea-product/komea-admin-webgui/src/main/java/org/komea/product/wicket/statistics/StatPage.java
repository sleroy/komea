
package org.komea.product.wicket.statistics;



import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.esper.IAlertStatisticsService;
import org.komea.product.wicket.LayoutPage;

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
        add(new Label("alert_number", Model.of(statService.getReceivedAlertsIn24LastHours())));
        final ChartOptions chartOptions = new ChartOptions();
        chartOptions.setType(SeriesType.SPLINE);
        
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
        
        final List<Coordinate<String, Float>> seriesData1 =
                new ArrayList<Coordinate<String, Float>>();
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1970,  9, 27)", 0f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1970, 10, 10)", 0.6f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1970, 10, 18)", 0.7f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1970, 11,  2)", 0.8f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1970, 11,  9)", 0.6f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1970, 11, 16)", 0.6f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1970, 11, 28)", 0.67f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  0,  1)", 0.81f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  0,  8)", 0.78f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  0, 12)", 0.98f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  0, 27)", 1.84f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  1, 10)", 1.80f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  1, 18)", 1.80f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  1, 24)", 1.92f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  2,  4)", 2.49f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  2, 11)", 2.79f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  2, 15)", 2.73f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  2, 25)", 2.61f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  3,  2)", 2.76f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  3,  6)", 2.82f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  3, 13)", 2.8f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  4,  3)", 2.1f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  4, 26)", 1.1f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  5,  9)", 0.25f));
        seriesData1.add(new Coordinate<String, Float>("Date.UTC(1971,  5, 12)", 0f));
        
        final CustomCoordinatesSeries<String, Float> series1 =
                new CustomCoordinatesSeries<String, Float>();
        series1.setName("Number of alerts per day");
        series1.setData(seriesData1);
        options.addSeries(series1);
        
        add(new Chart("chart", options));
        
    }
}
