/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.utils;



import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.PropertyModel;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;

import com.googlecode.wicket.jquery.core.IJQueryWidget;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import com.googlecode.wicket.jquery.ui.widget.tooltip.TooltipBehavior;



/**
 * @author rgalerme
 */
public class DialogFactory
{
    
    
    private static class AjaxButtonAddDialog extends AjaxButton
    {
        
        
        private final SelectMultipleDialog dialogPersonGroup;
        
        
        
        public AjaxButtonAddDialog(final String id, final SelectMultipleDialog dialogPersonGroup) {
        
        
            super(id);
            this.dialogPersonGroup = dialogPersonGroup;
        }
        
        
        @Override
        protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
        
        
            dialogPersonGroup.open(target);
        }
    }
    
    
    
    private static class AjaxButtonDelDialog extends AjaxButton
    {
        
        
        private final DataListSelectDialogBuilder data;
        
        
        
        public AjaxButtonDelDialog(final String id, final DataListSelectDialogBuilder data) {
        
        
            super(id);
            this.data = data;
        }
        
        
        @Override
        protected void onError(final AjaxRequestTarget target, final Form<?> form) {
        
        
            for (final IHasKey person : data.getChoiceEntityList()) {
                data.getCurrentEntityList().remove(person);
            }
            for (final CustomUpdater cupdater : data.getUpdaters()) {
                cupdater.update();
                target.add(cupdater.getComposant());
            }
            target.add(data.getListEntite());
            
        }
        
        
        @Override
        protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
        
        
            for (final IHasKey person : data.getChoiceEntityList()) {
                data.getCurrentEntityList().remove(person);
            }
            
            for (final CustomUpdater cupdater : data.getUpdaters()) {
                cupdater.update();
                target.add(cupdater.getComposant());
            }
            
            target.add(data.getListEntite());
        }
    }
    
    
    
    @Deprecated
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
            final IGenericService _service,
            final CustomUpdater... updaters) {
    
    
        final DataListSelectDialogBuilder data = new DataListSelectDialogBuilder();
        data.setPage(page);
        data.setIdList(idList);
        data.setIdBtnAdd(idBtnAdd);
        data.setIdBtnDel(idBtnDel);
        data.setIdDialog(idDialog);
        data.setNameFieldResult(nameFieldResult);
        data.setDisplayDialogMessage(displayDialogMessage);
        data.setChoiceEntityList(choiceEntityList);
        data.setCurrentEntityList(currentEntityList);
        data.setSelectDialogList(selectDialogList);
        data.setService(_service);
        
        if (updaters != null) {
            for (final CustomUpdater customUpdater : updaters) {
                data.addUpdater(customUpdater);
            }
        }
        DialogFactory.addMultipleListDialog(data);
    }
    
    
    public static void addMultipleListDialog(final DataListSelectDialogBuilder data) {
    
    
        if (data.getListEntite() == null) {
            final IChoiceRenderer<IHasKey> displayGroup = DialogFactory.getChoiceRendenerEntity();
            
            final ListMultipleChoice<IHasKey> listEntite;
            listEntite =
                    new ListMultipleChoice<IHasKey>(data.getIdList(),
                            new PropertyModel<List<IHasKey>>(data.getPage(),
                                    data.getNameFieldResult()), data.getCurrentEntityList());
            listEntite.setChoiceRenderer(displayGroup);
            listEntite.setMaxRows(8);
            listEntite.setOutputMarkupId(true);
            
            data.setListEntite(listEntite);
        }
        //
        data.getPage().add(data.getListEntite());
        
        if (!"".equals(data.getTooltips()) && data.getTooltips() != null) {
            final TooltipBehavior tooltipBehavior =
                    new TooltipBehavior(
                            IJQueryWidget.JQueryWidget.getSelector(data.getListEntite()));
            data.getListEntite().add(new AttributeModifier("title", data.getTooltips()));
            data.getListEntite().add(tooltipBehavior);
        }
        
        final SelectMultipleDialog dialogPersonGroup =
                new SelectMultipleDialog(data.getIdDialog(), data.getDisplayDialogMessage(),
                        (List<IHasKey>) (List<?>) data.getSelectDialogList())
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
                                        data.getService().selectByPrimaryKey(iEntity.getId());
                                if (!data.getCurrentEntityList().contains(selectByPrimaryKey1)) {
                                    data.getCurrentEntityList().add(selectByPrimaryKey1);
                                }
                                
                                for (final CustomUpdater cupdater : data.getUpdaters()) {
                                    cupdater.update();
                                    target.add(cupdater.getComposant());
                                }
                                
                                target.add(data.getListEntite());
                                
                            }
                        }
                    }
                };
        data.getPage().add(dialogPersonGroup);
        dialogPersonGroup.setFilter(data.getCurrentEntityList());
        dialogPersonGroup.addCustomFilter(data.getFilters());
        data.getPage().add(new AjaxButtonAddDialog(data.getIdBtnAdd(), dialogPersonGroup));
        final AjaxButton ajaxButton = new AjaxButtonDelDialog(data.getIdBtnDel(), data);
        // ajaxButton.setDefaultFormProcessing(false);
        
        data.getPage().add(ajaxButton);
        
    }
    
    
    @Deprecated
    public static void addSelectDialog(
            final MarkupContainer page,
            final String idDialog,
            final String idBtnAdd,
            final String idBtnDel,
            final String displayDialogMessage,
            final List<IHasKey> currentEntityList,
            final List<IHasKey> choiceEntityList,
            final List<IHasKey> selectDialogList,
            final IGenericService _service,
            final ListMultipleChoice<IHasKey> listEntite,
            final CustomUpdater... updaters) {
    
    
        final DataListSelectDialogBuilder data = new DataListSelectDialogBuilder();
        data.setPage(page);
        data.setIdBtnAdd(idBtnAdd);
        data.setIdBtnDel(idBtnDel);
        data.setIdDialog(idDialog);
        data.setListEntite(listEntite);
        data.setDisplayDialogMessage(displayDialogMessage);
        data.setChoiceEntityList(choiceEntityList);
        data.setCurrentEntityList(currentEntityList);
        data.setSelectDialogList(selectDialogList);
        data.setService(_service);
        
        if (updaters != null) {
            for (final CustomUpdater customUpdater : updaters) {
                data.addUpdater(customUpdater);
            }
        }
        DialogFactory.addMultipleListDialog(data);
        
    }
    
    
    public static IChoiceRenderer<IHasKey> getChoiceRendenerEntity() {
    
    
        final IChoiceRenderer<IHasKey> display = new IHasKeyChoiceRenderer();
        return display;
    }
    
    
    public static ICustomFilter getPersonGroupWithoutParentFilter(final Integer obId) {
    
    
        return new ICustomFilter()
        {
            
            
            @Override
            public boolean isNoDisplay(final IHasKey entity) {
            
            
                final PersonGroup person = (PersonGroup) entity;
                return person.getIdPersonGroupParent() != null
                        && !person.getIdPersonGroupParent().equals(obId);
            }
        };
    }
    
    
    public static ICustomFilter getPersonWithoutPersonGroupFilter(final Integer obId) {
    
    
        return new ICustomFilter()
        {
            
            
            @Override
            public boolean isNoDisplay(final IHasKey entity) {
            
            
                final Person person = (Person) entity;
                return person.getIdPersonGroup() != null && !person.getIdPersonGroup().equals(obId);
            }
        };
    }
    
}
