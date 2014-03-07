
package org.komea.product.wicket.widget.gravatar;



import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.Model;



public class GravatarImageLink extends WebComponent
{
    
    
    /**
     * 
     */
    private static final long serialVersionUID = 8204662172497459582L;
    
    
    
    /**
     * Builds a gravatar image from an email.
     * 
     * @param _id
     *            the id
     * @param _email
     *            the email
     */
    public GravatarImageLink(final String _id, final String _email) {
    
    
        super(_id, new GravatarModel(Model.of(_email), 150));
        
    }
    
    
    /**
     * Builds a gravatar image from an email.
     * 
     * @param _id
     *            the id
     * @param _email
     *            the email
     */
    public GravatarImageLink(final String _id, final String _email, final int _expectedSize) {
    
    
        super(_id, new GravatarModel(Model.of(_email), _expectedSize));
        
    }
    
    
    @Override
    protected void onComponentTag(final ComponentTag tag) {
    
    
        super.onComponentTag(tag);
        checkComponentTag(tag, "img");
        tag.put("src", getDefaultModelObjectAsString());
    }
}
