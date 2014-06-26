/**
 *
 */

package org.komea.product.backend.csv.service;



import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.service.dto.EntityKey;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class CSVKpiResultServiceTest
{
    
    
    /**
     * @author sleroy
     */
    private final class AnswerImplementation implements Answer<IEntity>
    {
        
        
        @Override
        public IEntity answer(final InvocationOnMock _invocation) throws Throwable {
        
        
            final IEntity mock2 = mock(IEntity.class);
            when(mock2.getKey()).thenReturn(
                    "entity" + ((EntityKey) _invocation.getArguments()[0]).getId());
            return mock2;
        }
    }
    
    
    
    @InjectMocks
    private CSVKpiResultService  cSVKpiResultService;
    // @Mock
    private final IEntityService entityService = mock(IEntityService.class, Mockito.withSettings()
                                                       .verboseLogging());
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.csv.service.CSVKpiResultService#exportCSV(org.komea.product.database.dto.KpiResult, java.io.Writer)}
     * .
     */
    @Test
    public final void testExportCSV() throws Exception {
    
    
        when(entityService.getEntityOrFail(any(EntityKey.class))).thenAnswer(
                new AnswerImplementation());
        
        
        final StringWriter writer = new StringWriter();
        final KpiResult kpiResult = new KpiResult();
        
        kpiResult.put(EntityKey.of(EntityType.PERSON, 1), 40);
        kpiResult.put(EntityKey.of(EntityType.PERSON, 2), 80);
        kpiResult.put(EntityKey.of(EntityType.PERSON, 3), 20);
        cSVKpiResultService.exportCSV(kpiResult, writer);
        final FileInputStream fos = new FileInputStream(new File("src/test/resources/example.csv"));
        final String expected = IOUtils.toString(fos);
        IOUtils.closeQuietly(fos);
        
        System.out.println(writer.getBuffer().toString());
        assertEquals(expected, writer.getBuffer().toString());
        
        
    }
}
