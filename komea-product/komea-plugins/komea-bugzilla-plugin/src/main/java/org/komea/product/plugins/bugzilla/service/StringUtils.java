package org.komea.product.plugins.bugzilla.service;

import java.util.ArrayList;
import java.util.List;

public abstract class StringUtils {

    private StringUtils() {
    }

    public static List<String> splitAndTrimWithoutEmpty(final String s, final String separator) {
        if (s == null) {
            return new ArrayList<String>(0);
        }
        final String[] split = s.split("\\s*" + separator + "\\s*");
        final List<String> result = new ArrayList<String>(split.length);
        for (final String string : split) {
            if (!string.isEmpty()) {
                result.add(string);
            }
        }
        return result;
    }
}
