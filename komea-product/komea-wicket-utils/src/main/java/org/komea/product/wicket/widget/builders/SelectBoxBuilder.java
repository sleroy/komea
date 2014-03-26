/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.widget.builders;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.PropertyModel;

/**
 *
 * @author rgalerme
 */
public class SelectBoxBuilder<T> {

    private final DropDownChoice<T> dropDownChoice;

    public static <T> SelectBoxBuilder<T> createWithEnum(final String _wicketId,
            final Object _data, Class<T> type) {
        return new SelectBoxBuilder<T>(_wicketId, _data, type, false);
    }

    public static <T> SelectBoxBuilder<T> createWithEnumRequire(final String _wicketId,
            final Object _data, Class<T> type) {
        return new SelectBoxBuilder<T>(_wicketId, _data, type, true);
    }

    private SelectBoxBuilder(String _wicketId, Object _data, Class<T> type, boolean _require) {

        final List<T> selectPersonRoles = Arrays.asList(type.getEnumConstants());
        final PropertyModel<T> selectionRoleModel
                = new PropertyModel<T>(_data, _wicketId);
        this.dropDownChoice = new DropDownChoice<T>(_wicketId, selectionRoleModel, selectPersonRoles, new ChoiceRenderer<T>("toString"));

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
}
