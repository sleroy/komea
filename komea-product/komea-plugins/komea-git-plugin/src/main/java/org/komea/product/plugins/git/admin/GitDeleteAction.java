
package org.komea.product.plugins.git.admin;



import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
import org.komea.product.wicket.widget.api.IDeleteAction;



public class GitDeleteAction implements IDeleteAction<GitRepositoryDefinition>
{
    
    
    private final IGitRepositoryService gitRepositoryDef;
    
    
    
    public GitDeleteAction(final IGitRepositoryService _gitRepositories) {
    
    
        gitRepositoryDef = _gitRepositories;
        
        
    }
    
    
    @Override
    public void delete(final GitRepositoryDefinition _object) {
    
    
        gitRepositoryDef.remove(_object);
        
    }
}
