package com.marmoush.kalah.adapter.repository;

import com.marmoush.kalah.core.domain.entity.Event;
import com.marmoush.kalah.core.domain.error.KalahError;
import com.marmoush.kalah.core.domain.port.EventRepo;
import com.marmoush.kalah.core.domain.value.Id;
import io.vavr.control.Either;
import io.vavr.control.Option;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryEventRepo implements EventRepo {
  private final Map<Id, List<Event>> m = new HashMap<>();

  @Override
  public Mono<Either<KalahError, Event>> addEvent(Event event) {
    List<Event> events = m.get(event.getId());
    if (events == null) {
      List<Event> evList = new ArrayList<>();
      evList.add(event);
      m.put(event.getId(), evList);
    } else {
      events.add(event);
    }
    return Mono.just(Either.right(event));
  }

  @Override
  public Mono<Option<Event>> findFirst(Id eventId) {
    List<Event> events = m.get(eventId);
    if (events == null || events.isEmpty()) {
      return Mono.just(Option.none());
    } else {
      return Mono.just(Option.of(events.get(0)));
    }
  }

  @Override
  public Flux<Event> streamEvents(Id eventId) {
    List<Event> events = m.get(eventId);
    if (events == null) {
      return Flux.empty();
    } else {
      return Flux.fromIterable(events);
    }
  }
}
