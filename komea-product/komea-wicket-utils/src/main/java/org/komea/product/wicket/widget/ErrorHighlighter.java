
package org.komea.product.wicket.widget;



import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;



public class ErrorHighlighter extends Behavior
{
    
    
    @Override
    public void onComponentTag(final Component _component, final ComponentTag _tag) {
    
    
        super.onComponentTag(_component, _tag);
        final FormComponent fc = (FormComponent) _component;
        if (!fc.isValid()) {
            _component.add(new AttributeModifier("class", "has-error has-feedback"));
        } else if (!fc.isValid()) {
            _component.add(new AttributeModifier("class", "has-success has-feedback"));
        }
    }
    
    
}
