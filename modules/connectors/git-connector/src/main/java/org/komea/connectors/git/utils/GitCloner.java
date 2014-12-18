
package org.komea.connectors.git.utils;


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
import org.komea.connectors.git.impl.ScmRepositoryDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitCloner
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
        Validate.notNull(_gitRepo.getName());
        this.storageFolder = _storageFolder;
        this.scmRepositoryDefinition = _gitRepo;

    }

    public void cloneRepository() {

        initializeStorageFolder();
        try {
            final CloneCommand cloneRepository = Git.cloneRepository();

            cloneRepository.setCloneAllBranches(true);
            cloneRepository.setNoCheckout(true);
            cloneRepository.setProgressMonitor(new TextProgressMonitor());

            if (StringUtils.isNotEmpty(this.scmRepositoryDefinition.getUserName())
                    && StringUtils.isNotEmpty(this.scmRepositoryDefinition.getPassword())) {
                final UsernamePasswordCredentialsProvider credentials = new UsernamePasswordCredentialsProvider(
                        this.scmRepositoryDefinition.getUserName(), this.scmRepositoryDefinition.getPassword());

                cloneRepository.setCredentialsProvider(credentials);
            }

            this.git = cloneRepository.setURI(this.scmRepositoryDefinition.getUrl()).setDirectory(this.scmClonedDirectory).call();
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
        this.fileRepository = this.git.getRepository();

    }

    public Git getGit() {

        return this.git;
    }
    
    /**
     * Initialize the storage folder.
     */
    private void initializeStorageFolder() {

        this.scmClonedDirectory = new File(this.storageFolder, this.scmRepositoryDefinition.getName());
        try {
            FileUtils.deleteDirectory(this.scmClonedDirectory);
        } catch (final IOException e) {
            LOGGER.error("Could not delete the folder : {}", this.scmClonedDirectory);
        }

        if (!this.scmClonedDirectory.exists() && !this.scmClonedDirectory.mkdirs()) {
            throw new IllegalArgumentException("Could not create " + this.scmClonedDirectory);
        }

        this.scmClonedDirectory.deleteOnExit();
        this.scmRepositoryDefinition.setCloneDirectory(new File(this.scmClonedDirectory + "/.git"));
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "GitCloner [fileRepository=" + this.fileRepository + ", git=" + this.git + ", scmClonedDirectory=" + this.scmClonedDirectory
                + ", scmRepositoryDefinition=" + this.scmRepositoryDefinition + ", storageFolder=" + this.storageFolder + "]";
    }
}
