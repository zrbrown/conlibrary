package com.geekway.conlibrary.db.repository;

import com.geekway.conlibrary.db.dto.AttendeeCheckoutDetailDto;
import com.geekway.conlibrary.db.dto.AttendeeCheckoutDto;
import com.geekway.conlibrary.db.dto.GameCopyCheckoutDetailDto;
import com.geekway.conlibrary.db.dto.GameCopyCheckoutDto;
import com.geekway.conlibrary.db.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    @Query("""
            select new com.geekway.conlibrary.db.dto.AttendeeCheckoutDto(
                c.id, c.startDatetime, c.endDatetime, gc.id, g.title
            ) from Checkout c
            join c.libraryGameCopy.gameCopy gc
            join gc.game g
            where c.attendee.id = :attendeeId
            order by c.startDatetime asc
            """)
    List<AttendeeCheckoutDto> getAttendeeCheckouts(@Param("attendeeId") long attendeeId);

    @Query("""
            select new com.geekway.conlibrary.db.dto.GameCopyCheckoutDto(
                c.id, c.startDatetime, c.endDatetime, a.id, a.firstName, a.lastName
            ) from Checkout c
            join c.attendee a
            where c.libraryGameCopy.gameCopy.id = :gameCopyId
            order by c.startDatetime asc
            """)
    List<GameCopyCheckoutDto> getGameCopyCheckouts(@Param("gameCopyId") long gameCopyId);

    @Query("""
            select new com.geekway.conlibrary.db.dto.GameCopyCheckoutDetailDto(
                lgc.id, lgc.gameCopy.game.title, c.name, c.surname, c.checkoutId
            ) from LibraryGameCopy lgc
            left join lateral (
                select lgc_c.id as checkoutId,
                       lgc_c.attendee.firstName as name,
                       lgc_c.attendee.lastName as surname
                from lgc.checkouts lgc_c
                where lgc_c.endDatetime is null
            ) as c
            where lgc.libraryCopyId = :libraryCopyId and
            lgc.library.event.id = :eventId
            """)
    Optional<GameCopyCheckoutDetailDto> getCheckoutGameCopy(@Param("libraryCopyId") String libraryCopyId,
                                                            @Param("eventId") long eventId);

    @Query("""
            select new com.geekway.conlibrary.db.dto.AttendeeCheckoutDetailDto(
                c.libraryGameCopyId, c.gameTitles, coalesce(c.gameCount, 0), a.id, a.firstName, a.lastName, c.checkoutId
            ) from Attendee a
            left join lateral(
                select a_c.id as checkoutId,
                       a_c.libraryGameCopy.id as libraryGameCopyId,
                       listagg(a_c.libraryGameCopy.gameCopy.game.title, ', ') as gameTitles,
                       count(a_c) as gameCount
                from a.checkouts a_c
                where a_c.endDatetime is null
                group by a_c.id,
                         a_c.libraryGameCopy.id
            ) as c
            where a.badgeId = :badgeId and
            a.event.id = :eventId
            """)
    Optional<AttendeeCheckoutDetailDto> getCheckoutAttendeeGameCopy(@Param("badgeId") String badgeId,
                                                                    @Param("eventId") long eventId);

    @Query("""
            select new com.geekway.conlibrary.db.dto.AttendeeCheckoutDetailDto(
                c.libraryGameCopyId, c.gameTitles, coalesce(c.gameCount, 0), a.id, a.firstName, a.lastName, c.checkoutId
            ) from Attendee a
            left join lateral(
                select a_c.id as checkoutId,
                       a_c.libraryGameCopy.id as libraryGameCopyId,
                       listagg(a_c.libraryGameCopy.gameCopy.game.title, ', ') as gameTitles,
                       count(a_c) as gameCount
                from a.checkouts a_c
                where a_c.endDatetime is null
                group by a_c.id,
                         a_c.libraryGameCopy.id
            ) as c
            where a.id = :attendeeId and
            a.event.id = :eventId
            """)
    Optional<AttendeeCheckoutDetailDto> getCheckoutAttendeeGameCopy(@Param("attendeeId") long badgeId,
                                                                    @Param("eventId") long eventId);
}
