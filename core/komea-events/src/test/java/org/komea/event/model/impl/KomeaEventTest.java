package org.komea.event.model.impl;

import com.google.common.collect.Maps;
import java.util.Date;
import org.joda.time.DateTime;
import org.junit.Test;

public class KomeaEventTest {

    @Test
    public void test() {
        KomeaEvent event = new KomeaEvent(Maps.newHashMap());
        event.fieldEquals("provider2", "");
        event = new KomeaEvent("", "");
        event.put("date", String.valueOf(new Date().getTime()));
        event.fieldEquals("provider", "");
        event.isAfter(new DateTime());
        event.isBefore(new DateTime());
    }
}
