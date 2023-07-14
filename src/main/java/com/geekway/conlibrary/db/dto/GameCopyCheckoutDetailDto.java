package com.geekway.conlibrary.db.dto;

public record GameCopyCheckoutDetailDto(Long libraryGameCopyId,
                                        String gameTitle,
                                        String attendeeName,
                                        String attendeeSurname,
                                        Long checkoutId) {
}
