/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.widget.builders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.PropertyModel;

/**
 *
 * @author rgalerme
 */
public class SelectBoxBuilder<T> implements Serializable {

    private final DropDownChoice<T> dropDownChoice;

    public static <T> SelectBoxBuilder<T> createWithEnum(final String _wicketId,
            final Object _data, Class<T> type) {
        return new SelectBoxBuilder<T>(_wicketId, _data, type, false, new ChoiceRendenerLower<T>("toString"));
    }

    public static <T> SelectBoxBuilder<T> createWithEnumCustom(final String _wicketId,
            final Object _data, Class<T> type, IChoiceRenderer<T> choiceRendener) {
        return new SelectBoxBuilder<T>(_wicketId, _data, type, false, choiceRendener);
    }

    public static <T> SelectBoxBuilder<T> createWithEnumRequire(final String _wicketId,
            final Object _data, Class<T> type) {
        return new SelectBoxBuilder<T>(_wicketId, _data, type, true, new ChoiceRendenerLower<T>("toString"));
    }

    public static DropDownChoice<String> createSelectNotRequire(final String _wicketId, final String _idFielResult, Object _ModelObject, Object[] _listValue) {
        List<String> valueStr = new ArrayList<String>();
        valueStr.add("");
        for (Object valueType : _listValue) {
            valueStr.add(valueType.toString());
        }
        return new DropDownChoice<String>(_wicketId, new PropertyModel<String>(_ModelObject, _idFielResult), valueStr);
    }

    private SelectBoxBuilder(String _wicketId, Object _data, Class<T> type, boolean _require, IChoiceRenderer<T> choiceRendener) {

        final List<T> selectPersonRoles = Arrays.asList(type.getEnumConstants());

        final PropertyModel<T> selectionRoleModel
                = new PropertyModel<T>(_data, _wicketId);
        this.dropDownChoice = new DropDownChoice<T>(_wicketId, selectionRoleModel, selectPersonRoles,choiceRendener);

        if (!_require) {
            this.dropDownChoice.setRequired(true);
            this.dropDownChoice.setNullValid(false);
        } else {
            this.dropDownChoice.setNullValid(true);
        }

    }

    public DropDownChoice<T> build() {
        return this.dropDownChoice;
    }

    private static class ChoiceRendenerLower<T> extends ChoiceRenderer<T> implements Serializable {

        public ChoiceRendenerLower(String displayExpression) {
            super(displayExpression);
        }

        @Override
        public Object getDisplayValue(T object) {
            Object displayValue = super.getDisplayValue(object); //To change body of generated methods, choose Tools | Templates.

            if (displayValue instanceof String) {
                String chaine = (String) displayValue;
                chaine = chaine.replace("_", " ");
                chaine = chaine.substring(0, 1) + chaine.substring(1).toLowerCase();
                displayValue = chaine;
            }

            return displayValue;

        }
    }
}
