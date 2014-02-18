
package org.komea.product.wicket.widget;



import java.util.Map.Entry;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;



public final class RedirectPageLink extends Link
{
    
    
    private final Entry<String, Class> item;
    
    
    
    public RedirectPageLink(final String _id, final Entry<String, Class> _item) {
    
    
        super(_id);
        item = _item;
        final Label label = new Label("text", Model.of(item.getKey()));
        add(label);
    }
    
    
    @Override
    public void onClick() {
    
    
        getRequestCycle().setResponsePage(item.getValue());
        
    }
}
