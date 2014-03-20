/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.project;



import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.utils.IteratorUtil;



/**
 * @author rgalerme
 */
public class ProjectDataModel extends SortableDataProvider<Project, String>
{
    
    
    private final IProjectService projectService;
    
    
    
    public ProjectDataModel(final IProjectService projectService) {
    
    
        this.projectService = projectService;
    }
    
    
    @Override
    public Iterator<? extends Project> iterator(final long _first, final long _count) {
    
    
        return IteratorUtil.buildIterator(projectService.selectAll(), _first, _count);
    }
    
    
    @Override
    public IModel<Project> model(final Project t) {
    
    
        return Model.of(t);
    }
    
    
    @Override
    public long size() {
    
    
        return projectService.getAllProjectsEntities().size();
    }
    
}
