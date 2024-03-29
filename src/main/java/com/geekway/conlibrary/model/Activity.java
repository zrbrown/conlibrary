package com.geekway.conlibrary.model;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public record Activity(OffsetDateTime start, OffsetDateTime end, List<GameCopyMinimal> copyCheckouts,
                       Map<GameCopyMinimal, Duration> plays) {
    private static final DateTimeFormatter CHECK_OUT_IN_FORMAT = DateTimeFormatter.ofLocalizedTime(
            FormatStyle.SHORT
    ).withZone(ZoneOffset.of("-06:00"));

    public Duration duration() {
        return Duration.between(start(), end() == null ? OffsetDateTime.now() : end());
    }

    public String startDisplay() {
        return start().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US) + " " + CHECK_OUT_IN_FORMAT.format(start());
    }

    public String endDisplay() {
        return (end().getDayOfWeek() == start().getDayOfWeek() ?
                "" :
                end().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US))
                + " " + CHECK_OUT_IN_FORMAT.format(end());
    }
}
