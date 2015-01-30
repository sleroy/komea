package org.komea.events.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

public class KomeaEventTest {

    @Test
    public void test() {
        Date date = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("date2", date);
        KomeaEvent event = new KomeaEvent(map);
        event.setDate(date);
        Date field = event.field("date2", Date.class);
        Assert.assertEquals(date, event.getDate());
        Assert.assertEquals(date, field);
        Assert.assertTrue(event.fieldEquals("date2", date));
        Assert.assertTrue(event.isAfter(new DateTime().minusDays(1)));
        Assert.assertTrue(event.isBefore(new DateTime().plusDays(1)));
    }
}
