package com.dovaleac.chessai.core.analysis.positional_facts;

import com.dovaleac.chessai.core.pieces.Piece;

public class AbstractOnePiecePositionalFact implements PositionalFact{

  private final Piece piece;
  private final PositionalFactType factType;

  public AbstractOnePiecePositionalFact(Piece piece, PositionalFactType factType) {
    this.piece = piece;
    this.factType = factType;
  }

  @Override
  public PositionalFactType getFactType() {
    return factType;
  }

  public Piece getPiece() {
    return piece;
  }
}
