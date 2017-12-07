package com.dovaleac.chessai.core.moves;

import com.dovaleac.chessai.core.pieces.Piece;

public class PieceMove {
  private final Piece piece;
  private final Square targetSquare;

  public PieceMove(Piece piece, Square targetSquare) {
    this.piece = piece;
    this.targetSquare = targetSquare;
  }

  public Piece getPiece() {
    return piece;
  }

  public Square getTargetSquare() {
    return targetSquare;
  }
}
