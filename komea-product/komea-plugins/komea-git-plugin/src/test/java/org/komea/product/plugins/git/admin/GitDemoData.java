/**
 * 
 */

package org.komea.product.plugins.git.admin;



import org.komea.product.plugins.git.model.GitRepositoryDefinition;



/**
 * @author sleroy
 */
public class GitDemoData
{
    
    
    public static GitRepositoryDefinition buildRepositoryDemo() {
    
    
        final GitRepositoryDefinition gitRepositoryDefinition = new GitRepositoryDefinition();
        gitRepositoryDefinition.setKey("GIT_REPO");
        gitRepositoryDefinition.setRepoName("reporepo");
        return gitRepositoryDefinition;
    }
}
