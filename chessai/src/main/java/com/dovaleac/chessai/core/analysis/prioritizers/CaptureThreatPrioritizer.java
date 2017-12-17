package com.dovaleac.chessai.core.analysis.prioritizers;

import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.analysis.MovePrioritizer;
import com.dovaleac.chessai.core.pieces.Figure;
import com.dovaleac.chessai.core.pieces.Piece;
import com.dovaleac.chessai.core.valuables.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CaptureThreatPrioritizer extends MovePrioritizer {
  @Override
  protected List<Priority> analyze(Position position, Position.PositionRewritingInfo lastMove) throws NoKingPresentException {
    Piece capturer = lastMove.getMove().resultingPiece(position);
    List<Piece> capturablePieces = position.getPiecesByColor(position.getTurn()).stream()
        .filter(capturable -> capturer.isAttacking(position, capturable.getSquare()))
        .collect(Collectors.toList());

    List<Piece> actionDiscovered = new ArrayList<>(0);

    capturablePieces.addAll(actionDiscovered);

    if (capturablePieces.isEmpty()) {
      return new ArrayList<>(0);
    }
    List<Priority> result = new ArrayList<>(1);

    capturablePieces.forEach(piece ->
        result.add(Priority.captureThreat(piece.getFigure(), capturer.getFigure())));

    if (capturablePieces.size() > 1) {
      capturablePieces = capturablePieces
          .stream()
          .sorted((p1, p2) -> {
            if (p1.getFigure() == Figure.KING) {
              return 1;
            }
            if (p2.getFigure() == Figure.KING) {
              return -1;
            }
            double p1Value = p1.getFigure().getBaseValue();
            double p2Value = p2.getFigure().getBaseValue();
            if (p1Value == p2Value) {
              return 0;
            }
            return (p1Value > p2Value) ? 1 : -1;
          })
          .collect(Collectors.toList());
      result.add(0, Priority.fork(capturablePieces.get(1).getFigure(), capturer.getFigure()));
    }

    //TODO: calculate skewers
    if (Arrays.asList(Figure.rangeFigures()).contains(capturer.getFigure())) {

    }


    return result;
  }
}
