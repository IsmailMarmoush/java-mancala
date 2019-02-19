package com.marmoush.kalah.adapter.file;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.marmoush.kalah.adapter.Resources.JsonDir.GAME_JSON;
import static com.marmoush.kalah.adapter.Resources.TEST_YAML;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileAdapterTest {
  @Test
  public void fileAsStringTest() throws IOException {
    FileAdapter.of(GAME_JSON);
  }

  @Test
  public void parseYamlShouldReturnList() throws IOException {
    Map<String, Object> map = FileAdapter.parseYaml(TEST_YAML);
    List<String> list = (List<String>) map.get("list");
    assertEquals(list, List.of("hi", "hello", "bye"));
  }
}
