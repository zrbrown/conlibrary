package com.geekway.conlibrary.model;

public record GameCopyDetail(long id, String libraryCopyId, long libraryId, String libraryName,
                             String owner, String notes, String gameTitle) {
}
