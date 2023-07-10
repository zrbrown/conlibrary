package com.geekway.conlibrary.db.repository;

import com.geekway.conlibrary.db.dto.GameDto;
import com.geekway.conlibrary.db.entity.Games;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Games, Long> {

    @Query("""
            select new com.geekway.conlibrary.db.dto.GameDto(
                g.id, g.title, count(gc),
                (select count(c_c) from Checkouts c_c
                join c_c.gameCopies c_gc
                join c_gc.games c_g
                where c_g.id = g.id and
                c_c.endDatetime is null),
                count(p),
                (select min(c.startDatetime) from Checkouts c_c
                join c_c.gameCopies c_gc
                join c_gc.games c_g
                where c_g.id = g.id and
                c_c.endDatetime is null)
            ) from Games g
            join g.gameCopieses gc
            left join gc.checkoutses c
            left join c.playses p
            where g.id = :gameId
            group by g.id, g.title
            """)
    Optional<GameDto> getGame(@Param("gameId") long id);

    @Query("""
            select new com.geekway.conlibrary.db.dto.GameDto(
                g.id, g.title, count(gc),
                (select count(c_c) from Checkouts c_c
                join c_c.gameCopies c_gc
                join c_gc.games c_g
                where c_g.id = g.id and
                c_c.endDatetime is null),
                count(p),
                (select min(c.startDatetime) from Checkouts c_c
                join c_c.gameCopies c_gc
                join c_gc.games c_g
                where c_g.id = g.id and
                c_c.endDatetime is null)
            ) from Games g
            join g.gameCopieses gc
            left join gc.checkoutses c
            left join c.playses p
            group by g.id, g.title
            """)
    Page<GameDto> getAll(Pageable pageable);
}
