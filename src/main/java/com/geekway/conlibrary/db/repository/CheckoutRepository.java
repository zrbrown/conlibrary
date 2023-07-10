package com.geekway.conlibrary.db.repository;

import com.geekway.conlibrary.db.dto.AttendeeCheckoutDto;
import com.geekway.conlibrary.db.dto.GameCopyCheckoutDto;
import com.geekway.conlibrary.db.entity.Checkouts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkouts, Long> {

    @Query("""
            select new com.geekway.conlibrary.db.dto.AttendeeCheckoutDto(
                c.id, c.startDatetime, c.endDatetime, gc.id, g.title
            ) from Checkouts c
            join c.gameCopies gc
            join gc.games g
            where c.attendees.id = :attendeeId
            order by c.startDatetime asc
            """)
    List<AttendeeCheckoutDto> getAttendeeCheckouts(@Param("attendeeId") long attendeeId);

    @Query("""
            select new com.geekway.conlibrary.db.dto.GameCopyCheckoutDto(
                c.id, c.startDatetime, c.endDatetime, a.id, a.firstName, a.lastName
            ) from Checkouts c
            join c.attendees a
            where c.gameCopies.id = :gameCopyId
            order by c.startDatetime asc
            """)
    List<GameCopyCheckoutDto> getGameCopyCheckouts(@Param("gameCopyId") long gameCopyId);
}
