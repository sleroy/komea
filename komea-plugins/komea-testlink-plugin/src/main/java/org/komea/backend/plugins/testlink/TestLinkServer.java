/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.testlink;

/**
 *
 * @author rgalerme
 */
public class TestLinkServer {

    private String address;
    private String key;

    public TestLinkServer() {
    }

    public TestLinkServer(String address, String key) {
        this.address = address;
        this.key = key;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
