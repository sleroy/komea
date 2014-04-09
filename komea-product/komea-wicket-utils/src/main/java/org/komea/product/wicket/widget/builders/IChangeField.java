/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.widget.builders;

import java.io.Serializable;

/**
 *
 * @author rgalerme
 */
public interface IChangeField<T> extends Serializable {
    
    public void changeField(T _object);
    
}
