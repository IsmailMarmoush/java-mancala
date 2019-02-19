package com.marmoush.kalah.rest;

import com.google.gson.GsonBuilder;
import com.marmoush.kalah.adapter.gson.GsonAdapter;
import com.marmoush.kalah.adapter.idgenerator.SerialIdGenerator;
import com.marmoush.kalah.adapter.idgenerator.UUIDGenerator;
import com.marmoush.kalah.adapter.repository.InMemoryEventRepo;
import com.marmoush.kalah.core.domain.port.EventRepo;
import com.marmoush.kalah.core.domain.port.IdGenerator;
import com.marmoush.kalah.core.domain.port.Json;
import com.marmoush.kalah.core.services.CommandService;
import com.marmoush.kalah.rest.controller.GameController;
import io.vavr.gson.VavrGson;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServer;
import reactor.netty.http.server.HttpServerRoutes;

import java.util.function.Consumer;

public final class Dependencies {
  private final AppConfig appConfig;

  // Dependencies
  private final Json json;
  private final IdGenerator idGenerator;
  private final EventRepo eventRepo;
  private final CommandService commandService;
  private final GameController gameController;
  private final HttpServer httpServer;
  private final HttpClient httpClient;

  public Dependencies(AppConfig appConfig) {
    this.appConfig = appConfig;

    // Setup Repositories
    this.eventRepo = new InMemoryEventRepo();

    // Setup Identity Generator
    if (!appConfig.getIdGeneratorType().equals("serial")) {
      this.idGenerator = new UUIDGenerator();
    } else {
      this.idGenerator = new SerialIdGenerator();
    }

    // Setup Json Dependencies
    GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
    gsonBuilder = VavrGson.registerAll(gsonBuilder);
    json = new GsonAdapter(gsonBuilder.create());

    // Setup Services
    this.commandService = new CommandService(idGenerator, eventRepo);
    this.gameController = new GameController(this.commandService, this.json);

    // Setup Server
    this.httpServer = HttpServer.create()
                                .host(appConfig.getHost())
                                .port(appConfig.getPort())
                                .route(getRoutes())
                                .wiretap(appConfig.isWiretapping());
    this.httpClient = HttpClient.create().wiretap(true).baseUrl(appConfig.getHost() + ":" + appConfig.getPort());
  }

  private Consumer<HttpServerRoutes> getRoutes() {
    return routes -> routes.post(appConfig.getGameControllerPath(), (req, resp) -> gameController.createGame(req, resp))
                           .put(appConfig.getGameControllerPath() + "/{gameId}/pits/{pitId}",
                                (req, resp) -> gameController.move(req, resp));
  }

  public GameController getGameController() {
    return gameController;
  }

  public HttpServer getHttpServer() {
    return this.httpServer;
  }

  public HttpClient getHttpClient() {
    return this.httpClient;
  }

  public Json getJson() {
    return json;
  }

  public EventRepo getEventRepo(){
    return this.eventRepo;
  }

}
