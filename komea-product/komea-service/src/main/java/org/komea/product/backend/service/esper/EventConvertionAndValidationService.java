
package org.komea.product.backend.service.esper;



import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dao.EventTypeDao;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.EventTypeCriteria;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonGroupCriteria;
import org.komea.product.database.model.ProjectCriteria;
import org.komea.product.database.model.ProviderCriteria;
import org.springframework.stereotype.Service;



@Service
public class EventConvertionAndValidationService implements IEventConvertionAndValidationService
{
    
    
    private PersonDao      personDAO;
    
    
    private PersonGroupDao personGroupDAO;
    
    
    private ProjectDao     projectDAO;
    
    private ProviderDao    providerDAO;
    private EventTypeDao   eventTypeDAO;
    
    
    
    @Override
    public IEvent convert(final EventSimpleDto _dto) {
    
    
        final Event event = new Event();
        event.setDate(_dto.getDate());
        
        final EventTypeCriteria eventTypeCriteria = new EventTypeCriteria();
        eventTypeCriteria.createCriteria().andEventKeyEqualTo(_dto.getEventType());
        event.setEventType(CollectionUtil.singleOrNull(eventTypeDAO
                .selectByCriteria(eventTypeCriteria)));
        
        event.setMessage(_dto.getMessage());
        
        final PersonGroupCriteria personGroupCriteria = new PersonGroupCriteria();
        personGroupCriteria.createCriteria().andPersonGroupKeyEqualTo(_dto.getEventType());
        event.setPersonGroup(CollectionUtil.singleOrNull(personGroupDAO
                .selectByCriteria(personGroupCriteria)));
        
        event.setProperties(_dto.getProperties());
        event.setUrl(_dto.getUrl());
        event.setValue(_dto.getValue());
        
        final ProjectCriteria projectCriteria = new ProjectCriteria();
        projectCriteria.createCriteria().andProjectKeyEqualTo(_dto.getProject());
        event.setProject(CollectionUtil.singleOrNull(projectDAO.selectByCriteria(projectCriteria)));
        
        final ProviderCriteria providerCriteria = new ProviderCriteria();
        providerCriteria.createCriteria().andNameEqualTo(_dto.getProvider());
        event.setProvider(CollectionUtil.singleOrNull(providerDAO
                .selectByCriteria(providerCriteria)));
        event.setPersons(new ArrayList<Person>());
        for (final String userName : _dto.getPersons()) {
            
            final PersonCriteria personCriteria = new PersonCriteria();
            personCriteria.createCriteria().andLoginEqualTo(userName);
            event.getPersons().add(
                    CollectionUtil.singleOrNull(personDAO.selectByCriteria(personCriteria)));
        }
        
        return event;
    }
    
    
    public EventTypeDao getEventTypeDAO() {
    
    
        return eventTypeDAO;
    }
    
    
    public PersonDao getPersonDAO() {
    
    
        return personDAO;
    }
    
    
    public PersonGroupDao getPersonGroupDAO() {
    
    
        return personGroupDAO;
    }
    
    
    public ProjectDao getProjectDAO() {
    
    
        return projectDAO;
    }
    
    
    public ProviderDao getProviderDAO() {
    
    
        return providerDAO;
    }
    
    
    public void setEventTypeDAO(final EventTypeDao _eventTypeDAO) {
    
    
        eventTypeDAO = _eventTypeDAO;
    }
    
    
    public void setPersonDAO(final PersonDao _personDAO) {
    
    
        personDAO = _personDAO;
    }
    
    
    public void setPersonGroupDAO(final PersonGroupDao _personGroupDAO) {
    
    
        personGroupDAO = _personGroupDAO;
    }
    
    
    public void setProjectDAO(final ProjectDao _projectDAO) {
    
    
        projectDAO = _projectDAO;
    }
    
    
    public void setProviderDAO(final ProviderDao _providerDAO) {
    
    
        providerDAO = _providerDAO;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.esper.IEventConvertionAndValidationService#validate(org.komea.product.database.alert.IEvent)
     */
    @Override
    public void validate(final IEvent _event) {
    
    
        if (_event.getProvider() == null) { throw new IllegalArgumentException("invalid provider."); }
        if (_event.getEventType() == null) { throw new IllegalArgumentException(
                "Event not registered, ignoring it."); }
        
        if (StringUtils.isEmpty(_event.getMessage())) { throw new IllegalArgumentException(
                "invalid message"); }
        
        
    }
    
    
}
