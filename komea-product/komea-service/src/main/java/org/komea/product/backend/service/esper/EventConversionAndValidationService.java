
package org.komea.product.backend.service.esper;



import javax.annotation.PostConstruct;

import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.backend.service.entities.ProjectService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.ObjectValidation;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dao.EventTypeDao;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.EventTypeCriteria;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonGroupCriteria;
import org.komea.product.database.model.ProviderCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;



/**
 */
@Service
public class EventConversionAndValidationService implements IEventConversionAndValidationService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("event-validation");
    
    @Autowired
    private EventTypeDao        eventTypeDAO;
    
    @Autowired
    private PersonDao           personDAO;
    @Autowired
    private PersonGroupDao      personGroupDAO;
    @Autowired
    private IProjectService     projectService;
    
    @Autowired
    private ProviderDao         providerDAO;
    
    
    private ObjectValidation    validator;
    
    
    
    /**
     * Method convert.
     * 
     * @param _dto
     *            EventSimpleDto
     * @return IEvent
     * @see org.komea.product.backend.service.esper.IEventConversionAndValidationService#convert(EventSimpleDto)
     */
    @Override
    public IEvent convert(final EventSimpleDto _dto) {
    
    
        final Event event = new Event();
        event.setDate(_dto.getDate());
        
        final EventTypeCriteria eventTypeCriteria = new EventTypeCriteria();
        eventTypeCriteria.createCriteria().andEventKeyEqualTo(_dto.getEventType());
        event.setEventType(CollectionUtil.singleOrNull(eventTypeDAO
                .selectByCriteria(eventTypeCriteria)));
        
        event.setMessage(_dto.getMessage());
        
        final String personGroupKey = _dto.getPersonGroup();
        if (personGroupKey != null) {
            final PersonGroupCriteria personGroupCriteria = new PersonGroupCriteria();
            personGroupCriteria.createCriteria().andPersonGroupKeyEqualTo(personGroupKey);
            event.setPersonGroup(CollectionUtil.singleOrNull(personGroupDAO
                    .selectByCriteria(personGroupCriteria)));
        }
        
        event.setProperties(_dto.getProperties());
        event.setUrl(_dto.getUrl());
        event.setValue(_dto.getValue());
        
        final String projectKey = _dto.getProject();
        if (projectKey != null) {
            event.setProject(projectService.getOrCreate(projectKey));
        }
        
        final ProviderCriteria providerCriteria = new ProviderCriteria();
        providerCriteria.createCriteria().andUrlEqualTo(_dto.getProvider());
        event.setProvider(CollectionUtil.singleOrNull(providerDAO
                .selectByCriteria(providerCriteria)));
        
        if (!Strings.isNullOrEmpty(_dto.getPerson())) {
            final PersonCriteria personCriteria = new PersonCriteria();
            personCriteria.or().andLoginEqualTo(_dto.getPerson());
            
            event.setPerson(CollectionUtil.singleOrNull(personDAO.selectByCriteria(personCriteria)));
        }
        
        return event;
    }
    
    
    /**
     * Method getEventTypeDAO.
     * 
     * @return EventTypeDao
     */
    public EventTypeDao getEventTypeDAO() {
    
    
        return eventTypeDAO;
    }
    
    
    /**
     * Method getPersonDAO.
     * 
     * @return PersonDao
     */
    public PersonDao getPersonDAO() {
    
    
        return personDAO;
    }
    
    
    /**
     * Method getPersonGroupDAO.
     * 
     * @return PersonGroupDao
     */
    public PersonGroupDao getPersonGroupDAO() {
    
    
        return personGroupDAO;
    }
    
    
    /**
     * Method getProjectDAO.
     * 
     * @return ProjectDao
     */
    public IProjectService getProjectDAO() {
    
    
        return projectService;
    }
    
    
    /**
     * Method getProviderDAO.
     * 
     * @return ProviderDao
     */
    public ProviderDao getProviderDAO() {
    
    
        return providerDAO;
    }
    
    
    @PostConstruct
    public void initialize() {
    
    
        validator = new ObjectValidation();
        
    }
    
    
    /**
     * Method setEventTypeDAO.
     * 
     * @param _eventTypeDAO
     *            EventTypeDao
     */
    public void setEventTypeDAO(final EventTypeDao _eventTypeDAO) {
    
    
        eventTypeDAO = _eventTypeDAO;
    }
    
    
    /**
     * Method setPersonDAO.
     * 
     * @param _personDAO
     *            PersonDao
     */
    public void setPersonDAO(final PersonDao _personDAO) {
    
    
        personDAO = _personDAO;
    }
    
    
    /**
     * Method setPersonGroupDAO.
     * 
     * @param _personGroupDAO
     *            PersonGroupDao
     */
    public void setPersonGroupDAO(final PersonGroupDao _personGroupDAO) {
    
    
        personGroupDAO = _personGroupDAO;
    }
    
    
    /**
     * Method setProjectDAO.
     * 
     * @param _projectDAO
     *            ProjectDao
     */
    public void setProjectDAO(final ProjectService _projectDAO) {
    
    
        projectService = _projectDAO;
    }
    
    
    /**
     * Method setProviderDAO.
     * 
     * @param _providerDAO
     *            ProviderDao
     */
    public void setProviderDAO(final ProviderDao _providerDAO) {
    
    
        providerDAO = _providerDAO;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.esper.IEventConvertionAndValidationService#validate(org.komea.product.database.alert.IEvent)
     */
    @Override
    public void validate(final IEvent _event) {
    
    
        validateObject(_event);
        validateObject(_event.getEventType());
        validateObject(_event.getProvider());
        
    }
    
    
    /**
     * Method validationObject.
     * 
     * @param _object
     *            T
     */
    @Override
    public <T> void validateObject(final T _object) {
    
    
        validator.validateObject(_object);
    }
}
