package com.dovaleac.chessai.core.moves;

import com.dovaleac.chessai.core.ConsolidatedPosition;

import java.util.stream.Stream;

public interface AllMovesCalculator {
  Stream<Move> calculateAllMoves(ConsolidatedPosition position);
}
