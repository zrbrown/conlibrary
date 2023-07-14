package com.geekway.conlibrary.db.repository;

import com.geekway.conlibrary.db.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {
}
