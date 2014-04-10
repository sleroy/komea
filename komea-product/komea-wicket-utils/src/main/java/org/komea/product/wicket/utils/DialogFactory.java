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
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.komea.product.database.api.IHasKey;



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
            final List<IHasKey> currentEntityList,
            final List<IHasKey> choiceEntityList,
            final List<IHasKey> selectDialogList,
            final IGenericService _service) {
    
    
        final IChoiceRenderer<IHasKey> displayGroup = DialogFactory.getChoiceRendenerEntity();
        
        final ListMultipleChoice<IHasKey> listEntite;
        listEntite =
                new ListMultipleChoice<IHasKey>(idList, new PropertyModel<List<IHasKey>>(page,
                        nameFieldResult), currentEntityList);
        listEntite.setChoiceRenderer(displayGroup);
        listEntite.setMaxRows(8);
        listEntite.setOutputMarkupId(true);
        page.add(listEntite);
        
        final SelectMultipleDialog dialogPersonGroup =
                new SelectMultipleDialog(idDialog, displayDialogMessage,
                        (List<IHasKey>) (List<?>) selectDialogList)
                {
                    
                    
                    @Override
                    public void onClose(final AjaxRequestTarget target, final DialogButton button) {
                    
                    
                        // FIXME
                    }
                    
                    
                    @Override
                    protected void onSubmit(final AjaxRequestTarget target) {
                    
                    
                        final List<IHasKey> selected = getSelected();
                        for (final IHasKey iEntity : selected) {
                            if (iEntity != null) {
                                final IHasKey selectByPrimaryKey1 =
                                        (IHasKey) _service.selectByPrimaryKey(iEntity.getId());
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
            
            
                for (final IHasKey person : choiceEntityList) {
                    currentEntityList.remove(person);
                }
                
                target.add(listEntite);
            }
            
            
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
            
            
                for (final IHasKey person : choiceEntityList) {
                    currentEntityList.remove(person);
                }
                
                target.add(listEntite);
            }
        });
        
    }
    
    
    public static IChoiceRenderer<IHasKey> getChoiceRendenerEntity() {
    
    
        final IChoiceRenderer<IHasKey> display = new IChoiceRenderer<IHasKey>()
        {
            
            
            @Override
            public Object getDisplayValue(final IHasKey t) {
            
            
                return t.getDisplayName();
            }
            
            
            @Override
            public String getIdValue(final IHasKey t, final int i) {
            
            
                return String.valueOf(t.getId());
            }
        };
        return display;
    }
    
}
