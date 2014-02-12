
package org.komea.product.backend.service.entities;


import java.util.List;

import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.dto.TeamDto;
import org.komea.product.database.model.PersonGroup;

/**
 * Komea service to manage person groupŝ
 * <p>
 * 
 * @author $Author: jguidoux $
 * @since 12 févr. 2014
 */
public interface IPersonGroupService {
    
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
     * This method get the list of depaertment associate to an person
     * 
     * @param _personID
     *            a person
     * @return the departments list
     */
    PersonGroup getDepartment(Integer _personID);
    
}
