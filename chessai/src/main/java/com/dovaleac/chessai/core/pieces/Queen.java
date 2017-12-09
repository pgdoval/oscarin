package com.dovaleac.chessai.core.pieces;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.moves.Square;

public class Queen extends Piece {
  Queen(Square square, Color color) {
    super(Figure.QUEEN, square, color);
  }
}
