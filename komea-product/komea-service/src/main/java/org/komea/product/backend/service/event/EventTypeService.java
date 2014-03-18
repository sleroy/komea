package org.komea.product.backend.service.event;

import java.util.List;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.database.dao.EventTypeDao;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.EventTypeCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public final class EventTypeService extends AbstractService<EventType, Integer, EventTypeCriteria>
        implements IEventTypeService {

    @Autowired
    private EventTypeDao requiredDAO;

    @Override
    public EventTypeDao getRequiredDAO() {
        return requiredDAO;
    }

    @Override
    protected EventTypeCriteria createPersonCriteriaOnLogin(String key) {
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
