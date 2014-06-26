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
public class ManageMessageConnexion implements Serializable {

    private Etat etat;

    public ManageMessageConnexion() {
        etat = Etat.NONE;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public boolean visibleError() {
        return etat.equals(Etat.ERROR);
    }

    public boolean visibleWaiting() {
        return etat.equals(Etat.WAITING);
    }

    public boolean visibleSuccess() {
        return etat.equals(Etat.SUCCESS);
    }

    public static enum Etat {

        ERROR, WAITING, SUCCESS, NONE
    }

     public static final String ENDLINE = System.getProperty("line.separator"); 
     
    public static String recursiveDisplayTrace(Throwable cause) {
        if (cause == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(cause.getMessage()).append(ENDLINE);
        for (StackTraceElement element : cause.getStackTrace()) {
            sb.append("    ");
            sb.append(element.toString());
            sb.append(ENDLINE);
        }
        sb.append(recursiveDisplayTrace(cause.getCause()));
        return sb.toString();

    }
}
