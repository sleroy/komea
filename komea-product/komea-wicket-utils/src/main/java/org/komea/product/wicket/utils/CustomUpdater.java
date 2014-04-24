/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.utils;

import java.io.Serializable;
import org.apache.wicket.Component;

/**
 *
 * @author rgalerme
 */
public abstract class CustomUpdater implements Serializable {
    private final Component composant;

    public CustomUpdater(Component composant) {
        this.composant = composant;
    }

    public Component getComposant() {
        return composant;
    }
    
    public abstract void update();
}
