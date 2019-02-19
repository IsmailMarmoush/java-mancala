package com.marmoush.kalah.rest.controller;

import com.marmoush.kalah.core.domain.entity.Event;
import com.marmoush.kalah.core.domain.entity.GameCreated;
import com.marmoush.kalah.core.domain.entity.StonesMoved;
import com.marmoush.kalah.core.domain.port.EventRepo;
import com.marmoush.kalah.core.domain.port.Json;
import com.marmoush.kalah.core.domain.value.Id;
import com.marmoush.kalah.rest.AppConfig;
import com.marmoush.kalah.rest.Dependencies;
import com.marmoush.kalah.rest.TestResources;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.DisposableServer;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.Duration;

import static com.marmoush.kalah.adapter.file.FileAdapter.parseYaml;

public class GameControllerTest {
  private static final Logger log = LoggerFactory.getLogger(GameControllerTest.class.getName());

  private static DisposableServer commandServer;
  private static AppConfig appConfig;
  private static Dependencies deps;
  private static Json json;
  private static EventRepo eventRepo;

  @BeforeAll
  public static void beforeAll() throws IOException {
    appConfig = new AppConfig(parseYaml(TestResources.TEST_YAML));
    deps = new Dependencies(appConfig);
    json = deps.getJson();
    eventRepo = deps.getEventRepo();
    commandServer = deps.getHttpServer().bindNow(Duration.ofSeconds(10));
    post(appConfig.getGameControllerPath(), ""); // 0
    post(appConfig.getGameControllerPath(), " "); // 1
    post(appConfig.getGameControllerPath(), " "); // 2
  }

  @AfterAll
  public static void afterAll() {
    commandServer.disposeNow();
  }

  @Test
  public void testCreateGame() {
    Tuple2<Integer, String> t = post(appConfig.getGameControllerPath(), "");
    Assertions.assertEquals(201, t._1.intValue());
    System.out.println(t._2);
  }

  @Test
  public void testMove() {
    Tuple2<Integer, String> firstMove = put(appConfig.getGameControllerPath() + "/0/pits/0", "");
    Tuple2<Integer, String> secondMove = put(appConfig.getGameControllerPath() + "/0/pits/2", "");
    Flux<Event> events = eventRepo.streamEvents(Id.of(0));
    StepVerifier.create(events.log())
                .expectNext(new GameCreated(Id.of(0), 6, 6))
                .expectNext(new StonesMoved(Id.of(0), 0))
                .expectNext(new StonesMoved(Id.of(0), 2))
                .expectComplete()
                .verify();
    Assertions.assertEquals(200, firstMove._1.intValue());
    System.out.println(firstMove._2);

    Assertions.assertEquals(200, secondMove._1.intValue());
    System.out.println(secondMove._2);
  }

  @Test
  void samePlayerMove() {
    Tuple2<Integer, String> firstMove = put(appConfig.getGameControllerPath() + "/0/pits/1", "");
    Tuple2<Integer, String> secondMove = put(appConfig.getGameControllerPath() + "/0/pits/4", "");
    Assertions.assertEquals(400, secondMove._1.intValue());
    System.out.println(secondMove._2);
  }

  @Test
  void wrongPitId() {
    Tuple2<Integer, String> move = put(appConfig.getGameControllerPath() + "/1/pits/20", " ");
    Assertions.assertEquals(400, move._1.intValue());
    System.out.println(move._2);
  }

  private static Tuple2<Integer, String> post(String path, String payload) {
    return deps.getHttpClient()
               .post()
               .uri(path)
               .send(ByteBufFlux.fromString(Mono.justOrEmpty(payload)))
               .responseSingle((res, body) -> Mono.just(res.status().code())
                                                  .zipWith(body.asString().defaultIfEmpty("empty content")))
               .map(t -> Tuple.of(t.getT1(), t.getT2()))
               .block();
  }

  private static Tuple2<Integer, String> put(String path, String payload) {
    return deps.getHttpClient()
               .put()
               .uri(path)
               .send(ByteBufFlux.fromString(Mono.justOrEmpty(payload)))
               .responseSingle((res, body) -> Mono.just(res.status().code())
                                                  .zipWith(body.asString().defaultIfEmpty("empty content")))
               .map(t -> Tuple.of(t.getT1(), t.getT2()))
               .block();
  }
}
