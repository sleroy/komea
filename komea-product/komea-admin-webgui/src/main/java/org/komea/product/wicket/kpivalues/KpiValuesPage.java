
package org.komea.product.wicket.kpivalues;



import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.api.IQueryService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.kpi.TimeSerie;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.TimeCoordinate;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.wicket.LayoutPage;
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
public class KpiValuesPage extends LayoutPage
{
    
    
    /**
     * @author sleroy
     */
    private final class FormExtension extends Form<Void>
    {
        
        
        /**
         * @param _id
         */
        private FormExtension(final String _id) {
        
        
            super(_id);
        }
        
        
        @Override
        protected void onSubmit() {
        
        
            final PageParameters parameters = new PageParameters();
            if (kpiChoice != null) {
                parameters.add("kpiChoice", kpiChoice.getKey());
            }
            setResponsePage(KpiValuesPage.class, parameters);
            
        }
    }
    
    
    
    private final class KpiValueList extends ListView<IEntity>
    {
        
        
        private final Kpi kpi;
        
        
        
        public KpiValueList(
                final String _id,
                final List<IEntity> _entitiesByEntityType,
                final Kpi _kpi) {
        
        
            super(_id, _entitiesByEntityType);
            kpi = _kpi;
            
        }
        
        
        @Override
        protected void populateItem(final ListItem<IEntity> _item) {
        
        
            final IEntity entity = _item.getModelObject();
            
            _item.add(new Label("entity", Model.of(_item.getModelObject().getDisplayName())));
            final HistoryKey historyKey = HistoryKey.of(kpi, entity);
            _item.add(new Label("lastvalue", Model.of(statsService
                    .getLastStoredValueInHistory(historyKey))));
            _item.add(new Label("currentvalue", Model.of(statsService
                    .evaluateTheCurrentKpiValue(historyKey))));
        }
    }
    
    
    
    private static final Logger LOGGER           = LoggerFactory.getLogger(KpiValuesPage.class);
    
    /**
     * 
     */
    private static final long   serialVersionUID = 825152658028992367L;
    @SpringBean
    private IEntityService      entityService;
    
    private Object              event;
    
    
    @SpringBean
    private IEventEngineService eventEngineService;
    
    private transient Kpi       kpiChoice;
    
    @SpringBean
    private IQueryService       kpiQueryService;
    
    
    @SpringBean
    private IKPIService         kpiService;
    
    
    @SpringBean
    private MeasureDao          measureDao;
    
    
    @SpringBean
    private IStatisticsAPI      statsService;
    
    
    
    public KpiValuesPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        
        
        add(new FeedbackPanel("feedback"));
        final String kpiParameterName = _parameters.get("kpiChoice").toString();
        if (kpiParameterName != null) {
            kpiChoice = kpiService.selectByKey(kpiParameterName);
        }
        final KpiCriteria sort = new KpiCriteria();
        sort.setOrderByClause("name ASC");
        final List<Kpi> kpiList = kpiService.selectByCriteria(sort);
        
        buildKpiChoicer(kpiList);
        
        if (kpiChoice == null) {
            add(new Label("kpi", ""));
            
            final List<IEntity> entitiesByEntityType = Collections.EMPTY_LIST;
            
            add(new KpiValueList("rows", entitiesByEntityType, kpiChoice));
            add(new Label("details", "no information available"));
            add(new Label("values", "no values"));
            
            buildEventList(Collections.EMPTY_LIST);
            add(new Label("chart", "No graph available"));
            
            
        } else {
            add(new Label("kpi", "Kpi " + kpiChoice.getName()));
            final boolean dynamicQuery = kpiQueryService.isDynamicQuery(kpiChoice);
            add(new Label("details", dynamicQuery
                    ? "This kpi is a dynamic query."
                        : "This kpi is produced from events."));
            final MeasureCriteria measureCriteria = new MeasureCriteria();
            measureCriteria.createCriteria().andIdKpiEqualTo(FormulaID.of(kpiChoice).getId());
            add(new Label("values", measureDao.countByCriteria(measureCriteria)));
            final List<IEntity> entitiesByEntityType =
                    entityService.getEntitiesByEntityType(kpiChoice.getEntityType());
            
            add(new KpiValueList("rows", entitiesByEntityType, kpiChoice));
            if (dynamicQuery) {
                buildEventList(Collections.EMPTY_LIST);
            } else {
                final ICEPQuery query = eventEngineService.getQuery(FormulaID.of(kpiChoice));
                buildEventList(query.getStatement().getAggregateView());
            }
            buildChart();
        }
        
        
    }
    
    
    public void buildChart() {
    
    
        final ChartOptions chartOptions = new ChartOptions();
        
        chartOptions.setSpacingRight(20);
        chartOptions.setType(SeriesType.SPLINE);
        chartOptions.setBorderRadius(1);
        chartOptions.setBorderColor(Color.GRAY);
        
        final Options options = new Options();
        options.setChartOptions(chartOptions);
        final String kpiname = kpiChoice.getName();
        options.setTitle(new Title("KPI " + kpiname));
        
        final Axis xAxis = new Axis();
        xAxis.setType(AxisType.DATETIME);
        
        final DateTimeLabelFormat dateTimeLabelFormat = new DateTimeLabelFormat();
        dateTimeLabelFormat.setProperty(DateTimeProperties.MONTH, "%e. %b").setProperty(
                DateTimeProperties.YEAR, "%b");
        xAxis.setDateTimeLabelFormats(dateTimeLabelFormat);
        options.setxAxis(xAxis);
        
        final Axis yAxis = new Axis();
        yAxis.setTitle(new Title(kpiname));
        yAxis.setMin(0);
        options.setyAxis(yAxis);
        
        final Tooltip tooltip = new Tooltip();
        tooltip.setFormatter(new Function(
                "return '<b>'+ this.series.name +'</b><br/>'+Highcharts.dateFormat('%e. %b', this.x) +': '+ this.y +' alerts';"));
        options.setTooltip(tooltip);
        
        add(buildGraphic(options, "chart", kpiChoice));
    }
    
    
    private void buildEventList(final List list) {
    
    
        final ListChoice<Serializable> listEvents = new ListChoice<Serializable>("events", list);
        listEvents.setMaxRows(20);
        add(listEvents);
    }
    
    
    private Chart buildGraphic(final Options options, final String _chartID, final Kpi _kpi) {
    
    
        // Define the data points. All series have a dummy year
        // of 1970/71 in order to be compared on the same x axis. Note
        // that in JavaScript, months start at 0 for January, 1 for February
        // etc.
        
        final Map<EntityKey, List<Coordinate<Long, Integer>>> series =
                new HashMap<EntityKey, List<Coordinate<Long, Integer>>>();
        
        final List<IEntity> entitiesByEntityType =
                entityService.getEntitiesByEntityType(_kpi.getEntityType());
        LOGGER.debug("KPI {} has {} entities", _kpi.getDisplayName(), entitiesByEntityType.size());
        final PeriodTimeSerieOptions timeSerieOptions = buildPeriodTimeOptions(_kpi);
        for (final IEntity eKey : entitiesByEntityType) {
            final TimeSerie history =
                    statsService.buildPeriodTimeSeries(timeSerieOptions, eKey.getEntityKey());
            LOGGER.debug("Time serie with {} values", history.getCoordinates().size());
            
            for (final TimeCoordinate measure : history.getCoordinates()) {
                
                final EntityKey entityKeyOfMeasure =
                        EntityKey.of(_kpi.getEntityType(), measure.getEntityID());
                if (entityKeyOfMeasure.isUncompleteKey()) {
                    continue;
                }
                List<Coordinate<Long, Integer>> list = series.get(entityKeyOfMeasure);
                if (list == null) {
                    series.put(entityKeyOfMeasure, list =
                            new ArrayList<Coordinate<Long, Integer>>());
                }
                
                list.add(new Coordinate<Long, Integer>(measure.getDate().toDate().getTime(),
                        measure.getValue().intValue()));
            }
        }
        
        createSeries(options, series);
        
        return new Chart(_chartID, options);
    }
    
    
    private void buildKpiChoicer(final List<Kpi> kpiList) {
    
    
        final PropertyModel<Kpi> choiceSetter = new PropertyModel<Kpi>(this, "kpiChoice");
        final ListChoice<Kpi> listChoiceKpis =
                new ListChoice<Kpi>("kpis", choiceSetter, kpiList, new IChoiceRenderer<Kpi>()
                {
                    
                    
                    @Override
                    public Object getDisplayValue(final Kpi _object) {
                    
                    
                        return _object.getName();
                    }
                    
                    
                    @Override
                    public String getIdValue(final Kpi _object, final int _index) {
                    
                    
                        return _object.getKpiKey();
                    }
                });
        listChoiceKpis.setMaxRows(15);
        
        final Form<?> form = new FormExtension("form");
        add(form);
        form.add(listChoiceKpis);
    }
    
    
    private PeriodTimeSerieOptions buildPeriodTimeOptions(final Kpi _kpi) {
    
    
        final PeriodTimeSerieOptions timeSerieOptions = new PeriodTimeSerieOptions(_kpi);
        timeSerieOptions.untilNow();
        timeSerieOptions.lastYears(2);
        timeSerieOptions.pickBestGranularity();
        timeSerieOptions.setGroupFormula(GroupFormula.AVG_VALUE);
        timeSerieOptions.setKpiID(_kpi.getId());
        return timeSerieOptions;
    }
    
    
    private void createSeries(
            final Options options,
            final Map<EntityKey, List<Coordinate<Long, Integer>>> series) {
    
    
        for (final Entry<EntityKey, List<Coordinate<Long, Integer>>> serieValue : series.entrySet()) {
            final CustomCoordinatesSeries<Long, Integer> oneChartSerie =
                    new CustomCoordinatesSeries<Long, Integer>();
            try {
                final IEntity entityOrFail = entityService.getEntityOrFail(serieValue.getKey());
                oneChartSerie.setName(entityOrFail.getDisplayName());
            } catch (final Exception entityOrFail) {
                LOGGER.debug("Entity not found ", entityOrFail);
                continue;
            }
            
            oneChartSerie.setData(serieValue.getValue());
            options.addSeries(oneChartSerie);
        }
    }
}
