package com.marmoush.kalah.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingTest {
  private static final Logger log = LoggerFactory.getLogger(LoggingTest.class.getName());

  @BeforeEach
  void before() {
  }

  @Test
  void testLoggingLevels() {
    log.info("info");
    log.warn("warning");
    log.error("severe");
  }
}
