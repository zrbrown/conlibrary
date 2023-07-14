package com.geekway.conlibrary.db.repository;

import com.geekway.conlibrary.db.entity.LibraryGameCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryGameCopyRepository extends JpaRepository<LibraryGameCopy, Long> {
}
