package org.komea.product.backend.service.measure;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.SearchMeasuresDto;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MeasureService implements IMeasureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasureService.class);
    @Autowired
    private MeasureDao measureDao;

    public MeasureService() {
        super();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Measure> getMeasures(List<Kpi> kpis, List<BaseEntity> entities,
            SearchMeasuresDto searchMeasuresDto) {
        if (kpis.isEmpty() || entities.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        final MeasureCriteria measureCriteria = new MeasureCriteria();
        final Date from = searchMeasuresDto.getFromDate();
        final Date to = searchMeasuresDto.getToDate();
        for (final IEntity entity : entities) {
            final Integer idEntity = entity.getId();
            for (final Kpi kpi : kpis) {
                final Integer idKpi = kpi.getId();
                final MeasureCriteria.Criteria criteria = measureCriteria.or();
                criteria.andIdKpiEqualTo(idKpi);
                if (from != null) {
                    criteria.andDateGreaterThanOrEqualTo(from);
                }
                if (to != null) {
                    criteria.andDateLessThanOrEqualTo(to);
                }
                switch (searchMeasuresDto.getEntityType()) {
                    case PERSON:
                        criteria.andIdPersonEqualTo(idEntity);
                        break;
                    case PROJECT:
                        criteria.andIdProjectEqualTo(idEntity);
                        break;
                    case TEAM:
                    case DEPARTMENT:
                        criteria.andIdPersonGroupEqualTo(idEntity);
                        break;
                }
            }
        }
        final Integer limit = searchMeasuresDto.getNbMeasures();
        final RowBounds rowBounds = new RowBounds(0, limit == null ? Integer.MAX_VALUE : limit);
        final List<Measure> measures = measureDao.selectByCriteriaWithRowbounds(
                measureCriteria, rowBounds);
        Collections.sort(measures, new Comparator<Measure>() {

            @Override
            public int compare(Measure o1, Measure o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return measures;
    }

}
