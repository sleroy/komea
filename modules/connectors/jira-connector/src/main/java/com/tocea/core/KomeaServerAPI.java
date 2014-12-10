/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;

import org.komea.event.storage.IEventStorage;

/**
 *
 * @author rgalerme
 */
public class KomeaServerAPI {

    private final IEventStorage eventStorage;

    public KomeaServerAPI(IEventStorage eventStorage) {
        this.eventStorage = eventStorage;
    }

    public IEventStorage getEventStorage() {
        return eventStorage;
    }
    
    

}
