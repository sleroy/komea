/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.scm.userinterface;

import java.util.List;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.komea.product.plugins.scm.api.IScmRepositoryService;
import org.komea.product.wicket.StatelessLayoutPage;
import org.komea.product.wicket.utils.AbstractDeleteAction;

/**
 *
 * @author rgalerme
 */
public class ScmDeleteAction extends AbstractDeleteAction<ScmRepositoryDefinition>{

    private final IScmRepositoryService scmService;
    private final List<ScmRepositoryDefinition> pageAffiche;

    public ScmDeleteAction(IScmRepositoryService scmService, List<ScmRepositoryDefinition> pageAffiche, StatelessLayoutPage _page) {
        super(_page, "dialogdelete");
        this.scmService = scmService;
        this.pageAffiche = pageAffiche;
    }
    
    
    
    @Override
    public void deleteAction() {
        scmService.remove(getObject());
        pageAffiche.remove(getObject());
    }
    
}
