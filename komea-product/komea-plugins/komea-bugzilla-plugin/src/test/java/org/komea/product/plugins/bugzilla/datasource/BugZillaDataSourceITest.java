/**
 *
 */

package org.komea.product.plugins.bugzilla.datasource;



import java.util.List;

import org.junit.Test;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertFalse;

import static org.mockito.Mockito.when;



/**
 * This is AN INTEGRATION TEST, depending of the bugzilla on EOS.
 *
 * @author sleroy
 */

public class BugZillaDataSourceITest extends AbstractSpringIntegrationTestCase
{


    private final IBZConfigurationDAO bugZillaConfiguration =
            Mockito.mock(IBZConfigurationDAO.class);

    @Autowired
    private ISpringService            springService;



    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.datasource.BugZillaDataSource#fetchData()}.
     */
    @Test
    public final void testFetchData() throws Exception {


        final BugZillaDataSource bugZillaDataSource = new BugZillaDataSource();
        springService.autowirePojo(bugZillaDataSource);
        when(bugZillaConfiguration.selectAll()).thenReturn(Lists.newArrayList(fakeConfiguration()));

        bugZillaDataSource.setBugZillaConfiguration(bugZillaConfiguration);
        // EXECUTION WITH PROXY CONTAINING TWO PROJECTS , with both 3 bugs
        final List<IIssue> fetchData = bugZillaDataSource.getData();
        assertFalse(fetchData.isEmpty());

    }


    /**
     * @return
     */
    private BZServerConfiguration fakeConfiguration() {


        final BZServerConfiguration bzServerConfiguration = new BZServerConfiguration();

        bzServerConfiguration.setAddress("http://eos.tocea/bugzilla/");
        bzServerConfiguration.setLogin("jeremie.guidoux@tocea.com");
        bzServerConfiguration.setPassword("tocea35");
        bzServerConfiguration.setReminderAlert(10);
        bzServerConfiguration.setAutocreateProjects(true);
        return bzServerConfiguration;
    }

}
