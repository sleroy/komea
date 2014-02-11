
package org.komea.product.wicket.widget;



import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.FormComponent;



public class ErrorHighlighter extends Behavior
{
    
    
    private FormComponent fc;
    
    
    
    @Override
    public void beforeRender(final Component _component) {
    
    
        super.beforeRender(_component);
        if (!fc.isValid()) {
            _component.add(new AttributeModifier("class", "has-error has-feedback"));
        }
    }
    
    
    @Override
    public void bind(final Component c) {
    
    
        // / check c is formcomponent
        fc = (FormComponent) c;
    }
    
    
}
