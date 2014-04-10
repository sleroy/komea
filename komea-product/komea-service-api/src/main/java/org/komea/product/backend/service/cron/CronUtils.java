package org.komea.product.backend.service.cron;

import org.quartz.CronExpression;

public abstract class CronUtils {

    public static boolean isValidCronExpression(final String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

}
