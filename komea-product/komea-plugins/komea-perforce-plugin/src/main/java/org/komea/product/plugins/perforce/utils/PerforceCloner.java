/**
 * 
 */

package org.komea.product.plugins.perforce.utils;



import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.plugin.IScmCloner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.perforce.p4java.server.IServer;
import com.perforce.p4java.server.ServerFactory;



/**
 * @author sleroy
 */
public class PerforceCloner implements IScmCloner
{
    
    
    private static final Logger           LOGGER = LoggerFactory.getLogger(PerforceCloner.class);
    
    
    private File                          scmClonedDirectory;
    private final ScmRepositoryDefinition scmRepositoryDefinition;
    private IServer                       server;
    
    
    private final File                    storageFolder;
    
    
    
    /**
     * Builds the repository.
     * 
     * @param _storageFolder
     * @param _repositoryName
     * @param _repositoryURL
     */
    public PerforceCloner(final File _storageFolder, final ScmRepositoryDefinition _gitRepo) {
    
    
        super();
        Validate.notNull(_storageFolder);
        Validate.notNull(_gitRepo);
        Validate.notNull(_gitRepo.getKey());
        storageFolder = _storageFolder;
        scmRepositoryDefinition = _gitRepo;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmCloner#cloneRepository()
     */
    @Override
    public void cloneRepository() {
    
    
        initializeStorageFolder();
        try {
            
            server = ServerFactory.getServer(scmRepositoryDefinition.getUrl(), null);
            server.setUserName(scmRepositoryDefinition.getUserName());
            server.login(scmRepositoryDefinition.getPassword(), true);
            
            server.connect();
            
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (server != null) {
                try {
                    server.disconnect();
                } catch (final Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        
        
    }
    
    
    public IServer getServer() {
    
    
        return server;
    }
    
    
    public void setScmClonedDirectory(final File _gitRepositoryFolder) {
    
    
        scmClonedDirectory = _gitRepositoryFolder;
    }
    
    
    public void setServer(final IServer _server) {
    
    
        server = _server;
    }
    
    
    /**
     * Initialize the storage folder.
     */
    private void initializeStorageFolder() {
    
    
        scmClonedDirectory = new File(storageFolder, "repo" + scmRepositoryDefinition.getKey());
        try {
            FileUtils.deleteDirectory(scmClonedDirectory);
        } catch (final IOException e) {
            LOGGER.error("Could not delete the folder : {}", scmClonedDirectory);
        }
        
        if (!scmClonedDirectory.exists() && !scmClonedDirectory.mkdirs()) { throw new IllegalArgumentException(
                "Could not create " + scmClonedDirectory); }
        
        scmClonedDirectory.deleteOnExit();
        scmRepositoryDefinition.setCloneDirectory(scmClonedDirectory);
    }
}
