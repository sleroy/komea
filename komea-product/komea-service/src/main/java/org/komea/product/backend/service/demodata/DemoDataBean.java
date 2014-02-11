
package org.komea.product.backend.service.demodata;


import java.util.Random;

import javax.annotation.PostConstruct;

import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.alert.EventDtoBuilder;
import org.komea.product.database.dao.EventTypeDao;
import org.komea.product.database.dao.GroupKindDao;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.EventTypeCriteria;
import org.komea.product.database.model.GroupKind;
import org.komea.product.database.model.GroupKindCriteria;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonGroupCriteria;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.PersonRoleCriteria;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.ProviderCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoDataBean {
    
    @Autowired
    private IEventPushService eventPushService;
    
    @Autowired
    private PersonDao         personDAO;
    
    @Autowired
    private PersonRoleDao     personRoleDao;
    
    @Autowired
    private GroupKindDao      groupKindDAO;
    
    @Autowired
    private PersonGroupDao    personGroupDao;
    
    @Autowired
    private ProviderDao       providerDao;
    
    @Autowired
    private EventTypeDao      eventTypeDAO;
    
    public DemoDataBean() {
    
        super();
    }
    
    public GroupKindDao getGroupKindDAO() {
    
        return groupKindDAO;
    }
    
    public PersonGroupDao getPersonGroupDao() {
    
        return personGroupDao;
    }
    
    public PersonRoleDao getPersonRoleDao() {
    
        return personRoleDao;
    }
    
    @PostConstruct
    public void init() {
    
        PersonRole administrator = new PersonRole(null, "Administrator");
        PersonRoleCriteria prCriteria = new PersonRoleCriteria();
        prCriteria.createCriteria().andNameEqualTo("Administrator");
        if (personRoleDao.countByCriteria(prCriteria) == 0) {
            personRoleDao.insert(administrator);
        }
        
        final PersonRole userRole = new PersonRole(null, "Standard user");
        prCriteria = new PersonRoleCriteria();
        prCriteria.createCriteria().andNameEqualTo("Standard user");
        if (personRoleDao.countByCriteria(prCriteria) == 0) {
            personRoleDao.insert(userRole);
        }
        
        final Person record = new Person(null, null, null, "Obiwan", "Kenobi", "obiwan@lightforce.net", "obiwan", "");
        record.setIdPersonRole(userRole.getId());
        PersonCriteria pCriteria = new PersonCriteria();
        pCriteria.createCriteria().andLoginEqualTo("obiwan");
        if (personDAO.countByCriteria(pCriteria) == 0) {
            personDAO.insert(record);
            
        }
        
        final Person record2 = new Person(null, null, null, "Dark", "Maul", "darkmaul@darkforce.net", "dmaul", "");
        record2.setIdPersonRole(userRole.getId());
        pCriteria = new PersonCriteria();
        pCriteria.createCriteria().andLoginEqualTo("dmaul");
        if (personDAO.countByCriteria(pCriteria) == 0) {
            personDAO.insert(record2);
            
        }
        
        final Person record3 = new Person(null, null, null, "Luke", "Skywalker", "lskywalker@lightforce.net", "lskywalker", "");
        record3.setIdPersonRole(userRole.getId());
        pCriteria = new PersonCriteria();
        pCriteria.createCriteria().andLoginEqualTo("lskywalker");
        if (personDAO.countByCriteria(pCriteria) == 0) {
            personDAO.insert(record3);
            
        }
        
        final GroupKind departmentKind = new GroupKind(null, "Department");
        GroupKindCriteria gkCriteria = new GroupKindCriteria();
        gkCriteria.createCriteria().andNameEqualTo("Department");
        if (groupKindDAO.countByCriteria(gkCriteria) == 0) {
            groupKindDAO.insert(departmentKind);
        }
        
        final GroupKind teamKind = new GroupKind(null, "Team");
        gkCriteria = new GroupKindCriteria();
        gkCriteria.createCriteria().andNameEqualTo("Team");
        if (groupKindDAO.countByCriteria(gkCriteria) == 0) {
            groupKindDAO.insert(teamKind);
        }
        
        final PersonGroup department = new PersonGroup();
        department.setName("Department ABC");
        department.setDescription("Example of Department");
        department.setPersonGroupKey("DEPARTMENT_ABC");
        department.setIdGroupKind(departmentKind.getId());
        department.setIdPersonGroupParent(null);
        department.setDepth(1);
        PersonGroupCriteria pgCriteria = new PersonGroupCriteria();
        pgCriteria.createCriteria().andPersonGroupKeyEqualTo("DEPARTMENT_ABC");
        if (personGroupDao.countByCriteria(pgCriteria) == 0) {
            personGroupDao.insert(department);
        }
        
        final PersonGroup team = new PersonGroup();
        team.setName("Team 1");
        team.setDescription("Example of Team");
        team.setPersonGroupKey("TEAMA");
        team.setIdGroupKind(teamKind.getId());
        team.setIdPersonGroupParent(department.getId());
        team.setDepth(2);
        pgCriteria = new PersonGroupCriteria();
        pgCriteria.createCriteria().andPersonGroupKeyEqualTo("TEAMA");
        if (personGroupDao.countByCriteria(pgCriteria) == 0) {
            personGroupDao.insert(team);
        }
        
        final PersonGroup team2 = new PersonGroup();
        team2.setName("Team 2");
        team2.setDescription("Example of Team");
        team2.setPersonGroupKey("TEAMB");
        team2.setIdGroupKind(teamKind.getId());
        team2.setIdPersonGroupParent(department.getId());
        team2.setDepth(2);
        pgCriteria = new PersonGroupCriteria();
        pgCriteria.createCriteria().andPersonGroupKeyEqualTo("TEAMB");
        if (personGroupDao.countByCriteria(pgCriteria) == 0) {
            personGroupDao.insert(team2);
        }
        
        Provider provider = new Provider();
        provider.setIcon("icon.png");
        provider.setName("jenkins");
        provider.setUrl("http://komea.tocea.com/jenkins");
        provider.setProviderType(ProviderType.JENKINS);
        
        ProviderCriteria criteria = new ProviderCriteria();
        criteria.createCriteria().andNameEqualTo("jenkins");
        if (providerDao.countByCriteria(criteria) == 0) {
            
            providerDao.insert(provider);
        }
        
        EventType eventType = new EventType();
        eventType.setCategory("Build step");
        eventType.setDescription("a build has been launched");
        eventType.setEnabled(true);
        eventType.setEventKey("BUILD_LAUNCHED");
        eventType.setEntityType(EntityType.PROJECT);
        eventType.setIdProvider(provider.getId());
        eventType.setName("build launched");
        eventType.setSeverity(Severity.INFO);
        
        EventTypeCriteria criteria2 = new EventTypeCriteria();
        criteria2.createCriteria().andEventKeyEqualTo("BUILD_LAUNCHED");
        if (eventTypeDAO.countByCriteria(criteria2) == 0) {
            
            eventTypeDAO.insert(eventType);
        }
        
        provider = new Provider();
        provider.setIcon("icon2.png");
        provider.setName("DEMO");
        provider.setUrl("http://komea.tocea.com/demo");
        provider.setProviderType(ProviderType.JENKINS);
        
        ProviderCriteria criteria3 = new ProviderCriteria();
        criteria3.createCriteria().andNameEqualTo("DEMO");
        if (providerDao.countByCriteria(criteria3) == 0) {
            
            providerDao.insert(provider);
        }
        
        eventType = new EventType();
        eventType.setCategory("DEMO");
        eventType.setDescription("demo alert");
        eventType.setEnabled(true);
        eventType.setEventKey("demo_alert");
        eventType.setEntityType(EntityType.PROJECT);
        eventType.setIdProvider(provider.getId());
        eventType.setName("demoAlert");
        eventType.setSeverity(Severity.MAJOR);
        EventTypeCriteria criteria4 = new EventTypeCriteria();
        criteria4.createCriteria().andEventKeyEqualTo("demo_alert");
        if (eventTypeDAO.countByCriteria(criteria4) == 0) {
            
            eventTypeDAO.insert(eventType);
        }
        
    }
    @Scheduled(fixedRate = 10000)
    public void sendAlert() {
    
        for (int i = 0; i < 10; ++i) {
            final EventSimpleDto event = EventDtoBuilder.newAlert().message("Demo alert" + new Random().nextInt(12)).project("SYSTEM")
                    .provided("DEMO").eventType("demo_alert").build();
            eventPushService.sendEventDto(event);
            
        }
    }
    
    public void setGroupKindDAO(final GroupKindDao _groupKindDAO) {
    
        groupKindDAO = _groupKindDAO;
    }
    
    public void setPersonGroupDao(final PersonGroupDao _personGroupDao) {
    
        personGroupDao = _personGroupDao;
    }
    
    public void setPersonRoleDao(final PersonRoleDao _personRoleDao) {
    
        personRoleDao = _personRoleDao;
    }
    
}
