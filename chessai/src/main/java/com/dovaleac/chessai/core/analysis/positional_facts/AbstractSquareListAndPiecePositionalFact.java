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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AbstractSquareListAndPiecePositionalFact that = (AbstractSquareListAndPiecePositionalFact) o;

    if (squares != null ? !squares.equals(that.squares) : that.squares != null) {
      return false;
    }
    if (piece != null ? !piece.equals(that.piece) : that.piece != null) {
      return false;
    }
    return factType == that.factType;
  }

  @Override
  public int hashCode() {
    int result = squares != null ? squares.hashCode() : 0;
    result = 31 * result + (piece != null ? piece.hashCode() : 0);
    result = 31 * result + (factType != null ? factType.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("AbstractSquareListAndPiecePositionalFact{");
    sb.append("squares=").append(squares);
    sb.append(", piece=").append(piece);
    sb.append(", factType=").append(factType);
    sb.append('}');
    return sb.toString();
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
