
package org.komea.product.service;



import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.komea.product.database.dao.EventTypeMapper;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.EventTypeCriteria;
import org.komea.product.database.model.Provider;
import org.komea.product.plugins.exceptions.AlreadyExistingEventTypeException;
import org.komea.product.plugins.exceptions.InvalidEventTypeDescriptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class EventTypeService implements IEventTypeService
{
    
    
    @Autowired
    private EventTypeMapper     eventTypeDAO;
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventTypeService.class);
    
    
    
    public EventTypeService() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.service.IEventTypeService#getEventTypeDAO()
     */
    @Override
    public EventTypeMapper getEventTypeDAO() {
    
    
        return eventTypeDAO;
    }
    
    
    /**
     * Builds a new criteria with the name.
     * 
     * @param _eventType
     * @return
     */
    public EventTypeCriteria newCriteriaSelectByName(final EventType _eventType) {
    
    
        final EventTypeCriteria selectEventTypeByName = new EventTypeCriteria();
        selectEventTypeByName.createCriteria().andNameEqualTo(_eventType.getEventKey());
        return selectEventTypeByName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.service.IEventTypeService#registerEvent(org.komea.product.database.model.Provider,
     * org.komea.product.database.model.EventType)
     */
    @Override
    public void registerEvent(@NotNull
    final Provider _provider, @Valid
    final EventType _eventType) {
    
    
        LOGGER.info("Registering event type {} with {}", _eventType.getName(), _provider.getName());
        final EventTypeCriteria selectByName = newCriteriaSelectByName(_eventType);
        final int existingProvider = eventTypeDAO.countByExample(selectByName);
        if (existingProvider > 0) { throw new AlreadyExistingEventTypeException(_eventType); }
        if (_eventType.getId() != null) { throw new InvalidEventTypeDescriptionException(
                "EventType DTO should not register primary key"); }
        _eventType.setIdProvider(_provider.getId());
        eventTypeDAO.insert(_eventType);
        
        
    }
    
    
    public void setEventTypeDAO(final EventTypeMapper _eventTypeDAO) {
    
    
        eventTypeDAO = _eventTypeDAO;
    }
    
}
