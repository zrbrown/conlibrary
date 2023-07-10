package com.geekway.conlibrary.db.repository;

import com.geekway.conlibrary.db.dto.AttendeePlayDto;
import com.geekway.conlibrary.db.entity.Plays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayRepository extends JpaRepository<Plays, Long> {

    @Query("""
            select new com.geekway.conlibrary.db.dto.AttendeePlayDto(
                c.id, c.startDatetime, c.endDatetime, gc.id, g.title
            ) from Plays p
            join p.checkouts c
            join c.gameCopies gc
            join gc.games g
            where p.attendees.id = :attendeeId
            order by c.endDatetime asc
            """)
    List<AttendeePlayDto> getAttendeeCheckouts(@Param("attendeeId") long attendeeId);
}
