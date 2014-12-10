/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;


/**
 *
 * @author rgalerme
 */
public class JiraConfiguration {

  private String url;
  private String login;
  private String pass;

    public JiraConfiguration(String url, String login, String pass) {
        this.url = url;
        this.login = login;
        this.pass = pass;
    }

    public JiraConfiguration() {
        
        this.url="";
        this.login="";
        this.pass="";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }




}
