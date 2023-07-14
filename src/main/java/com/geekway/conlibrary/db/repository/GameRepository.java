package com.geekway.conlibrary.db.repository;

import com.geekway.conlibrary.db.dto.GameDto;
import com.geekway.conlibrary.db.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("""
            select new com.geekway.conlibrary.db.dto.GameDto(
                g.id, g.title, count(gc), current.checkoutCount,
                (select count(p) from Play p
                    where p.checkout.libraryGameCopy.gameCopy.game.id = g.id),
                current.earliest
            ) from Game g
            join g.gameCopies gc
            left join lateral (
                select count(c_c) as checkoutCount, min(c_c.startDatetime) as earliest from Checkout c_c
                    join c_c.libraryGameCopy.gameCopy c_gc
                    join c_gc.game c_g
                    where c_g.id = g.id and
                    c_c.endDatetime is null) as current
            where g.id = :gameId
            group by g.id, g.title, current.checkoutCount, current.earliest
            """)
    Optional<GameDto> getGame(@Param("gameId") long id);

    @Query("""
            select new com.geekway.conlibrary.db.dto.GameDto(
                g.id, g.title, count(gc), current.checkoutCount,
                (select count(p) from Play p
                    where p.checkout.libraryGameCopy.gameCopy.game.id = g.id),
                    current.earliest
            ), current.earliest as earliestCheckout
            from Game g
            join g.gameCopies gc
            join gc.libraryGameCopies lgc
            left join lateral (
                select count(c_c) as checkoutCount, min(c_c.startDatetime) as earliest from Checkout c_c
                    join c_c.libraryGameCopy.gameCopy c_gc
                    join c_gc.game c_g
                    where c_g.id = g.id and
                    c_c.endDatetime is null) as current
            where lgc.library.event.id = :eventId
            group by g.id, g.title, current.checkoutCount, current.earliest
            """)
    Page<GameDto> getAll(@Param("eventId") long eventId, Pageable pageable);
}
