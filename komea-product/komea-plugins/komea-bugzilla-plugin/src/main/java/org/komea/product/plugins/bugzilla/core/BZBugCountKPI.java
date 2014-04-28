/**
 *
 */
package org.komea.product.plugins.bugzilla.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.komea.product.backend.kpi.search.ISearchedElement;
import org.komea.product.backend.kpi.search.Search;
import org.komea.product.backend.kpi.search.SearchUtils;
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

    public static BZBugCountKPI create(final Search... searchs) {
        return new BZBugCountKPI(Arrays.asList(searchs));
    }

    private final List<Search> searchs;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IBZConfigurationDAO bugZillaConfiguration;

    @Autowired
    private IBZServerProxyFactory proxyFactory;

    public BZBugCountKPI(final String searchs) {
        this(SearchUtils.convert(searchs));
    }

    public BZBugCountKPI() {
        this(Collections.<Search>emptyList());
    }

    public BZBugCountKPI(final List<Search> searchs) {
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
                for (final String productName : productNames) {
                    final Project project = projectService.selectByKey(productName);
                    if (project == null) {
                        continue;
                    }
                    final ITuple tuple = TupleFactory.newTuple(project.getEntityKey());
                    final List<BzBug> bugs = bugzillaProxy.getBugs(productName);
                    final List<ISearchedElement> searchedElements = new ArrayList<ISearchedElement>();
                    for (final ISearchedElement searchedElement : bugs) {
                        searchedElements.add(searchedElement);
                    }
                    int result = SearchUtils.nbElementsMatchesSearches(searchedElements, searchs);
                    tupleResultMap.insertEntry(tuple, result);
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

    @Override
    public String getFormula() {
        return "new " + this.getClass().getName() + "(\"" + SearchUtils.convert(searchs) + "\")";
    }

}
