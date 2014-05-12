/**
 * 
 */

package org.komea.product.backend.service;

import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.komea.product.backend.service.history.HistoryKey;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.cep.api.formula.tuple.ITuple;
import org.komea.product.cep.formula.tuple.TupleFactory;
import org.komea.product.database.model.Person;
import org.komea.product.plugins.git.bean.GitProviderPlugin;
import org.komea.product.plugins.git.utils.GitRepositoryProxy;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author sleroy
 */
@Service
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class ScmKpiHistoryBuilder implements IAdminAction {

	/**
	 * @author sleroy
	 */
	private final class DayGroupingFunction implements IScmCommitGroupingFunction<ITuple> {

		@Override
		public ITuple getKey(final IScmCommit _commit) {

			final DateTime commitTime = _commit.getCommitTime();

			return TupleFactory.newTuple(commitTime.year().get(), commitTime.monthOfYear().get(), commitTime
			        .dayOfMonth().get());
		}
	}

	/**
	 * @author sleroy
	 */
	private final class IScmCommitGroupingFunctionImplementation implements IScmCommitGroupingFunction<ITuple> {

		@Override
		public ITuple getKey(final IScmCommit _commit) {

			final Person author = _commit.getAuthor();
			return TupleFactory.newTuple(author.getEntityKey());
		}
	}

	private static final Logger	   LOGGER	= LoggerFactory.getLogger("scm-kpi-history-builder");

	@Autowired
	private GitExampleFeedBean	   feedBean;

	@Autowired
	private IStatisticsAPI	       measureHistoryService;

	@Autowired
	private GitProviderPlugin	   plugin;

	@Autowired
	private ScmRepositoryFactories	repositoryProxyFactory;

	@Autowired
	private IScmRepositoryService	repositoryService;

	@Autowired
	private IScmKpiPlugin	       scmKpiPlugin;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.IAdminAction#execute(org.komea.product
	 * .backend.service.ProgressListener)
	 */
	@Override
	public void execute(final ProgressListener _logger) {

		_logger.progressStart(new ProgressEvent("Fetching repositories", 0, 1));
		int totalSteps = 0;
		try {
			LOGGER.info("-<KPI>-------------------------------------------</KPI>-");
			LOGGER.info("\t");

			final List<ScmRepositoryDefinition> allRepositories = repositoryService.getAllRepositories();
			totalSteps = 1 + allRepositories.size();
			_logger.progressUpdate(new ProgressEvent("Repositories found", 1, totalSteps));
			LOGGER.info("Detection of new scm repositories ------ {}", allRepositories);
			int res = 1;
			for (final ScmRepositoryDefinition scmRepositoryDefinition : allRepositories) {

				_logger.progressUpdate(new ProgressEvent("Build history from repository "
				        + scmRepositoryDefinition.getRepoName(), res, totalSteps));
				buildScmStatisticsHistory(scmRepositoryDefinition);
				_logger.progressUpdate(new ProgressEvent("History built from repository "
				        + scmRepositoryDefinition.getRepoName(), res, totalSteps));
				res++;

			}
			LOGGER.info("-----------------------------");
		} finally {
			_logger.progressFinish(new ProgressEvent("Repositories analysed", totalSteps, totalSteps));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.komea.product.backend.service.IAdminAction#getActionName()
	 */
	@Override
	public String getActionName() {

		return "Re-build the kpi history from scm repositories";
	}

	private HistoryKey buildHistoryKey(final ITuple user, final Integer id) {

		return HistoryKey.of(id, (EntityKey) user.getFirst());
	}

	private void buildScmStatisticsHistory(final ScmRepositoryDefinition _scmRepositoryDefinition) {

		if (_scmRepositoryDefinition.isHistoryGenerated()) { return; }
		LOGGER.info("<< Treating SCM Repository {} >>", _scmRepositoryDefinition.getRepoName());
		GitRepositoryProxy proxy = null;

		try {
			proxy = (GitRepositoryProxy) repositoryProxyFactory.newProxy(_scmRepositoryDefinition);
			prepareRepository(_scmRepositoryDefinition, proxy);
			final String repoName = _scmRepositoryDefinition.getRepoName();
			LOGGER.info("Analyzing repository : {}", repoName);
			LOGGER.info("Received branches from repository {}", repoName);
			final ScmCommitTable<ITuple> allCommitsFromABranch = proxy.getCommitMap(dayGrouping());
			LOGGER.info("SCM Repository {} has {} periods to backup in history",
			        _scmRepositoryDefinition.getRepoName(), allCommitsFromABranch.getNumberOfKeys());
			for (final ITuple periodDate : allCommitsFromABranch.keys()) {

				LOGGER.info("Building statistics for the day {}", periodDate);
				buildUsersStatistics(allCommitsFromABranch, periodDate);

			}

		} catch (final Exception exception) {
			LOGGER.error("Exception ", exception);
		} finally {
			_scmRepositoryDefinition.setHistoryGenerated();
			IOUtils.closeQuietly(proxy);
		}

	}

	private void buildUsersStatistics(final ScmCommitTable<ITuple> allCommitsFromABranch, final ITuple _periodDate) {

		final DateTime analysisDate = convertTimeIntoDayTime(_periodDate);
		final ScmCommitTable<ITuple> userTable = ScmCommitTable.buildTableFromCommitsAndKey(
		        allCommitsFromABranch.getListOfCommitsPerKey(_periodDate), groupByUser());
		for (final ITuple user : userTable.keys()) {

			LOGGER.info("Grouping statistics for user {}", user);
			buildUserStatistics(analysisDate, userTable, user);

		}
	}

	private void buildUserStatistics(final DateTime analysisDate, final ScmCommitTable<ITuple> userTable,
	        final ITuple user) {

		final Collection<IScmCommit> commitsOfTheDay = userTable.getListOfCommitsPerKey(user);
		measureHistoryService.storeValueInHistory(
		        buildHistoryKey(user, scmKpiPlugin.numberOfCommitsPerDayPerUser().getId()), new Double(
		                new NumberOfCommitsPerDay().compute(commitsOfTheDay)), analysisDate);
		measureHistoryService.storeValueInHistory(
		        buildHistoryKey(user, scmKpiPlugin.numberOfAddedLinesPerUser().getId()), new Double(
		                new NumberOfAddedLinesPerDay().compute(commitsOfTheDay)), analysisDate);
		measureHistoryService.storeValueInHistory(
		        buildHistoryKey(user, scmKpiPlugin.numberofDeletedLinesPerDayPerUser().getId()), new Double(
		                new NumberOfDeletedLinesPerDay().compute(commitsOfTheDay)), analysisDate);
		measureHistoryService.storeValueInHistory(
		        buildHistoryKey(user, scmKpiPlugin.numberOfChangedFilesPerDayPerUser().getId()), new Double(
		                new NumberOfModifiedFilesPerDay().compute(commitsOfTheDay)), analysisDate);
		measureHistoryService.storeValueInHistory(
		        buildHistoryKey(user, scmKpiPlugin.numberOfChangedLinesPerDayPerUser().getId()), new Double(
		                new NumberOfModifiedLinesPerDay().compute(commitsOfTheDay)), analysisDate);
		measureHistoryService.storeValueInHistory(
		        buildHistoryKey(user, scmKpiPlugin.averageCommitMessageLength().getId()), new Double(
		                new AverageCommitMessageLength().compute(commitsOfTheDay)), analysisDate);
		measureHistoryService.storeValueInHistory(
		        buildHistoryKey(user, scmKpiPlugin.numberTotalOfModifiedLinesPerUser().getId()), new Double(
		                new TotalNumberOfModifiedLinesPerDay().compute(commitsOfTheDay)), analysisDate);

	}

	private DateTime convertTimeIntoDayTime(final ITuple periodDate) {

		final DateTime analysisDate = new DateTime().withYear((Integer) periodDate.values().get(0))
		        .withMonthOfYear((Integer) periodDate.values().get(1))
		        .withDayOfMonth((Integer) periodDate.values().get(2));
		return analysisDate;
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

	private void prepareRepository(final ScmRepositoryDefinition scmRepositoryDefinition, final GitRepositoryProxy proxy) {

		if (!scmRepositoryDefinition.isCloned()) {
			LOGGER.info("A cloning is required.");
			proxy.getScmCloner().cloneRepository();
		}
	}
}
