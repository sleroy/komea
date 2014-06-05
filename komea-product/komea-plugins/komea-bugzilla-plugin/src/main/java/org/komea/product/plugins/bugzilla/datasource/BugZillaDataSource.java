/**
 * 
 */

package org.komea.product.plugins.bugzilla.datasource;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.product.backend.service.dataplugin.IDynamicDataSourcePool;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugtracker.datasource.IssuePlugin;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IIssuePlugin;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.api.IBZServerProxy;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.plugins.model.IDynamicDataSource;
import org.komea.product.plugins.model.IDynamicDataSourceSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.j2bugzilla.base.Bug;



/**
 * @author sleroy
 */
@Component("bz-source")
public class BugZillaDataSource implements IDynamicDataSource<IIssuePlugin>
{
    
    
    private static final Logger       LOGGER = LoggerFactory.getLogger(BugZillaDataSource.class);
    
    @Autowired
    private IBZConfigurationDAO       bugZillaConfiguration;
    
    @Autowired
    private BugZillaToIssueConvertor  bugZillaToIssueConvertor;
    
    
    @Autowired
    private IDynamicDataSourcePool    dataSourcePool;
    
    
    private IDynamicDataSourceSession dynamicDataSourceSession;
    
    @Autowired
    private IProjectService           projectService;
    
    @Autowired
    private IBZServerProxyFactory     proxyFactory;
    
    
    
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
    
    
    public IDynamicDataSourceSession getDynamicDataSourceSession() {
    
    
        return dynamicDataSourceSession;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("Registering data source");
        CacheConfigurationBuilder.create().maximumSize(1);
        setDynamicDataSourceSession(dataSourcePool.register("bugzilla-source", this,
                CacheConfigurationBuilder.expirationTimeCache(5, TimeUnit.MINUTES)));
        
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
            obtainIssuesForProjectsOfAServer(issues, conf, bugzillaProxy, productNames);
        } catch (final Exception ex) {
            LOGGER.error("Error during the bugzilla server update {} : reason {}",
                    conf.getAddress(), ex.getMessage(), ex);
        } finally {
            bugZillaConfiguration.saveOrUpdate(conf);
            IOUtils.closeQuietly(bugzillaProxy);
        }
    }
    
    
    public void obtainIssuesForProjectsOfAServer(
            final List<IIssue> issues,
            final BZServerConfiguration conf,
            final IBZServerProxy bugzillaProxy,
            final List<String> productNames) {
    
    
        for (final String productName : productNames) {
            final String projectKomeaName = conf.createOrRetrieveAliasForProjectName(productName);
            final Project project =
                    obtainProjectFromConfigurationAndProductName(conf, projectKomeaName);
            if (null == project) {
                continue;
            }
            final List<Bug> bugs = bugzillaProxy.getBugs(productName);
            issues.addAll(bugZillaToIssueConvertor.convertAll(bugs, project));
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
    
    
    public void setDynamicDataSourceSession(
            final IDynamicDataSourceSession _dynamicDataSourceSession) {
    
    
        dynamicDataSourceSession = _dynamicDataSourceSession;
    }
}
