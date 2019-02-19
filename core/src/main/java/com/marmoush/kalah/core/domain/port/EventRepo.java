package com.marmoush.kalah.core.domain.port;

import com.marmoush.kalah.core.domain.entity.Event;
import com.marmoush.kalah.core.domain.error.KalahError;
import com.marmoush.kalah.core.domain.value.Id;
import io.vavr.control.Either;
import io.vavr.control.Option;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventRepo {
  Mono<Either<KalahError, Event>> addEvent(Event event);

  Mono<Option<Event>> findFirst(Id eventId);

  Flux<Event> streamEvents(Id eventId);
}
