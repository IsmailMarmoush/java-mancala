package com.marmoush.kalah.adapter.idgenerator;

import com.marmoush.kalah.core.domain.port.IdGenerator;

import java.util.concurrent.atomic.AtomicInteger;

public class SerialIdGenerator implements IdGenerator {
  private static AtomicInteger atomicInteger = new AtomicInteger();

  @Override
  public String generate() {
    return atomicInteger.getAndIncrement() + "";
  }
}
