package org.komea.product.backend.service.entities;

import java.util.ArrayList;
import java.util.List;
import org.komea.product.database.dao.HasProjectPersonGroupDao;
import org.komea.product.database.model.HasProjectPersonGroupCriteria;
import org.komea.product.database.model.HasProjectPersonGroupKey;
import org.komea.product.database.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectPersonGroupService implements IProjectPersonGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectPersonGroupService.class);

    @Autowired
    private HasProjectPersonGroupDao projectPersonGroupDao;

    @Autowired
    private ProjectService projectService;

    @Override
    public List<Project> getProjectsAssociateToPersonGroup(int _personGroupId) {
        final HasProjectPersonGroupCriteria criteria = new HasProjectPersonGroupCriteria();
        criteria.createCriteria().andIdPersonGroupEqualTo(_personGroupId);
        final List<HasProjectPersonGroupKey> selectByCriteria = projectPersonGroupDao.selectByCriteria(criteria);
        final List<Integer> projectIds = new ArrayList<Integer>(selectByCriteria.size());
        for (final HasProjectPersonGroupKey projectPersonGroup : selectByCriteria) {
            projectIds.add(projectPersonGroup.getIdProject());
        }
        return projectService.selectByPrimaryKeyList(projectIds);
    }

}
