package com.geekway.conlibrary.db.dto;

public record AttendeeCheckoutDetailDto(Long libraryGameCopyId,
                                        String gameTitles,
                                        long checkoutCount,
                                        long attendeeId,
                                        String attendeeName,
                                        String attendeeSurname,
                                        Long checkoutId) {
}
