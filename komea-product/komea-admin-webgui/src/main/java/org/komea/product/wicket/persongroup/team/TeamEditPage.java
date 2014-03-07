/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup.team;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.SelectDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;

/**
 *
 * @author rgalerme
 */
public class TeamEditPage extends LayoutPage {

    @SpringBean
    private IPersonGroupService prService;

    public TeamEditPage(PageParameters _parameters) {
        this(_parameters, new PersonGroup());
    }

    public TeamEditPage(PageParameters _parameters, PersonGroup _personGroup) {
        super(_parameters);

        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        add(feedbackPanel);

        final TeamForm teamForm = new TeamForm("form", prService, feedbackPanel, new CompoundPropertyModel<PersonGroup>(_personGroup), this);
        add(teamForm);

        IChoiceRenderer<PersonGroup> iChoiceRenderer = new IChoiceRenderer<PersonGroup>() {

            @Override
            public Object getDisplayValue(PersonGroup t) {
                return t.getName();
            }

            @Override
            public String getIdValue(PersonGroup t, int i) {
                return String.valueOf(t.getId());
            }

        };

        final SelectDialog<PersonGroup> dialogPersonGroup = new SelectDialog<PersonGroup>("dialogParent", "Choose a parent", prService, iChoiceRenderer) {

            @Override
            public void onClose(AjaxRequestTarget target, DialogButton button) {
//                target.add(kpiForm);
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                PersonGroup selectedPersonGroup = getSelectedProvider();
                if (selectedPersonGroup != null) {
                    teamForm.getPersonGroup().setIdPersonGroupParent(selectedPersonGroup.getId());
                    teamForm.getParentName().setName(selectedPersonGroup.getName());
                    target.add(teamForm.getParentField());
                }
            }

        };
        add(dialogPersonGroup);
        teamForm.add(new AjaxLinkLayout<LayoutPage>("btnParent", this) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialogPersonGroup.open(art);

            }
        });
    }

}
