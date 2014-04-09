package org.komea.product.web.rest.api;

import java.util.Collections;
import java.util.List;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.enums.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/entities")
public class EntitiesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntitiesController.class);

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IPersonGroupService personGroupService;

    @RequestMapping(method = RequestMethod.POST, value = "/type")
    @ResponseBody
    public List<BaseEntityDto> getEntities(@RequestBody EntityType entityType) {

        switch (entityType) {
            case DEPARTMENT:
                return personGroupService.convertPersonGroupsToBaseEntities(
                        personGroupService.getAllDepartmentsPG(), EntityType.DEPARTMENT);
            case TEAM:
                return personGroupService.convertPersonGroupsToBaseEntities(
                        personGroupService.getAllTeamsPG(), EntityType.TEAM);
            case PROJECT:
                return projectService.projectsToBaseEntities(projectService.selectAll());
            case PERSON:
                return personService.convertPersonsToBaseEntities(personService.selectAll());
        }
        return Collections.EMPTY_LIST;
    }

}
