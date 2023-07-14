package com.geekway.conlibrary.model;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.Locale;

public record GameCopySummary(long id, String libraryId, String library, String owner, String notes,
                              OffsetDateTime activeCheckoutStart, Attendee borrower,
                              CheckoutStatus checkoutStatus) {
    private static final DateTimeFormatter CHECK_OUT_IN_FORMAT = DateTimeFormatter.ofLocalizedTime(
            FormatStyle.SHORT
    ).withZone(ZoneOffset.of("-06:00"));

    public String checkoutTimeDisplay() {
        return activeCheckoutStart().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US) + " " + CHECK_OUT_IN_FORMAT.format(activeCheckoutStart());
    }

    public Duration checkoutDuration() {
        return Duration.between(activeCheckoutStart(), OffsetDateTime.now());
    }
}
