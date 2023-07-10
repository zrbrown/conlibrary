package com.geekway.conlibrary.db.dto;

public record AttendeeSummaryDto(long id,
                                 String name,
                                 String surname,
                                 String badgeId,
                                 String currentCheckouts,
                                 long checkoutCount,
                                 long playCount) {
}
