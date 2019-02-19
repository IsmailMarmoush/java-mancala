package com.marmoush.kalah.adapter.idgenerator;

import com.marmoush.kalah.core.domain.port.IdGenerator;

import java.util.UUID;

public class UUIDGenerator implements IdGenerator {
  @Override
  public String generate() {
    return UUID.randomUUID().toString();
  }
}
