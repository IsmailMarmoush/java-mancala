package com.marmoush.kalah.core.domain.value;


import io.vavr.collection.List;


import java.util.Objects;

public class KalahGame {
  private final List<Integer> playerOnePits;
  private final List<Integer> playerTwoPits;
  private final int nPits;
  private final int nStones;
  private final boolean playerOneTurn;

  public KalahGame(int nPits, int nStones) {
    this(nPits, nStones, List.fill(nPits, nStones).append(0), List.fill(nPits, nStones).append(0), true);
  }

  public KalahGame(int nPits,
                   int nStones,
                   List<Integer> playerOnePits,
                   List<Integer> playerTwoPits,
                   boolean playerOneTurn) {
    this.nPits = nPits;
    this.nStones = nStones;
    this.playerOnePits = playerOnePits;
    this.playerTwoPits = playerTwoPits;
    this.playerOneTurn = playerOneTurn;
  }

  public List<Integer> getPlayerOnePits() {
    return playerOnePits;
  }

  public List<Integer> getPlayerTwoPits() {
    return playerTwoPits;
  }

  public int getnPits() {
    return nPits;
  }

  public int getnStones() {
    return nStones;
  }

  public boolean isPlayerOneTurn() {
    return playerOneTurn;
  }

  public KalahGame switchSides() {
    return new KalahGame(nPits, nStones, playerTwoPits, playerOnePits, playerOneTurn);
  }

  public KalahGame manc(int idx) {
    return mancala(this, idx);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    KalahGame kalahGame = (KalahGame) o;
    return getnPits() == kalahGame.getnPits() && getnStones() == kalahGame.getnStones() &&
           isPlayerOneTurn() == kalahGame.isPlayerOneTurn() &&
           getPlayerOnePits().equals(kalahGame.getPlayerOnePits()) &&
           getPlayerTwoPits().equals(kalahGame.getPlayerTwoPits());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPlayerOnePits(), getPlayerTwoPits(), getnPits(), getnStones(), isPlayerOneTurn());
  }

  @Override
  public String toString() {
    return "KalahGame{" + "playerOnePits=" + playerOnePits + ", playerTwoPits=" + playerTwoPits + ", nPits=" + nPits +
           ", nStones=" + nStones + ", playerOneTurn=" + playerOneTurn + '}';
  }

  public static KalahGame mancala(KalahGame kg, int idx) {
    KalahGame result = null;
    List<Integer> p1 = kg.getPlayerOnePits();
    List<Integer> p2 = kg.getPlayerTwoPits();
    if (!kg.isPlayerOneTurn()) {
      p1 = kg.getPlayerTwoPits();
      p2 = kg.getPlayerOnePits();
    }
    int lastIdx = p1.size() - 1;

    // First Step
    int stonesLeft = p1.get(idx);
    p1 = p1.update(idx, 0);
    idx++;

    // Following Steps
    while (stonesLeft != 0) {
      // If last stone and pit is the house
      if (stonesLeft == 1 && idx == lastIdx) {
        // return add stone to the house, and return kalah board with same player turn
        result = new KalahGame(kg.getnPits(), kg.getnStones(), p1.update(idx, p1.last() + 1), p2, kg.isPlayerOneTurn());
        break;
      }
      // If last stone and the pit is empty
      if (stonesLeft == 1 && p1.get(idx) == 0) {
        // take the other player's stones in the adjacent pit, and turn the player
        result = new KalahGame(kg.getnPits(),
                               kg.getnStones(),
                               p1.update(lastIdx, p1.last() + p2.get(idx) + stonesLeft),
                               p2.update(idx, 0),
                               !kg.isPlayerOneTurn());
        break;
      }

      // normal flow, add stone to the pit
      p1 = p1.update(idx, p1.get(idx) + 1);
      stonesLeft--;
      if (idx == lastIdx)
        idx = 0;
      else
        idx++;
    }
    if (result == null) {
      result = new KalahGame(kg.getnPits(), kg.getnStones(), p1, p2, !kg.isPlayerOneTurn());
    }
    if (!kg.isPlayerOneTurn()) {
      // Switch back to original sides
      result = result.switchSides();
    }
    return result;
  }
}



