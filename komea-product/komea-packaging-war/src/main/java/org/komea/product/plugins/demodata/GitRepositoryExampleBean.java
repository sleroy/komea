
package org.komea.product.plugins.demodata;



import javax.annotation.PostConstruct;

import org.komea.product.backend.service.ISettingService;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * Inserts a default RSS Feed.
 * 
 * @author sleroy
 */
@Component
public class GitRepositoryExampleBean
{
    
    
    private static final String   DEFAULT_VALUE          = "0";
    
    private static final String   GIT_REPOSITORY_SAMPLES = "GIT_REPOSITORY_SAMPLES";
    
    @Autowired
    private IGitRepositoryService repository;
    
    @Autowired
    private ISettingService       settingsService;
    
    
    
    @PostConstruct
    public void initRssFeed() {
    
    
        registerRepo("XStream Fedora GIT", "http://pkgs.fedoraproject.org/cgit/xstream.git",
                "XSTREAM");
        registerRepo("Maven GIT", "https://git-wip-us.apache.org/repos/asf/maven.git", "MAVEN");
        registerRepo("Scertify GIT", "http://sleroy@zeus:8080/scm/git/scertify", "SCERTIFY");
        registerRepo("SRA GIT", "http://sleroy@zeus:8080/scm/git/web-applications", "SRA");
        registerRepo("CIFlow prototype", "http://sleroy@zeus:8080/scm/git/scertify-ciflow",
                "CIFLOW");
        
    }
    
    
    private void registerRepo(final String _feedName, final String _url, final String _project) {
    
    
        final GitRepositoryDefinition gitRepository =
                GitRepositoryDefinition.newGitRepository(_feedName, _url);
        gitRepository.setProjectForRepository(_project);
        repository.saveOrUpdate(gitRepository);
        
    }
}
