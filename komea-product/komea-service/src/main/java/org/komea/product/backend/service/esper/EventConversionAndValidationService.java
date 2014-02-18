package org.komea.product.backend.service.esper;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.komea.product.backend.exceptions.KomeaConstraintViolationException;
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
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonGroupCriteria;
import org.komea.product.database.model.ProjectCriteria;
import org.komea.product.database.model.ProviderCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventConversionAndValidationService implements IEventConversionAndValidationService {

    @Autowired
    private PersonDao personDAO;

    @Autowired
    private PersonGroupDao personGroupDAO;

    @Autowired
    private ProjectDao projectDAO;
    @Autowired
    private ProviderDao providerDAO;
    @Autowired
    private EventTypeDao eventTypeDAO;

    private Validator validator;

    @Override
    public IEvent convert(final EventSimpleDto _dto) {

        final Event event = new Event();
        event.setDate(_dto.getDate());

        final EventTypeCriteria eventTypeCriteria = new EventTypeCriteria();
        eventTypeCriteria.createCriteria().andEventKeyEqualTo(_dto.getEventType());
        event.setEventType(CollectionUtil.singleOrNull(
                eventTypeDAO.selectByCriteria(eventTypeCriteria)));

        event.setMessage(_dto.getMessage());

        final String personGroupKey = _dto.getPersonGroup();
        if (personGroupKey != null) {
            final PersonGroupCriteria personGroupCriteria = new PersonGroupCriteria();
            personGroupCriteria.createCriteria().andPersonGroupKeyEqualTo(personGroupKey);
            event.setPersonGroup(CollectionUtil.singleOrNull(
                    personGroupDAO.selectByCriteria(personGroupCriteria)));
        }

        event.setProperties(_dto.getProperties());
        event.setUrl(_dto.getUrl());
        event.setValue(_dto.getValue());

        final String projectKey = _dto.getProject();
        if (projectKey != null) {
            final ProjectCriteria projectCriteria = new ProjectCriteria();
            projectCriteria.createCriteria().andProjectKeyEqualTo(projectKey);
            event.setProject(CollectionUtil.singleOrNull(
                    projectDAO.selectByCriteria(projectCriteria)));
        }

        final ProviderCriteria providerCriteria = new ProviderCriteria();
        providerCriteria.createCriteria().andUrlEqualTo(_dto.getProvider());
        event.setProvider(CollectionUtil.singleOrNull(
                providerDAO.selectByCriteria(providerCriteria)));

        if (!_dto.getPersons().isEmpty()) {
            final PersonCriteria personCriteria = new PersonCriteria();
            for (final String login : _dto.getPersons()) {
                personCriteria.or().andLoginEqualTo(login);
            }
            event.setPersons(personDAO.selectByCriteria(personCriteria));
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

    @PostConstruct
    public void initialize() {

        validator = Validation.buildDefaultValidatorFactory().getValidator();

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

        validationObject(_event);
        validationObject(_event.getEventType());
        validationObject(_event.getProvider());

    }

    public <T> void validationObject(final T _object) {

        final Set<ConstraintViolation<T>> constraintViolationException
                = validator.validate(_object);
        if (!constraintViolationException.isEmpty()) {
            throw new KomeaConstraintViolationException(
                    new HashSet<ConstraintViolation<?>>(constraintViolationException));
        }
    }

}
