package com.geekway.conlibrary.db.dto;

import java.time.OffsetDateTime;

public record GameDto(long gameId,
                      String gameTitle,
                      long copyCount,
                      long currentCheckoutCount,
                      long playCount,
                      OffsetDateTime activeCheckoutStart) {
}
