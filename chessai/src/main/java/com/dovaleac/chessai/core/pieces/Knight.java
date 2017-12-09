package com.dovaleac.chessai.core.pieces;

import com.dovaleac.chessai.core.Color;
import com.dovaleac.chessai.core.moves.Square;

public class Knight extends Piece {
  Knight(Square square, Color color) {
    super(Figure.KNIGHT, square, color);
  }
}
