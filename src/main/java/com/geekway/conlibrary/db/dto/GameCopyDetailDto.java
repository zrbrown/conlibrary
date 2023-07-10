package com.geekway.conlibrary.db.dto;

import java.time.OffsetDateTime;

public record GameCopyDetailDto(long copyId,
                                String libraryId,
                                String library,
                                String owner,
                                String notes,
                                String gameTitle) {
}
