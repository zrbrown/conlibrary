package com.geekway.conlibrary.model;

public record GameCopy(long id, String libraryCopyId, long libraryId, String libraryName,
                       String owner, String notes) {
}
