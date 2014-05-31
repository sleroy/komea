/**
 *
 */

package org.komea.product.plugins.mantis.core;



import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.mantis.api.IMantisConfigurationDAO;
import org.komea.product.plugins.mantis.api.IMantisServerProxy;
import org.komea.product.plugins.mantis.api.IMantisServerProxyFactory;
import org.komea.product.plugins.mantis.model.MantisServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import biz.futureware.mantis.rpc.soap.client.IssueData;

import com.google.common.collect.Iterables;



/**
 * This class defines a kpi based on counting the number of bugs answering to a
 * collection of criterias.
 * 
 * @author sleroy
 */
public class MantisBugCountKPI implements IDynamicDataQuery<KpiResult>
{
    
    
    private static final Logger          LOGGER = LoggerFactory.getLogger(MantisBugCountKPI.class);
    
    
    @Autowired
    private IMantisConfigurationDAO      bugZillaConfiguration;
    
    
    private final IBugMatcher<IssueData> matcher;
    
    @Autowired
    private IProjectService              projectService;
    
    
    @Autowired
    private IMantisServerProxyFactory    proxyFactory;
    
    
    
    public MantisBugCountKPI(final IBugMatcher<IssueData> _matcher) {
    
    
        super();
        matcher = _matcher;
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
        for (final MantisServerConfiguration conf : bugZillaConfiguration.selectAll()) {
            IMantisServerProxy bugzillaProxy = null;
            try {
                bugzillaProxy = proxyFactory.newConnector(conf);
                Validate.notNull(bugzillaProxy);
                for (final String productName : bugzillaProxy.getProductNames()) {
                    countBugsPerProject(kpiResult, bugzillaProxy, productName);
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
    
    
    public void setMantisConfiguration(final IMantisConfigurationDAO bugZillaConfiguration) {
    
    
        this.bugZillaConfiguration = bugZillaConfiguration;
    }
    
    
    public void setProjectService(final IProjectService ProjectService) {
    
    
        projectService = ProjectService;
    }
    
    
    public void setProxyFactory(final IMantisServerProxyFactory proxyFactory) {
    
    
        this.proxyFactory = proxyFactory;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "MantisBugCountKPI [\\n\\tbugZillaConfiguration="
                + bugZillaConfiguration + ", \\n\\tmatcher=" + matcher + ", \\n\\tprojectService="
                + projectService + ", \\n\\tproxyFactory=" + proxyFactory + "]";
    }
    
    
    private void countBugsPerProject(
            final KpiResult kpiResult,
            final IMantisServerProxy bugzillaProxy,
            final String productName) {
    
    
        final Project project = projectService.getOrCreate(productName);
        Iterable<IssueData> bugs = bugzillaProxy.getBugs(productName);
        bugs = Iterables.filter(bugs, new MatcherPredicate(matcher));
        
        kpiResult.put(project.getEntityKey(), Iterables.size(bugs));
    }
    
}
