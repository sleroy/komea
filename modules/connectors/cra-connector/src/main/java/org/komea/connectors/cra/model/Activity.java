package org.komea.connectors.cra.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Activity implements Comparable<Activity> {

    private static final String COLUMN_SEPARATOR = ";";
    private static final String ROW_SEPARATOR = "\n";
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public static String toCsv(final List<Activity> activities) {
        if (activities.isEmpty()) {
            return "";
        }
        Collections.sort(activities);
        final StringBuilder stringBuilder = new StringBuilder(activities.size() * 100);
        for (final Activity activity : activities) {
            stringBuilder.append(activity.toCSV()).append(ROW_SEPARATOR);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private final String email;
    private final String projectCode;
    private final String activityName;
    private final Date date;
    private final Double value;

    public Activity(final String email, final String projectCode,
            final String activityName, final Date date, final Double value) {
        this.email = email;
        this.projectCode = projectCode;
        this.activityName = activityName;
        this.date = date;
        this.value = value;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getEmail() {
        return email;
    }

    public Date getDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Activity{" + "email=" + email + ", projectCode=" + projectCode
                + ", activityName=" + activityName + ", date=" + date + ", value=" + value + '}';
    }

    public String toCSV() {
        return DATE_FORMAT.format(date) + COLUMN_SEPARATOR + projectCode + COLUMN_SEPARATOR
                + activityName + COLUMN_SEPARATOR + email + COLUMN_SEPARATOR + value;
    }

    @Override
    public int compareTo(Activity o) {
        return getDate().compareTo(o.getDate());
    }

}
