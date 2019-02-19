package com.marmoush.kalah.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.marmoush.kalah.adapter.file.FileAdapter.parseYaml;

public class AppConfigTest {
  public static final String TEST_YAML = "dev/test.yaml";

  @Test
  public void shouldReturnRightConfigs() throws IOException {
    AppConfig dbConf = new AppConfig(parseYaml(TEST_YAML));
    Assertions.assertEquals("kalah", dbConf.getProjectName());
  }
}
