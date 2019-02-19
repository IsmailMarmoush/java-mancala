package com.marmoush.kalah.core.domain.error;

public class AlreadyExists extends KalahError {
  public AlreadyExists(){
    this("Already Exists!");
  }
  public AlreadyExists(String message) {
    super(message);
  }
}
