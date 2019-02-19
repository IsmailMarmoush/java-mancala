package com.marmoush.kalah.adapter.repository;

import com.marmoush.kalah.core.domain.entity.Event;
import com.marmoush.kalah.core.domain.entity.GameCreated;
import com.marmoush.kalah.core.domain.entity.StonesMoved;
import com.marmoush.kalah.core.domain.error.KalahError;
import com.marmoush.kalah.core.domain.value.Id;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class InMemoryEventRepoTest {
  Event e1 = new GameCreated(new Id("stream-1"), 6, 6);
  Event e1_1 = new StonesMoved(new Id("stream-1"), 1);
  Event e2 = new GameCreated(new Id("stream-2"), 6, 6);
  Event e2_1 = new StonesMoved(new Id("stream-1"), 1);

  @Test
  void nonBlockingSaveEvent() {
    InMemoryEventRepo repo = new InMemoryEventRepo();
    Mono<Either<KalahError, Event>> save = repo.addEvent(e1);
    StepVerifier.create(save.log()).expectNext(Either.right(e1)).expectComplete().verify();
  }
}
