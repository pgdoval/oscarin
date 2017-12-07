package com.dovaleac.chessai.core.pieces;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.moves.Square;

public class King extends Piece {
  King(Square square, Color color) {
    super(Figure.KING, square, color);
  }

  @Override
  public Piece move(Square square, Figure figureToCrown) {

  }
}
