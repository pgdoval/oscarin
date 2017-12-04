package com.dovaleac.chessai.core;

public class Move {
  private final Piece piece;
  private final Square targetSquare;

  public Move(Piece piece, Square targetSquare) {
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
