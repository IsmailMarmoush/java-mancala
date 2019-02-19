package com.marmoush.kalah.core.domain.error;

public class NotYourTurn extends PitMoveError {
  public NotYourTurn() {
    super("Not your turn!");
  }
}
