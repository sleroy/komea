/**
 * 
 */

package org.komea.product.backend.service;



import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.komea.product.backend.api.IMeasureHistoryService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.formula.tuple.TupleFactory;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.Person;
import org.komea.product.plugins.perforce.utils.PerforceRepositoryProxy;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.ScmRepositoryFactories;
import org.komea.product.plugins.scm.api.IScmKpiPlugin;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.kpi.functions.AverageCommitMessageLength;
import org.komea.product.plugins.scm.kpi.functions.NumberOfAddedLinesPerDay;
import org.komea.product.plugins.scm.kpi.functions.NumberOfCommitsPerDay;
import org.komea.product.plugins.scm.kpi.functions.NumberOfDeletedLinesPerDay;
import org.komea.product.plugins.scm.kpi.functions.NumberOfModifiedFilesPerDay;
import org.komea.product.plugins.scm.kpi.functions.NumberOfModifiedLinesPerDay;
import org.komea.product.plugins.scm.kpi.functions.ScmCommitTable;
import org.komea.product.plugins.scm.kpi.functions.TotalNumberOfModifiedLinesPerDay;
import org.komea.product.plugins.scm.utils.IScmCommitGroupingFunction;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class ScmKpiHistoryBuilderTest extends AbstractSpringIntegrationTestCase
{
    
    
    /**
     * @author sleroy
     */
    private final class DayGroupingFunction implements IScmCommitGroupingFunction<ITuple>
    {
        
        
        @Override
        public ITuple getKey(final IScmCommit _commit) {
        
        
            final DateTime commitTime = _commit.getCommitTime();
            
            return TupleFactory.newTuple(commitTime.year().get(), commitTime.monthOfYear().get(),
                    commitTime.dayOfMonth().get());
        }
    }
    
    
    
    /**
     * @author sleroy
     */
    private final class IScmCommitGroupingFunctionImplementation implements
            IScmCommitGroupingFunction<ITuple>
    {
        
        
        @Override
        public ITuple getKey(final IScmCommit _commit) {
        
        
            final Person author = _commit.getAuthor();
            return TupleFactory.newTuple(author.getEntityKey());
        }
    }
    
    
    
    private static final Logger    LOGGER = LoggerFactory.getLogger(ScmKpiHistoryBuilderTest.class);
    
    @Autowired
    private IMeasureHistoryService measureHistoryService;
    
    
    @Autowired
    private ScmRepositoryFactories repositoryProxyFactory;
    
    @Autowired
    private IScmRepositoryService  repositoryService;
    
    @Autowired
    private IScmKpiPlugin          scmKpiPlugin;
    
    
    
    @Test
    public void testKpiHistoryBUilder() {
    
    
        for (final ScmRepositoryDefinition scmRepositoryDefinition : repositoryService
                .getAllRepositories()) {
            buildScmStatisticsHistory(scmRepositoryDefinition);
            
        }
    }
    
    
    private HistoryKey buildHistoryKey(final ITuple user, final Integer id) {
    
    
        return HistoryKey.of(id, (EntityKey) user.getFirst());
    }
    
    
    private DateTime buildMeasureTime(final ITuple periodDate) {
    
    
        final DateTime analysisDate =
                new DateTime().withYear((Integer) periodDate.values().get(0))
                        .withMonthOfYear((Integer) periodDate.values().get(1))
                        .withDayOfMonth((Integer) periodDate.values().get(2));
        return analysisDate;
    }
    
    
    private void buildScmStatisticsHistory(final ScmRepositoryDefinition scmRepositoryDefinition) {
    
    
        // scmRepositoryDefinition.setCloneDirectory(new File(
        // "/home/sleroy/git/cleancoder-course/.git"));
        PerforceRepositoryProxy proxy = null;
        
        try {
            proxy = (PerforceRepositoryProxy) repositoryProxyFactory.newProxy(scmRepositoryDefinition);
            proxy.getScmCloner().cloneRepository();
            final String repoName = scmRepositoryDefinition.getRepoName();
            LOGGER.info("Analyzing repository : {}", repoName);
            LOGGER.info("Received branches from repository {}", repoName);
            final ScmCommitTable<ITuple> allCommitsFromABranch = proxy.getCommitMap(dayGrouping());
            LOGGER.info("SCM Repository {} has {} periods to backup in history",
                    scmRepositoryDefinition.getRepoName(), allCommitsFromABranch.getNumberOfKeys());
            for (final ITuple periodDate : allCommitsFromABranch.keys()) {
                
                LOGGER.info("Building statistics for the day {}", periodDate);
                
                
                buildUsersStatistics(allCommitsFromABranch, periodDate);
                
            }
            
        } catch (final Exception exception) {
            LOGGER.error("Exception ", exception);
        } finally {
            IOUtils.closeQuietly(proxy);
        }
        
        
        final List<Measure> filteredHistory =
                measureHistoryService.getHistory(HistoryKey.of(scmKpiPlugin
                        .numberOfCommitsPerDayPerUser().getId()));
        System.out.println(filteredHistory);
    }
    
    
    private void buildUsersStatistics(
            final ScmCommitTable<ITuple> allCommitsFromABranch,
            final ITuple _periodDate) {
    
    
        final DateTime analysisDate = buildMeasureTime(_periodDate);
        final ScmCommitTable<ITuple> userTable =
                ScmCommitTable.buildTableFromCommitsAndKey(
                        allCommitsFromABranch.getListOfCommitsPerKey(_periodDate), groupByUser());
        for (final ITuple user : userTable.keys()) {
            
            
            buildUserStatistics(analysisDate, userTable, user);
            
            
        }
    }
    
    
    private void buildUserStatistics(
            final DateTime analysisDate,
            final ScmCommitTable<ITuple> userTable,
            final ITuple user) {
    
    
        final Collection<IScmCommit> commitsOfTheDay = userTable.getListOfCommitsPerKey(user);
        measureHistoryService.storeMeasure(
                buildHistoryKey(user, scmKpiPlugin.numberOfCommitsPerDayPerUser().getId()),
                new Double(new NumberOfCommitsPerDay().compute(commitsOfTheDay)), analysisDate);
        measureHistoryService.storeMeasure(
                buildHistoryKey(user, scmKpiPlugin.numberOfAddedLinesPerUser().getId()),
                new Double(new NumberOfAddedLinesPerDay().compute(commitsOfTheDay)), analysisDate);
        measureHistoryService
                .storeMeasure(
                        buildHistoryKey(user, scmKpiPlugin.numberofDeletedLinesPerDayPerUser()
                                .getId()),
                        new Double(new NumberOfDeletedLinesPerDay().compute(commitsOfTheDay)),
                        analysisDate);
        measureHistoryService.storeMeasure(
                buildHistoryKey(user, scmKpiPlugin.numberOfChangedFilesPerDayPerUser().getId()),
                new Double(new NumberOfModifiedFilesPerDay().compute(commitsOfTheDay)),
                analysisDate);
        measureHistoryService.storeMeasure(
                buildHistoryKey(user, scmKpiPlugin.numberOfChangedLinesPerDayPerUser().getId()),
                new Double(new NumberOfModifiedLinesPerDay().compute(commitsOfTheDay)),
                analysisDate);
        measureHistoryService
                .storeMeasure(
                        buildHistoryKey(user, scmKpiPlugin.averageCommitMessageLength().getId()),
                        new Double(new AverageCommitMessageLength().compute(commitsOfTheDay)),
                        analysisDate);
        measureHistoryService.storeMeasure(
                buildHistoryKey(user, scmKpiPlugin.numberTotalOfModifiedLinesPerUser().getId()),
                new Double(new TotalNumberOfModifiedLinesPerDay().compute(commitsOfTheDay)),
                analysisDate);
        
        
    }
    
    
    /**
     * @return
     */
    private IScmCommitGroupingFunction<ITuple> dayGrouping() {
    
    
        return new DayGroupingFunction();
    }
    
    
    /**
     * @return
     */
    private IScmCommitGroupingFunction<ITuple> groupByUser() {
    
    
        return new IScmCommitGroupingFunctionImplementation();
    }
}
