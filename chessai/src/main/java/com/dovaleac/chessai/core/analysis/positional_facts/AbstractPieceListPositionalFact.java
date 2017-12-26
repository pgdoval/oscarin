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
