/**
 *
 */
package org.komea.product.backend.batch;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.joda.time.Months;
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
import org.komea.product.database.dto.BZUser;
import org.komea.product.database.dto.BugBugZilla;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author sleroy
 */
@Service
@Scope(value = "prototype")
public class RebuildHistoryService implements IRebuildHistoryService {

    private final class FilterBugsExistingAtThisTime implements IFilter<IIssue> {

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

    /**
     * @author sleroy
     */
    private final class IIssuePluginImplementation implements IIssuePlugin {

        @Override
        public void cleanCache() {

            //
        }

        @Override
        public List<IIssue> getData() {

            return RebuildHistoryService.this.getData();

        }

        @Override
        public boolean isEmpty() {

            return false;
        }

        @Override
        public List<IIssue> searchData(final IFilter<IIssue> _dataFilter) {

            return null;
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger("bugzilla-history-service");

    /**
     * @return
     */
    public static BZServerConfiguration configuration() {

        final BZServerConfiguration bzServerConfiguration = new BZServerConfiguration();
        bzServerConfiguration.setAutocreateProjects(true);
        bzServerConfiguration.setOpenClosedStatus(new HashSet<String>(), new HashSet<String>(Arrays.asList("CLOSED")));
        bzServerConfiguration.setResolutions(new HashSet<String>(), new HashSet<String>(Arrays.asList("FIXED")));
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
    private IEventEngineService eventEngineService;

    @Autowired
    private IKPIService kpiService;

    private BugzillaDao mapper;

    @Autowired
    private MeasureDao measureDAO;

    private final Map<Integer, Person> persons = Maps.newHashMap();

    @Autowired
    private IPersonService personService;

    @Autowired
    private IProjectService projectService;
    @Autowired
    private IBZServerProxyFactory serverProxyFactory;

    @Autowired
    private IStatisticsAPI statisticsAPI;


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataTable#getData()
     */
    Map<Integer, Project> products = new HashMap();

    public RebuildHistoryService() {

        super();

    }

    public List<IIssue> getData() {

        if (getMapper() == null) {
            System.out.println();
        }
        Validate.notNull(getMapper());
        return convertIssues(getMapper().listBugs());
    }

    public BugzillaDao getMapper() {

        return mapper;
    }

    public IPersonService getPersonService() {

        return personService;
    }

    public IProjectService getProjectService() {

        return projectService;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.batch.IRebuildHistoryService#run()
     */
    @Override
    public void run() {

        for (final ProjectDto dto : mapper.getProjects()) {
            products.put(dto.getId(), projectService.getOrCreate(dto.getName()));
        }
        for (final BZUser bzUser : mapper.getUsers()) {
            persons.put(bzUser.getUserid(),
                    personService.findOrCreatePersonByEmail(bzUser.getLogin_name()));
        }

        List<KpiAndQueryObject> kpis = Lists.newArrayList();
        try {

            LOGGER.info("Rebuilding history of bugzilla servers");
            final List<IIssue> data = getData();
            LOGGER.debug("Working on {} issues", data.size());
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
            final int weeks = Months.monthsBetween(beginDate, untilNow).getMonths();
            int weekIdx = 1;
            LOGGER.info("Loading history for issues");
            loadHistoryForIssues(data);
            while (untilNow.isAfter(beginDate)) {

                LOGGER.info("############ Iteration {} <  {} : {}%", beginDate, untilNow, weekIdx
                        * 100 / weeks);
                LOGGER.info("Filtering {} bugs for this period", data.size());
                final List<IIssue> existedInPastIssues
                        = CollectionUtil.filter(data, new FilterBugsExistingAtThisTime(untilNow));
                LOGGER.info("At this period, we work on {} issues", existedInPastIssues.size());

                for (final KpiAndQueryObject kpiAndQueryObject : kpis) {
                    try {
                        if (kpiAndQueryObject.getKpi().getEntityType() == EntityType.PERSON) {
                            continue;
                        } else {
                            LOGGER.info("Kpi : per project {}", kpiAndQueryObject.getKpi()
                                    .getDisplayName());
                        }
                        // if (getNumberOfMeasuresForThisDate(untilNow, kpiAndQueryObject) > 0) { // Already existing
                        // continue;
                        // }

                        kpiAndQueryObject.getQuery().setIssuePlugins(new IIssuePlugin[]{
                            new MockIssuePlugin(existedInPastIssues)});
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

                untilNow = untilNow.minusMonths(1); // WEEK PER WEEK
                weekIdx++;
            }
        } finally {
            for (final KpiAndQueryObject kpiAndQueryObject : kpis) {
                kpiAndQueryObject.getQuery().setFilter(kpiAndQueryObject.getOriginalFilter());
            }
        }
    }

    @Override
    public void setMapper(final BugzillaDao _mapper) {

        mapper = _mapper;
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

            bug.setProject(products.get(bug.getProduct_id()));
            Validate.notNull(bug.getProject());
            bug.setBzServerConfiguration(configuration());
            bug.setPersonService(personService);
            bug.setBugzillaDao(getMapper());
            bug.setUsers(persons);
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
                final KpiAndQueryObject kpiAndQueryObject
                        = new KpiAndQueryObject(kpi, filterKpi.getFilter(), filterKpi);
                queries.add(kpiAndQueryObject);
                filterKpi.setIssuePlugins(new IIssuePlugin[]{
                    new IIssuePluginImplementation()});
                filterKpi.setFilter(new RebuildFilter(kpiAndQueryObject.getOriginalFilter(),
                        convertorService, getMapper()));
            }
        }
        return queries;
    }

    private int getNumberOfMeasuresForThisDate(
            final DateTime beginDate,
            final KpiAndQueryObject _kpiAndQueryObject) {

        final MeasureCriteria measureCriteria = new MeasureCriteria();
        measureCriteria.createCriteria().andDateEqualTo(beginDate.toDate())
                .andIdKpiEqualTo(FormulaID.of(_kpiAndQueryObject.getKpi()).getId());
        final int countByCriteria = measureDAO.countByCriteria(measureCriteria);
        return countByCriteria;
    }

    @SuppressWarnings("boxing")
    private void loadHistory(final IIssue _issue) {

        final BugBugZilla issueWrapper = (BugBugZilla) _issue;
        // Already loaded
        if (!issueWrapper.getHistory().isEmpty()) {
            LOGGER.trace("History already loaded for {}", issueWrapper.getBug_id());
            return;
        }

        final List<org.komea.product.database.dto.BugHistory> history
                = getMapper().getHistory(issueWrapper.getBug_id());
        LOGGER.trace("History loaded for bug {}", issueWrapper.getId());
        sortHistoryFromMostRecentToOldest(history);
        LOGGER.debug("Sorting history of bug {} with  {} elements", issueWrapper.getId(),
                history.size());
        issueWrapper.setHistory(history);
    }

    private void loadHistoryForIssues(final List<IIssue> existedInPastIssues) {

        int number = 1;
        LOGGER.debug("Fetching history of bugs...");
        for (final IIssue issue : existedInPastIssues) {
            if (number % 100 == 0) {
                LOGGER.info("Fetching history for bug {}/{}", number, existedInPastIssues.size());
            }
            loadHistory(issue);
            number++;
        }
    }

    private void sortHistoryFromMostRecentToOldest(
            final List<org.komea.product.database.dto.BugHistory> _history) {

        Collections.sort(_history, new MostRecentHistoryBefore());

    }
}
