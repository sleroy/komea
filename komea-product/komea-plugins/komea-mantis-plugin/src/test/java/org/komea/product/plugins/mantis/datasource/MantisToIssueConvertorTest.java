/**
 *
 */

package org.komea.product.plugins.mantis.datasource;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.entities.IPersonService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class MantisToIssueConvertorTest
{
    
    
    @InjectMocks
    private MantisToIssueConvertor mantisToIssueConvertor;
    @Mock
    private IPersonService         personService;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.mantis.datasource.MantisToIssueConvertor#convert(biz.futureware.mantis.rpc.soap.client.IssueData, org.komea.product.database.model.Project, org.komea.product.plugins.mantis.model.MantisServerConfiguration)}
     * .
     */
    @Test
    public final void testConvert() throws Exception {
    
    
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.mantis.datasource.MantisToIssueConvertor#convertAll(java.util.List, org.komea.product.database.model.Project, org.komea.product.plugins.mantis.model.MantisServerConfiguration)}
     * .
     */
    @Test
    public final void testConvertAll() throws Exception {
    
    
        throw new RuntimeException("not yet implemented");
    }
    
}
