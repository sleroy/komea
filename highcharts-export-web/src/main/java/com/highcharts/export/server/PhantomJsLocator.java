package com.highcharts.export.server;

import java.io.File;
import org.apache.commons.lang.SystemUtils;

public class PhantomJsLocator {

    private final String location;

    public PhantomJsLocator() {
        location = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace(
                this.getClass().getName().replace(".", "/") + ".class", "phantomjs/" + getOsExecutable());
        final File file = new File(location);
        file.setExecutable(true);
    }

    public String getLocation() {
        return location;
    }

    private String getOsExecutable() {
        final String arch = SystemUtils.OS_ARCH;
        if (SystemUtils.IS_OS_WINDOWS) {
            return "windows/phantomjs.exe";
        } else if (SystemUtils.IS_OS_MAC) {
            return "mac/phantomjs";
        } else {
            if (arch != null && arch.contains("64")) {
                return "linux-64/phantomjs";
            } else {
                return "linux-32/phantomjs";
            }
        }
    }
}
