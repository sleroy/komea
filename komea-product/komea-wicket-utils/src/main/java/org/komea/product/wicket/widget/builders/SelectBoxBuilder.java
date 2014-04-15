/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.widget.builders;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.ui.widget.tooltip.TooltipBehavior;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.AttributeModifier;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.PropertyModel;

/**
 * @author rgalerme
 */
public class SelectBoxBuilder<T> implements Serializable {

    private static class ChoiceRendenerLower<T> extends ChoiceRenderer<T> {

        public ChoiceRendenerLower(final String displayExpression) {

            super(displayExpression);
        }

        @Override
        public Object getDisplayValue(final T object) {

            Object displayValue = super.getDisplayValue(object); // To change body of generated methods, choose Tools | Templates.

            if (displayValue instanceof String) {
                String chaine = (String) displayValue;
                chaine = chaine.replace("_", " ");
                chaine = chaine.substring(0, 1) + chaine.substring(1).toLowerCase();
                displayValue = chaine;
            }

            return displayValue;

        }
    }

    public static DropDownChoice<String> createSelectNotRequire(
            final String _wicketId,
            final String _idFielResult,
            final Object _ModelObject,
            final Object[] _listValue) {

        final List<String> valueStr = new ArrayList<String>();
        valueStr.add("");
        for (final Object valueType : _listValue) {
            valueStr.add(valueType.toString());
        }
        return new DropDownChoice<String>(_wicketId, new PropertyModel<String>(_ModelObject,
                _idFielResult), valueStr);
    }

    public static <T> SelectBoxBuilder<T> createWithEnum(
            final String _wicketId,
            final Object _data,
            final Class<T> type) {

        return new SelectBoxBuilder<T>(_wicketId, _data, type, false, new ChoiceRendenerLower<T>(
                "toString"));
    }

    public static <T> SelectBoxBuilder<T> createWithEnumCustom(
            final String _wicketId,
            final Object _data,
            final Class<T> type,
            final IChoiceRenderer<T> choiceRendener) {

        return new SelectBoxBuilder<T>(_wicketId, _data, type, false, choiceRendener);
    }

    public static <T> SelectBoxBuilder<T> createWithEnumRequire(
            final String _wicketId,
            final Object _data,
            final Class<T> type) {

        return new SelectBoxBuilder<T>(_wicketId, _data, type, true, new ChoiceRendenerLower<T>(
                "toString"));
    }

    private final DropDownChoice<T> dropDownChoice;

    private SelectBoxBuilder(
            final String _wicketId,
            final Object _data,
            final Class<T> type,
            final boolean _require,
            final IChoiceRenderer<T> choiceRendener) {

        final List<T> selectPersonRoles = Arrays.asList(type.getEnumConstants());

        final PropertyModel<T> selectionRoleModel = new PropertyModel<T>(_data, _wicketId);
        this.dropDownChoice
                = new DropDownChoice<T>(_wicketId, selectionRoleModel, selectPersonRoles,
                        choiceRendener);

        if (!_require) {
            this.dropDownChoice.setRequired(true);
            this.dropDownChoice.setNullValid(false);
        } else {
            this.dropDownChoice.setNullValid(true);
        }

    }

    public SelectBoxBuilder<T> withTooltip(final String _toolTip) {

        final TooltipBehavior tooltipBehavior
                = new TooltipBehavior(IJQueryWidget.JQueryWidget.getSelector(dropDownChoice));
        dropDownChoice.add(new AttributeModifier("title", _toolTip));
        dropDownChoice.add(tooltipBehavior);
        return this;
    }

    public DropDownChoice<T> build() {

        return this.dropDownChoice;
    }
}
