
package org.komea.product.backend.service.demodata;



import java.util.Random;

import javax.annotation.PostConstruct;

import org.komea.product.backend.auth.IPasswordEncoder;
import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.alert.EventDtoBuilder;
import org.komea.product.database.dao.EventTypeDao;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dao.ProviderDao;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.PersonGroupType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.EventTypeCriteria;
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



@ProviderPlugin(
        eventTypes = {
            @EventTypeDef(
                    category = "SCM",
                    description = "",
                    enabled = true,
                    entityType = EntityType.SYSTEM,
                    key = "event-demo",
                    name = "Demonstration event",
                    severity = Severity.MAJOR) },
        icon = "demo",
        name = "Demo Provider plugin",
        type = ProviderType.OTHER,
        url = "/demoProvider")
public class DemoDataBean
{
    
    
    @Autowired
    private IEventPushService eventPushService;
    
    @Autowired
    private PersonDao         personDAO;
    
    @Autowired
    private PersonRoleDao     personRoleDao;
    
    @Autowired
    private PersonGroupDao    personGroupDao;
    
    @Autowired
    private ProviderDao       providerDao;
    
    @Autowired
    private EventTypeDao      eventTypeDAO;
    
    
    @Autowired
    private IPasswordEncoder  encoder;
    
    
    
    public DemoDataBean() {
    
    
        super();
    }
    
    
    public PersonGroupDao getPersonGroupDao() {
    
    
        return personGroupDao;
    }
    
    
    public PersonRoleDao getPersonRoleDao() {
    
    
        return personRoleDao;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        final PersonRole administrator = new PersonRole(null, "ADMIN", "Administrator");
        PersonRoleCriteria prCriteria = new PersonRoleCriteria();
        prCriteria.createCriteria().andNameEqualTo("Administrator");
        if (personRoleDao.countByCriteria(prCriteria) == 0) {
            personRoleDao.insert(administrator);
        }
        
        
        final PersonRole userRole = new PersonRole(null, "ADMIN", "Standard user");
        prCriteria = new PersonRoleCriteria();
        prCriteria.createCriteria().andNameEqualTo("Standard user");
        if (personRoleDao.countByCriteria(prCriteria) == 0) {
            personRoleDao.insert(userRole);
        }
        
        
        final Person obiwan =
                new Person(null, null, null, "Obiwan", "Kenobi", "obiwan@lightforce.net", "obiwan",
                        encoder.encodePassword("obiwan"));
        
        createUser(obiwan, userRole);
        
        final Person admin =
                new Person(null, null, null, "admin", "admin", "admiweb@tocea.com", "admin",
                        encoder.encodePassword("admin"));
        createUser(admin, administrator);
        
        final Person record2 =
                new Person(null, null, null, "Dark", "Maul", "darkmaul@darkforce.net", "dmaul",
                        encoder.encodePassword("dmaul"));
        createUser(record2, userRole);
        
        final Person record3 =
                new Person(null, null, null, "Luke", "Skywalker", "lskywalker@lightforce.net",
                        "lskywalker", encoder.encodePassword("lskywalker"));
        createUser(record3, userRole);
        
        final PersonGroup department = new PersonGroup();
        department.setName("Department ABC");
        department.setDescription("Example of Department");
        department.setPersonGroupKey("DEPARTMENT_ABC");
        department.setIdPersonGroupParent(null);
        department.setType(PersonGroupType.DEPARTMENT);
        ;
        PersonGroupCriteria pgCriteria = new PersonGroupCriteria();
        pgCriteria.createCriteria().andPersonGroupKeyEqualTo("DEPARTMENT_ABC");
        if (personGroupDao.countByCriteria(pgCriteria) == 0) {
            personGroupDao.insert(department);
        }
        
        final PersonGroup team = new PersonGroup();
        team.setName("Team 1");
        team.setDescription("Example of Team");
        team.setPersonGroupKey("TEAMA");
        team.setIdPersonGroupParent(department.getId());
        team.setType(PersonGroupType.TEAM);
        pgCriteria = new PersonGroupCriteria();
        pgCriteria.createCriteria().andPersonGroupKeyEqualTo("TEAMA");
        if (personGroupDao.countByCriteria(pgCriteria) == 0) {
            personGroupDao.insert(team);
        }
        
        final PersonGroup team2 = new PersonGroup();
        team2.setName("Team 2");
        team2.setDescription("Example of Team");
        team2.setPersonGroupKey("TEAMB");
        team2.setIdPersonGroupParent(department.getId());
        team2.setType(PersonGroupType.TEAM);
        pgCriteria = new PersonGroupCriteria();
        pgCriteria.createCriteria().andPersonGroupKeyEqualTo("TEAMB");
        if (personGroupDao.countByCriteria(pgCriteria) == 0) {
            personGroupDao.insert(team2);
        }
        
        Provider provider = new Provider();
        provider.setIcon("icon.png");
        provider.setName("jenkins");
        provider.setUrl("http://komea.tocea.com/jenkins");
        provider.setProviderType(ProviderType.CI_BUILD);
        
        final ProviderCriteria criteria = new ProviderCriteria();
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
        
        final EventTypeCriteria criteria2 = new EventTypeCriteria();
        criteria2.createCriteria().andEventKeyEqualTo("BUILD_LAUNCHED");
        if (eventTypeDAO.countByCriteria(criteria2) == 0) {
            
            eventTypeDAO.insert(eventType);
        }
        
        provider = new Provider();
        provider.setIcon("icon2.png");
        provider.setName("DEMO");
        provider.setUrl("http://komea.tocea.com/demo");
        provider.setProviderType(ProviderType.CI_BUILD);
        
        final ProviderCriteria criteria3 = new ProviderCriteria();
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
        final EventTypeCriteria criteria4 = new EventTypeCriteria();
        criteria4.createCriteria().andEventKeyEqualTo("demo_alert");
        if (eventTypeDAO.countByCriteria(criteria4) == 0) {
            
            eventTypeDAO.insert(eventType);
        }
        
    }
    
    
    @Scheduled(fixedRate = 10000)
    public void sendAlert() {
    
    
        for (int i = 0; i < 10; ++i) {
            final EventSimpleDto event =
                    EventDtoBuilder
                            .newAlert()
                            .message(
                                    "Event sent by Demonstration DemoDataBean"
                                            + new Random().nextInt(12)).project("SYSTEM")
                            .provided("/demoProvider").eventType("event-demo").build();
            eventPushService.sendEventDto(event);
            
        }
    }
    
    
    public void setPersonGroupDao(final PersonGroupDao _personGroupDao) {
    
    
        personGroupDao = _personGroupDao;
    }
    
    
    public void setPersonRoleDao(final PersonRoleDao _personRoleDao) {
    
    
        personRoleDao = _personRoleDao;
    }
    
    
    private Person createUser(final Person record, final PersonRole userRole) {
    
    
        record.setIdPersonRole(userRole.getId());
        final PersonCriteria pCriteria = new PersonCriteria();
        pCriteria.createCriteria().andLoginEqualTo(record.getLogin());
        if (personDAO.countByCriteria(pCriteria) == 0) {
            personDAO.insert(record);
            
        }
        return record;
    }
    
}
