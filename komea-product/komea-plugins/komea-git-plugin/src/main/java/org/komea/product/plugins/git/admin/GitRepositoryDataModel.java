
package org.komea.product.plugins.git.admin;



import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
import org.komea.product.wicket.utils.IteratorUtil;



public final class GitRepositoryDataModel extends
        SortableDataProvider<GitRepositoryDefinition, String>
{
    
    
    private final IGitRepositoryService gitRepositoryService;
    
    
    
    public GitRepositoryDataModel(final IGitRepositoryService _gitRepository) {
    
    
        gitRepositoryService = _gitRepository;
        
        
    }
    
    
    @Override
    public Iterator<? extends GitRepositoryDefinition> iterator(final long _first, final long _count) {
    
    
        return IteratorUtil
                .buildIterator(gitRepositoryService.getAllRepositories(), _first, _count);
    }
    
    
    @Override
    public IModel<GitRepositoryDefinition> model(final GitRepositoryDefinition _object) {
    
    
        return Model.of(_object);
    }
    
    
    @Override
    public long size() {
    
    
        return getGitRepositoryDefinitions().size();
    }
    
    
    private List<GitRepositoryDefinition> getGitRepositoryDefinitions() {
    
    
        return gitRepositoryService.getAllRepositories();
    }
}
