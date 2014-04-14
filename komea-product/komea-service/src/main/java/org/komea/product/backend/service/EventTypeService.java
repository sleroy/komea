package org.komea.product.backend.service;

import java.util.List;

import javax.validation.Valid;

import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.plugins.IEventTypeService;
import org.komea.product.database.dao.EventTypeDao;
import org.komea.product.database.dao.IGenericDAO;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.EventTypeCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Service
@Transactional
public class EventTypeService extends AbstractService<EventType, Integer, EventTypeCriteria>
        implements IEventTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger("event-type-service");

    @Autowired
    private EventTypeDao requiredDAO;

    public EventTypeService() {

        super();
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.genericservice.AbstractService#getRequiredDAO()
     */
    @Override
    public IGenericDAO<EventType, Integer, EventTypeCriteria> getRequiredDAO() {

        return requiredDAO;
    }

    /**
     * Builds a new criteria with the key.
     *
     * @param _eventType
     * @return EventTypeCriteria
     */
    public EventTypeCriteria newCriteriaSelectByKey(final EventType _eventType) {

        final EventTypeCriteria selectEventTypeByKey = new EventTypeCriteria();
        selectEventTypeByKey.createCriteria().andEventKeyEqualTo(_eventType.getEventKey());
        return selectEventTypeByKey;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IEventTypeService#registerEvent(org.komea.product.database.model.Provider,
     * org.komea.product.database.model.EventType)
     */
    @Override
    public void registerEvent(@Valid
            final EventType _eventType) {

        LOGGER.debug("Registering event type '{}'", _eventType.getName());
        final EventTypeCriteria selectByName = newCriteriaSelectByKey(_eventType);
        final boolean exists = requiredDAO.countByCriteria(selectByName) > 0;
        if (!exists) {
            insert(_eventType);
        }

    }

    public void setRequiredDAO(final EventTypeDao _requiredDAO) {

        requiredDAO = _requiredDAO;
    }

    @Override
    protected EventTypeCriteria createKeyCriteria(String key) {
        final EventTypeCriteria criteria = new EventTypeCriteria();
        criteria.createCriteria().andEventKeyEqualTo(key);
        return criteria;
    }

    @Override
    public List<EventType> getEventTypes(EntityType entityType) {
        final EventTypeCriteria eventTypeCriteria = new EventTypeCriteria();
        eventTypeCriteria.createCriteria().andEntityTypeEqualTo(entityType);
        return requiredDAO.selectByCriteria(eventTypeCriteria);
    }

}
