package com.abc;

import java.time.LocalDateTime;

/**
 * DateProvider singleton.
 */
public class DateProvider
{
    private static DateProvider instance = new DateProvider();

    // Make the class immutable
    private DateProvider() {}

    public static DateProvider getInstance() { return instance; }

    public static LocalDateTime now() { return LocalDateTime.now(); }
}
