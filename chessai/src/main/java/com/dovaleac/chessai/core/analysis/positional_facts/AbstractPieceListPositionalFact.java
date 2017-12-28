package com.dovaleac.chessai.core.analysis.positional_facts;

import com.dovaleac.chessai.core.pieces.Piece;

import java.util.List;

public class AbstractPieceListPositionalFact implements PositionalFact{

  private final List<Piece> pieces;
  private final PositionalFactType factType;

  public AbstractPieceListPositionalFact(List<Piece> pieces, PositionalFactType factType) {
    this.pieces = pieces;
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

    AbstractPieceListPositionalFact that = (AbstractPieceListPositionalFact) o;

    if (pieces != null ? !pieces.equals(that.pieces) : that.pieces != null) {
      return false;
    }
    return factType == that.factType;
  }

  @Override
  public int hashCode() {
    int result = pieces != null ? pieces.hashCode() : 0;
    result = 31 * result + (factType != null ? factType.hashCode() : 0);
    return result;
  }

  @Override
  public PositionalFactType getFactType() {
    return factType;
  }

  public List<Piece> getPieces() {
    return pieces;
  }

  public static class DoubledPawns extends AbstractPieceListPositionalFact {
    public DoubledPawns(List<Piece> pieces) {
      super(pieces, PositionalFactType.DOUBLED_PAWNS);
    }
  }

  public static class PassedPawnCouple extends AbstractPieceListPositionalFact {
    public PassedPawnCouple(List<Piece> pieces) {
      super(pieces, PositionalFactType.PASSED_PAWN_COUPLE);
    }
  }

}
