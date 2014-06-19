package org.komea.product.web.cyfe.rest.service;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.model.timeserie.TimeSerieOptions;
import org.komea.product.service.dto.EntityStringKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService implements IStatsService {

    @Autowired
    private IKPIService kpiService;

    @Autowired
    private IEntityService entityService;

    @Autowired
    private IStatisticsAPI statisticsAPI;

    private Kpi getKpiFromString(String _kpiKey) {

        return kpiService.selectByKeyOrFail(_kpiKey);

    }

    private IEntity getEntityFromString(EntityType _entityType, String _entityKey) {

        return entityService.getEntityOrFail(EntityStringKey.of(_entityType, _entityKey));

    }

    @Override
    public Double evaluateKpiValue(String _kpiKey, String _entityKey,
            TimeScale _timescale) {

        Kpi kpi = getKpiFromString(_kpiKey);
        IEntity entity = getEntityFromString(kpi.getEntityType(), _entityKey);

        TimeSerieOptions timeSerieOptions = new TimeSerieOptions(kpi);
        timeSerieOptions.setTimeScale(_timescale != null ? _timescale : TimeScale.PER_DAY);

        return statisticsAPI.evaluateKpiValue(timeSerieOptions, entity.getEntityKey());

    }

    @Override
    public Double evaluateKpiValueWithDate(String _kpiKey, String _entityKey,
            TimeScale _timescale, Date _date) {

        Kpi kpi = getKpiFromString(_kpiKey);
        IEntity entity = getEntityFromString(kpi.getEntityType(), _entityKey);

        PeriodTimeSerieOptions options = new PeriodTimeSerieOptions(kpi);
        options.setTimeScale(_timescale != null ? _timescale : TimeScale.PER_DAY);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(_date);

        switch (_timescale) {
            case PER_MONTH:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                options.setFromPeriod(new DateTime(calendar.getTime()));
                calendar.add(Calendar.MONTH, 1);
                options.setToPeriod(new DateTime(calendar.getTime()));
                break;
            case PER_YEAR:
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
                options.setFromPeriod(new DateTime(calendar.getTime()));
                calendar.set(Calendar.DAY_OF_MONTH, 31);
                calendar.set(Calendar.MONTH, Calendar.DECEMBER);
                options.setToPeriod(new DateTime(calendar.getTime()));
                break;
            default:
                options.setFromPeriod(new DateTime(calendar.getTime()));
                options.setToPeriod(new DateTime(calendar.getTime()));
                break;
        }

        return statisticsAPI.evaluateKpiValueOnPeriod(options, entity.getEntityKey());

    }

}
