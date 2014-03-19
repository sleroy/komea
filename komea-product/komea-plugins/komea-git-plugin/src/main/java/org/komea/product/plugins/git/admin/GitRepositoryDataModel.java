
package org.komea.product.plugins.git.admin;



import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;



public final class GitRepositoryDataModel extends
        SortableDataProvider<GitRepositoryDefinition, String>
{
    
    
    private final IGitRepositoryService gitRepositoryService;
    
    
    
    public GitRepositoryDataModel(final IGitRepositoryService _gitRepository) {
    
    
        gitRepositoryService = _gitRepository;
        
        
    }
    
    
    @Override
    public Iterator<? extends GitRepositoryDefinition> iterator(final long _first, final long _count) {
    
    
        final List<GitRepositoryDefinition> allRepositories =
                gitRepositoryService.getAllRepositories();
        if (_first < 0) { return Collections.EMPTY_LIST.iterator(); }
        if (_first >= allRepositories.size()) { return Collections.EMPTY_LIST.iterator(); }
        final int toIndex = Math.min(allRepositories.size(), (int) (_first + _count));
        return allRepositories.subList((int) _first, toIndex).iterator();
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
