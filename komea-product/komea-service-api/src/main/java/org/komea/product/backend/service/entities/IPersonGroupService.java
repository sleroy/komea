package org.komea.product.backend.service.entities;

import java.util.List;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.dto.TeamDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.PersonGroup;

/**
 * Komea service to manage person groupŝ
 * <p>
 *
 * @author $Author: jguidoux $
 * @since 12 févr. 2014
 * @version $Revision: 1.0 $
 */
public interface IPersonGroupService {

    /**
     * This method list all departments
     *
    
     * @return the departments list */
    List<DepartmentDto> getAllDepartments();

    /**
     * This method list all teams
     *
    
     * @return the team list */
    List<TeamDto> getAllTeams();

    /**
     * This method get a department from a group. if the group is a department,
     * it will be return otherwise it will find it in the parent group
     *
     * @param _groupID a group id
    
     * @return the department */
    PersonGroup getDepartment(Integer _groupID);

    /**
     * This method get a team from a group. if the group is a team, it will be
     * return otherwise it will find it in the parent group
     *
     * @param _groupID a group id
    
     * @return the team */
    PersonGroup getTeam(Integer _groupID);

    /**
     * Method getPersonGroups.
     * @param personGroupKeys List<String>
     * @param entityType EntityType
     * @return List<PersonGroup>
     */
    List<PersonGroup> getPersonGroups(List<String> personGroupKeys, EntityType entityType);

    /**
     * Method personGroupsToBaseEntities.
     * @param personGroups List<PersonGroup>
     * @param entityType EntityType
     * @return List<BaseEntity>
     */
    List<BaseEntity> personGroupsToBaseEntities(List<PersonGroup> personGroups, EntityType entityType);

}
