/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.jiraconnector.core;

import java.text.SimpleDateFormat;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.greenhopper.GreenHopperClient;

/**
 *
 * @author rgalerme
 */
public class JiraServerContext {

    public final static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    public final static Integer GetOccurence = 1000;

    private JiraClient jiraClient;
    private GreenHopperClient greenHopper;


     JiraServerContext(JiraClient gh) {
        this.jiraClient = gh;
        greenHopper = new GreenHopperClient(gh);
    }

    public JiraClient getClient() {
        return jiraClient;
    }

    public GreenHopperClient getGreenHopper() {
        return greenHopper;
    }
}
