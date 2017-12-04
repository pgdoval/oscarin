package com.dovaleac.chessai.core;

import java.util.stream.Stream;

public interface Figure {
  double getBaseValue();
  Stream<Move> calculatePossibleMoves(DeltaBasedPosition position);
  char asChar();

}
