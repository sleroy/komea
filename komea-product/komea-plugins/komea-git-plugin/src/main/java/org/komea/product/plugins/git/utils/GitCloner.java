
package org.komea.product.plugins.git.utils;



import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.plugin.IScmCloner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class GitCloner implements IScmCloner
{
    
    
    private static final Logger           LOGGER = LoggerFactory.getLogger(GitCloner.class);
    
    
    private Repository                    fileRepository;
    
    
    private Git                           git;
    
    private File                          scmClonedDirectory;
    private final ScmRepositoryDefinition scmRepositoryDefinition;
    private final File                    storageFolder;
    
    
    
    /**
     * Builds the repository.
     * 
     * @param _storageFolder
     * @param _repositoryName
     * @param _repositoryURL
     */
    public GitCloner(final File _storageFolder, final ScmRepositoryDefinition _gitRepo) {
    
    
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
            final CloneCommand cloneRepository = Git.cloneRepository();
            
            cloneRepository.setCloneAllBranches(true);
            cloneRepository.setNoCheckout(true);
            cloneRepository.setProgressMonitor(new TextProgressMonitor());
            
            
            if (StringUtils.isNotEmpty(scmRepositoryDefinition.getUserName())
                    && StringUtils.isNotEmpty(scmRepositoryDefinition.getPassword())) {
                final UsernamePasswordCredentialsProvider credentials =
                        new UsernamePasswordCredentialsProvider(
                                scmRepositoryDefinition.getUserName(),
                                scmRepositoryDefinition.getPassword());
                
                cloneRepository.setCredentialsProvider(credentials);
            }
            
            git =
                    cloneRepository.setURI(scmRepositoryDefinition.getUrl())
                            .setDirectory(scmClonedDirectory).call();
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
        fileRepository = git.getRepository();
        
    }
    
    
    public void setScmClonedDirectory(final File _gitRepositoryFolder) {
    
    
        scmClonedDirectory = _gitRepositoryFolder;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "GitCloner [fileRepository="
                + fileRepository + ", git=" + git + ", scmClonedDirectory=" + scmClonedDirectory
                + ", scmRepositoryDefinition=" + scmRepositoryDefinition + ", storageFolder="
                + storageFolder + "]";
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


    public Git getGit() {
    
    
        return git;
    }


    public void setGit(Git _git) {
    
    
        git = _git;
    }
}
