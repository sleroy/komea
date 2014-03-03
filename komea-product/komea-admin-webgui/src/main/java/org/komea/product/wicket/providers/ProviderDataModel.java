/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.providers;

import java.util.Iterator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.ProviderCriteria;

/**
 *
 * @author rgalerme
 */
public class ProviderDataModel extends SortableDataProvider<Provider, String>{

    private final IProviderService providerService;

    public ProviderDataModel(IProviderService providerService) {
        this.providerService = providerService;
    }
    
    
    @Override
    public Iterator<? extends Provider> iterator(long l, long l1) {
        return this.providerService.selectByCriteria(new ProviderCriteria()).subList((int)l, (int)l1).iterator();
    }

    @Override
    public long size() {
        return this.providerService.selectByCriteria(new ProviderCriteria()).size();
        
    }

    @Override
    public IModel<Provider> model(Provider t) {
        return Model.of(t);
    }
    
}
