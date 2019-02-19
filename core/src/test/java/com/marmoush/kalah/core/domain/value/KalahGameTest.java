package com.marmoush.kalah.core.domain.value;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static com.marmoush.kalah.core.domain.value.KalahGame.mancala;
import static org.assertj.core.api.Assertions.*;

public class KalahGameTest {
  @Test
  public void oneMove() {
    KalahGame r = mancala(new KalahGame(6, 6), 0);
    System.out.println(r);
    assertThat(r.getPlayerOnePits()).isEqualTo(List.of(0, 7, 7, 7, 7, 7, 1));
  }

  @Test
  public void twoMoves() {
    KalahGame s1 = mancala(new KalahGame(6, 6), 1);
    KalahGame s2 = mancala(s1, 2);
    assertThat(s2.getPlayerOnePits()).isEqualTo(List.of(7, 0, 7, 7, 7, 7, 1));
    assertThat(s2.getPlayerTwoPits()).isEqualTo(List.of(7, 7, 0, 7, 7, 7, 1));
  }

  @Test
  public void many() {
    KalahGame m = mancala(new KalahGame(6, 6), 1).manc(1).manc(2);
    assertThat(m.getPlayerOnePits()).isEqualTo(List.of(8, 1, 0, 8, 8, 8, 10));
    assertThat(m.getPlayerTwoPits()).isEqualTo(List.of(7, 0, 0, 7, 7, 7, 1));
  }
}
