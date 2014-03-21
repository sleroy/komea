/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.utils;


import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.List;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.api.IEntity;
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
    
    public static void addListWithSelectDialog(MarkupContainer page,String idList,String idDialog,String idBtnAdd,String idBtnDel,String nameFieldResult,String displayDialogMessage,final List<IEntity> currentEntityList,final List<IEntity> choiceEntityList, final List<IEntity> selectDialogList,final IGenericService _service)
    {
            IChoiceRenderer<IEntity> displayGroup = DialogFactory.getChoiceRendenerEntity();

        final ListMultipleChoice<IEntity> listEntite;
        listEntite = new ListMultipleChoice<IEntity>(idList, new PropertyModel<List<IEntity>>(page, nameFieldResult), currentEntityList);
        listEntite.setChoiceRenderer(displayGroup);
        listEntite.setMaxRows(8);
        listEntite.setOutputMarkupId(true);
        page.add(listEntite);
        

        final SelectMultipleDialog<IEntity> dialogPersonGroup = new SelectMultipleDialog<IEntity>(idDialog, displayDialogMessage, (List<IEntity>) (List<?>) selectDialogList) {

            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                List<IEntity> selected = getSelected();
                for (IEntity iEntity : selected) {
                    if (iEntity != null) {
                        IEntity selectByPrimaryKey1 = (IEntity) _service.selectByPrimaryKey(iEntity.getId());
                        if (!currentEntityList.contains(selectByPrimaryKey1)) {
                            currentEntityList.add(selectByPrimaryKey1);
                        }
                        target.add(listEntite);
                    }
                }
            }
        };
        page.add(dialogPersonGroup);
        dialogPersonGroup.setFilter(currentEntityList);
        page.add(new AjaxLinkLayout<Object>(idBtnAdd, null) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialogPersonGroup.open(art);
            }
        });

        page.add(new AjaxButton(idBtnDel) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                for (IEntity person : choiceEntityList) {
                    currentEntityList.remove(person);
                }

                target.add(listEntite);
            }
        });
        
        
    }


}
