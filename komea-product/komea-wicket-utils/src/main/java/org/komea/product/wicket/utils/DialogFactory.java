/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.utils;

import com.google.common.util.concurrent.AbstractService;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.List;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;

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
    
    public static void addListWithSelectDialog(MarkupContainer page,String idList,String idDialog,String nameFieldResult,String displayDialogMessage,List<IEntity> currentEntityList,List<IEntity> choiceEntityList,AbstractService service)
    {
//            IChoiceRenderer<IEntity> displayGroup = DialogFactory.getChoiceRendenerEntity();
//
//        ListMultipleChoice<IEntity> listEntite = new ListMultipleChoice<IEntity>("table", new PropertyModel<List<IEntity>>(this, "selectedEntity"), currentEntityList);
//        listEntite.setChoiceRenderer(displayGroup);
//        listEntite.setMaxRows(8);
//        listEntite.setOutputMarkupId(true);
//        add(listEntite);
//        
//
//        final SelectMultipleDialog<IEntity> dialogPersonGroup = new SelectMultipleDialog<IEntity>("dialogAddPerson", "Choose teams", (List<IEntity>) (List<?>) allTeamsPG) {
//
//            @Override
//            public void onClose(AjaxRequestTarget target, DialogButton button) {
//            }
//
//            @Override
//            protected void onSubmit(AjaxRequestTarget target) {
//                List<IEntity> selected = getSelected();
//                for (IEntity iEntity : selected) {
//                    if (iEntity != null) {
//                        IEntity selectByPrimaryKey1 = prService.selectByPrimaryKey(iEntity.getId());
//                        if (!currentEntityList.contains(selectByPrimaryKey1)) {
//                            currentEntityList.add(selectByPrimaryKey1);
//                        }
//                        target.add(listEntite);
//                    }
//                }
//            }
//        };
//        add(dialogPersonGroup);
//        dialogPersonGroup.setFilter(currentEntityList);
//        add(new AjaxLinkLayout<Object>("btnAddPerson", null) {
//
//            @Override
//            public void onClick(final AjaxRequestTarget art) {
//                dialogPersonGroup.open(art);
//            }
//        });
//
//        add(new AjaxButton("btnDelPerson") {
//
//            @Override
//            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
//                for (IEntity person : selectedEntity) {
//                    currentEntityList.remove(person);
//                }
//
//                target.add(listEntite);
//            }
//        });
        
        
    }


}
