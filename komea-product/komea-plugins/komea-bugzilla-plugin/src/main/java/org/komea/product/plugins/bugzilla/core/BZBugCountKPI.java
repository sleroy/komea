/**
 *
 */

package org.komea.product.plugins.bugzilla.core;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.backend.kpi.search.ISearchedElement;
import org.komea.product.backend.kpi.search.Search;
import org.komea.product.backend.kpi.search.SearchUtils;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.api.IBZServerProxy;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.plugins.bugzilla.model.BzBug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * This class defines a kpi based on counting the number of bugs answering to a
 * collection of criterias.
 * 
 * @author sleroy
 */
public class BZBugCountKPI implements IDynamicDataQuery
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BZBugCountKPI.class);
    
    
    
    public static BZBugCountKPI create(final Search... searchs) {
    
    
        return new BZBugCountKPI(Arrays.asList(searchs));
    }
    
    
    
    @Autowired
    private IBZConfigurationDAO   bugZillaConfiguration;
    
    @Autowired
    private IProjectService       projectService;
    
    @Autowired
    private IBZServerProxyFactory proxyFactory;
    
    private final List<Search>    searchs;
    
    
    
    public BZBugCountKPI() {
    
    
        searchs = Collections.EMPTY_LIST;
    }
    
    
    public BZBugCountKPI(final List<Search> searchs) {
    
    
        super();
        this.searchs = searchs;
    }
    
    
    public BZBugCountKPI(final String searchs) {
    
    
        this(SearchUtils.convert(searchs));
    }
    
    
    @Override
    public BackupDelay getBackupDelay() {
    
    
        return BackupDelay.DAY;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.dynamicdata.IDynamicDataQuery#getResult()
     */
    @Override
    public KpiResult getResult() {
    
    
        final KpiResult kpiResult = new KpiResult();
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
                    final List<BzBug> bugs = bugzillaProxy.getBugs(productName);
                    final List<ISearchedElement> searchedElements =
                            new ArrayList<ISearchedElement>();
                    for (final ISearchedElement searchedElement : bugs) {
                        searchedElements.add(searchedElement);
                    }
                    final int result =
                            SearchUtils.nbElementsMatchesSearches(searchedElements, searchs);
                    kpiResult.put(project.getEntityKey(), result);
                }
            } catch (final Exception ex) {
                LOGGER.error("Error during the bugzilla server update {} : reason {}",
                        conf.getAddress(), ex.getMessage(), ex);
            } finally {
                IOUtils.closeQuietly(bugzillaProxy);
            }
        }
        return kpiResult;
    }
    
    
    public void setBugZillaConfiguration(final IBZConfigurationDAO bugZillaConfiguration) {
    
    
        this.bugZillaConfiguration = bugZillaConfiguration;
    }
    
    
    public void setProjectService(final IProjectService ProjectService) {
    
    
        projectService = ProjectService;
    }
    
    
    public void setProxyFactory(final IBZServerProxyFactory proxyFactory) {
    
    
        this.proxyFactory = proxyFactory;
    }
    
    
    @Override
    public String toString() {
    
    
        return "BZBugCountKPI{" + "searchs=" + searchs + '}';
    }
    
}
