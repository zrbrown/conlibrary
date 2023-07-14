package com.geekway.conlibrary.db.dto;

public record GameCopyDto(long copyId,
                          String libraryCopyId,
                          long libraryId,
                          String owner,
                          String notes) {
}
