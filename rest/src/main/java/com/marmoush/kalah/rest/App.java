package com.marmoush.kalah.rest;

import com.marmoush.kalah.adapter.file.FileAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.netty.DisposableServer;

import java.io.IOException;
import java.time.Duration;
import java.util.stream.Stream;

public class App {
  private static final Logger LOG = LoggerFactory.getLogger(App.class.getName());
  private static final String DEFAULT_CONFIGS = "dev/app.yaml";

  public static void main(String[] args) {
    LOG.debug("hello DEBUG");
    LOG.info("hello INFO");
    LOG.warn("hello WARNING");
    LOG.error("hello ERROR");
    String configPath = Stream.of(args)
                              .filter(s -> s.contains("--config="))
                              .findFirst()
                              .map(s -> s.split("=")[1])
                              .orElse(DEFAULT_CONFIGS);
    try {
      AppConfig appConfig = new AppConfig(FileAdapter.parseYaml(configPath));
      Dependencies deps = new Dependencies(appConfig);
      //      DisposableServer disposableServer = deps.getHttpServer()
      //                                              .bindNow(Duration.ofSeconds(appConfig.getMaxStartupTime()));

      deps.getHttpServer().bindNow(Duration.ofSeconds(10)).onDispose().block();
      //      disposableServer.disposeNow();
      LOG.info("Server Shutdown!");
    } catch (IOException e) {
      LOG.error("Application couldn't be started,{}", e.getMessage());
      e.printStackTrace();
    }
  }

}
