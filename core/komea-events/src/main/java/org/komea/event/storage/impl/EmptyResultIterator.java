/**
 *
 */
package org.komea.event.storage.impl;

import org.komea.event.model.impl.KomeaEvent;
import org.skife.jdbi.v2.ResultIterator;

/**
 * @author sleroy
 */
public final class EmptyResultIterator implements
        ResultIterator<KomeaEvent> {

    public static final ResultIterator<KomeaEvent> EMPTY = new EmptyResultIterator();

    @Override
    public void close() {

    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public KomeaEvent next() {
        return null;
    }

    @Override
    public void remove() {

    }
}
