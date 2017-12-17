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
