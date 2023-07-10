package com.geekway.conlibrary.db.repository;

import com.geekway.conlibrary.db.dto.AttendeeDetailDto;
import com.geekway.conlibrary.db.dto.AttendeeSummaryDto;
import com.geekway.conlibrary.db.entity.Attendees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendees, Long> {

    @Query("""
            select new com.geekway.conlibrary.db.dto.AttendeeSummaryDto(
                a.id, a.firstName, a.lastName, a.badgeId, listagg(g.title, ', '),
                (select count(ch) from Checkouts ch where ch.attendees.id = a.id),
                (select count(p) from Plays p where p.attendees.id = a.id)
            ) from Attendees a
            left join Checkouts c on c.attendees.id = a.id and c.endDatetime is null
            left join c.gameCopies gc
            left join gc.games g
            group by a.id, a.firstName, a.lastName, a.badgeId
            """)
    Page<AttendeeSummaryDto> getAll(Pageable pageable);

    @Query("""
            select new com.geekway.conlibrary.db.dto.AttendeeDetailDto(
                a.id, a.firstName, a.lastName, a.pronouns, a.badgeId
            ) from Attendees a
            where a.id = :attendeeId
            """)
    Optional<AttendeeDetailDto> findAttendee(@Param("attendeeId") long attendeeId);
}
