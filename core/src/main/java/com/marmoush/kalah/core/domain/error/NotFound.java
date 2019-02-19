package com.marmoush.kalah.core.domain.error;

public class NotFound extends KalahError {
  public NotFound() {
    this("Not found!");
  }

  public NotFound(String message) {
    super(message);
  }
}
