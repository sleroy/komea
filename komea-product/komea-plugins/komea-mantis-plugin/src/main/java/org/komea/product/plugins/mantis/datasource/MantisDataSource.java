/**
 *
 */

package org.komea.product.plugins.mantis.datasource;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * @author sleroy
 */
@Component("mantis-source")
public class MantisDataSource implements IDynamicDataSource<IIssuePlugin>
{
    
    
    private static final Logger      LOGGER = LoggerFactory.getLogger(MantisDataSource.class);
    
    @Autowired
    private IBZConfigurationDAO      bugZillaConfiguration;
    
    @Autowired
    private MantisToIssueConvertor mantisToIssueConvertor;
    
    
    @Autowired
    private IDynamicDataSourcePool   dataSourcePool;
    
    
    @Autowired
    private IProjectService          projectService;
    
    @Autowired
    private IBZServerProxyFactory    proxyFactory;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataSource#getData()
     */
    @Override
    public IIssuePlugin fetchData() {
    
    
        final List<IIssue> issues = new ArrayList(1000);
        for (final BZServerConfiguration conf : bugZillaConfiguration.selectAll()) {
            final IBZServerProxy bugzillaProxy = null;
            obtainIssuesForEachBugZillaServer(issues, conf, bugzillaProxy);
        }
        return new IssuePlugin(issues);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataSource#getDefinition()
     */
    @Override
    public Class<IIssuePlugin> getDefinition() {
    
    
        return IIssuePlugin.class;
    }
    
    
    public void obtainIssuesForEachBugZillaServer(
            final List<IIssue> issues,
            final BZServerConfiguration conf,
            IBZServerProxy bugzillaProxy) {
    
    
        try {
            bugzillaProxy = proxyFactory.newConnector(conf);
            Validate.notNull(bugzillaProxy);
            final List<String> productNames = bugzillaProxy.getProductNames();
            LOGGER.info("List of projects found on the server {} => {}", conf.getAddress(),
                    productNames);
            obtainIssuesForProjectsOfAServer(conf, bugzillaProxy, issues, productNames);
        } catch (final Exception ex) {
            LOGGER.error("Error during the bugzilla server update {} : reason {}",
                    conf.getAddress(), ex.getMessage(), ex);
        } finally {
            bugZillaConfiguration.saveOrUpdate(conf);
            IOUtils.closeQuietly(bugzillaProxy);
        }
    }
    
    
    public void obtainIssuesForProjectsOfAServer(
            final BZServerConfiguration conf,
            final IBZServerProxy bugzillaProxy,
            final List<IIssue> issues,
            final List<String> productNames) {
    
    
        for (final String productName : productNames) {
            final Project project = obtainProjectFromConfigurationAndProductName(conf, productName);
            if (null == project) {
                continue;
            }
            final List<Bug> bugs = bugzillaProxy.getBugs(productName);
            issues.addAll(mantisToIssueConvertor.convertAll(bugs, project, conf));
        }
    }
    
    
    public Project obtainProjectFromConfigurationAndProductName(
            final BZServerConfiguration conf,
            final String projectKomeaName) {
    
    
        Project project = projectService.selectByKey(projectKomeaName);
        if (project == null) {
            if (conf.isAutocreateProjects()) {
                project = projectService.getOrCreate(projectKomeaName);
            } else {
                
            }
        }
        return project;
    }


    public void setBugZillaConfiguration(final IBZConfigurationDAO _bugZillaConfiguration) {
    
    
        bugZillaConfiguration = _bugZillaConfiguration;
    }
    
    
}
