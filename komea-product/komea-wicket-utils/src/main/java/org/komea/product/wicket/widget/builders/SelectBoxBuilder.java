/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.widget.builders;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.database.enums.EntityType;

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

    public static DropDownChoice<String> createSelectNotRequire(final String _wicketId, final String _idFielResult, Object _ModelObject, Object[] _listValue) {
        List<String> valueStr = new ArrayList<String>();
        valueStr.add("");
        for (Object valueType : _listValue) {
            valueStr.add(valueType.toString());
        }
        return new DropDownChoice<String>(_wicketId, new PropertyModel<String>(_ModelObject, _idFielResult), valueStr);
    }

    public static <T> SelectBoxBuilder<T> createWithBooleanRequire(final String _wicketId,
            final Object _data) {
        return new SelectBoxBuilder<T>(_wicketId, _data, false);
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

    private SelectBoxBuilder(String _wicketId, Object _data, boolean _require) {

        final List<Boolean> selectPersonRoles = new ArrayList<Boolean>();
        selectPersonRoles.add(Boolean.FALSE);
        selectPersonRoles.add(Boolean.TRUE);

        final PropertyModel<Boolean> selectionRoleModel
                = new PropertyModel<Boolean>(_data, _wicketId);
        this.dropDownChoice = (DropDownChoice<T>) new DropDownChoice<Boolean>(_wicketId, selectionRoleModel, selectPersonRoles, new ChoiceRenderer<Boolean>("toString"));
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
