package com.marmoush.kalah.core.domain.entity;

import com.marmoush.kalah.core.domain.value.Id;

import java.util.Objects;

public abstract class Event {
  private final Id id;

  protected Event(Id id) {
    this.id = id;
  }

  public Id getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Event event = (Event) o;
    return Objects.equals(getId(), event.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }
}
