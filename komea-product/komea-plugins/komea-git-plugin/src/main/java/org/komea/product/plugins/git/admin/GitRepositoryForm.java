
package org.komea.product.plugins.git.admin;



import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.UrlValidator;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;



/**
 * Formular to edit properties in the settings page.
 * 
 * @author sleroy
 */
public final class GitRepositoryForm extends Form<GitRepositoryDefinition>
{
    
    
    /**
     * @author sleroy
     */
    private final class SubmitButton extends AjaxButton
    {
        
        
        /**
         * @param _id
         * @param _form
         */
        private SubmitButton(final String _id, final Form<?> _form) {
        
        
            super(_id, _form);
        }
        
        
        @Override
        protected void onError(final AjaxRequestTarget target, final Form<?> form) {
        
        
            feedBack.setVisible(true);
            error("Error found in the formular");
            target.add(feedBack);
        }
        
        
        @Override
        protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
        
        
            feedBack.setVisible(false);
            info("Submitted information");
            // repaint the feedback panel so that it is hidden
            target.add(feedBack);
            
        }
    }
    
    
    
    private final Component               feedBack;
    private final IGitRepositoryService   gitRepositoryService;
    private final LayoutPage              page;
    private final GitRepositoryDefinition repositoryDefinition;
    
    
    
    // private final TeamSelectorDialog teamDialog;
    public GitRepositoryForm(
            final String _id,
            final Component _feedBack,
            final CompoundPropertyModel<GitRepositoryDefinition> _compoundPropertyModel,
            final LayoutPage _page,
            final IGitRepositoryService _gitRepositoryService) {
    
    
        super(_id, _compoundPropertyModel);
        page = _page;
        feedBack = _feedBack;
        gitRepositoryService = _gitRepositoryService;
        repositoryDefinition = _compoundPropertyModel.getObject();
        feedBack.setVisible(false);
        
        add(TextFieldBuilder.<String> createRequired("repoName", repositoryDefinition, "repoName")
                .simpleValidator(3, 255).withTooltip("A name for the repository is required.")
                .build());
        add(TextFieldBuilder
                .<String> create("projectForRepository", repositoryDefinition,
                        "projectForRepository")
                
                .withTooltip(
                        "Provides a project if the whole repository is associated to a project.")
                .build());
        add(TextFieldBuilder.<String> createRequired("url", repositoryDefinition, "url")
                .withValidation(new UrlValidator())
                .withTooltip("The repository url location is required.").build());
        add(TextFieldBuilder.<String> create("userName", repositoryDefinition, "userName")
        
        .withTooltip("Provides a user name to authenticate is necessary.").build());
        add(TextFieldBuilder.<String> createPassword("password", repositoryDefinition, "password")
                .withTooltip("Provides a password to authenticate is necessary.").build());
        
        
        add(new SubmitButton("submit", this));
        
        add(new AjaxLinkLayout<LayoutPage>("cancel", page)
        {
            
            
            @Override
            public void onClick(final AjaxRequestTarget art) {
            
            
                final LayoutPage page = getCustom();
                page.setResponsePage(new GitRepositoryPage(page.getPageParameters()));
            }
        });
        
    }
    
    
    public GitRepositoryDefinition getGitRepositoryDefinition() {
    
    
        return repositoryDefinition;
    }
    
    
    /**
     * Validation the formular : settings are updated from the DTO
     */
    @Override
    protected void onSubmit() {
    
    
        gitRepositoryService.saveOrUpdate(repositoryDefinition); // full save comme les autres
        setResponsePage(new GitRepositoryPage(new PageParameters()));
    }
    
}
