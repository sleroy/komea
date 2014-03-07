/**
 * 
 */

package org.komea.product.plugins.git.bean;



import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.plugins.git.cron.GitCloner;
import org.komea.product.plugins.git.model.GitRepo;
import org.komea.product.plugins.git.repositories.api.IGitCloner;
import org.komea.product.plugins.git.repositories.api.IGitClonerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * This class defines the manager to handler cloned repository on the local disk.
 * 
 * @author sleroy
 */
@Service
public class GitClonerService implements IGitClonerService
{
    
    
    private static org.slf4j.Logger    LOGGER            = LoggerFactory.getLogger("git-cloner");
    
    private final Map<Long, GitCloner> clonedDirectories = new HashMap<Long, GitCloner>();
    
    @Autowired
    private IKomeaFS                   komeaFS;
    
    
    
    public IKomeaFS getKomeaFS() {
    
    
        return komeaFS;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.bean.IGitClonerService#getCloner(java.lang.String)
     */
    @Override
    public IGitCloner getOrCreate(final GitRepo _gitID) {
    
    
        if (clonedDirectories.containsKey(_gitID)) { return clonedDirectories.get(_gitID); }
        LOGGER.info("Cloning workspace for git repo : {}", _gitID.getRepoName());
        final GitCloner gitCloner = new GitCloner(getSystem(), _gitID);
        gitCloner.initRepository();
        clonedDirectories.put(_gitID.getId(), gitCloner);
        return gitCloner;
    }
    
    
    public File getSystem() {
    
    
        return komeaFS.getFileSystemFolder("git-clone-repository");
    }
    
    
    public void setKomeaFS(final IKomeaFS _komeaFS) {
    
    
        komeaFS = _komeaFS;
    }
    
    
}
