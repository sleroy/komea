/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.utils;



import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.api.IEntity;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;



/**
 * @author rgalerme
 */
public class DialogFactory
{
    
    
    public static void addListWithSelectDialog(
            final MarkupContainer page,
            final String idList,
            final String idDialog,
            final String idBtnAdd,
            final String idBtnDel,
            final String nameFieldResult,
            final String displayDialogMessage,
            final List<IEntity> currentEntityList,
            final List<IEntity> choiceEntityList,
            final List<IEntity> selectDialogList,
            final IGenericService _service) {
    
    
        final IChoiceRenderer<IEntity> displayGroup = DialogFactory.getChoiceRendenerEntity();
        
        final ListMultipleChoice<IEntity> listEntite;
        listEntite =
                new ListMultipleChoice<IEntity>(idList, new PropertyModel<List<IEntity>>(page,
                        nameFieldResult), currentEntityList);
        listEntite.setChoiceRenderer(displayGroup);
        listEntite.setMaxRows(8);
        listEntite.setOutputMarkupId(true);
        page.add(listEntite);
        
        final SelectMultipleDialog dialogPersonGroup =
                new SelectMultipleDialog(idDialog, displayDialogMessage,
                        (List<IEntity>) (List<?>) selectDialogList)
                {
                    
                    
                    @Override
                    public void onClose(final AjaxRequestTarget target, final DialogButton button) {
                    
                    
                        // FIXME
                    }
                    
                    
                    @Override
                    protected void onSubmit(final AjaxRequestTarget target) {
                    
                    
                        final List<IEntity> selected = getSelected();
                        for (final IEntity iEntity : selected) {
                            if (iEntity != null) {
                                final IEntity selectByPrimaryKey1 =
                                        (IEntity) _service.selectByPrimaryKey(iEntity.getId());
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
        page.add(new AjaxLinkLayout<Object>(idBtnAdd, null)
        {
            
            
            @Override
            public void onClick(final AjaxRequestTarget art) {
            
            
                dialogPersonGroup.open(art);
            }
        });
        
        page.add(new AjaxButton(idBtnDel)
        {
            
            
            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {
            
            
                for (final IEntity person : choiceEntityList) {
                    currentEntityList.remove(person);
                }
                
                target.add(listEntite);
            }
            
            
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
            
            
                for (final IEntity person : choiceEntityList) {
                    currentEntityList.remove(person);
                }
                
                target.add(listEntite);
            }
        });
        
    }
    
    
    public static IChoiceRenderer<IEntity> getChoiceRendenerEntity() {
    
    
        final IChoiceRenderer<IEntity> display = new IChoiceRenderer<IEntity>()
        {
            
            
            @Override
            public Object getDisplayValue(final IEntity t) {
            
            
                return t.getDisplayName();
            }
            
            
            @Override
            public String getIdValue(final IEntity t, final int i) {
            
            
                return String.valueOf(t.getId());
            }
        };
        return display;
    }
    
}
