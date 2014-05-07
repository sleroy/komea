/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.scm.userinterface;

import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.wicket.widget.api.IEditAction;

/**
 *
 * @author rgalerme
 */
public class ScmEditAction implements IEditAction<ScmRepositoryDefinition> {

    private final ScmPage scmPage;

    public ScmEditAction(ScmPage scmPage) {
        this.scmPage = scmPage;
    }

    @Override
    public void selected(ScmRepositoryDefinition _object) {
        this.scmPage.setResponsePage(new ScmEditPage(this.scmPage.getPageParameters(),_object));
    }
    
}
