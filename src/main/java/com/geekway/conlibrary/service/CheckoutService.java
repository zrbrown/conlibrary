package com.geekway.conlibrary.service;

import com.geekway.conlibrary.db.dto.AttendeeCheckoutDetailDto;
import com.geekway.conlibrary.db.dto.GameCopyCheckoutDetailDto;
import com.geekway.conlibrary.db.entity.Checkout;
import com.geekway.conlibrary.db.repository.AttendeeRepository;
import com.geekway.conlibrary.db.repository.CheckoutRepository;
import com.geekway.conlibrary.db.repository.LibraryGameCopyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final AttendeeRepository attendeeRepository;
    private final LibraryGameCopyRepository libraryGameCopyRepository;

    public CheckoutService(CheckoutRepository checkoutRepository, AttendeeRepository attendeeRepository,
                           LibraryGameCopyRepository libraryGameCopyRepository) {
        this.checkoutRepository = checkoutRepository;
        this.attendeeRepository = attendeeRepository;
        this.libraryGameCopyRepository = libraryGameCopyRepository;
    }

    public Optional<GameCopyCheckoutDetailDto> searchGameBarcode(String barcode) {
        return checkoutRepository.getCheckoutGameCopy(barcode, 1);
    }

    public Optional<AttendeeCheckoutDetailDto> searchAttendeeBarcode(String barcode) {
        return checkoutRepository.getCheckoutAttendeeGameCopy(barcode, 1);
    }

    @Transactional
    public void checkout(long libraryCopyId, long attendeeId, boolean overrideLimit) {
        AttendeeCheckoutDetailDto attendee = checkoutRepository.getCheckoutAttendeeGameCopy(attendeeId, 1).orElseThrow();
        if (attendee.checkoutCount() > 0 && !overrideLimit) {
            throw new RuntimeException("Attendee already has games checked out. Override limit must be indicated to check out any more.");
        }

        Checkout checkout = new Checkout();
        checkout.setAttendee(attendeeRepository.getReferenceById(attendeeId));
        checkout.setLibraryGameCopy(libraryGameCopyRepository.getReferenceById(libraryCopyId));
        checkout.setStartDatetime(OffsetDateTime.now());
        checkoutRepository.save(checkout);
    }

    @Transactional
    public void checkin(long checkoutId) {
        Checkout checkout = checkoutRepository.findById(checkoutId).orElseThrow();

        checkout.setEndDatetime(OffsetDateTime.now());
        checkoutRepository.save(checkout);
    }
}
