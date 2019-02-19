package com.marmoush.kalah.rest;

import java.util.HashMap;
import java.util.Map;

public class AppConfig {
  private final Map<String, Object> configs;

  public AppConfig(final Map<String, Object> configs) {
    this.configs = new HashMap<>(configs);
  }

  public String getProjectName() {
    return (String) configs.get("projectName");
  }

  public String getGameControllerPath() {
    return (String) configs.get("gameControllerPath");
  }

  public String getHost() {
    return (String) configs.get("host");
  }

  public int getPort() {
    return Integer.parseInt((String) configs.get("port"));
  }

  public int getMaxStartupTime() {
    return Integer.parseInt((String) configs.get("maxStartupTime"));
  }

  public boolean isWiretapping() {
    return Boolean.parseBoolean((String) configs.get("isWiretapping"));
  }

  public String getIdGeneratorType() {
    return (String) configs.get("idGeneratorType");
  }
}
