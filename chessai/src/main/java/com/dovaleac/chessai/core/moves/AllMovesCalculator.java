package com.dovaleac.chessai.core.moves;

import com.dovaleac.chessai.core.Position;

import java.util.stream.Stream;

public interface AllMovesCalculator {
  Stream<Move> calculateAllMoves(Position position);
}
