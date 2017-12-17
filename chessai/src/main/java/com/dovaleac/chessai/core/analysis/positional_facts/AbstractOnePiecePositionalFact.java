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

  public class LatePawn extends AbstractOnePiecePositionalFact {

    public LatePawn(Piece piece) {
      super(piece, PositionalFactType.LATE_PAWN);
    }
  }
  public class IsolatedPawn extends AbstractOnePiecePositionalFact {

    public IsolatedPawn(Piece piece) {
      super(piece, PositionalFactType.ISOLATED_PAWN);
    }
  }
  public class PassedPawn extends AbstractOnePiecePositionalFact {

    public PassedPawn(Piece piece) {
      super(piece, PositionalFactType.PASSED_PAWN);
    }
  }
  public class PassedProtectedPawn extends AbstractOnePiecePositionalFact {

    public PassedProtectedPawn(Piece piece) {
      super(piece, PositionalFactType.PASSED_PROTECTED_PAWN);
    }
  }
  public class KnightIsInOutpost extends AbstractOnePiecePositionalFact {

    public KnightIsInOutpost(Piece piece) {
      super(piece, PositionalFactType.KNIGHT_IS_IN_OUTPOST);
    }
  }
  public class BishopIsInOutpost extends AbstractOnePiecePositionalFact {

    public BishopIsInOutpost(Piece piece) {
      super(piece, PositionalFactType.BISHOP_IS_IN_OUTPOST);
    }
  }
  public class RivalBishopIsDead extends AbstractOnePiecePositionalFact {

    public RivalBishopIsDead(Piece piece) {
      super(piece, PositionalFactType.RIVAL_BISHOP_OF_SAME_COLOR_IS_DEAD);
    }
  }
  public class RookIsInSeventhOrEighth extends AbstractOnePiecePositionalFact {

    public RookIsInSeventhOrEighth(Piece piece) {
      super(piece, PositionalFactType.ROOK_IN_SEVENTH_OR_EIGHTH);
    }
  }
  public class RookControlsOpenColumn extends AbstractOnePiecePositionalFact {

    public RookControlsOpenColumn(Piece piece) {
      super(piece, PositionalFactType.ROOK_CONTROLS_OPEN_COLUMN);
    }
  }

}
