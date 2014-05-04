/**
 * 
 */

package org.komea.product.plugins.scm;



import javax.annotation.PostConstruct;

import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.model.Kpi;
import org.komea.product.plugins.scm.api.IScmKpiPlugin;
import org.komea.product.plugins.scm.kpi.functions.AverageCommitMessageLength;
import org.komea.product.plugins.scm.kpi.functions.NumberOfAddedLinesPerDay;
import org.komea.product.plugins.scm.kpi.functions.NumberOfCommitsPerDay;
import org.komea.product.plugins.scm.kpi.functions.NumberOfDeletedLinesPerDay;
import org.komea.product.plugins.scm.kpi.functions.NumberOfModifiedFilesPerDay;
import org.komea.product.plugins.scm.kpi.functions.NumberOfModifiedLinesPerDay;
import org.komea.product.plugins.scm.kpi.functions.TotalNumberOfModifiedLinesPerDay;
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
    private static final Kpi BUILD  = KpiBuilder
                                            .createAscending()
                                            .nameAndKeyDescription(
                                                    "Average commit message length per user")
                                            .providerType(ProviderType.SCM)
                                            .entityType(EntityType.PERSON).expirationYear()
                                            .interval(0d, 10000d)
                                            .query(AverageCommitMessageLength.class).cronDays(1)
                                            .build();
    /**
     * 
     */
    private static final Kpi BUILD2 =
                                            KpiBuilder
                                                    .createAscending()
                                                    .nameAndKeyDescription(
                                                            "Number of added lines per user")
                                                    .providerType(ProviderType.SCM)
                                                    .entityType(EntityType.PERSON).expirationYear()
                                                    .query(NumberOfAddedLinesPerDay.class)
                                                    .interval(0d, 1000000d).cronDays(1).build();
    /**
     * 
     */
    private static final Kpi BUILD3 = KpiBuilder
                                            .createAscending()
                                            .nameAndKeyDescription(
                                                    "Number of commits per day per user")
                                            .providerType(ProviderType.SCM)
                                            .entityType(EntityType.PERSON).expirationYear()
                                            .interval(0d, 25 * 60d)
                                            .query(NumberOfCommitsPerDay.class).cronDays(1).build();
    /**
     * 
     */
    private static final Kpi BUILD4 = KpiBuilder
                                            .createAscending()
                                            .nameAndKeyDescription(
                                                    "Number of deleted lines per day per user")
                                            .providerType(ProviderType.SCM)
                                            .entityType(EntityType.PERSON).expirationYear()
                                            .interval(0d, 1000000d)
                                            .query(NumberOfDeletedLinesPerDay.class).cronDays(1)
                                            .build();
    /**
     * 
     */
    private static final Kpi BUILD5 = KpiBuilder
                                            .createAscending()
                                            .nameAndKeyDescription(
                                                    "Number of changed lines per day per user")
                                            .providerType(ProviderType.SCM)
                                            .entityType(EntityType.PERSON).expirationYear()
                                            .interval(0d, 1000000d)
                                            .query(NumberOfModifiedLinesPerDay.class).cronDays(1)
                                            .build();
    private static final Kpi BUILD6 = KpiBuilder
                                            .createAscending()
                                            .nameAndKeyDescription(
                                                    "Number total of modified lines per user")
                                            .providerType(ProviderType.SCM).interval(0d, 1000000d)
                                            .entityType(EntityType.PERSON).expirationYear()
                                            .query(TotalNumberOfModifiedLinesPerDay.class)
                                            .cronDays(1).build();
    private static final Kpi BUILD7 = KpiBuilder
                                            .createAscending()
                                            .nameAndKeyDescription(
                                                    "Number total of modified files per user")
                                            .providerType(ProviderType.SCM)
                                            .entityType(EntityType.PERSON).expirationYear()
                                            .interval(0d, 1000000d)
                                            .query(NumberOfModifiedFilesPerDay.class).cronDays(1)
                                            .build();
    
    @Autowired
    private IKPIService      kpiService;
    
    
    
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
        saveOrUpdate(numberOfChangedFilesPerDayPerUser());
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
    
    
    /**
     * @return
     */
    @Override
    public Kpi numberOfChangedFilesPerDayPerUser() {
    
    
        return BUILD7;
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
    
    
        final Kpi findKPI = kpiService.findKPI(KpiKey.ofKpi(_kpi));
        if (findKPI != null) {
            _kpi.setId(findKPI.getId());
            return;
        }
        kpiService.saveOrUpdate(_kpi);
        
    }
}
