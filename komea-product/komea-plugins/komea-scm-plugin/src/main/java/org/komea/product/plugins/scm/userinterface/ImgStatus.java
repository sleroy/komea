/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.scm.userinterface;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.ResourceReference;

/**
 *
 * @author rgalerme
 */
public final class ImgStatus extends Panel {

    public ImgStatus(String id, ResourceReference image) {
        super(id);
        add(new Image("status", image));
    }
}
