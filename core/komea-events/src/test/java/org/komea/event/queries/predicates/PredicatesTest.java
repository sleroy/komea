package org.komea.event.queries.predicates;

import com.google.common.base.Predicate;
import java.util.Arrays;
import java.util.Date;
import org.joda.time.DateTime;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.komea.event.model.KomeaEvent;

public class PredicatesTest {

    private static KomeaEvent newEvent(final String project, final String eventString,
            final Date eventDate, final Number eventNumber) {
        final KomeaEvent event = new KomeaEvent();
        event.put("project", project);
        event.put("eventDate", eventDate);
        event.put("eventNumber", eventNumber);
        event.put("eventString", eventString);
        return event;
    }

    @Test
    public void testPredicteStringEquals() {
        final Predicate<KomeaEvent> predicate = PredicateUtils.fromPredicateDto(
                new PredicateDto(PredicateType.STRING_EQUALS, "eventString", "azerty"));
        assertFalse(predicate.apply(newEvent("project_1", null, new DateTime().toDate(), 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty2", new DateTime().toDate(), 10)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), 10)));
    }

    @Test
    public void testPredicteStringContains() {
        final Predicate<KomeaEvent> predicate = PredicateUtils.fromPredicateDto(
                new PredicateDto(PredicateType.STRING_CONTAINS, "eventString", "zert"));
        assertFalse(predicate.apply(newEvent("project_1", null, new DateTime().toDate(), 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azety", new DateTime().toDate(), 10)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), 10)));
    }

    @Test
    public void testPredicteNumberLower() {
        final Predicate<KomeaEvent> predicate = PredicateUtils.fromPredicateDto(
                new PredicateDto(PredicateType.NUMBER_LOWER, "eventNumber", 5));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), null)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), 6)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), 5)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), 5.0)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), 4.9)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), 4)));
    }

    @Test
    public void testPredicteNumberGreater() {
        final Predicate<KomeaEvent> predicate = PredicateUtils.fromPredicateDto(
                new PredicateDto(PredicateType.NUMBER_GREATER, "eventNumber", 5));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), null)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), 4)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), 4.9)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), 5)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), 5.0)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), 5.1)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new DateTime().toDate(), 6)));
    }

    @Test
    public void testPredicteDateBefore() {
        final Predicate<KomeaEvent> predicate = PredicateUtils.fromPredicateDto(
                new PredicateDto(PredicateType.DATE_BEFORE, "eventDate", new Date(114, 6, 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", null, 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new Date(114, 6, 10), 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new Date(114, 7, 10), 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new Date(115, 6, 10), 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new Date(114, 6, 11), 10)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new Date(114, 5, 10), 10)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new Date(114, 6, 9), 10)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new Date(113, 6, 10), 10)));
    }

    @Test
    public void testPredicteDateAfter() {
        final Predicate<KomeaEvent> predicate = PredicateUtils.fromPredicateDto(
                new PredicateDto(PredicateType.DATE_AFTER, "eventDate", new Date(114, 6, 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", null, 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new Date(114, 6, 10), 10)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new Date(114, 7, 10), 10)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new Date(115, 6, 10), 10)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new Date(114, 6, 11), 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new Date(114, 5, 10), 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new Date(114, 6, 9), 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new Date(113, 6, 10), 10)));
    }

    @Test
    public void testPredicteAnd() {
        final PredicateDto predicate1 = new PredicateDto(PredicateType.NUMBER_GREATER, "eventNumber", 5);
        final PredicateDto predicate2 = new PredicateDto(PredicateType.STRING_EQUALS, "eventString", "azerty");
        final Predicate<KomeaEvent> predicate = PredicateUtils.fromPredicateDto(
                new PredicateDto(PredicateType.AND, Arrays.asList(predicate1, predicate2)));
        assertFalse(predicate.apply(newEvent("project_1", null, new Date(114, 6, 10), 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new Date(114, 6, 10), null)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty2", new Date(114, 6, 10), 10)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty", new Date(114, 6, 10), 4)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new Date(114, 6, 10), 10)));
    }

    @Test
    public void testPredicteOr() {
        final PredicateDto predicate1 = new PredicateDto(PredicateType.NUMBER_GREATER, "eventNumber", 5);
        final PredicateDto predicate2 = new PredicateDto(PredicateType.STRING_EQUALS, "eventString", "azerty");
        final Predicate<KomeaEvent> predicate = PredicateUtils.fromPredicateDto(
                new PredicateDto(PredicateType.OR, Arrays.asList(predicate1, predicate2)));
        assertFalse(predicate.apply(newEvent("project_1", "azerty2", new Date(114, 6, 10), 4)));
        assertFalse(predicate.apply(newEvent("project_1", null, new Date(114, 6, 10), null)));
        assertTrue(predicate.apply(newEvent("project_1", null, new Date(114, 6, 10), 10)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new Date(114, 6, 10), null)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty2", new Date(114, 6, 10), 10)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new Date(114, 6, 10), 4)));
        assertTrue(predicate.apply(newEvent("project_1", "azerty", new Date(114, 6, 10), 10)));
    }

}
