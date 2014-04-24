/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.utils;

import org.komea.product.database.api.IHasKey;

/**
 *
 * @author rgalerme
 */
public interface ICustomFilter {

    public boolean isNoDisplay(IHasKey entity);
}
