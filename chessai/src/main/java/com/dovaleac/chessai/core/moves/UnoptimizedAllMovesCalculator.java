package com.dovaleac.chessai.core.moves;

import com.dovaleac.chessai.core.ConsolidatedPosition;

import java.util.stream.Stream;

public class UnoptimizedAllMovesCalculator implements AllMovesCalculator {
  @Override
  public Stream<Move> calculateAllMoves(ConsolidatedPosition position) {
    return position.getPiecesByColor(position.getTurn()).stream()
        .flatMap(piece -> piece.getPossibleMoves(position).stream());
  }
}
