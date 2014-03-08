package org.komea.product.backend.service.entities;

import java.util.List;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.dto.BaseEntity;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.dto.TeamDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.PersonGroupCriteria;

/**
 * Komea service to manage person groupŝ
 * <p>
 *
 * @author $Author: jguidoux $
 * @since 12 févr. 2014
 * @version $Revision: 1.0 $
 */
public interface IPersonGroupService extends
        IGenericService<PersonGroup, Integer, PersonGroupCriteria> {

    /**
     * This method list all departments
     *
     * @return the departments list
     */
    List<PersonGroup> getAllDepartmentsPG();

    /**
     * This method list all teams
     *
     * @return the team list
     */
    List<PersonGroup> getAllTeamsPG();

    /**
     * This method list all departments
     *
     * @return the departments list
     */
    List<DepartmentDto> getAllDepartments();

    /**
     * This method list all teams
     *
     * @return the team list
     */
    List<TeamDto> getAllTeams();

    /**
     * This method get a department from a group. if the group is a department,
     * it will be return otherwise it will find it in the parent group
     *
     * @param _groupID a group id
     * @return the department
     */
    PersonGroup getDepartment(Integer _groupID);

    /**
     * Method getPersonGroups.
     *
     * @param personGroupKeys List<String>
     * @param entityType EntityType
     * @return List<PersonGroup>
     */
    List<PersonGroup> searchPersonGroups(List<String> personGroupKeys, EntityType entityType);

    /**
     * This method get a team from a group. if the group is a team, it will be
     * return otherwise it will find it in the parent group
     *
     * @param _groupID a group id
     * @return the team
     */
    PersonGroup getTeam(Integer _groupID);

    /**
     * Method personGroupsToBaseEntities.
     *
     * @param personGroups List<PersonGroup>
     * @param entityType EntityType
     * @return List<BaseEntity>
     */
    List<BaseEntity> convertPersonGroupsToBaseEntities(
            List<PersonGroup> personGroups,
            EntityType entityType);

    /**
     * get sub personGroups of a personGroup
     *
     * @param groupId id of the parent personGroup
     * @return list of personGroups
     */
    List<PersonGroup> getChildren(Integer groupId);

    /**
     * get parent personGroup of a personGroup
     *
     * @param groupId id of the child personGroup
     * @return parent personGroup
     */
    PersonGroup getParent(Integer groupId);

    /**
     * get teams of a project
     *
     * @param _projectId id of the project
     * @return list of teams
     */
    List<PersonGroup> getTeamsOfProject(Integer _projectId);

}
