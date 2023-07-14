package com.geekway.conlibrary.db.repository;

import com.geekway.conlibrary.db.dto.AttendeePlayDto;
import com.geekway.conlibrary.db.entity.Play;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayRepository extends JpaRepository<Play, Long> {

    @Query("""
            select new com.geekway.conlibrary.db.dto.AttendeePlayDto(
                c.id, c.startDatetime, c.endDatetime, gc.id, g.title
            ) from Play p
            join p.checkout c
            join c.libraryGameCopy.gameCopy gc
            join gc.game g
            where p.attendee.id = :attendeeId
            order by c.endDatetime asc
            """)
    List<AttendeePlayDto> getAttendeeCheckouts(@Param("attendeeId") long attendeeId);
}
