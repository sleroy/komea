
package org.komea.product.backend.service.entities;



import org.komea.product.database.dao.HasProjectPersonDao;
import org.komea.product.database.model.HasProjectPersonCriteria;
import org.komea.product.database.model.HasProjectPersonKey;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.espertech.esper.client.annotation.Audit;



/**
 */
@Service
@Transactional
public class ProjectPersonService implements IProjectPersonService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectPersonService.class);
    
    @Audit
    private HasProjectPersonDao projectPersonDAO;
    
    
    
    @Override
    @Transactional
    public void updatePersonToProjectLink(final Project _project, final Person _person) {
    
    
        LOGGER.debug("Update project {} person {} now is linked ");
        final HasProjectPersonCriteria hasProjectPersonCriteria = new HasProjectPersonCriteria();
        hasProjectPersonCriteria.createCriteria().andIdPersonEqualTo(_person.getId());
        projectPersonDAO.deleteByCriteria(hasProjectPersonCriteria);
        if (_project != null) {
            projectPersonDAO.insert(new HasProjectPersonKey(_project.getId(), _person.getId()));
        }
    }
}
