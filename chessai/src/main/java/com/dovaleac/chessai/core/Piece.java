package com.dovaleac.chessai.core;

public abstract class Piece <FT extends Figure>{
  private final FT figure;
  private final Square square;

  public Piece(FT figure, Square square) {
    this.figure = figure;
    this.square = square;
  }

  public Piece move(Square square) {
    return move(square, null);
  }

  public abstract Piece move(Square square, Figure figureToCrown);

  public FT getFigure() {
    return figure;
  }

  public Square getSquare() {
    return square;
  }


}
