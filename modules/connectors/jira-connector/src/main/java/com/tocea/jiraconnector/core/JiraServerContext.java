/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.jiraconnector.core;

import com.tocea.jiraconnector.generalplugin.BadConfigurationException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.greenhopper.GreenHopperClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;

/**
 *
 * @author rgalerme
 */
public class JiraServerContext {

    public final static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    public final static Integer GetOccurence = 1000;

    private JiraClient jiraClient;
    private GreenHopperClient greenHopper;

    public static JiraServerContext getNewInstance(JiraConfiguration configuration) throws BadConfigurationException {

        if ("".equals(configuration.getUrl())) {
            throw new BadConfigurationException("Map of jira configuration deos not contain the correct field");
        }
        JiraClient client;
        if (containUser(configuration)) {
            client = new JiraClient(configuration.getUrl(), new BasicCredentials(configuration.getLogin(), configuration.getPass()),true);          
        } else {
            client = new JiraClient(configuration.getUrl(),null,true);
        }
        return new JiraServerContext(client);
    }
    
    private static boolean containUser(JiraConfiguration configuration) {

        boolean result;
        result = "".equals(configuration.getLogin()) || configuration.getLogin() == null || configuration.getPass() == null || "".equals(configuration.getPass());

        return !result;
    }

    private JiraServerContext(JiraClient gh) {
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
