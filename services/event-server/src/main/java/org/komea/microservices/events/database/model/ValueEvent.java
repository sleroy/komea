package org.komea.microservices.events.database.model;

import org.komea.event.model.impl.KomeaEvent;

public class ValueEvent extends KomeaEvent {

    private Double value;

    public ValueEvent() {
        super();

    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(final Double _value) {
        this.value = _value;
    }

    @Override
    public String toString() {
        return "ValueEvent [value=" + this.value + "]";
    }

}
