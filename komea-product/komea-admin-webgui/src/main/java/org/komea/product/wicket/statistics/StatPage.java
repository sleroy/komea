package org.komea.product.wicket.statistics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.api.IKpiValueService;
import org.komea.product.backend.service.esper.IEventStatisticsService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Measure;
import org.komea.product.service.dto.EventTypeStatistic;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class StatPage extends LayoutPage {

	/**
	 * @author sleroy
	 */
	private final class BackupLink extends AjaxLink {

		/**
         *
         */
		private static final long	serialVersionUID	= -6616588401268566760L;

		/**
		 * @param _id
		 */
		private BackupLink(final String _id) {

			super(_id);
		}

		@Override
		public void onClick(final AjaxRequestTarget _target) {

			LOGGER.info("Backup KPI values into the history");

			statisticsAPI.backupKpiValuesIntoHistory(BackupDelay.HOUR);
			statisticsAPI.backupKpiValuesIntoHistory(BackupDelay.DAY);
			statisticsAPI.backupKpiValuesIntoHistory(BackupDelay.WEEK);
			statisticsAPI.backupKpiValuesIntoHistory(BackupDelay.MONTH);

			_target.getPage().setResponsePage(StatPage.class);

		}
	}

	/**
	 * @author sleroy
	 */
	private final class LoadableDetachableModelExtension extends LoadableDetachableModel<Long> {

		@Override
		protected Long load() {

			return statService.getReceivedAlertsIn24LastHours();

		}
	}

	private static final Logger	    LOGGER	         = LoggerFactory.getLogger(StatPage.class);

	/**
     *
     */
	private static final long	    serialVersionUID	= 825152658028992367L;
	@SpringBean
	private IKpiValueService	    kpiValueService;

	@SpringBean
	private IStatisticsAPI	        statisticsAPI;

	@SpringBean
	private IEventStatisticsService	statService;

	public StatPage(final PageParameters _parameters) {

		super(_parameters);
		add(new Label("alert_number", new LoadableDetachableModelExtension()));
		generateLabelForAlertsWithCriticity(Severity.BLOCKER);
		generateLabelForAlertsWithCriticity(Severity.CRITICAL);
		generateLabelForAlertsWithCriticity(Severity.MAJOR);
		generateLabelForAlertsWithCriticity(Severity.MINOR);
		generateLabelForAlertsWithCriticity(Severity.INFO);

		final ChartOptions chartOptions = new ChartOptions();

		chartOptions.setSpacingRight(20);
		chartOptions.setType(SeriesType.SPLINE);
		chartOptions.setBorderRadius(1);
		chartOptions.setBorderColor(Color.GRAY);

		final Options options = new Options();
		options.setChartOptions(chartOptions);

		options.setTitle(new Title("Number of events received per day"));
		//

		final Axis xAxis = new Axis();
		xAxis.setType(AxisType.DATETIME);

		final DateTimeLabelFormat dateTimeLabelFormat = new DateTimeLabelFormat();
		dateTimeLabelFormat.setProperty(DateTimeProperties.MONTH, "%e. %b").setProperty(DateTimeProperties.YEAR, "%b");
		xAxis.setDateTimeLabelFormats(dateTimeLabelFormat);
		options.setxAxis(xAxis);

		final Axis yAxis = new Axis();
		yAxis.setTitle(new Title("Number of events"));
		yAxis.setMin(0);
		options.setyAxis(yAxis);

		final Tooltip tooltip = new Tooltip();
		tooltip.setFormatter(new Function(
		        "return '<b>'+ this.series.name +'</b><br/>'+Highcharts.dateFormat('%e. %b', this.x) +': '+ this.y +' events';"));
		options.setTooltip(tooltip);

		buildGraphic(options);

		add(new BackupLink("backup-values"));
		final List<EventTypeStatistic> receivedAlertTypesIn24LastHours = statService
		        .getReceivedAlertTypesIn24LastHours();
		final DataTable<EventTypeStatistic, String> table = DataTableBuilder
		        .<EventTypeStatistic, String> newTable("table").addColumn("Event type", "type")
		        .addColumn("Provider", "provider").addColumn("Number", "value")
		        .displayRows(receivedAlertTypesIn24LastHours.size() + 10).withListData(receivedAlertTypesIn24LastHours)
		        .build();
		add(table);

	}

	public IKpiValueService getKpiValueService() {

		return kpiValueService;
	}

	@Override
	public String getTitle() {

		return getString("administration.title.statistics");
	}

	public void setKpiValueService(final IKpiValueService _kpiValueService) {

		kpiValueService = _kpiValueService;
	}

	private void buildGraphic(final Options options) {

		// Define the data points. All series have a dummy year
		// of 1970/71 in order to be compared on the same x axis. Note
		// that in JavaScript, months start at 0 for January, 1 for February
		// etc.
		final List<Coordinate<Long, Integer>> seriesData1 = new ArrayList<Coordinate<Long, Integer>>();

		for (final Measure measure : statService.getAllMeasures()) {
			seriesData1.add(new Coordinate<Long, Integer>(measure.getDate().getTime(), measure.getValue().intValue()));
		}

		final CustomCoordinatesSeries<Long, Integer> series1 = new CustomCoordinatesSeries<Long, Integer>();
		series1.setName("Number of events received this day");
		series1.setData(seriesData1);
		options.addSeries(series1);

		add(new Chart("chart", options));
	}

	private void generateLabelForAlertsWithCriticity(final Severity criticity) {

		add(new Label(criticity.name().toLowerCase(), new LoadableDetachableModel<Long>() {

			@Override
			protected Long load() {

				return statService.getNumberOfAlerts(criticity);

			}

		}));
	}
}
