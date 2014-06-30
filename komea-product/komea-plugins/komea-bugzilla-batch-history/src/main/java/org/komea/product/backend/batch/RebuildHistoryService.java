/**
 *
 */

package org.komea.product.backend.batch;



import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.backend.service.entities.PersonService;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.IFilter;
import org.komea.product.backend.utils.StringList;
import org.komea.product.database.dao.BugzillaDao;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.dto.BugBugZilla;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.plugins.bugtracker.kpis.IssueFilterKPI;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IIssuePlugin;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.komea.product.plugins.bugzilla.api.IBugZillaToIssueConvertor;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.plugins.datasource.DataCustomFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;



/**
 * @author sleroy
 */

public class RebuildHistoryService implements Runnable, IRebuildHistoryService, IIssuePlugin
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



    private static final Logger LOGGER = LoggerFactory.getLogger("bugzilla-history-service");
    
    
    
    /**
     * @return
     */
    public static BZServerConfiguration configuration() {
    
    
        final BZServerConfiguration bzServerConfiguration = new BZServerConfiguration();
        bzServerConfiguration.setAutocreateProjects(true);
        bzServerConfiguration.setOpenClosedStatus(StringList.EMPTY, new StringList("CLOSED"));
        bzServerConfiguration.setResolutions(StringList.EMPTY, new StringList("FIXED"));
        bzServerConfiguration.getSeverityMap().clear();
        bzServerConfiguration.getSeverityMap().put("enhancement (G3)", Severity.INFO);
        bzServerConfiguration.getSeverityMap().put("critical (G0)", Severity.CRITICAL);
        bzServerConfiguration.getSeverityMap().put("major (G1)", Severity.MAJOR);
        bzServerConfiguration.getSeverityMap().put("minor (G2)", Severity.MINOR);
        bzServerConfiguration.getSeverityMap().put("blocker", Severity.BLOCKER);
        
        
        bzServerConfiguration.setAddress("https://bugzilla.softathome.com/bugzilla");
        bzServerConfiguration.setLogin("sylvain.leroy@tocea.com");
        bzServerConfiguration.setPassword("Pyz17Xgt");
        bzServerConfiguration.setReminderAlert(10);
        
        return bzServerConfiguration;
    }
    
    
    
    @Autowired
    private IBugZillaToIssueConvertor convertorService;
    @Autowired
    private IEventEngineService       eventEngineService;

    @Autowired
    private IKPIService               kpiService;


    @Autowired
    private MeasureDao                measureDAO;


    private SqlSession                openSession;
    @Autowired
    private IPersonService            personService;


    private final String              productID;
    
    
    @Autowired
    private IProjectService           projectService;
    
    @Autowired
    private IBZServerProxyFactory     serverProxyFactory;
    
    
    @Autowired
    private IStatisticsAPI            statisticsAPI;
    
    
    
    /**
     *
     */
    public RebuildHistoryService(final String productID) {


        super();
        this.productID = productID;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssuePlugin#cleanCache()
     */
    @Override
    public void cleanCache() {


        //

    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataTable#getData()
     */
    @Override
    public List<IIssue> getData() {


        return convertIssues(openSession.getMapper(BugzillaDao.class).listBugs(productID));
    }


    public IPersonService getPersonService() {


        return personService;
    }


    public IProjectService getProjectService() {


        return projectService;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataTable#isEmpty()
     */
    @Override
    public boolean isEmpty() {


        return false;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.batch.IRebuildHistoryService#run()
     */
    @Override
    public void run() {


        List<KpiAndQueryObject> kpis = Lists.newArrayList();
        try {

            LOGGER.info("Rebuilding history of bugzilla servers");
            final List<IIssue> data = getData();
            LOGGER.info("Working on {} issues", data.size());
            if (data.isEmpty()) {
                LOGGER.warn("No data");
                return;
            }


            kpis = getKpisWithBackupFunction();
            if (kpis.isEmpty()) {
                LOGGER.warn("No kpi");
                return;
            }
            final DateTime beginDate = computeBeginningTime(data);
            LOGGER.info("Begin of history ====> {}", beginDate);
            DateTime untilNow = new DateTime();
            // Now iteration, one week per week
            final int weeks = Days.daysBetween(beginDate, untilNow).getDays();
            int weekIdx = 1;
            while (untilNow.isAfter(beginDate)) {

                if (getNumberOfMeasuresForThisDate(untilNow) > 0) { // Already existing
                    LOGGER.info("Skip {} <  {}", beginDate, untilNow);
                    weekIdx++;
                    continue;
                }
                LOGGER.info("############ Iteration {} <  {} : {}%", beginDate, untilNow, weekIdx
                        * 100 / weeks);
                final List<IIssue> existedInPastIssues =
                        CollectionUtil.filter(data, new FilterBugsExistingAtThisTime(untilNow));
                LOGGER.info("At this period, we work on {} issues", existedInPastIssues.size());
                loadHistoryForIssues(existedInPastIssues);
                for (final KpiAndQueryObject kpiAndQueryObject : kpis) {
                    try {
                        kpiAndQueryObject.getQuery().setIssuePlugins(new IIssuePlugin[] {
                                new MockIssuePlugin(existedInPastIssues) });
                        ((RebuildFilter) kpiAndQueryObject.getQuery().getFilter())
                        .setCheckTime(untilNow);
                        final KpiResult result = kpiAndQueryObject.getQuery().getResult();
                        result.iterate(new StoreValueIntoMeasureResultIterator(statisticsAPI,
                                kpiAndQueryObject.getKpi().getId(), untilNow));
                    } catch (final Exception e) {
                        LOGGER.error("Could not create data at {} with kpi {}", untilNow,
                                kpiAndQueryObject.getKpi(), e);
                    }
                }


                untilNow = untilNow.minusDays(1); // MONTH PER MONTH
                weekIdx++;
            }
        } finally {
            for (final KpiAndQueryObject kpiAndQueryObject : kpis) {
                kpiAndQueryObject.getQuery().setFilter(kpiAndQueryObject.getOriginalFilter());
            }
        }
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataTable#searchData(org.komea.product.backend.utils.IFilter)
     */
    @Override
    public List<IIssue> searchData(final IFilter<IIssue> _dataFilter) {


        throw new UnsupportedOperationException();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.batch.IRebuildHistoryService#setMyBatis(org.apache.ibatis.session.SqlSession)
     */
    @Override
    public void setMyBatis(final SqlSession _openSession) {


        openSession = _openSession;


    }
    
    
    public void setPersonService(final PersonService _personService) {


        personService = _personService;
    }
    
    
    public void setProjectService(final IProjectService _projectService) {


        projectService = _projectService;
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
     * @param _listBugs
     * @return
     */
    private List<IIssue> convertIssues(final List<BugBugZilla> _listBugs) {


        for (final BugBugZilla bug : _listBugs) {
            bug.setProject(projectService.getOrCreate(productID));
            bug.setBzServerConfiguration(configuration());
            bug.setPersonService(personService);
            bug.setBugzillaDao(openSession.getMapper(BugzillaDao.class));
            final DataCustomFields customFields = (DataCustomFields) bug.getCustomFields();
            customFields.put("status", bug.getBug_status());
            customFields.put("resolutino", bug.getResolutionName());
            
        }
        return (List) _listBugs;
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
                filterKpi.setIssuePlugins(new IIssuePlugin[] {
                        this });
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
    
    
    @SuppressWarnings("boxing")
    private void loadHistory(final IIssue _issue) {


        final BugBugZilla issueWrapper = (BugBugZilla) _issue;
        // Already loaded
        if (!issueWrapper.getHistory().isEmpty()) {
            LOGGER.trace("History already loaded for {}", issueWrapper.getBug_id());
            return;
        }

        final List<org.komea.product.database.dto.BugHistory> history =
                openSession.getMapper(BugzillaDao.class).getHistory(issueWrapper.getBug_id());
        LOGGER.info("History loaded for bug {}", issueWrapper.getId());
        sortHistoryFromMostRecentToOldest(history);
        LOGGER.debug("Sorting history of bug {} with  {} elements", issueWrapper.getId(),
                history.size());
        issueWrapper.setHistory(history);
    }
    
    
    private void loadHistoryForIssues(final List<IIssue> existedInPastIssues) {


        int number = 1;
        LOGGER.info("Fetching history of bugs...");
        for (final IIssue issue : existedInPastIssues) {
            LOGGER.info("Fetching history for bug {}/{}", number, existedInPastIssues.size());
            loadHistory(issue);
            number++;
        }
    }


    private void sortHistoryFromMostRecentToOldest(
            final List<org.komea.product.database.dto.BugHistory> _history) {


        Collections.sort(_history, new MostRecentHistoryBefore());

    }
}
