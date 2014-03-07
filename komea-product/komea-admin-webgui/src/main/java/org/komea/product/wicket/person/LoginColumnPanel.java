
package org.komea.product.wicket.person;



import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.widget.gravatar.GravatarDefaultImage;
import org.komea.product.wicket.widget.gravatar.GravatarImage;



/**
 * Panel login + avatar
 */
public class LoginColumnPanel extends Panel
{
    
    
    public LoginColumnPanel(final String id, final IModel<Person> model) {
    
    
        super(id, new CompoundPropertyModel(model));
        
        add(new GravatarImage("avatar", model.getObject().getEmail(),
                GravatarDefaultImage.MONSTERID, 20));
        add(new Label("login", model.getObject().getLogin()));
    }
}
