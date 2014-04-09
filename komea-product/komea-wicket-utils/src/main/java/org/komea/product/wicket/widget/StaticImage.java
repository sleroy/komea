
package org.komea.product.wicket.widget;



import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;



/**
 * Static image.
 * 
 * @author sleroy
 */
public class StaticImage extends WebComponent
{
    
    
    /**
     * @param id
     *            wicket id on the page
     * @param model
     *            reference the external URL from which the image is gotten
     *            for ex.: "http://images.google.com/img/10293.gif"
     */
    public StaticImage(final String id, final IModel<String> urlModel) {
    
    
        super(id, urlModel);
    }
    
    
    @Override
    protected void onComponentTag(final ComponentTag tag) {
    
    
        super.onComponentTag(tag);
        checkComponentTag(tag, "img");
        tag.put("src", getDefaultModelObjectAsString());
    }
}
