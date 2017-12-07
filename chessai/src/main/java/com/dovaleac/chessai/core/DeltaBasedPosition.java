package com.dovaleac.chessai.core;

import com.dovaleac.chessai.core.moves.PieceMove;

import java.util.List;

public class DeltaBasedPosition {
  private final List<PieceMove> moves;
  private final ConsolidatedPosition basePosition;

  public DeltaBasedPosition(List<PieceMove> moves, ConsolidatedPosition basePosition) {
    this.moves = moves;
    this.basePosition = basePosition;
  }

  public List<PieceMove> getMoves() {
    return moves;
  }

  public ConsolidatedPosition getBasePosition() {
    return basePosition;
  }

  public ConsolidatedPosition asConsolidated() {
    return moves.stream().reduce(basePosition, ConsolidatedPosition::move, (m1, m2) -> m2);
  }
}
