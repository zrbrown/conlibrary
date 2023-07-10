package com.geekway.conlibrary.db.repository;

import com.geekway.conlibrary.db.dto.GameCopyDetailDto;
import com.geekway.conlibrary.db.dto.GameCopyDto;
import com.geekway.conlibrary.db.entity.GameCopies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameCopyRepository extends JpaRepository<GameCopies, Long> {

    @Query("""
            select new com.geekway.conlibrary.db.dto.GameCopyDetailDto(
                        gc.id, lgc.libraryCopyId, lgc.libraries.name, gc.owner, gc.notes, g.title
                    ) from GameCopies gc
                    join gc.librariesGameCopieses lgc
                    join gc.games g
                    where gc.id = :gameCopyId and
                    lgc.libraries.events.id = :eventId
            """)
    Optional<GameCopyDetailDto> findGameCopy(@Param("gameCopyId") long gameCopyId,
                                             @Param("eventId") long eventId);

    @Query("""
            select new com.geekway.conlibrary.db.dto.GameCopyDto(
                gc.id, lgc.libraryCopyId, lgc.libraries.name, gc.owner, gc.notes, attendee.start, attendee.id, attendee.first, attendee.last
            ) from GameCopies gc
            join gc.librariesGameCopieses lgc
            left join lateral (select min(c_c.startDatetime) as start, c_a.id as id, c_a.firstName as first, c_a.lastName as last
                from Checkouts c_c
                join c_c.gameCopies c_gc
                join c_gc.games c_g
                join c_c.attendees c_a
                where c_g.id = gc.games.id and
                c_c.endDatetime is null
                group by c_a.id, c_a.firstName, c_a.lastName) attendee
            where gc.games.id = :gameId and
            lgc.libraries.events.id = :eventId
            """)
    Page<GameCopyDto> findCopiesByGame(@Param("gameId") long gameId,
                                       @Param("eventId") long eventId,
                                       Pageable pageable);
}
