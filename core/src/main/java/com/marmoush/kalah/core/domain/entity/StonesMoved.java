package com.marmoush.kalah.core.domain.entity;

import com.marmoush.kalah.core.domain.value.Id;

import java.util.Objects;

public class StonesMoved extends Event {
  private final int pitId;

  public StonesMoved(Id gameId, int pitId) {
    super(gameId);
    this.pitId = pitId;
  }

  public int getPitId() {
    return pitId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;
    StonesMoved that = (StonesMoved) o;
    return getPitId() == that.getPitId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getPitId());
  }
}
