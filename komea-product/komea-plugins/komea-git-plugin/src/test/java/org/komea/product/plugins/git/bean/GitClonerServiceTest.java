/**
 * 
 */

package org.komea.product.plugins.git.bean;



import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.plugins.git.model.GitRepo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class GitClonerServiceTest
{
    
    
    @InjectMocks
    private GitClonerService gitClonerService;
    
    
    @Mock
    private IKomeaFS         komeaFS;
    @Mock
    private File             system;
    
    
    
    /**
     * Test method for {@link org.komea.product.plugins.git.bean.GitClonerService#getOrCreate(org.komea.product.plugins.git.model.GitRepo)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetOrCreate() throws Exception {
    
    
        // Should create a git cloner but fail on initializeation.
        final GitRepo gitID = new GitRepo();
        gitID.setId(1L);
        gitClonerService.setSystem(new File("target/tmp"));
        gitClonerService.getOrCreate(gitID);
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.git.bean.GitClonerService#initialize()}.
     */
    @Test
    public final void testInitialize() throws Exception {
    
    
        Mockito.when(komeaFS.getFileSystemFolder("git-clone-repository")).thenReturn(
                new File("/tmp"));
        gitClonerService.initialize();
        Assert.assertEquals("/tmp", gitClonerService.getSystem().getAbsolutePath());
    }
    
}
