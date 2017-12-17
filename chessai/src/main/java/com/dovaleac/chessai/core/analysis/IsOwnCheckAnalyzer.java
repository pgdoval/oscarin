package com.dovaleac.chessai.core.analysis;

import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.analysis.prioritizers.NoKingPresentException;
import com.dovaleac.chessai.core.pieces.Figure;
import com.dovaleac.chessai.core.pieces.Piece;

public class IsOwnCheckAnalyzer extends LastMoveAnalyzer<Boolean> {
  @Override
  protected Boolean analyze(Position position, Position.PositionRewritingInfo lastMove) throws NoKingPresentException {
    Piece king = position.getPiecesByColor(position.getTurn().flip())
        .stream()
        .filter(piece -> piece.getFigure() == Figure.KING)
        .findAny()
        .orElseThrow(NoKingPresentException::new);

    return position.getPiecesByColor(position.getTurn())
        .stream()
        .anyMatch(piece -> piece.isAttacking(position, king.getSquare()));

  }
}
