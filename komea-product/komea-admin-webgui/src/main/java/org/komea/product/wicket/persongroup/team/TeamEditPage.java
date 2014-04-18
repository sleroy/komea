/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.persongroup.team;

import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
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

    @SpringBean
    private IProjectService projectService;

    @SpringBean
    private IPersonService personService;

    public TeamEditPage(PageParameters _parameters) {
        this(_parameters, new PersonGroup(), true);
    }

    public TeamEditPage(PageParameters _parameters, PersonGroup _personGroup) {
        this(_parameters, _personGroup, false);
    }

    private TeamEditPage(PageParameters _parameters, PersonGroup _personGroup, boolean isNew) {
        super(_parameters);

        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);

        final TeamForm teamForm = new TeamForm(isNew, "form", projectService, personService, prService,
                feedbackPanel, new CompoundPropertyModel<PersonGroup>(_personGroup), this);
        String message;
        if (isNew) {
            message = getString("teampage.save.add.title");
        } else {
            message = getString("teampage.save.edit.title");
        }
        teamForm.add(new Label("legend", message));
        add(teamForm);

    }

    @Override
    public String getTitle() {
        return getString("teampage.main.title");
    }

}
