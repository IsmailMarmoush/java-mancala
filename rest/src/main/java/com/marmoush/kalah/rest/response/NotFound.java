package com.marmoush.kalah.rest.response;

import java.util.Collections;
import java.util.Map;

public class NotFound {

  private String message;
  private Map<String, String> values;

  public NotFound(String message, Map.Entry<String, String>... entries) {
    this.message = message;
    values = Collections.unmodifiableMap(Map.ofEntries(entries));
  }

  public String getMessage() {
    return message;
  }

  public Map<String, String> getValues() {
    return values;
  }
}
