package com.marmoush.kalah.core.services;

import com.marmoush.kalah.core.domain.entity.Event;
import com.marmoush.kalah.core.domain.entity.GameCreated;
import com.marmoush.kalah.core.domain.entity.StonesMoved;
import com.marmoush.kalah.core.domain.error.KalahError;
import com.marmoush.kalah.core.domain.error.NotYourTurn;
import com.marmoush.kalah.core.domain.error.WrongIndex;
import com.marmoush.kalah.core.domain.port.EventRepo;
import com.marmoush.kalah.core.domain.port.IdGenerator;
import com.marmoush.kalah.core.domain.value.Id;
import com.marmoush.kalah.core.domain.value.KalahGame;
import io.vavr.control.Either;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

public class CommandService {
  public static final int N_OF_PITS = 6;
  public static final int N_OF_STONES = 6;

  private final IdGenerator idGenerator;
  private final EventRepo eventRepo;

  public CommandService(IdGenerator idGenerator, EventRepo eventRepo) {
    this.idGenerator = idGenerator;
    this.eventRepo = eventRepo;
  }

  public Mono<Either<KalahError, Event>> createGame() {
    Id id = new Id(idGenerator.generate());
    return eventRepo.addEvent(new GameCreated(id, N_OF_PITS, N_OF_STONES));
  }

  public Mono<Either<KalahError, KalahGame>> move(Id gameId, int pitId) {
    if (pitId < 0 || pitId == N_OF_PITS || pitId > N_OF_PITS * 2)
      return Mono.just(Either.left(new WrongIndex()));
    // Player one move check
    boolean comingFromPlayerOne = (pitId < N_OF_PITS) ? true : false;
    Mono<KalahGame> previousMoves = replayEvents(new KalahGame(6, 6), eventRepo.streamEvents(gameId));
    Mono<Either<KalahError, KalahGame>> newMoveAdded = previousMoves.map(g -> {
      if (g.isPlayerOneTurn() == comingFromPlayerOne) {
        return Either.right(g.manc(pitId));
      } else {
        return Either.left(new NotYourTurn());
      }
    });
    return newMoveAdded.flatMap(gm -> eventRepo.addEvent(new StonesMoved(gameId, pitId)).map(e -> gm));
  }

  public Mono<KalahGame> replayEvents(KalahGame init, Flux<Event> events) {
    return events.reduce(init,
                         (a, b) -> Match(b).of(Case($(instanceOf(GameCreated.class)),
                                                    e -> new KalahGame(e.getnPits(), e.getnStones())),
                                               Case($(instanceOf(StonesMoved.class)), e -> init.manc(e.getPitId()))));

  }

}
