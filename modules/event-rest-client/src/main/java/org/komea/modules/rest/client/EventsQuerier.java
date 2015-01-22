package org.komea.modules.rest.client;

import java.util.Date;
import java.util.Map;
import org.joda.time.DateTime;
import org.komea.event.model.DateInterval;
import org.komea.event.queries.executor.EventsQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventsQuerier {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsQuerier.class.getName());

    private QueriesRestClient client;

    private String backupDelay;
    private EventsQuery eventsQuery;

    public EventsQuerier(final String eventServerHost, final String BackupDelay, final EventsQuery eventsQuery) {
        this.client = new QueriesRestClient(eventServerHost);
        this.backupDelay = BackupDelay;
        this.eventsQuery = eventsQuery;
    }

    public Map<String, Number> getResult() {
        initDatesWithBackupDelay();
        return queriesResult();
    }

    public Map<String, Number> getResult(final DateTime _fromPeriod, final DateTime _toPeriod) {
        initInterval(_fromPeriod.toDate(), _toPeriod.toDate());
        return queriesResult();
    }

    private Map<String, Number> queriesResult() {
        return client.execute(eventsQuery);
    }

    private void initInterval(final Date from, final Date to) {
        eventsQuery.setInterval(new DateInterval(from, to));
    }

    private void initDatesWithBackupDelay() {
        final DateTime now = DateTime.now();
        final Date from = getFromDateWithBackupBelay(now).minusMillis(1).toDate();
        final Date to = getToDateWithBackupDelay(now).toDate();
        initInterval(from, to);
    }

    private DateTime getFromDateWithBackupBelay(final DateTime dateTime) {
        DateTime date = dateTime.withMillisOfSecond(0).withSecondOfMinute(0).withMinuteOfHour(0);
        switch (backupDelay) {
            case "MONTH":
                date = date.withHourOfDay(0).withDayOfMonth(1);
                break;
            case "WEEK":
                date = date.withHourOfDay(0).withDayOfWeek(1);
                break;
            case "DAY":
                date = date.withHourOfDay(0);
                break;
            case "HOUR":
                break;
        }
        return date;
    }

    private DateTime getToDateWithBackupDelay(final DateTime dateTime) {
        DateTime date = new DateTime(getFromDateWithBackupBelay(dateTime));
        switch (backupDelay) {
            case "MONTH":
                date = date.plusMonths(1);
                break;
            case "WEEK":
                date = date.plusWeeks(1);
                break;
            case "DAY":
                date = date.plusDays(1);
                break;
            case "HOUR":
                date = date.plusHours(1);
                break;
        }
        return date;
    }

    public String getBackupDelay() {
        return backupDelay;
    }

    public void setBackupDelay(String backupDelay) {
        this.backupDelay = backupDelay;
    }

    public EventsQuery getEventsQuery() {
        return eventsQuery;
    }

    public void setEventsQuery(EventsQuery eventsQuery) {
        this.eventsQuery = eventsQuery;
    }

    public QueriesRestClient getClient() {
        return client;
    }

    public void setClient(QueriesRestClient client) {
        this.client = client;
    }

}
