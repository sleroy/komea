/**
 * 
 */

package org.komea.product.plugins.git.repositories.api;



import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;



/**
 * @author sleroy
 *
 */
public interface IGitCloner
{
    
    
    public abstract Git getGit();
    
    
    public abstract File getGitRepositoryFolder();
    
    
    public abstract Repository getRepository();
    
    
    public abstract File getStorageFolder();
    
}
