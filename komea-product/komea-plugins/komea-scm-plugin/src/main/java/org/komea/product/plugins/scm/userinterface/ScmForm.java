/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.scm.userinterface;

import java.util.List;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.api.IHasKey;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.repository.model.ScmType;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.komea.product.wicket.StatelessLayoutPage;
import org.komea.product.wicket.utils.NameGeneric;
import org.komea.product.wicket.utils.SelectDialog;
import org.komea.product.wicket.widget.builders.AjaxLinkLayout;
import org.komea.product.wicket.widget.builders.SelectBoxBuilder;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;
import org.ocpsoft.prettytime.PrettyTime;

/**
 *
 * @author rgalerme
 */
public class ScmForm extends Form<ScmRepositoryDefinition> {

    private final IScmRepositoryService scmService;
    private final IProjectService projectService;
    private final StatelessLayoutPage page;
    private final Component feedBack;
    private final ScmRepositoryDefinition scmData;
    private final NameGeneric affichageDate;
    private final NameGeneric projectName;
    private final String savPassword;

    public ScmForm(IProjectService _projectService, IScmRepositoryService _scmService, StatelessLayoutPage _page, Component _feedBack, ScmRepositoryDefinition _scmData, String id, IModel<ScmRepositoryDefinition> model, boolean _isNew) {
        super(id, model);
        this.scmService = _scmService;
        this.page = _page;
        this.feedBack = _feedBack;
        this.scmData = _scmData;
        this.projectService = _projectService;
        feedBack.setVisible(false);
        PrettyTime p = new PrettyTime();
        affichageDate = new NameGeneric(p.format(scmData.getLastDateCheckout()));
        projectName = new NameGeneric("");
        // url field
        ScmType type = scmData.getType();
        add(SelectBoxBuilder.<ScmType>createWithEnum("type", scmData,
                ScmType.class).withTooltip(getString("scm.save.form.tooltips.type")).build());

        add(TextFieldBuilder.createURL("url", scmData, "url")
                .withTooltip(getString("scm.save.form.tooltips.url")).simpleValidator(3, 255).build());
        // required input text field
        TextFieldBuilder<String> keyFieldBuilder = TextFieldBuilder.<String>createRequired("key", scmData, "key")
                .simpleValidator(0, 255).withTooltip(getString("scm.save.form.tooltips.key"));

        if (_isNew) {
            keyFieldBuilder.UniqueStringValidator(getString("global.field.tooltip.key"), scmService);
        } else {
            keyFieldBuilder.buildTextField().add(new AttributeModifier("readonly", "readonly"));
        }
        add(keyFieldBuilder.build());

        add(TextFieldBuilder.<String>createRequired("reponame", scmData, "repoName")
                .simpleValidator(0, 255).withTooltip(getString("scm.save.form.tooltips.reponame")).build());

        // input text field
        add(TextFieldBuilder.<String>create("username", scmData, "userName")
                .simpleValidator(0, 255).withTooltip(getString("scm.save.form.tooltips.username")).build());
        savPassword = scmData.getPassword();
        add(TextFieldBuilder.<String>createPasswordNoRequire("password", scmData, "password")
                .simpleValidator(0, 255).withTooltip(getString("scm.save.form.tooltips.password")).build());
        Integer selectedProject = Integer.valueOf(0);
        if (scmData.getProjectForRepository() != null) {
            Project selectByKey = projectService.selectByAlias(scmData.getProjectForRepository());
            if (selectByKey != null) {
                projectName.setName(selectByKey.getDisplayName());
                selectedProject = selectByKey.getId();
            }
        }

        final TextField<String> projectField = TextFieldBuilder.<String>create("project", projectName, "name")
                .simpleValidator(0, 255).withTooltip(getString("scm.save.form.tooltips.project")).buildTextField();
        add(projectField);
        add(TextFieldBuilder.<String>create("lastdate", affichageDate, "name")
                .simpleValidator(0, 255).withTooltip(getString("scm.save.form.tooltips.lastdate")).build());

        // select field
        add(new AjaxLinkLayout<StatelessLayoutPage>("cancel", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {

                final StatelessLayoutPage page = getCustom();
                page.setResponsePage(new ScmPage(page.getPageParameters()));
            }
        });

        add(new AjaxButton("submit", this) {

            @Override
            protected void onError(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(true);
                // repaint the feedback panel so errors are shown
                target.add(feedBack);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {

                feedBack.setVisible(false);
                // repaint the feedback panel so that it is hidden
                target.add(feedBack);
                if (scmData.getPassword() == null || "".equals(scmData.getPassword())) {
                    scmData.setPassword(savPassword);
                }
                scmService.saveOrUpdate(scmData);
                page.setResponsePage(new ScmPage(page.getPageParameters()));

            }
        });

        final SelectDialog dialogProject = new SelectDialog("dialogProject", getString("scm.save.form.dialog.tooltips.project"), (List<IHasKey>) (List<?>) projectService.selectAll(), selectedProject) {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {

                IHasKey selectedPersonGroup = getSelected();
                if (selectedPersonGroup != null) {
                    projectName.setName(selectedPersonGroup.getDisplayName());
                    scmData.setProjectForRepository(selectedPersonGroup.getKey());
                } else {
                    scmData.setProjectForRepository("");
                    projectName.setName("");
                }
                projectField.clearInput();
                target.add(projectField);
            }

        };
        page.add(dialogProject);
        add(new AjaxLinkLayout<StatelessLayoutPage>("btnProject", page) {

            @Override
            public void onClick(final AjaxRequestTarget art) {
                dialogProject.open(art);

            }
        });
    }

}
