package org.komea.event.queries.factory;

import java.io.File;
import java.util.Random;
import org.apache.commons.io.FileUtils;

public enum DbType {

    H2_FILE("h2:file", ";MODE=MYSQL;", getRandomTempPath(), "sa", ""),
    H2_MEM("h2:mem", ";MODE=MYSQL;", getRandomName(), "sa", ""),
    MYSQL("mysql", "", "//localhost/events", "root", "root");

    private final String type;
    private final String extraOptions;
    private final String defaultUrl;
    private final String defaultUser;
    private final String defaultPassword;

    private DbType(final String type, final String extraOptions,
            final String defaultUrl, final String defaultUser, final String defaultPassword) {
        this.type = type;
        this.extraOptions = extraOptions;
        this.defaultUrl = defaultUrl;
        this.defaultUser = defaultUser;
        this.defaultPassword = defaultPassword;
    }

    public String getType() {
        return type;
    }

    public String getExtraOptions() {
        return extraOptions;
    }

    public String getDefaultUrl() {
        return defaultUrl;
    }

    public String getDefaultUser() {
        return defaultUser;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    private static String getRandomName() {
        return "events_" + new Random().nextLong();
    }

    private static String getRandomTempPath() {
        return new File(FileUtils.getTempDirectory(), getRandomName()).getAbsolutePath();
    }

}
