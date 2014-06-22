/**
 *
 */

package org.komea.product.plugins.bugzilla.service.backup;



import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.IFilter;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.plugins.bugtracker.kpis.IssueFilterKPI;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IIssuePlugin;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.komea.product.plugins.bugzilla.api.IBugZillaRebuildHistory;
import org.komea.product.plugins.bugzilla.api.IBugZillaToIssueConvertor;
import org.komea.product.plugins.bugzilla.core.BugHistory;
import org.komea.product.plugins.bugzilla.core.GetBugHistory;
import org.komea.product.plugins.bugzilla.datasource.BZIssueWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.j2bugzilla.base.BugzillaConnector;
import com.j2bugzilla.base.BugzillaException;



/**
 * @author sleroy
 */
@Service
@Transactional
public class BugZillaRebuildHistoryService implements IBugZillaRebuildHistory
{


    private final class FilterBugsExistingAtThisTime implements IFilter<IIssue>
    {


        private final DateTime beginDate;



        public FilterBugsExistingAtThisTime(final DateTime _beginDate) {


            super();
            beginDate = _beginDate;
        }


        @Override
        public boolean matches(final IIssue _task) {


            return _task.getDateSubmitted().isBefore(beginDate)
                    || _task.getDateSubmitted().isEqual(beginDate);
        }
    }



    private static final Logger                  LOGGER     =
            LoggerFactory
            .getLogger("bugzilla-history-service");


    private final Map<String, BugzillaConnector> connectors = new HashMap();
    @Autowired
    private IBugZillaToIssueConvertor            convertorService;

    @Autowired
    private IEventEngineService                  eventEngineService;

    @Autowired()
    @Qualifier("bugzilla-source")
    private IIssuePlugin                         issuePlugin;


    @Autowired
    private IKPIService                          kpiService;


    @Autowired
    private MeasureDao                           measureDAO;


    @Autowired
    private IBZServerProxyFactory                serverProxyFactory;
    @Autowired
    IStatisticsAPI                               statisticsAPI;



    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugzilla.service.backup.IBZRebuildHistoryService#rebuildHistory()
     */
    
    
    @Override
    public void rebuildHistory() {


        LOGGER.info("Rebuilding history of bugzilla servers");
        final List<IIssue> data = issuePlugin.getData();
        LOGGER.info("Working on {} issues", data.size());
        if (data.isEmpty()) {
            LOGGER.warn("No data");
            return;
        }

        final List<KpiAndQueryObject> kpis = getKpisWithBackupFunction();
        if (kpis.isEmpty()) {
            LOGGER.warn("No kpi");
            return;
        }
        DateTime beginDate = computeBeginningTime(data);
        LOGGER.info("Beginning at {}", beginDate);
        final DateTime untilNow = new DateTime();
        // Now iteration, one week per week
        while (beginDate.isBefore(untilNow)) {

            if (getNumberOfMeasuresForThisDate(beginDate) > 0) { // Already existing
                LOGGER.info("Skip {} <  {}", beginDate, untilNow);
                continue;
            }
            LOGGER.info("Iteration {} <  {}", beginDate, untilNow);
            final List<IIssue> existedInPastIssues =
                    CollectionUtil.filter(data, new FilterBugsExistingAtThisTime(beginDate));
            LOGGER.info("At this period, we work on {} issues", existedInPastIssues.size());
            loadHistoryForIssues(existedInPastIssues);
            for (final KpiAndQueryObject kpiAndQueryObject : kpis) {
                kpiAndQueryObject.getQuery().setIssuePlugins(new IIssuePlugin[] {
                        new MockIssuePlugin(existedInPastIssues) });
                final KpiResult result = kpiAndQueryObject.getQuery().getResult();
                result.iterate(new StoreValueIntoMeasureResultIterator(statisticsAPI,
                        kpiAndQueryObject.getKpi().getId(), beginDate));
            }


            beginDate = beginDate.plusWeeks(1); // WEEK PER WEEK

        }


    }


    private DateTime computeBeginningTime(final List<IIssue> data) {


        DateTime beginDate = null;
        // Find begin date
        for (final IIssue issue : data) {
            if (beginDate == null || issue.getDateSubmitted().isBefore(beginDate)) {
                beginDate = issue.getDateSubmitted();
            }
        }
        return beginDate;
    }


    /**
     * Returns the list of kpis with backup functions
     *
     * @return
     */
    private List<KpiAndQueryObject> getKpisWithBackupFunction() {


        final List<KpiAndQueryObject> queries = Lists.newArrayList();
        for (final Kpi kpi : kpiService.selectAll()) {
            final IQuery query = eventEngineService.getQuery(FormulaID.of(kpi));
            if (query instanceof IssueFilterKPI) {
                final IssueFilterKPI filterKpi = (IssueFilterKPI) query;
                final KpiAndQueryObject kpiAndQueryObject =
                        new KpiAndQueryObject(kpi, filterKpi.getFilter(), filterKpi);
                queries.add(kpiAndQueryObject);
                filterKpi.setFilter(new RebuildFilter(kpiAndQueryObject.getOriginalFilter(),
                        convertorService));
            }
        }
        return queries;
    }


    private int getNumberOfMeasuresForThisDate(final DateTime beginDate) {


        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.createCriteria().andDateEqualTo(beginDate.toDate());
        return measureDAO.countByCriteria(measureCriteria);
    }


    private void loadHistory(final IIssue _issue) {


        final BZIssueWrapper issueWrapper = (BZIssueWrapper) _issue;
        // Already loaded
        if (!issueWrapper.getHistory().isEmpty()) {
            LOGGER.trace("History already loaded for {}", issueWrapper.getId());
            return;
        }

        final GetBugHistory getHistory = new GetBugHistory(issueWrapper.getBug().getID());
        try {
            final String address = issueWrapper.getServerConfiguration().getAddress();
            final BugzillaConnector bugzillaConnector = obtainConnector(issueWrapper, address);
            bugzillaConnector.executeMethod(getHistory);
            LOGGER.info("History loaded for bug {}", issueWrapper.getBug().getID());
            final List<BugHistory> bugHistory = getHistory.getBugHistory();
            sortHistoryFromMostRecentToOldest(bugHistory);
            LOGGER.debug("Sorting history of bug {} with  {} elements", issueWrapper.getBug()
                    .getID(), bugHistory.size());
            issueWrapper.setHistory(bugHistory);
        } catch (final BugzillaException e) {
            LOGGER.error(e.getMessage(), e);
        }

    }


    private void loadHistoryForIssues(final List<IIssue> existedInPastIssues) {


        LOGGER.info("Fetching history of bugs...");
        for (final IIssue issue : existedInPastIssues) {
            loadHistory(issue);
        }
    }


    private BugzillaConnector obtainConnector(
            final BZIssueWrapper _issueWrapper,
            final String _address) {


        LOGGER.info("Obtaining connection on server to get history of the issue");
        BugzillaConnector bugzillaConnector = connectors.get(_address);
        if (bugzillaConnector == null) {
            bugzillaConnector =
                    serverProxyFactory.newConnector(_issueWrapper.getServerConfiguration())
                    .getConnector();
            connectors.put(_address, bugzillaConnector);

        }
        return bugzillaConnector;
    }


    private void sortHistoryFromMostRecentToOldest(final List<BugHistory> bugHistory) {


        Collections.sort(bugHistory, new MostRecentHistoryBefore());
    }
}
