package com.dovaleac.chessai.core.pieces;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.moves.Square;

public class Bishop extends Piece {
  Bishop(Square square, Color color) {
    super(Figure.BISHOP, square, color);
  }
}
