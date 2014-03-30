
package org.komea.product.plugins.git.cron;



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
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitCloner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class GitCloner implements IGitCloner
{
    
    
    private static final Logger           LOGGER = LoggerFactory.getLogger(GitCloner.class);
    private Repository                    fileRepository;
    
    private Git                           git;
    private final GitRepositoryDefinition gitRepositoryDefinition;
    private File                          gitRepositoryFolder;
    private final File                    storageFolder;
    
    
    
    /**
     * Builds the repository.
     * 
     * @param _storageFolder
     * @param _repositoryName
     * @param _repositoryURL
     */
    public GitCloner(final File _storageFolder, final GitRepositoryDefinition _gitRepo) {
    
    
        super();
        Validate.notNull(_storageFolder);
        Validate.notNull(_gitRepo);
        Validate.notNull(_gitRepo.getKey());
        storageFolder = _storageFolder;
        gitRepositoryDefinition = _gitRepo;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.utils.IGitCloner#getGit()
     */
    @Override
    public Git getGit() {
    
    
        return git;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.utils.IGitCloner#getGitRepositoryFolder()
     */
    @Override
    public File getGitRepositoryFolder() {
    
    
        return gitRepositoryFolder;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.utils.IGitCloner#getRepository()
     */
    @Override
    public Repository getRepository() {
    
    
        return fileRepository;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.git.utils.IGitCloner#getStorageFolder()
     */
    @Override
    public File getStorageFolder() {
    
    
        return storageFolder;
    }
    
    
    public void initRepository() {
    
    
        initializeStorageFolder();
        try {
            final CloneCommand cloneRepository = Git.cloneRepository();
            
            cloneRepository.setCloneAllBranches(true);
            cloneRepository.setNoCheckout(true);
            
            cloneRepository.setProgressMonitor(new TextProgressMonitor());
            
            
            if (StringUtils.isNotEmpty(gitRepositoryDefinition.getUserName())
                    && StringUtils.isNotEmpty(gitRepositoryDefinition.getPassword())) {
                final UsernamePasswordCredentialsProvider credentials =
                        new UsernamePasswordCredentialsProvider(
                                gitRepositoryDefinition.getUserName(),
                                gitRepositoryDefinition.getPassword());
                
                cloneRepository.setCredentialsProvider(credentials);
            }
            
            git =
                    cloneRepository.setURI(gitRepositoryDefinition.getUrl())
                            .setDirectory(gitRepositoryFolder).call();
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
        fileRepository = git.getRepository();
        
    }
    
    
    public void setGitRepositoryFolder(final File _gitRepositoryFolder) {
    
    
        gitRepositoryFolder = _gitRepositoryFolder;
    }
    
    
    private void initializeStorageFolder() {
    
    
        gitRepositoryFolder = new File(storageFolder, "repo" + gitRepositoryDefinition.getKey());
        try {
            FileUtils.deleteDirectory(gitRepositoryFolder);
        } catch (final IOException e) {
            LOGGER.error("Could not delete the folder : {}", gitRepositoryFolder);
        }
        
        if (!gitRepositoryFolder.exists() && !gitRepositoryFolder.mkdirs()) { throw new IllegalArgumentException(
                "Could not create " + gitRepositoryFolder); }
        
        gitRepositoryFolder.deleteOnExit();
        gitRepositoryDefinition.setCloneDirectory(gitRepositoryFolder);
    }
}
