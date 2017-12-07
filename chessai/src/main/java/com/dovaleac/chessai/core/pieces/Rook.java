package com.dovaleac.chessai.core.pieces;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.moves.Square;

public class Rook extends Piece {
  Rook(Square square, Color color) {
    super(Figure.ROOK, square, color);
  }

  @Override
  public Piece move(Square square, Figure figureToCrown) {

  }
}
