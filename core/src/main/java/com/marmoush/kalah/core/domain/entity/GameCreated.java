package com.marmoush.kalah.core.domain.entity;

import com.marmoush.kalah.core.domain.value.Id;

import java.util.Objects;

public class GameCreated extends Event {

  private final int nPits;
  private final int nStones;

  public GameCreated(Id gameId, int nPits, int nStones) {
    super(gameId);
    this.nPits = nPits;
    this.nStones = nStones;
  }

  public int getnPits() {
    return nPits;
  }

  public int getnStones() {
    return nStones;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    GameCreated that = (GameCreated) o;
    return getnPits() == that.getnPits() && getnStones() == that.getnStones();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getnPits(), getnStones());
  }
}
