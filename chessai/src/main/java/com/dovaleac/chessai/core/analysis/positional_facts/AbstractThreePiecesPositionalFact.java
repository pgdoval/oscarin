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
