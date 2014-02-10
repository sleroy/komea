
package org.komea.product.backend.service.demodata;



import java.util.Random;

import javax.annotation.PostConstruct;

import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.alert.EventDtoBuilder;
import org.komea.product.database.dao.GroupKindDao;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.dao.PersonGroupDao;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.GroupKind;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class DemoDataBean
{
    
    
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
    
    
        personRoleDao.insert(new PersonRole(null, "Administrator"));
        final PersonRole userRole = new PersonRole(null, "Standard user");
        personRoleDao.insert(userRole);
        
        
        final Person record =
                new Person(null, null, null, "Obiwan", "Kenobi", "obiwan@lightforce.net", "obiwan");
        record.setIdPersonRole(userRole.getId());
        personDAO.insert(record);
        
        final Person record2 =
                new Person(null, null, null, "Dark", "Maul", "darkmaul@darkforce.net", "dmaul");
        record2.setIdPersonRole(userRole.getId());
        personDAO.insert(record2);
        
        final Person record3 =
                new Person(null, null, null, "Luke", "Skywalker", "lskywalker@lightforce.net",
                        "lskywalker");
        record3.setIdPersonRole(userRole.getId());
        personDAO.insert(record3);
        
        final GroupKind departmentKind = new GroupKind(null, "Department");
        groupKindDAO.insert(departmentKind);
        
        final GroupKind teamKind = new GroupKind(null, "Team");
        groupKindDAO.insert(teamKind);
        
        
        final PersonGroup department = new PersonGroup();
        department.setName("Department ABC");
        department.setDescription("Example of Department");
        department.setPersonGroupKey("DEPARTMENT_ABC");
        department.setIdGroupKind(departmentKind.getId());
        department.setIdPersonGroupParent(null);
        personGroupDao.insert(department);
        
        
        final PersonGroup team = new PersonGroup();
        team.setName("Team 1");
        team.setDescription("Example of Team");
        team.setPersonGroupKey("TEAMA");
        team.setIdGroupKind(teamKind.getId());
        team.setIdPersonGroupParent(department.getId());
        personGroupDao.insert(team);
        
        
        final PersonGroup team2 = new PersonGroup();
        team2.setName("Team 2");
        team2.setDescription("Example of Team");
        team2.setPersonGroupKey("TEAMB");
        team2.setIdGroupKind(teamKind.getId());
        team2.setIdPersonGroupParent(department.getId());
        personGroupDao.insert(team2);
    }
    
    
    @Scheduled(fixedRate = 10000)
    public void sendAlert() {
    
    
        for (int i = 0; i < 10; ++i) {
            final EventSimpleDto event =
                    EventDtoBuilder.newAlert().message("Demo alert").project("SYSTEM")
                            .provided("DEMO" + new Random().nextInt(12)).eventType("demo_alert")
                            .build();
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
