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
import org.komea.product.plugins.scm.kpi.functions.NumberOfCommitsPerDayPerProject;
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
public class ScmKpiPlugin implements IScmKpiPlugin {

    /**
     *
     */
    public static final Kpi ADDED_LINES
            = KpiBuilder
            .createAscending()
            .nameAndKeyDescription(
                    "Added lines")
            .providerType(ProviderType.SCM)
            .entityType(EntityType.PERSON)
            .query(NumberOfAddedLinesPerDay.class)
            .interval(0d, 1000000d)
            .dailyKPI().build();
    /**
     *
     */
    public static final Kpi AVERAGE_COMMIT
            = KpiBuilder
            .createAscending()
            .nameAndKeyDescription(
                    "Commit message length")
            .providerType(ProviderType.SCM)
            .entityType(EntityType.PERSON)
            .interval(0d, 10000d)
            .query(AverageCommitMessageLength.class)
            .dailyKPI().build();
    public static final Kpi BUILD6
            = KpiBuilder
            .createAscending()
            .nameAndKeyDescription(
                    "Modified lines")
            .providerType(ProviderType.SCM)
            .interval(0d, 1000000d)
            .entityType(EntityType.PERSON)
            .query(TotalNumberOfModifiedLinesPerDay.class)
            .dailyKPI().build();
    public static final Kpi BUILD7
            = KpiBuilder
            .createAscending()
            .nameAndKeyDescription(
                    "Modified files")
            .providerType(ProviderType.SCM)
            .entityType(EntityType.PERSON)
            .interval(0d, 1000000d)
            .query(NumberOfModifiedFilesPerDay.class)
            .dailyKPI().build();
    /**
     *
     */
    public static final Kpi CHANGED_LINES
            = KpiBuilder
            .createAscending()
            .nameAndKeyDescription(
                    "Changed lines / day")
            .providerType(ProviderType.SCM)
            .entityType(EntityType.PERSON)
            .interval(0d, 1000000d)
            .query(NumberOfModifiedLinesPerDay.class)
            .dailyKPI().build();
    /**
     *
     */
    public static final Kpi DELETED_LINES
            = KpiBuilder
            .createAscending()
            .nameAndKeyDescription(
                    "Deleted lines / day")
            .providerType(ProviderType.SCM)
            .entityType(EntityType.PERSON)
            .interval(0d, 1000000d)
            .query(NumberOfDeletedLinesPerDay.class)
            .dailyKPI().build();
    public static final Kpi NUMBER_COMMITS
            = KpiBuilder
            .createAscending()
            .nameAndKeyDescription(
                    "Commits / day")
            .providerType(ProviderType.SCM)
            .entityType(EntityType.PERSON)
            .interval(0d, 25 * 60d)
            .query(NumberOfCommitsPerDay.class)
            .hourly().build();
    public static final Kpi NUMBER_COMMITS_PROJECT
            = KpiBuilder
            .createAscending()
            .nameAndKeyDescription(
                    "Commits / day")
            .providerType(ProviderType.SCM)
            .entityType(EntityType.PROJECT)
            .interval(0d, 25 * 60d)
            .query(NumberOfCommitsPerDayPerProject.class)
            .hourly().build();

    @Autowired
    public IKPIService kpiService;

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.plugins.scm.kpi.IScmKpiPlugin#averageCommitMessageLength
     * ()
     */
    @Override
    public Kpi averageCommitMessageLength() {

        return AVERAGE_COMMIT;

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
        saveOrUpdate(NUMBER_COMMITS_PROJECT);

    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.plugins.scm.kpi.IScmKpiPlugin#numberOfAddedLinesPerUser
     * ()
     */
    @Override
    public Kpi numberOfAddedLinesPerUser() {

        return ADDED_LINES;

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
     * @see org.komea.product.plugins.scm.kpi.IScmKpiPlugin#
     * numberOfChangedLinesPerDayPerUser()
     */
    @Override
    public Kpi numberOfChangedLinesPerDayPerUser() {

        return CHANGED_LINES;

    }

    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.plugins.scm.kpi.IScmKpiPlugin#numberOfCommitsPerDayPerUser
     * ()
     */
    @Override
    public Kpi numberOfCommitsPerDayPerUser() {

        return NUMBER_COMMITS;

    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.kpi.IScmKpiPlugin#
     * numberofDeletedLinesPerDayPerUser()
     */
    @Override
    public Kpi numberofDeletedLinesPerDayPerUser() {

        return DELETED_LINES;

    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.kpi.IScmKpiPlugin#
     * numberTotalOfModifiedLinesPerUser()
     */
    @Override
    public Kpi numberTotalOfModifiedLinesPerUser() {

        return BUILD6;

    }

    /**
     * @param _numberSuccessBuildPerDay
     */
    public void saveOrUpdate(final Kpi _kpi) {

        final Kpi findKPI = kpiService.findKPI(KpiKey.ofKpi(_kpi));
        if (findKPI != null) {
            _kpi.setId(findKPI.getId());
            return;
        }
        kpiService.saveOrUpdate(_kpi);

    }
}
