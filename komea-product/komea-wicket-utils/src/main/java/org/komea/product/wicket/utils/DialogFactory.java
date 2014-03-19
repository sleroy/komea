/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.utils;

import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;

/**
 *
 * @author rgalerme
 */
public class DialogFactory {

    public static IChoiceRenderer<IEntity> getChoiceRendenerEntity() {
        IChoiceRenderer<IEntity> display = new IChoiceRenderer<IEntity>() {
            @Override
            public Object getDisplayValue(IEntity t) {
                return t.getDisplayName();
            }

            @Override
            public String getIdValue(IEntity t, int i) {
                return String.valueOf(t.getId());
            }
        };
        return display;
    }
    


}
