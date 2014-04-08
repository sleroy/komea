
package org.komea.product.plugins.demodata;



import org.springframework.stereotype.Component;



/**
 * Inserts a default RSS Feed.
 * 
 * @author sleroy
 */
@Component
public class GitRepositoryExampleBean
{
    
    //
    // private static final String DEFAULT_VALUE = "0";
    //
    // private static final String GIT_REPOSITORY_SAMPLES = "GIT_REPOSITORY_SAMPLES";
    //
    // private static Logger LOGGER =
    // LoggerFactory
    // .getLogger(GitRepositoryExampleBean.class);
    //
    // @Autowired
    // private IScmRepositoryService repository;
    //
    //
    // @Autowired
    // private ISettingService settingsService;
    //
    //
    //
    // @PostConstruct
    // public void initGitFeed() {
    //
    //
    // registerRepo("XStream Fedora GIT", "http://pkgs.fedoraproject.org/cgit/xstream.git",
    // "XSTREAM");
    // registerRepo("Maven GIT", "https://git-wip-us.apache.org/repos/asf/maven.git", "MAVEN");
    // registerRepo("Scertify GIT", "http://sleroy@zeus:8080/scm/git/scertify", "SCERTIFY");
    // registerRepo("SRA GIT", "http://sleroy@zeus:8080/scm/git/web-applications", "SRA");
    // registerRepo("CIFlow prototype", "http://sleroy@zeus:8080/scm/git/scertify-ciflow",
    // "CIFLOW");
    //
    // }
    //
    //
    // /**
    // * @param _feedName
    // * @param _url
    // * @return
    // */
    // private ScmRepositoryDefinition newGitRepository(final String _feedName, final String _url) {
    //
    //
    // final ScmRepositoryDefinition scmRepositoryDefinition = new ScmRepositoryDefinition();
    // scmRepositoryDefinition.setType("GIT");
    // scmRepositoryDefinition.setRepoName(_feedName);
    // scmRepositoryDefinition.setKey(ScmRepositoryDefinition.transformNameInKey(_feedName));
    // scmRepositoryDefinition.setUrl(_url);
    // return scmRepositoryDefinition;
    // }
    //
    //
    // private void registerRepo(final String _feedName, final String _url, final String _project) {
    //
    //
    // LOGGER.info("Registering demo GIT repositories {} {} {}", _feedName, _url, _project);
    // final ScmRepositoryDefinition gitRepository = newGitRepository(_feedName, _url);
    // gitRepository.setProjectForRepository(_project);
    // repository.saveOrUpdate(gitRepository);
    //
    // }
}
