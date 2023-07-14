package com.geekway.conlibrary.db.dto;

import java.time.OffsetDateTime;

public record GameCopyDetailDto(long copyId,
                                String libraryCopyId,
                                long libraryId,
                                String libraryName,
                                String owner,
                                String notes,
                                String gameTitle) {
}
