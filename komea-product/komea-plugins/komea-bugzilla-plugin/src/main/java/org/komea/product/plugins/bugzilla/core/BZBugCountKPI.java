/**
 *
 */
package org.komea.product.plugins.bugzilla.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.api.formula.tuple.ITuple;
import org.komea.eventory.formula.tuple.TupleFactory;
import org.komea.eventory.formula.tuple.TupleResultMap;
import org.komea.eventory.query.CEPResult;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.api.IBZServerProxy;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.plugins.bugzilla.model.BzBug;
import org.komea.product.plugins.bugzilla.model.BzSearch;
import org.komea.product.plugins.bugzilla.service.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class defines a kpi based on counting the number of bugs answering to a
 * collection of criterias.
 *
 * @author sleroy
 */
public final class BZBugCountKPI implements IDynamicDataQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(BZBugCountKPI.class);

    public static BZBugCountKPI create(final BzSearch... searchs) {
        return new BZBugCountKPI(Arrays.asList(searchs));
    }

    private final List<BzSearch> searchs;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IBZConfigurationDAO bugZillaConfiguration;

    @Autowired
    private IBZServerProxyFactory proxyFactory;

    public BZBugCountKPI(final String searchs) {
        final List<String> searchsString = StringUtils.splitAndTrimWithoutEmpty(searchs, "#");
        final List<BzSearch> bzSearchs = new ArrayList<BzSearch>(searchsString.size());
        for (final String searchString : searchsString) {
            final BzSearch bzSearch = BzSearch.fromString(searchString);
            bzSearchs.add(bzSearch);
        }
        this.searchs = bzSearchs;
    }

    public BZBugCountKPI() {
        this("");
    }

    public BZBugCountKPI(final List<BzSearch> searchs) {
        super();
        this.searchs = searchs;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.cep.dynamicdata.IDynamicDataQuery#getResult()
     */
    @Override
    public ICEPResult getResult() {
        final TupleResultMap<Integer> tupleResultMap = new TupleResultMap<Integer>();
        for (final BZServerConfiguration conf : bugZillaConfiguration.selectAll()) {
            IBZServerProxy bugzillaProxy = null;
            try {
                bugzillaProxy = proxyFactory.newConnector(conf);
                Validate.notNull(bugzillaProxy);
                final List<String> productNames = bugzillaProxy.getProductNames();
                System.out.println("PRODUCT NAMES : " + productNames);
                for (final String productName : productNames) {
                    System.out.println("PRODUCT : " + productName);
                    final Project project = projectService.selectByKey(productName);
                    if (project == null) {
                        continue;
                    }
                    System.out.println("EXISTS !");
                    final ITuple tuple = TupleFactory.newTuple(project.getEntityKey());
                    final List<BzBug> bugs = bugzillaProxy.getBugs(productName);
                    System.out.println("BUGS SIZE : " + bugs.size());
                    int cpt = 0;
                    for (final BzBug bug : bugs) {
                        if (isBugMatchesAtLeastOneFilter(bug)) {
                            cpt++;
                        }
                    }
                    tupleResultMap.insertEntry(tuple, cpt);
                }
            } catch (final Exception ex) {
                LOGGER.error("Error during the bugzilla server update {} : reason {}",
                        conf.getAddress(), ex.getMessage(), ex);
            } finally {
                IOUtils.closeQuietly(bugzillaProxy);
            }
        }
        final ICEPResult result = CEPResult.buildFromMap(tupleResultMap);
        return result;
    }

    private boolean isBugMatchesSearch(final BzBug bug, final BzSearch search) {
        for (final String key : search.getParameterkeys()) {
            if (bug.getKeys().contains(key)) {
                final List<String> values = search.getValues(key);
                final Boolean accept = search.isAccept(key);
                final String parameter = bug.getParameter(key);
                if (accept != values.contains(parameter)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isBugMatchesAtLeastOneFilter(final BzBug bug) {
        for (final BzSearch search : searchs) {
            if (isBugMatchesSearch(bug, search)) {
                return true;
            }
        }
        return searchs.isEmpty();
    }

    public void setBugZillaConfiguration(IBZConfigurationDAO bugZillaConfiguration) {
        this.bugZillaConfiguration = bugZillaConfiguration;
    }

    public void setProxyFactory(IBZServerProxyFactory proxyFactory) {
        this.proxyFactory = proxyFactory;
    }

    public void setProjectService(IProjectService ProjectService) {
        this.projectService = ProjectService;
    }

    @Override
    public String toString() {
        return "BZBugCountKPI{" + "searchs=" + searchs + '}';
    }

}
