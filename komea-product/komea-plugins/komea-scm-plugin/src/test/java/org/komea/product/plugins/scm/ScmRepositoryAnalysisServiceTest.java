/**
 * 
 */

package org.komea.product.plugins.scm;



import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.Person;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.plugin.IScmCommit;
import org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy;
import org.komea.product.plugins.scm.api.plugin.ScmCommit;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class ScmRepositoryAnalysisServiceTest
{
    
    
    @Mock
    private IEventPushService            esperEngine;
    
    
    @Mock
    private IPersonService               personService;
    @Mock
    private IScmRepositoryProxy          proxy;
    
    
    @InjectMocks
    private ScmRepositoryAnalysisService scmRepositoryAnalysisService;
    
    
    
    @Before
    public void beforeTest() {
    
    
        final ScmEventFactory eventFactory = new ScmEventFactory(new ScmRepositoryDefinition());
        when(proxy.getEventFactory()).thenReturn(eventFactory);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.scm.ScmRepositoryAnalysisService#analysis(org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy)}
     * .
     */
    @Test 
    public final void testAnalysis() throws Exception {
    
    
        // SHoult not fail
        
        final ScmRepositoryDefinition value = new ScmRepositoryDefinition();
        when(proxy.getRepositoryDefinition()).thenReturn(value);
        
        scmRepositoryAnalysisService.analysis(proxy);
        verify(esperEngine, times(2)).sendEventDto(Mockito.any(EventSimpleDto.class));
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.scm.ScmRepositoryAnalysisService#checkNewCommits(org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy, java.lang.String)}
     * .
     */
    @Test 
    public final void testCheckNewCommits() throws Exception {
    
    
        final ScmCommit scmCommit = new ScmCommit();
        scmCommit.setAuthor(new Person());
        final IScmCommit iScmCommit = scmCommit;
        final List<IScmCommit> list = Lists.newArrayList(iScmCommit, iScmCommit);
        final ScmRepositoryDefinition value = new ScmRepositoryDefinition();
        when(proxy.getRepositoryDefinition()).thenReturn(value);
        value.setLastDateCheckout(new Date());
        when(proxy.getAllCommitsFromABranch(Matchers.eq("TRUC"), Matchers.any(DateTime.class)))
                .thenReturn(list);
        
        scmRepositoryAnalysisService.checkNewCommits(proxy, "TRUC");
        
        final ArgumentCaptor<EventSimpleDto> forClass =
                ArgumentCaptor.forClass(EventSimpleDto.class);
        
        verify(esperEngine, times(2)).sendEventDto(forClass.capture());
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.scm.ScmRepositoryAnalysisService#checkNumberOfBranches(org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy)}
     * .
     */
    @Test 
    public final void testCheckNumberOfBranches() throws Exception {
    
    
        when(proxy.getBranches()).thenReturn(Lists.newArrayList("BRANCH1", "BRANCH2"));
        
        scmRepositoryAnalysisService.checkNumberOfBranches(proxy);
        final ArgumentCaptor<EventSimpleDto> forClass =
                ArgumentCaptor.forClass(EventSimpleDto.class);
        
        verify(esperEngine, times(1)).sendEventDto(forClass.capture());
        final EventSimpleDto value = forClass.getValue();
        assertEquals(2.0, value.getValue(), 0.0);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.scm.ScmRepositoryAnalysisService#checkNumberOfTagsPerBranch(org.komea.product.plugins.scm.api.plugin.IScmRepositoryProxy, java.lang.String)}
     * .
     */
    @Test 
    public final void testCheckNumberOfTagsPerBranch() throws Exception {
    
    
        when(proxy.getAllTagsFromABranch("TRUC")).thenReturn(Sets.newHashSet("BRANCH1", "BRANCH2"));
        
        scmRepositoryAnalysisService.checkNumberOfTagsPerBranch(proxy, "TRUC");
        final ArgumentCaptor<EventSimpleDto> forClass =
                ArgumentCaptor.forClass(EventSimpleDto.class);
        
        verify(esperEngine, times(1)).sendEventDto(forClass.capture());
        final EventSimpleDto value = forClass.getValue();
        assertEquals(2.0, value.getValue(), 0.0);
    }
    
}
