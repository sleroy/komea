/**
 *
 */

package org.komea.product.plugins.mantis.datasource;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.komea.product.backend.service.dataplugin.IDynamicDataSourcePool;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugtracker.datasource.IssuePlugin;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IIssuePlugin;
import org.komea.product.plugins.mantis.api.IMantisConfigurationDAO;
import org.komea.product.plugins.mantis.api.IMantisServerProxy;
import org.komea.product.plugins.mantis.api.IMantisServerProxyFactory;
import org.komea.product.plugins.mantis.model.MantisServerConfiguration;
import org.komea.product.plugins.model.IDynamicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import biz.futureware.mantis.rpc.soap.client.IssueData;



/**
 * @author sleroy
 */
@Component("mantis-source")
public class MantisDataSource implements IDynamicDataSource<IIssuePlugin>
{


    private static final Logger       LOGGER = LoggerFactory.getLogger(MantisDataSource.class);

    @Autowired
    private IDynamicDataSourcePool    dataSourcePool;

    @Autowired
    private IMantisConfigurationDAO   mantisConfiguration;


    @Autowired
    private MantisToIssueConvertor    mantisToIssueConvertor;


    @Autowired
    private IProjectService           projectService;

    @Autowired
    private IMantisServerProxyFactory proxyFactory;



    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataSource#getData()
     */
    @Override
    public IIssuePlugin fetchData() {


        final List<IIssue> issues = new ArrayList(1000);
        
        final List<MantisServerConfiguration> selectAll = mantisConfiguration.selectAll();
        LOGGER.info("Fetching issues from {} servers", selectAll.size());
        for (final MantisServerConfiguration conf : selectAll) {
            
            final IMantisServerProxy bugzillaProxy = null;
            obtainIssuesForEachMantisServer(issues, conf, bugzillaProxy);
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


    public void obtainIssuesForEachMantisServer(
            final List<IIssue> issues,
            final MantisServerConfiguration conf,
            IMantisServerProxy mantisProxy) {


        try {
            LOGGER.info("Scanning issues from {}", conf.getAddress());
            mantisProxy = proxyFactory.newConnector(conf);
            Validate.notNull(mantisProxy);
            final List<String> productNames = mantisProxy.getProductNames();
            LOGGER.info("List of projects found on the server {} => {}", conf.getAddress(),
                    productNames);
            obtainIssuesForProjectsOfAServer(conf, mantisProxy, issues, productNames);
        } catch (final Exception ex) {
            LOGGER.error("Error during the bugzilla server update {} : reason {}",
                    conf.getAddress(), ex.getMessage(), ex);
        } finally {
            mantisConfiguration.saveOrUpdate(conf);
            IOUtils.closeQuietly(mantisProxy);
        }
    }


    public void obtainIssuesForProjectsOfAServer(
            final MantisServerConfiguration conf,
            final IMantisServerProxy mantisProxy,
            final List<IIssue> issues,
            final List<String> productNames) {


        for (final String productName : productNames) {
            LOGGER.info("Fetching issues for the project {}", productName);
            final Project project = obtainProjectFromConfigurationAndProductName(conf, productName);
            if (null == project) {
                continue;
            }
            final List<IssueData> bugs = mantisProxy.getBugs(productName);
            issues.addAll(mantisToIssueConvertor.convertAll(bugs, project, conf));
            LOGGER.info("issues {} collected for the project {}", bugs.size(), productName);
        }
    }


    public Project obtainProjectFromConfigurationAndProductName(
            final MantisServerConfiguration conf,
            final String projectName) {


        Project project = projectService.selectByKey(projectName);
        if (project == null) {
            if (conf.isAutocreateProjects()) {
                project = projectService.getOrCreate(projectName);
            } else {

            }
        }
        return project;
    }


    public void setMantisConfiguration(final IMantisConfigurationDAO _mantisConfiguration) {


        mantisConfiguration = _mantisConfiguration;
    }


    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {


        return "mantis-datasource";
    }
}
