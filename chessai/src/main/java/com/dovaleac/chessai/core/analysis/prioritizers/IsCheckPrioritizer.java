package com.dovaleac.chessai.core.analysis.prioritizers;

import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.analysis.MovePrioritizer;
import com.dovaleac.chessai.core.pieces.Figure;
import com.dovaleac.chessai.core.pieces.Piece;
import com.dovaleac.chessai.core.valuables.Priority;

import java.util.ArrayList;
import java.util.List;

public class IsCheckPrioritizer extends MovePrioritizer {
  @Override
  protected List<Priority> analyze(Position position, Position.PositionRewritingInfo lastMove) throws NoKingPresentException {
    Piece king = position.getPiecesByColor(position.getTurn())
        .stream()
        .filter(piece -> piece.getFigure() == Figure.KING)
        .findAny()
        .orElseThrow(NoKingPresentException::new);

    boolean isCheck = position.getPiecesByColor(position.getTurn().flip())
        .stream()
        .anyMatch(piece -> piece.isAttacking(position, king.getSquare()));

    List<Priority> result = new ArrayList<>(1);

    if (isCheck) {
      result.add(Priority.check());
    }
    return result;
  }
}
