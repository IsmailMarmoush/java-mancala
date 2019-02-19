package com.marmoush.kalah.core.domain.port;

import com.marmoush.kalah.core.domain.error.KalahError;
import com.marmoush.kalah.core.domain.value.Id;
import com.marmoush.kalah.core.domain.value.KalahGame;
import io.vavr.control.Either;
import reactor.core.publisher.Mono;

public interface GameRepo {
  Mono<Either<KalahError, KalahGame>> findGame(Id gameId);
}
