
package org.komea.product.wicket.widget.builders;



import org.apache.wicket.markup.html.link.Link;
import org.komea.product.wicket.widget.api.IClickAction;



public class LinkBuilder
{
    
    
    public static Link<String> newLink(final String _linkID, final IClickAction _clickAction) {
    
    
        return new Link<String>(_linkID)
        {
            
            
            @Override
            public void onClick() {
            
            
                debug("Click of link " + _linkID);
                _clickAction.action();
                
            }
        };
        
    }
    
    
    private LinkBuilder() {
    
    
    }
}
