/**
 *
 */

package org.komea.product.backend.service.standardkpi;


import javax.annotation.PostConstruct;

import org.komea.product.backend.api.standardkpi.IStandardKpiService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.model.Kpi;
import org.komea.product.plugins.kpi.standard.management.NumberOfProjectsPerPerson;
import org.komea.product.plugins.kpi.standard.management.NumberOfUsersPerProject;
import org.komea.product.service.dto.KpiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sleroy
 */

@Service
@Transactional
public class StandardKpiService implements IStandardKpiService {
    
    @Autowired
    private IKPIService kpiService;
    
    /**
     *
     */
    public StandardKpiService() {
    
        super();
        
    }
    
    @PostConstruct
    public void init() {
    
        saveOrUpdate(numberOfProjectPerUser());
        saveOrUpdate(numberOfUsersPerProject());
        
    }
    
    public Kpi numberOfProjectPerUser() {
    
        return KpiBuilder.createAscending().nameAndKey("Users per project")
                .description("This kpi defines the number of users involved in a project.").providerType(ProviderType.MANAGEMENT)
                .entityType(EntityType.PERSON).interval(0d, 100d).dynamicQuery(NumberOfProjectsPerPerson.class).build();
        
    }
    
    public Kpi numberOfUsersPerProject() {
    
        return KpiBuilder.createAscending().nameAndKey("Projects per user")
                .description("This kpi defines the number of projects a member is participating.").providerType(ProviderType.MANAGEMENT)
                .entityType(EntityType.PERSON).interval(0d, 100d).dynamicQuery(NumberOfUsersPerProject.class).build();
        
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.standardkpi.IStandardKpiService#saveOrUpdate(org.komea.product.database.model.Kpi)
     */
    @Override
    public void saveOrUpdate(final Kpi _kpi) {
    
        if (kpiService.findKPI(KpiKey.ofKpi(_kpi)) != null) {
            return;
        }
        kpiService.saveOrUpdate(_kpi);
        
    }
    
}
