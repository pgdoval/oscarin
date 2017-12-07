package com.dovaleac.chessai.core.pieces;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.moves.Square;

public class Pawn extends Piece {
  Pawn(Square square, Color color) {
    super(Figure.PAWN, square, color);
  }

  @Override
  public Piece move(Square square, Figure figureToCrown) {

  }
}
