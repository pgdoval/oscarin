package com.dovaleac.chessai.core.pieces;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.moves.Square;

public abstract class Piece {
  private Figure figure;
  private Square square;
  private Color color;

  public Piece(Figure figure, Square square, Color color) {
    this.figure = figure;
    this.square = square;
    this.color = color;

  }

  public static Piece of(Figure figure, Square square, Color color) {
    switch (figure) {
      case KING: return king(square, color);
      case QUEEN: return queen(square, color);
      case ROOK: return rook(square, color);
      case BISHOP: return bishop(square, color);
      case KNIGHT: return knight(square, color);
      case PAWN: return pawn(square, color);
      default: return null;
    }
  }

  public static Queen queen(Square square, Color color) {
    return new Queen(square, color);
  }

  public static King king(Square square, Color color) {
    return new King(square, color);
  }

  public static Bishop bishop(Square square, Color color) {
    return new Bishop(square, color);
  }

  public static Knight knight(Square square, Color color) {
    return new Knight(square, color);
  }

  public static Rook rook(Square square, Color color) {
    return new Rook(square, color);
  }

  public static Pawn pawn(Square square, Color color) {
    return new Pawn(square, color);
  }

  public Piece move(Square square) {
    return move(square, null);
  }

  public abstract Piece move(Square square, Figure figureToCrown);

  public Figure getFigure() {
    return figure;
  }

  public Square getSquare() {
    return square;
  }

  public Color getColor() {
    return color;
  }

  public void setFigure(Figure figure) {
    this.figure = figure;
  }

  public void setSquare(Square square) {
    this.square = square;
  }


}
