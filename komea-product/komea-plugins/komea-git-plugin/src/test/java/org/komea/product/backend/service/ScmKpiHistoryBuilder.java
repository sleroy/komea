/**
 * 
 */

package org.komea.product.backend.service;



import java.io.File;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.komea.eventory.api.formula.tuple.ITuple;
import org.komea.eventory.formula.tuple.TupleFactory;
import org.komea.product.backend.service.tomove.AverageCommitMessageLength;
import org.komea.product.backend.service.tomove.NumberOfAddedLinesPerDay;
import org.komea.product.backend.service.tomove.NumberOfCommitsPerDay;
import org.komea.product.backend.service.tomove.NumberOfDeletedLinesPerDay;
import org.komea.product.backend.service.tomove.NumberOfModifiedFilesPerDay;
import org.komea.product.backend.service.tomove.NumberOfModifiedLinesPerDay;
import org.komea.product.backend.service.tomove.ScmCommitPerDayTable;
import org.komea.product.backend.service.tomove.TotalNumberOfModifiedLinesPerDay;
import org.komea.product.database.model.Person;
import org.komea.product.plugins.git.utils.GitRepositoryProxy;
import org.komea.product.plugins.git.utils.IScmCommitGroupingFunction;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.ScmRepositoryFactories;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
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
    private ScmRepositoryFactories repositoryProxyFactory;
    
    
    @Autowired
    private IScmRepositoryService  repositoryService;
    
    
    
    @Test
    public void testKpiHistoryBUilder() {
    
    
        for (final ScmRepositoryDefinition scmRepositoryDefinition : repositoryService
                .getAllRepositories()) {
            scmRepositoryDefinition.setCloneDirectory(new File(
                    "/home/sleroy/git/cleancoder-course/.git"));
            final GitRepositoryProxy proxy =
                    (GitRepositoryProxy) repositoryProxyFactory.newProxy(scmRepositoryDefinition);
            final String repoName = scmRepositoryDefinition.getRepoName();
            LOGGER.info("Analyzing repository : {}", repoName);
            final List<String> branches = proxy.getBranches();
            LOGGER.info("Received branches from repository {} : branches {}", repoName, branches);
            for (final String branchName : branches) {
                try {
                    
                    final ScmCommitPerDayTable<ITuple> allCommitsFromABranch =
                            proxy.getCommitMap(branchName, dayGrouping());
                    LOGGER.info("Branch {} has {} periods", branchName,
                            allCommitsFromABranch.getNumberOfDays());
                    for (final ITuple periodDate : allCommitsFromABranch.keys()) {
                        LOGGER.info("Building statistics for the day {}", periodDate);
                        
                        
                        final ScmCommitPerDayTable<ITuple> userTable =
                                allCommitsFromABranch.buildTableFromCommitsAndKey(periodDate,
                                        groupByUser());
                        for (final ITuple user : userTable.keys()) {
                            
                            
                            LOGGER.info("Number of commits performed by {} that day {}", user
                                    .getFirst(),
                                    new NumberOfCommitsPerDay(userTable.getCommitsOfTheDay(user))
                                            .compute());
                            LOGGER.info(
                                    "Total of modified lines performed by {} that day {}",
                                    user.getFirst(),
                                    new TotalNumberOfModifiedLinesPerDay(userTable
                                            .getCommitsOfTheDay(user)).compute());
                            
                            LOGGER.info(
                                    "Number of changed lines performed by {} that day {}",
                                    user.getFirst(),
                                    new NumberOfModifiedLinesPerDay(userTable
                                            .getCommitsOfTheDay(user)).compute());
                            
                            LOGGER.info(
                                    "Number of added lines performed by {} that day {}",
                                    user.getFirst(),
                                    new NumberOfAddedLinesPerDay(userTable.getCommitsOfTheDay(user))
                                            .compute());
                            
                            LOGGER.info(
                                    "Number of deleted lines performed by {} that day {}",
                                    user.getFirst(),
                                    new NumberOfDeletedLinesPerDay(userTable
                                            .getCommitsOfTheDay(user)).compute());
                            LOGGER.info(
                                    "Number of modified files performed by {} that day {}",
                                    user.getFirst(),
                                    new NumberOfModifiedFilesPerDay(userTable
                                            .getCommitsOfTheDay(user)).compute());
                            LOGGER.info(
                                    "Average commit message length by {}  this day {}",
                                    user.getFirst(),
                                    new AverageCommitMessageLength(userTable
                                            .getCommitsOfTheDay(user)).compute());
                            
                            
                        }
                        
                    }
                    
                } catch (final Exception exception) {
                    LOGGER.error("Exception ", exception);
                }
            }
        }
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
    
    
        return new IScmCommitGroupingFunction<ITuple>()
        {
            
            
            @Override
            public ITuple getKey(final IScmCommit _commit) {
            
            
                final Person author = _commit.getAuthor();
                return TupleFactory.newTuple(author.getLogin());
            }
        };
    }
}
