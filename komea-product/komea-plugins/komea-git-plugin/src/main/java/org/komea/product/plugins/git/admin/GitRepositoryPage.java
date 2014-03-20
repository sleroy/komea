
package org.komea.product.plugins.git.admin;



import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;



/**
 * GIT admin page
 * 
 * @author sleroy
 */
public class GitRepositoryPage extends LayoutPage
{
    
    
    @SpringBean
    private IGitRepositoryService gitRepositories;
    
    
    
    public GitRepositoryPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        
        
        final IDeleteAction<GitRepositoryDefinition> gitDeleteAction =
                new GitDeleteAction(getGitRepositories());
        
        final IEditAction<GitRepositoryDefinition> gitEditAction =
                new GitEditAction(this, getGitRepositories());
        final ISortableDataProvider<GitRepositoryDefinition, String> dataProvider =
                new ListDataModel(gitRepositories.getAllRepositories());
        add(DataTableBuilder.<GitRepositoryDefinition, String> newTable("table")
                .addColumn("Repository name", "repoName").addColumn("User name", "userName")
                .addColumn("Project associated", "projectForRepository").addColumn("URL", "url")
                .withEditDeleteColumn(gitDeleteAction, gitEditAction).displayRows(10)
                .withData(dataProvider).build());
        
        
    }
    
    
    public IGitRepositoryService getGitRepositories() {
    
    
        return gitRepositories;
    }
    
    
    @Override
    public String getTitle() {
    
    
        return getString("GitRepositoryPage.title");
    }
    
    
    public void setGitRepositories(final IGitRepositoryService _gitRepositories) {
    
    
        gitRepositories = _gitRepositories;
    }
    
}
