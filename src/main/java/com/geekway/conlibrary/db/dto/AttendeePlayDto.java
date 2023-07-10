package com.geekway.conlibrary.db.dto;

import java.time.OffsetDateTime;

public record AttendeePlayDto(long checkoutId,
                              OffsetDateTime start,
                              OffsetDateTime end,
                              long gameCopyId,
                              String gameTitle) {
}
