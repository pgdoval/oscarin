package com.dovaleac.chessai.core.analysis.positional_facts;

import com.dovaleac.chessai.core.pieces.Piece;

public class AbstractThreePiecesPositionalFact implements PositionalFact{

  private final Piece piece;
  private final Piece piece2;
  private final Piece piece3;
  private final PositionalFactType factType;

  public AbstractThreePiecesPositionalFact(Piece piece, Piece piece2, Piece piece3,
                                           PositionalFactType factType) {
    this.piece = piece;
    this.piece2 = piece2;
    this.piece3 = piece3;
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

    AbstractThreePiecesPositionalFact that = (AbstractThreePiecesPositionalFact) o;

    if (piece != null ? !piece.equals(that.piece) : that.piece != null) {
      return false;
    }
    if (piece2 != null ? !piece2.equals(that.piece2) : that.piece2 != null) {
      return false;
    }
    if (piece3 != null ? !piece3.equals(that.piece3) : that.piece3 != null) {
      return false;
    }
    return factType == that.factType;
  }

  @Override
  public int hashCode() {
    int result = piece != null ? piece.hashCode() : 0;
    result = 31 * result + (piece2 != null ? piece2.hashCode() : 0);
    result = 31 * result + (piece3 != null ? piece3.hashCode() : 0);
    result = 31 * result + (factType != null ? factType.hashCode() : 0);
    return result;
  }

  @Override
  public PositionalFactType getFactType() {
    return factType;
  }

  public Piece getPiece() {
    return piece;
  }

  public class SkeweredPiece extends AbstractThreePiecesPositionalFact {

    public SkeweredPiece(Piece piece, Piece piece2, Piece piece3) {
      super(piece, piece2, piece3, PositionalFactType.SKEWERED_PIECE);
    }
  }

}
