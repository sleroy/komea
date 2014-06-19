
package org.komea.product.wicket.adminactions;



import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.IAdminAction;
import org.komea.product.backend.service.IAdminActionServices;
import org.komea.product.wicket.StatelessLayoutPage;



/**
 * Admin actions page : allow the user to launch administration actions.
 * 
 * @author sleroy
 */
public class AdminActionPage extends StatelessLayoutPage
{
    
    
    /**
     * @author sleroy
     */
    private final class ListViewExtension extends ListView<IAdminAction>
    {
        
        
        /**
         * @param _id
         */
        private ListViewExtension(final String _id) {
        
        
            super(_id);
        }
        
        
        @Override
        protected void populateItem(
                final ListItem<org.komea.product.backend.service.IAdminAction> _item) {
        
        
            _item.add(new Label("title", Model.of(_item.getModelObject().getActionName())));
            _item.add(new Link("adminaction", _item.getModel())
            {
                
                
                @Override
                public void onClick() {
                
                
                    final String executeAction = service.executeAction(_item.getModelObject());
                    AdminActionPage.this.info(executeAction);
                    
                }
            });
            
        }
    }
    
    
    
    @SpringBean
    private IAdminActionServices service;
    
    
    
    public AdminActionPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        
        final ListView<IAdminAction> listView = new ListViewExtension("adminactions");
        final List<IAdminAction> actions = service.getActions();
        listView.setModel(new Model(new ArrayList(actions)));
        add(listView);
    }
}
