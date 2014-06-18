
package org.komea.product.wicket;



import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;



/**
 * Login page
 * 
 * @author sleroy
 */
public class LoginPage extends StatelessLayoutPage
{
    
    
    private static class LoginForm extends StatelessForm
    {
        
        
        private static final long serialVersionUID = -6826853507535977683L;
        
        private String            password;
        private String            username;
        
        
        
        public LoginForm(final String id) {
        
        
            super(id);
            setModel(new CompoundPropertyModel(this));
            // add(new Label("usernameLabel", getString("login.username.label", null, "Username")));
            add(new RequiredTextField("username"));
            // add(new Label("passwordLabel", getString("login.password.label", null, "Username")));
            add(new PasswordTextField("password"));
            add(new FeedbackPanel("feedback"));
            
        }
        
        
        private void setDefaultResponsePageIfNecessary() {
        
        
            continueToOriginalDestination();
            setResponsePage(getApplication().getHomePage());
            
        }
        
        
        @Override
        protected void onSubmit() {
        
        
            final AuthenticatedWebSession session = AuthenticatedWebSession.get();
            if (session.signIn(username, password)) {
                setDefaultResponsePageIfNecessary();
            } else {
                error(getString("login.failed.badcredentials"));
            }
        }
    }
    
    
    
    public LoginPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        final LoginForm form = new LoginForm("loginForm");
        add(form);
        
    }
    
}
