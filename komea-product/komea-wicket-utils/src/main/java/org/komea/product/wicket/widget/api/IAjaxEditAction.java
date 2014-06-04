/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.widget.api;

import java.io.Serializable;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 *
 * @author rgalerme
 */
public interface IAjaxEditAction<T> extends Serializable {
     public void selected(T _object,final AjaxRequestTarget _target);
}
