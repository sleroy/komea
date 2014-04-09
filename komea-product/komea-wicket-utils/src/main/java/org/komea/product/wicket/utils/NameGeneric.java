/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.utils;

import java.io.Serializable;

/**
 *
 * @author rgalerme
 */
public class NameGeneric implements Serializable {

        private String name;

        public NameGeneric(String name) {
            this.name = name;
        }

        public NameGeneric() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
