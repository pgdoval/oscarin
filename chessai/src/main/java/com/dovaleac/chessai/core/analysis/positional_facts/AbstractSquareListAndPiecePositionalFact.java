package com.dovaleac.chessai.core.analysis.positional_facts;

import com.dovaleac.chessai.core.moves.Square;
import com.dovaleac.chessai.core.pieces.Piece;

import java.util.List;

public class AbstractSquareListAndPiecePositionalFact implements PositionalFact{

  private final List<Square> squares;
  private final Piece piece;
  private final PositionalFactType factType;

  public AbstractSquareListAndPiecePositionalFact(List<Square> squares, Piece piece,
                                                  PositionalFactType factType) {
    this.squares = squares;
    this.piece = piece;
    this.factType = factType;
  }

  @Override
  public PositionalFactType getFactType() {
    return factType;
  }

  public List<Square> getSquares() {
    return squares;
  }

  public class WeakSquaresNearKingBishopColor extends AbstractSquareListAndPiecePositionalFact {
    public WeakSquaresNearKingBishopColor(List<Square> squares) {
      super(squares, piece,
          PositionalFactType.ARE_THERE_WEAKNESSES_OF_BISHOPS_COLOR_NEAR_RIVALS_KING);
    }
  }

}
