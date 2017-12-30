package com.dovaleac.chessai.core.analysis.positional_facts;

import com.dovaleac.chessai.core.pieces.Piece;

import java.util.List;

public class AbstractPieceListAndPiecePositionalFact implements PositionalFact{

  private final List<Piece> pieces;
  private final Piece piece;
  private final PositionalFactType factType;

  public AbstractPieceListAndPiecePositionalFact(List<Piece> pieces, Piece piece,
                                                 PositionalFactType factType) {
    this.pieces = pieces;
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

    AbstractPieceListAndPiecePositionalFact that = (AbstractPieceListAndPiecePositionalFact) o;

    if (pieces != null ? !pieces.equals(that.pieces) : that.pieces != null) {
      return false;
    }
    if (piece != null ? !piece.equals(that.piece) : that.piece != null) {
      return false;
    }
    return factType == that.factType;
  }

  @Override
  public int hashCode() {
    int result = pieces != null ? pieces.hashCode() : 0;
    result = 31 * result + (piece != null ? piece.hashCode() : 0);
    result = 31 * result + (factType != null ? factType.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("AbstractPieceListAndPiecePositionalFact{");
    sb.append("pieces=").append(pieces);
    sb.append(", piece=").append(piece);
    sb.append(", factType=").append(factType);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public PositionalFactType getFactType() {
    return factType;
  }

  public List<Piece> getPieces() {
    return pieces;
  }

  public class OverloadedPiece extends AbstractPieceListAndPiecePositionalFact {
    public OverloadedPiece(List<Piece> pieces) {
      super(pieces, piece, PositionalFactType.OVERLOADED_PIECE);
    }
  }

}
