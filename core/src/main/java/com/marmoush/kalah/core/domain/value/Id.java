package com.marmoush.kalah.core.domain.value;

import java.util.Objects;

public class Id {
  private String value;

  public Id(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Id id = (Id) o;
    return Objects.equals(getValue(), id.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getValue());
  }

  public static Id of(String s) {
    return new Id(s);
  }

  public static Id of(int s) {
    return new Id(s + "");
  }
}
