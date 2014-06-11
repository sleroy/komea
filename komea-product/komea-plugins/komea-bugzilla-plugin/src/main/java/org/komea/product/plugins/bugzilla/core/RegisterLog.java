/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.core;

/**
 *
 * @author rgalerme
 */
public class RegisterLog {

    private Exception ex;
    private String name;

    public RegisterLog() {
    }

    public RegisterLog(Exception ex, String name) {
        this.ex = ex;
        this.name = name;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
