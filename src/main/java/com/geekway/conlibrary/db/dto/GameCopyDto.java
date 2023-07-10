package com.geekway.conlibrary.db.dto;

import java.time.OffsetDateTime;

public record GameCopyDto(long copyId,
                          String libraryId,
                          String library,
                          String owner,
                          String notes,
                          OffsetDateTime activeCheckoutStart,
                          Long attendeeId,
                          String attendeeName,
                          String attendeeSurname) {
}
