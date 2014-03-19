
package org.komea.product.plugins.git.admin;



import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.utils.KomeaEntry;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
import org.komea.product.wicket.LayoutPage;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class GitRepositoryAddPage extends LayoutPage
{
    
    
    @SpringBean
    private IGitRepositoryService gitRepositoryService;
    
    
    
    public GitRepositoryAddPage(final PageParameters _parameters) {
    
    
        this(_parameters, new GitRepositoryDefinition());
        
    }
    
    
    public GitRepositoryAddPage(
            final PageParameters _parameters,
            final GitRepositoryDefinition _object) {
    
    
        super(_parameters);
        
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);
        final GitRepositoryForm personForm =
                new GitRepositoryForm("form", feedbackPanel,
                        new CompoundPropertyModel<GitRepositoryDefinition>(_object), this,
                        gitRepositoryService);
        add(personForm);
        
        
    }
    
    
    @Override
    public List<? extends Entry<String, Class>> getMiddleLevelPages() {
    
    
        return Collections.singletonList(new KomeaEntry<String, Class>(
                getString("GitRepositoryPage.title"), GitRepositoryPage.class));
        
    }
    
    
    @Override
    public String getTitle() {
    
    
        return getString("GitRepositoryAddPage.title");
    }
    
    
}
