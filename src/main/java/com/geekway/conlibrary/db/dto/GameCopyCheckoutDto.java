package com.geekway.conlibrary.db.dto;

import java.time.OffsetDateTime;

public record GameCopyCheckoutDto(long checkoutId,
                                  OffsetDateTime start,
                                  OffsetDateTime end,
                                  long attendeeId,
                                  String attendeeName,
                                  String attendeeSurname) {
}
