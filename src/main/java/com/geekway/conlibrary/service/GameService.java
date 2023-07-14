package com.geekway.conlibrary.service;

import com.geekway.conlibrary.db.dto.GameCopyCheckoutDto;
import com.geekway.conlibrary.db.dto.GameCopyDetailDto;
import com.geekway.conlibrary.db.dto.GameCopySummaryDto;
import com.geekway.conlibrary.db.dto.GameDto;
import com.geekway.conlibrary.db.entity.LibraryGameCopy;
import com.geekway.conlibrary.db.repository.*;
import com.geekway.conlibrary.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.*;

@Transactional(readOnly = true)
@Service
public class GameService {

    private static final int NEAR_OVERDUE_MINUTES = 230;
    private static final int OVERDUE_MINUTES = 240;
    private static final int EXTREME_OVERDUE_MINUTES = 480;

    private final GameRepository gameRepository;
    private final GameCopyRepository gameCopyRepository;
    private final CheckoutRepository checkoutRepository;
    private final LibraryGameCopyRepository libraryGameCopyRepository;
    private final LibraryRepository libraryRepository;

    public GameService(GameRepository gameRepository, GameCopyRepository gameCopyRepository,
                       CheckoutRepository checkoutRepository, LibraryGameCopyRepository libraryGameCopyRepository,
                       LibraryRepository libraryRepository) {
        this.gameRepository = gameRepository;
        this.gameCopyRepository = gameCopyRepository;
        this.checkoutRepository = checkoutRepository;
        this.libraryGameCopyRepository = libraryGameCopyRepository;
        this.libraryRepository = libraryRepository;
    }

    public List<GameSummary> getGames() {
        OffsetDateTime now = OffsetDateTime.now();
        Page<GameDto> games = gameRepository.getAll(1, PageRequest.of(0, 100).withSort(Sort.by("earliestCheckout")));
        return games.stream()
                .map(game -> new GameSummary(
                        game.gameId(), game.gameTitle(), game.copyCount(), game.currentCheckoutCount(), game.playCount(),
                        games.stream()
                                .filter(g -> g.gameId() == game.gameId())
                                .min(Comparator.comparing(GameDto::activeCheckoutStart))
                                .map(g -> g.activeCheckoutStart() == null ? null : Duration.between(g.activeCheckoutStart(), now).toMinutes())
                                .map(GameService::getCheckoutStatus)
                                .orElse(CheckoutStatus.AVAILABLE)
                ))
                .toList();
    }

    public GameSummary getGame(long gameId) {
        OffsetDateTime now = OffsetDateTime.now();
        GameDto game = gameRepository.getGame(gameId).orElseThrow();
        Long checkoutLength = game.activeCheckoutStart() == null ? null : Duration.between(game.activeCheckoutStart(), now).toMinutes();
        return new GameSummary(
                game.gameId(), game.gameTitle(), game.copyCount(), game.currentCheckoutCount(),
                game.playCount(), getCheckoutStatus(checkoutLength)
        );
    }

    @Transactional
    public GameCopy updateGameCopy(long gameCopyId, long libraryGameCopyId, String libraryCopyId, long libraryId,
                                   String owner, String notes) {
        com.geekway.conlibrary.db.entity.GameCopy gameCopy = gameCopyRepository.findById(gameCopyId).orElseThrow();
        gameCopy.setOwner(owner);
        gameCopy.setNotes(notes);

        // TODO library game copy <> library management needs to be in a separate admin section
        LibraryGameCopy libraryCopy = libraryGameCopyRepository.findById(libraryGameCopyId).orElseThrow();
        libraryCopy.setLibraryCopyId(libraryCopyId);
        libraryCopy.setLibrary(libraryRepository.getReferenceById(libraryId));

        com.geekway.conlibrary.db.entity.GameCopy savedGameCopy = gameCopyRepository.save(gameCopy);
        LibraryGameCopy savedLibrariesGameCopies = libraryGameCopyRepository.save(libraryCopy);

        return new com.geekway.conlibrary.model.GameCopy(
                savedGameCopy.getId(),
                savedLibrariesGameCopies.getLibraryCopyId(),
                savedLibrariesGameCopies.getLibrary().getId(),
                savedLibrariesGameCopies.getLibrary().getName(),
                savedGameCopy.getOwner(),
                savedGameCopy.getNotes()
        );
    }

    public List<GameCopySummary> getGameCopies(long gameId) {
        // TODO centralize now() somewhere
        OffsetDateTime now = OffsetDateTime.now();
        Page<GameCopySummaryDto> gameCopies = gameCopyRepository.findCopiesByGame(gameId, 1, PageRequest.of(0, 100));
        return gameCopies.stream()
                .map(gc -> new GameCopySummary(
                        gc.copyId(), gc.libraryId(), gc.library(), gc.owner(), gc.notes(),
                        gc.activeCheckoutStart(), gc.attendeeId() == null ? null : new Attendee(
                        gc.attendeeId(), gc.attendeeName(), gc.attendeeSurname()),
                        getCheckoutStatus(gc.activeCheckoutStart() == null ? null : Duration.between(gc.activeCheckoutStart(), now).toMinutes())
                ))
                .toList();
    }

    public GameCopyDetail getGameCopy(long gameCopyId) {
        GameCopyDetailDto gameCopy = gameCopyRepository.findGameCopyDetail(gameCopyId, 1).orElseThrow();

        return new GameCopyDetail(
                gameCopy.copyId(),
                gameCopy.libraryCopyId(),
                gameCopy.libraryId(),
                gameCopy.libraryName(),
                gameCopy.owner(),
                gameCopy.notes(),
                gameCopy.gameTitle()
        );
    }

    public List<Checkout> getCheckouts(long gameCopyId) {
        OffsetDateTime now = OffsetDateTime.now();
        List<GameCopyCheckoutDto> gameCopyCheckouts = checkoutRepository.getGameCopyCheckouts(gameCopyId);
        List<Checkout> checkouts = new ArrayList<>();
        for (int i = 0; i < gameCopyCheckouts.size(); i++) {
            GameCopyCheckoutDto c = gameCopyCheckouts.get(i);
            OffsetDateTime nextCheckoutStart = i == gameCopyCheckouts.size() - 1 ? now : gameCopyCheckouts.get(i + 1).start();
            OffsetDateTime end = c.end() == null ? now : c.end();

            checkouts.add(new Checkout(
                    c.checkoutId(),
                    new Attendee(c.attendeeId(), c.attendeeName(), c.attendeeSurname()),
                    c.start(),
                    c.end(),
                    Duration.between(end, nextCheckoutStart),
                    now
            ));
        }
        Collections.reverse(checkouts);

        return checkouts;
    }

    private static CheckoutStatus getCheckoutStatus(Long checkoutLengthMinutes) {
        if (checkoutLengthMinutes == null) {
            return CheckoutStatus.AVAILABLE;
        } else if (checkoutLengthMinutes >= EXTREME_OVERDUE_MINUTES) {
            return CheckoutStatus.EXTREME_OVERDUE;
        } else if (checkoutLengthMinutes >= OVERDUE_MINUTES) {
            return CheckoutStatus.OVERDUE;
        } else if (checkoutLengthMinutes >= NEAR_OVERDUE_MINUTES) {
            return CheckoutStatus.NEAR_OVERDUE;
        } else {
            return CheckoutStatus.CHECKED_OUT;
        }
    }
}
