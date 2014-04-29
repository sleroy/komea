/**
 *
 */
package org.komea.product.backend.service.alert;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.MeasureDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.KpiAlertType;

/**
 * @author sleroy
 */
public class AlertCriteria {

    private KpiAlertType alertType;

    private BaseEntityDto entity;

    private EntityType entityType;

    private List<MeasureDto> measures;

    public AlertCriteria(
            final KpiAlertType _alertType,
            final BaseEntityDto _entity,
            final EntityType _entityType,
            final List<MeasureDto> _measures) {

        super();
        alertType = _alertType;
        entity = _entity;
        entityType = _entityType;
        measures = _measures;
        Validate.notNull(_alertType);
        Validate.notNull(_entity);
    }

    /**
     * @return the alertType
     */
    public KpiAlertType getAlertType() {

        return alertType;
    }

    /**
     * @return the entity
     */
    public BaseEntityDto getEntity() {

        return entity;
    }

    /**
     * @return the entityType
     */
    public EntityType getEntityType() {

        return entityType;
    }

    /**
     * @return the measures
     */
    public List<MeasureDto> getMeasures() {

        return measures;
    }

    /**
     * @param _alertType the alertType to set
     */
    public void setAlertType(final KpiAlertType _alertType) {

        alertType = _alertType;
    }

    /**
     * @param _entity the entity to set
     */
    public void setEntity(final BaseEntityDto _entity) {

        entity = _entity;
    }

    /**
     * @param _entityType the entityType to set
     */
    public void setEntityType(final EntityType _entityType) {

        entityType = _entityType;
    }

    /**
     * @param _measures the measures to set
     */
    public void setMeasures(final List<MeasureDto> _measures) {

        measures = _measures;
    }
}
