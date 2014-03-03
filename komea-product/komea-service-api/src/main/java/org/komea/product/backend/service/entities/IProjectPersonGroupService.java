package org.komea.product.backend.service.entities;

import java.util.List;
import org.komea.product.database.model.Project;

public interface IProjectPersonGroupService {

    /**
     * get projects of a person group
     *
     * @param _personGroupId id of the person group
     * @return projects
     */
    List<Project> getProjectsAssociateToPersonGroup(int _personGroupId);

}
