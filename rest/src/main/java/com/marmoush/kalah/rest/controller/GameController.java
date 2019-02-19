package com.marmoush.kalah.rest.controller;

import com.marmoush.kalah.core.domain.port.Json;
import com.marmoush.kalah.core.domain.value.Id;
import com.marmoush.kalah.core.domain.value.KalahGame;
import com.marmoush.kalah.core.services.CommandService;
import io.vavr.collection.List;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

public class GameController {
  private final Logger log = LoggerFactory.getLogger(GameController.class.getName());

  private final Json json;
  private final CommandService commandService;

  public GameController(CommandService commandService, Json json) {
    this.json = json;
    this.commandService = commandService;
  }

  public Publisher<Void> createGame(HttpServerRequest req, HttpServerResponse resp) {
    return commandService.createGame().flatMap(e -> {
      if (e.isRight()) {
        String message = createGameResponseBody(e.get().getId().getValue());
        return resp.status(201).sendString(Mono.just(message)).then();
      } else {
        return resp.status(400).sendString(Mono.just(e.getLeft().getMessage())).then();
      }
    });
  }

  public Publisher<Void> move(HttpServerRequest req, HttpServerResponse resp) {
    Id gameId = new Id(req.param("gameId"));
    int pitId = Integer.parseInt(req.param("pitId"));

    return commandService.move(gameId, pitId).flatMap(e -> {
      if (e.isRight()) {
        String s = moveResponseBody(gameId.getValue(), e.get());
        return resp.status(200).sendString(Mono.just(s)).then();
      } else {
        return resp.status(400).sendString(Mono.just(e.getLeft().getMessage())).then();
      }
    });
  }

  // TODO use Gson
  private String createGameResponseBody(String id) {
    return String.format("{ \"id\": \"%s\", \"url\": \"http://localhost:8080/games/%s\" }", id, id);
  }

  // TODO use Gson
  private String moveResponseBody(String id, KalahGame kg) {
    List<String> values = kg.getPlayerOnePits()
                            .appendAll(kg.getPlayerTwoPits())
                            .map(i -> i + "")
                            .zipWithIndex()
                            .map(t -> "\"" + t._2 + "\"" + ":" + "\"" + t._1 + "\"");
    String kgStr = String.join(",", values);
    return String.format("{\"id\":\"%s\",\"url\":\"http://localhost:8080/games/%s\",\"status\":{%s} }", id, id, kgStr);
  }
}
