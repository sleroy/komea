/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.scm.userinterface;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.builders.TextFieldBuilder;

/**
 *
 * @author rgalerme
 */
public class ScmForm extends Form<ScmRepositoryDefinition> {

    private final IScmRepositoryService scmService;
    private final LayoutPage page;
    private final Component feedBack;
    private final ScmRepositoryDefinition scmData;

    public ScmForm(IScmRepositoryService _scmService, LayoutPage _page, Component _feedBack, ScmRepositoryDefinition _scmData, String id, IModel<ScmRepositoryDefinition> model, boolean _isNew) {
        super(id, model);
        this.scmService = _scmService;
        this.page = _page;
        this.feedBack = _feedBack;
        this.scmData = _scmData;

        // url field
        add(TextFieldBuilder.createURL("url", scmData, "url")
                .withTooltip(getString("scm.save.form.tooltips.url")).simpleValidator(3, 255).build());

        // required input text field
        add(TextFieldBuilder.<String>createRequired("login", scmData, "login")
                .simpleValidator(0, 255).withTooltip(getString("global.save.form.field.tooltip.login")).build());
        TextFieldBuilder<String> keyFieldBuilder = TextFieldBuilder.<String>createRequired("key", scmData, "key")
                .simpleValidator(0, 255).withTooltip(getString("scm.save.form.tooltips.key"));

        if (_isNew) {
//            keyFieldBuilder.UniqueStringValidator(getString("global.field.tooltip.key"), scmService);
        } else {
            keyFieldBuilder.buildTextField().add(new AttributeModifier("readonly", "readonly"));
        }

        // input text field
        // select field
    }

}
