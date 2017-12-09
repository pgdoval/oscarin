package com.dovaleac.chessai.core.moves;

import com.dovaleac.chessai.core.Position;

import java.util.Objects;
import java.util.stream.Stream;

public class UnoptimizedAllMovesCalculator implements AllMovesCalculator {
  @Override
  public Stream<Move> calculateAllMoves(Position position) {
    return position.getPiecesByColor(position.getTurn()).stream()
        .flatMap(piece -> piece.getPossibleMoves(position).stream())
        .filter(Objects::nonNull);
  }
}
