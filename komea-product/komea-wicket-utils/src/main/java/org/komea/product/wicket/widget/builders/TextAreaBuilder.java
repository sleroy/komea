/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.widget.builders;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.ui.widget.tooltip.TooltipBehavior;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.komea.product.wicket.widget.ErrorHighlighter;

/**
 *
 * @author rgalerme
 */
public class TextAreaBuilder<T> {

    
    private final  TextArea<T> textArea;
    
    public static <T> TextAreaBuilder<T> create(final String _wicketId,
            final Object _data,
            final String _field) {
        return new TextAreaBuilder<T>(_wicketId, _data, _field);
    }


    private TextAreaBuilder(String _wicketId, Object _data, String _field) { 
        super();
           textArea = new TextArea<T>(_wicketId,new PropertyModel<T>(_data, _field));
        
    }
    
    
        public TextAreaBuilder addBehaviour(final Behavior _borderBehavior) {
    
    
        textArea.add(_borderBehavior);
        return this;
    }
    
    
    public TextArea<T> build() {
    
    
        return textArea;
    }
    
    
    /**
     * Highlight wrong values in the component.
     * 
     * @return the builder.
     */
    public TextAreaBuilder<T> highlightOnErrors() {
    
    
        textArea.add(new ErrorHighlighter());
        return this;
    }
    
    
    public TextAreaBuilder<T> simpleValidator(final int _minimumLength, final int _maximumLength) {
    
    
        textArea.add(new StringValidator(_minimumLength, _maximumLength));
        return this;
    }
    
    
    /**
     * Wraps the textArea with a validator.
     * 
     * @param _toolTip
     * @param _newTextField
     *            the text Area.
     * @param _emptyStringValidator
     *            the empty string validator.
     * @return the text Area;
     */
    public TextAreaBuilder<T> withTooltip(final String _toolTip) {
    
    
        final TooltipBehavior tooltipBehavior =
                new TooltipBehavior(IJQueryWidget.JQueryWidget.getSelector(textArea));
        textArea.add(new AttributeModifier("title", _toolTip));
        textArea.add(tooltipBehavior);
        return this;
    }
    
    
    /**
     * Wraps the textArea with a validator.
     * 
     * @param _newTextArea
     *            the text Area.
     * @param _emptyStringValidator
     *            the empty string validator.
     * @return the text Area;
     */
    public TextAreaBuilder<T> withValidation(final IValidator<T> _validator) {
    
    
        textArea.add(_validator);
        return this;
    }
}
