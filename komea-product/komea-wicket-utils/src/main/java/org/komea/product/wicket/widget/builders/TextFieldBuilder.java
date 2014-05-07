package org.komea.product.wicket.widget.builders;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.UrlTextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.wicket.utils.KeyValidator;
import org.komea.product.wicket.widget.ErrorHighlighter;

import com.googlecode.wicket.jquery.core.IJQueryWidget.JQueryWidget;
import com.googlecode.wicket.jquery.ui.widget.tooltip.TooltipBehavior;

public class TextFieldBuilder<T> {

    /**
     * Builds a password field from a pojo and its field.
     *
     * @param _wicketId
     * @param _data
     * @param _field
     * @return
     */
    public static <T> TextFieldBuilder<T> create(
            final String _wicketId,
            final Object _data,
            final String _field) {

        return new TextFieldBuilder<T>(_wicketId, _data, _field, false, false);
    }

    /**
     * Builds a textfield from a pojo and its field.
     *
     * @param _wicketId
     * @param _data
     * @param _field
     * @return
     */
    public static <T> TextFieldBuilder<T> createPassword(
            final String _wicketId,
            final Object _data,
            final String _field) {

        return new TextFieldBuilder<T>(_wicketId, _data, _field, true, true);
    }

    public static <T> TextFieldBuilder<T> createPasswordNoRequire(
            final String _wicketId,
            final Object _data,
            final String _field) {

        return new TextFieldBuilder<T>(_wicketId, _data, _field, false, true);
    }

    /**
     * Builds a textfield from a pojo and its field.
     *
     * @param _wicketId
     * @param _data
     * @param _field
     * @return
     */
    public static <T> TextFieldBuilder<T> createRequired(
            final String _wicketId,
            final Object _data,
            final String _field) {

        return new TextFieldBuilder<T>(_wicketId, _data, _field, true, false);
    }

    /**
     * Creates an url
     *
     * @param _wicketID the wicket id
     * @param _bugServer the bug server
     * @param _propertyModel the property model
     * @return the textfield builder;
     */
    public static <T> TextFieldBuilder<T> createURL(
            final String _wicketID,
            final T _bugServer,
            final String _propertyModel) {

        final UrlTextField urlTextField
                = new UrlTextField(_wicketID, new PropertyModel<String>(_bugServer, _propertyModel));
        urlTextField.setConvertEmptyInputStringToNull(false);
        return new TextFieldBuilder<T>(urlTextField);
    }

    private final Component textField;

    private TextFieldBuilder(final Component _textField) {

        super();
        this.textField = _textField;
    }

    private TextFieldBuilder(
            final String _wicketId,
            final Object _data,
            final String _field,
            final boolean _isRequired,
            final boolean _isPassword) {

        super();
        if (_isPassword) {
            textField = new PasswordTextField(_wicketId, new PropertyModel<String>(_data, _field));
            if (!_isRequired) {
                ((PasswordTextField) textField).setRequired(false);
            }

        } else if (_isRequired) {
            textField = new RequiredTextField<T>(_wicketId, new PropertyModel<T>(_data, _field));
        } else {
            final TextField<T> textField1
                    = new TextField<T>(_wicketId, new PropertyModel<T>(_data, _field));
            textField1.setConvertEmptyInputStringToNull(false);
            textField = textField1;
        }
    }

    public TextFieldBuilder addBehaviour(final Behavior _borderBehavior) {

        textField.add(_borderBehavior);
        return this;
    }

    public Component build() {

        return textField;
    }

    public TextField<T> buildTextField() {

        return (TextField<T>) textField;
    }

    /**
     * Highlight wrong values in the component.
     *
     * @return the builder.
     */
    public TextFieldBuilder<T> highlightOnErrors() {

        textField.add(new ErrorHighlighter());
        return this;
    }

    public TextFieldBuilder<T> simpleValidator(final int _minimumLength, final int _maximumLength) {

        textField.add(new StringValidator(_minimumLength, _maximumLength));
        return this;
    }

    public TextFieldBuilder<T> UniqueStringValidator(
            final String _fieldName,
            final IGenericService _service) {

        ((TextField<String>) textField).add(new KeyValidator(_service, _fieldName));
        return this;
    }

    /**
     * Wraps the textfield with a validator.
     *
     * @param _toolTip
     * @param _newTextField the text field.
     * @param _emptyStringValidator the empty string validator.
     * @return the text field;
     */
    public TextFieldBuilder<T> withTooltip(final String _toolTip) {

        final TooltipBehavior tooltipBehavior
                = new TooltipBehavior(JQueryWidget.getSelector(textField));
        textField.add(new AttributeModifier("title", _toolTip));
        textField.add(tooltipBehavior);
        return this;
    }

    /**
     * Wraps the textfield with a validator.
     *
     * @param _newTextField the text field.
     * @param _emptyStringValidator the empty string validator.
     * @return the text field;
     */
    public TextFieldBuilder<T> withValidation(final IValidator<T> _validator) {

        ((TextField<T>) textField).add(_validator);
        return this;
    }

}
