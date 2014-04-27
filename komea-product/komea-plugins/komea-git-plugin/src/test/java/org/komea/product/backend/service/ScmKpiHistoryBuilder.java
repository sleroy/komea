/**
 * 
 */

package org.komea.product.backend.service;



import java.io.File;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.komea.eventory.api.formula.tuple.ITuple;
import org.komea.eventory.formula.tuple.TupleFactory;
import org.komea.product.backend.api.IMeasureHistoryService;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.Person;
import org.komea.product.plugins.git.utils.GitRepositoryProxy;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.ScmRepositoryFactories;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.kpi.IScmKpiPlugin;
import org.komea.product.plugins.scm.kpi.functions.NumberOfCommitsPerDay;
import org.komea.product.plugins.scm.kpi.functions.ScmCommitPerDayTable;
import org.komea.product.plugins.scm.utils.IScmCommitGroupingFunction;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class ScmKpiHistoryBuilder extends AbstractSpringIntegrationTestCase
{
    
    
    /**
     * @author sleroy
     *
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
    
    
    
    private static final Logger    LOGGER = LoggerFactory.getLogger(ScmKpiHistoryBuilder.class);
    
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
    
    
        scmRepositoryDefinition.setCloneDirectory(new File(
                "/home/sleroy/git/cleancoder-course/.git"));
        final GitRepositoryProxy proxy =
                (GitRepositoryProxy) repositoryProxyFactory.newProxy(scmRepositoryDefinition);
        final String repoName = scmRepositoryDefinition.getRepoName();
        LOGGER.info("Analyzing repository : {}", repoName);
        LOGGER.info("Received branches from repository {}", repoName);
        
        try {
            final ScmCommitPerDayTable<ITuple> allCommitsFromABranch =
                    proxy.getCommitMap(dayGrouping());
            LOGGER.info("SCM Repository {} has {} periods to backup in history",
                    allCommitsFromABranch.getNumberOfKeys());
            for (final ITuple periodDate : allCommitsFromABranch.keys()) {
                
                LOGGER.info("Building statistics for the day {}", periodDate);
                final DateTime analysisDate = buildMeasureTime(periodDate);
                
                buildUsersStatistics(allCommitsFromABranch, periodDate, analysisDate);
                
            }
            
        } catch (final Exception exception) {
            LOGGER.error("Exception ", exception);
        }
        
        
        final List<Measure> filteredHistory =
                measureHistoryService.getHistory(HistoryKey.of(scmKpiPlugin
                        .numberOfCommitsPerDayPerUser().getId()));
        System.out.println(filteredHistory);
    }
    
    
    private void buildUsersStatistics(
            final ScmCommitPerDayTable<ITuple> allCommitsFromABranch,
            final ITuple periodDate,
            final DateTime analysisDate) {
    
    
        final ScmCommitPerDayTable<ITuple> userTable =
                allCommitsFromABranch.buildTableFromCommitsAndKey(periodDate, groupByUser());
        for (final ITuple user : userTable.keys()) {
            
            
            buildUserStatistics(analysisDate, userTable, user);
            
            
        }
    }
    
    
    private void buildUserStatistics(
            final DateTime analysisDate,
            final ScmCommitPerDayTable<ITuple> userTable,
            final ITuple user) {
    
    
        final Collection<IScmCommit> commitsOfTheDay = userTable.getListOfCommitsPerKey(user);
        final Integer id = scmKpiPlugin.numberOfCommitsPerDayPerUser().getId();
        final HistoryKey historyKey = buildHistoryKey(user, id);
        measureHistoryService.storeMeasure(historyKey, new Double(new NumberOfCommitsPerDay(
                commitsOfTheDay).compute()), analysisDate);
        
        //
        // measureHistoryService.storeMeasure(KpiKey.ofKpi(scmKpiPlugin.averageCommitMessageLength()),
        // new AverageCommitMessageLength(commitsOfTheDay).compute(),
        // (EntityKey) user.getFirst(), analysisDate);
        //
        // measureHistoryService.storeMeasure(KpiKey.ofKpi(scmKpiPlugin.numberOfAddedLinesPerUser()),
        // new NumberOfAddedLinesPerDay(commitsOfTheDay).compute(),
        // (EntityKey) user.getFirst(), analysisDate);
        // measureHistoryService.storeMeasure(
        // KpiKey.ofKpi(scmKpiPlugin.numberofDeletedLinesPerDayPerUser()),
        // new NumberOfDeletedLinesPerDay(commitsOfTheDay).compute(),
        // (EntityKey) user.getFirst(), analysisDate);
        // measureHistoryService.storeMeasure(
        // KpiKey.ofKpi(scmKpiPlugin.numberOfChangedLinesPerDayPerUser()),
        // new NumberOfModifiedLinesPerDay(commitsOfTheDay).compute(),
        // (EntityKey) user.getFirst(), analysisDate);
        // measureHistoryService.storeMeasure(
        // KpiKey.ofKpi(scmKpiPlugin.numberTotalOfModifiedLinesPerUser()),
        // new TotalNumberOfModifiedLinesPerDay(commitsOfTheDay).compute(),
        // (EntityKey) user.getFirst(), analysisDate);
        
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
