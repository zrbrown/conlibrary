package com.geekway.conlibrary.db.repository;

import com.geekway.conlibrary.db.dto.GameCopyDetailDto;
import com.geekway.conlibrary.db.dto.GameCopySummaryDto;
import com.geekway.conlibrary.db.entity.GameCopy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameCopyRepository extends JpaRepository<GameCopy, Long> {

    @Query("""
            select new com.geekway.conlibrary.db.dto.GameCopyDetailDto(
                        gc.id, lgc.libraryCopyId, lgc.id, lgc.library.name, gc.owner, gc.notes, g.title
                    ) from GameCopy gc
                    join gc.libraryGameCopies lgc
                    join gc.game g
                    where gc.id = :gameCopyId and
                    lgc.library.event.id = :eventId
            """)
    Optional<GameCopyDetailDto> findGameCopyDetail(@Param("gameCopyId") long gameCopyId,
                                                   @Param("eventId") long eventId);

    @Query("""
            select new com.geekway.conlibrary.db.dto.GameCopySummaryDto(
                gc.id, lgc.libraryCopyId, lgc.library.name, gc.owner, gc.notes,
                attendee.start, attendee.id, attendee.first, attendee.last
            ) from GameCopy gc
            join gc.libraryGameCopies lgc
            left join lateral (
                select c_c.startDatetime as start, c_a.id as id, c_a.firstName as first, c_a.lastName as last
                    from Checkout c_c
                    join c_c.libraryGameCopy.gameCopy c_gc
                    join c_c.attendee c_a
                    where c_gc.id = gc.id and
                    c_c.endDatetime is null) attendee
            where gc.game.id = :gameId and
            lgc.library.event.id = :eventId
            """)
    Page<GameCopySummaryDto> findCopiesByGame(@Param("gameId") long gameId,
                                              @Param("eventId") long eventId,
                                              Pageable pageable);
}
