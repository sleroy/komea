
package org.komea.product.plugins.git.admin;



import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
import org.komea.product.wicket.widget.api.IEditAction;



public class GitEditAction implements IEditAction<GitRepositoryDefinition>
{
    
    
    private final GitRepositoryPage     gitRepositoryPage;
    private final IGitRepositoryService gitRepositoryDAO;
    
    
    
    public GitEditAction(
            final GitRepositoryPage _editedPersonPage,
            final IGitRepositoryService _gitRepositories) {
    
    
        gitRepositoryPage = _editedPersonPage;
        gitRepositoryDAO = _gitRepositories;
        
    }
    
    
    @Override
    public void selected(final GitRepositoryDefinition _object) {
    
    
        gitRepositoryPage.setResponsePage(new GitRepositoryAddPage(gitRepositoryPage
                .getPageParameters(), _object));
        
        
        //
        
    }
    
}
