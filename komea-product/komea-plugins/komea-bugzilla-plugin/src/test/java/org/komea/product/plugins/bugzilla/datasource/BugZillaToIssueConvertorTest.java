/**
 * 
 */

package org.komea.product.plugins.bugzilla.datasource;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugzilla.api.IBugZillaToIssueConvertor;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.j2bugzilla.base.Bug;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class BugZillaToIssueConvertorTest
{
    
    
    @InjectMocks
    private IBugZillaToIssueConvertor bugZillaToIssueConvertor;
    @Mock
    private IPersonService           personService;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.bugzilla.datasource.BugZillaToIssueConvertor#convert(com.j2bugzilla.base.Bug, org.komea.product.database.model.Project)}
     * .
     */
    @Test
    public final void testConvert() throws Exception {
    
    
        final Bug mock = mock(Bug.class);
        assertTrue(bugZillaToIssueConvertor.convert(mock, new Project(),
                new BZServerConfiguration()) instanceof BZIssueWrapper);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.bugzilla.datasource.BugZillaToIssueConvertor#convertAll(java.util.List, org.komea.product.database.model.Project)}
     * .
     */
    @Test
    public final void testConvertAll() throws Exception {
    
    
        assertEquals(
                3,
                bugZillaToIssueConvertor.convertAll(
                        Lists.newArrayList(mock(Bug.class), mock(Bug.class), mock(Bug.class)),
                        new Project(), new BZServerConfiguration()).size());
    }
    
}
