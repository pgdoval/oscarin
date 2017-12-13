package com.dovaleac.chessai.core.moves;

import com.dovaleac.chessai.core.pieces.Piece;

public class PieceMove {
  private final Piece piece;
  private final Square targetSquare;
  private final boolean isCrowning;

  public PieceMove(Piece piece, Square targetSquare, boolean isCrowning) {
    this.piece = piece;
    this.targetSquare = targetSquare;
    this.isCrowning = isCrowning;
  }

  public PieceMove(Piece piece, Square targetSquare) {
    this(piece, targetSquare, false);
  }

  public Piece getPiece() {
    return piece;
  }

  public Square getTargetSquare() {
    return targetSquare;
  }

  public boolean isCrowning() {
    return isCrowning;
  }
}
