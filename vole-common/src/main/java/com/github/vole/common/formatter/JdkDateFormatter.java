package com.github.vole.common.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class JdkDateFormatter implements Formatter<Date> {
    public static final String[] DATE_PATTERNS = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yy-MM-dd", "yy-MM-dd HH:mm:ss"};

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        if (text == null || text.isEmpty()) return null;
        for (String pattern : DATE_PATTERNS) {
            if (text.length() == pattern.length()) {
                return (new SimpleDateFormat(pattern).parse(text));
            }
        }
        throw new IllegalArgumentException("can't find date parse pattern for '" + text + "'");
    }

    @Override
    public String print(Date object, Locale locale) {
        return object.toString();
    }
}
