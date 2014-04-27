/**
 * 
 */

package org.komea.product.plugins.scm.kpi;



import javax.annotation.PostConstruct;

import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author sleroy
 */
@Service
public class ScmKpiPlugin implements IScmKpiPlugin
{
    
    
    /**
     * 
     */
    private static final Kpi BUILD6 = KpiBuilder.createAscending()
            .nameAndKeyDescription("Number total of modified lines per user")
            .providerType(ProviderType.SCM).entityType(EntityType.PERSON).expirationYear()
            .query(ScmQueryImplementation.class).cronDays(1).build();
    /**
     * 
     */
    private static final Kpi BUILD5 = KpiBuilder.createAscending()
            .nameAndKeyDescription("Number of changed lines per day per user")
            .providerType(ProviderType.SCM).entityType(EntityType.PERSON).expirationYear()
            .query(ScmQueryImplementation.class).cronDays(1).build();
    /**
     * 
     */
    private static final Kpi BUILD4 = KpiBuilder.createAscending()
            .nameAndKeyDescription("Number of deleted lines per day per user")
            .providerType(ProviderType.SCM).entityType(EntityType.PERSON).expirationYear()
            .query(ScmQueryImplementation.class).cronDays(1).build();
    /**
     * 
     */
    private static final Kpi BUILD3 = KpiBuilder.createAscending()
            .nameAndKeyDescription("Number of commits per day per user")
            .providerType(ProviderType.SCM).entityType(EntityType.PERSON).expirationYear()
            .query(ScmQueryImplementation.class).cronDays(1).build();
    /**
     * 
     */
    private static final Kpi BUILD2 = KpiBuilder.createAscending().nameAndKeyDescription("Number of added lines per user")
            .providerType(ProviderType.SCM).entityType(EntityType.PERSON).expirationYear()
            .query(ScmQueryImplementation.class).cronDays(1).build();
    /**
     * 
     */
    private static final Kpi BUILD = KpiBuilder.createAscending()
            .nameAndKeyDescription("Average commit message length per user")
            .providerType(ProviderType.SCM).entityType(EntityType.PERSON).expirationYear()
            .query(ScmQueryImplementation.class).cronDays(1).build();
    @Autowired
    private IKPIService kpiService;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.kpi.IScmKpiPlugin#averageCommitMessageLength()
     */
    @Override
    public Kpi averageCommitMessageLength() {
    
    
        return BUILD;
        
    }
    
    
    @Transactional
    @PostConstruct
    public void initPlugin() {
    
    
        saveOrUpdate(averageCommitMessageLength());
        saveOrUpdate(numberOfAddedLinesPerUser());
        saveOrUpdate(numberOfChangedLinesPerDayPerUser());
        saveOrUpdate(numberOfCommitsPerDayPerUser());
        saveOrUpdate(numberofDeletedLinesPerDayPerUser());
        saveOrUpdate(numberTotalOfModifiedLinesPerUser());
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.kpi.IScmKpiPlugin#numberOfAddedLinesPerUser()
     */
    @Override
    public Kpi numberOfAddedLinesPerUser() {
    
    
        return BUILD2;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.kpi.IScmKpiPlugin#numberOfChangedLinesPerDayPerUser()
     */
    @Override
    public Kpi numberOfChangedLinesPerDayPerUser() {
    
    
        return BUILD5;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.kpi.IScmKpiPlugin#numberOfCommitsPerDayPerUser()
     */
    @Override
    public Kpi numberOfCommitsPerDayPerUser() {
    
    
        return BUILD3;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.kpi.IScmKpiPlugin#numberofDeletedLinesPerDayPerUser()
     */
    @Override
    public Kpi numberofDeletedLinesPerDayPerUser() {
    
    
        return BUILD4;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.kpi.IScmKpiPlugin#numberTotalOfModifiedLinesPerUser()
     */
    @Override
    public Kpi numberTotalOfModifiedLinesPerUser() {
    
    
        return BUILD6;
        
    }
    
    
    /**
     * @param _numberSuccessBuildPerDay
     */
    private void saveOrUpdate(final Kpi _kpi) {
    
    
        if (kpiService.findKPI(KpiKey.ofKpi(_kpi)) != null) { return; }
        kpiService.saveOrUpdate(_kpi);
        
    }
}
