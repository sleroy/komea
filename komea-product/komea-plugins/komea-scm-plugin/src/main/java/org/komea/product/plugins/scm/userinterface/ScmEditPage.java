/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.scm.userinterface;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.komea.product.wicket.LayoutPage;

/**
 *
 * @author rgalerme
 */
public final class ScmEditPage extends LayoutPage {

    @SpringBean
    private IScmRepositoryService scmService;
    
    @SpringBean
    private IProjectService projectService;

    public ScmEditPage(PageParameters _parameters) {
        this(_parameters, new ScmRepositoryDefinition(), true);
    }

    ScmEditPage(PageParameters pageParameters, ScmRepositoryDefinition _object) {
        this(pageParameters, _object, false);
    }

    ScmEditPage(PageParameters pageParameters, ScmRepositoryDefinition _object, boolean _isNew) {
        super(pageParameters);
        final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        feedbackPanel.setOutputMarkupId(true);
        feedbackPanel.setOutputMarkupPlaceholderTag(true);
        add(feedbackPanel);
        
        ScmForm scmForm = new ScmForm(projectService,scmService, this, feedbackPanel, _object, "form", new CompoundPropertyModel<ScmRepositoryDefinition>(_object),_isNew);
        add(scmForm);
        
        String message;
                if (_isNew) {
            message = getString("scm.save.add.title");
        } else {
            message = getString("scm.save.edit.title");
        }
        scmForm.add(new Label("legend", message));
    }
           @Override
    public String getTitle() {

        return getString("scm.main.title");
    }

}
