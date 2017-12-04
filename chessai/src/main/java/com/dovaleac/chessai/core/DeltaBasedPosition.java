package com.dovaleac.chessai.core;

import java.util.List;

public class DeltaBasedPosition {
  private final List<Move> moves;
  private final ConsolidatedPosition basePosition;

  public DeltaBasedPosition(List<Move> moves, ConsolidatedPosition basePosition) {
    this.moves = moves;
    this.basePosition = basePosition;
  }

  public List<Move> getMoves() {
    return moves;
  }

  public ConsolidatedPosition getBasePosition() {
    return basePosition;
  }

  public ConsolidatedPosition asConsolidated() {
    return moves.stream().reduce(basePosition, ConsolidatedPosition::move, (m1, m2) -> m2);
  }
}
