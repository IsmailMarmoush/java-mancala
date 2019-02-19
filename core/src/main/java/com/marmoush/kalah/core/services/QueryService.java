package com.marmoush.kalah.core.services;

import com.marmoush.kalah.core.domain.error.KalahError;
import com.marmoush.kalah.core.domain.port.GameRepo;
import com.marmoush.kalah.core.domain.value.Id;
import com.marmoush.kalah.core.domain.value.KalahGame;
import io.vavr.control.Either;
import reactor.core.publisher.Mono;

// TODO as soon as app evolves, do a loop which fills the game repo, either scheduled or as listener
public class QueryService {
  private final GameRepo gameRepo;

  public QueryService(GameRepo gameRepo) {
    this.gameRepo = gameRepo;
  }

  public Mono<Either<KalahError, KalahGame>> findGame(Id gameId) {
    return gameRepo.findGame(gameId);
  }
}
