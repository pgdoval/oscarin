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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AbstractOnePiecePositionalFact that = (AbstractOnePiecePositionalFact) o;

    if (piece != null ? !piece.equals(that.piece) : that.piece != null) {
      return false;
    }
    return factType == that.factType;
  }

  @Override
  public int hashCode() {
    int result = piece != null ? piece.hashCode() : 0;
    result = 31 * result + (factType != null ? factType.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("AbstractOnePiecePositionalFact{");
    sb.append("piece=").append(piece);
    sb.append(", factType=").append(factType);
    sb.append('}');
    return sb.toString();
  }

  public static class LatePawn extends AbstractOnePiecePositionalFact {

    public LatePawn(Piece piece) {
      super(piece, PositionalFactType.LATE_PAWN);
    }
  }
  public static class IsolatedPawn extends AbstractOnePiecePositionalFact {

    public IsolatedPawn(Piece piece) {
      super(piece, PositionalFactType.ISOLATED_PAWN);
    }
  }
  public static class PassedPawn extends AbstractOnePiecePositionalFact {

    public PassedPawn(Piece piece) {
      super(piece, PositionalFactType.PASSED_PAWN);
    }
  }
  public static class PassedProtectedPawn extends AbstractOnePiecePositionalFact {

    public PassedProtectedPawn(Piece piece) {
      super(piece, PositionalFactType.PASSED_PROTECTED_PAWN);
    }
  }
  public static class KnightIsInOutpost extends AbstractOnePiecePositionalFact {

    public KnightIsInOutpost(Piece piece) {
      super(piece, PositionalFactType.KNIGHT_IS_IN_OUTPOST);
    }
  }
  public static class BishopIsInOutpost extends AbstractOnePiecePositionalFact {

    public BishopIsInOutpost(Piece piece) {
      super(piece, PositionalFactType.BISHOP_IS_IN_OUTPOST);
    }
  }
  public static class RivalBishopIsDead extends AbstractOnePiecePositionalFact {

    public RivalBishopIsDead(Piece piece) {
      super(piece, PositionalFactType.RIVAL_BISHOP_OF_SAME_COLOR_IS_DEAD);
    }
  }
  public static class RookIsInSeventhOrEighth extends AbstractOnePiecePositionalFact {

    public RookIsInSeventhOrEighth(Piece piece) {
      super(piece, PositionalFactType.ROOK_IN_SEVENTH_OR_EIGHTH);
    }
  }
  public static class RookControlsOpenColumn extends AbstractOnePiecePositionalFact {

    public RookControlsOpenColumn(Piece piece) {
      super(piece, PositionalFactType.ROOK_CONTROLS_OPEN_COLUMN);
    }
  }
  public static class OwnPieceNearKing extends AbstractOnePiecePositionalFact {

    public OwnPieceNearKing(Piece piece) {
      super(piece, PositionalFactType.OWN_PIECE_NEAR_KING);
    }
  }
  public static class EnemyPieceNearKing extends AbstractOnePiecePositionalFact {

    public EnemyPieceNearKing(Piece piece) {
      super(piece, PositionalFactType.ENEMY_PIECE_NEAR_KING);
    }
  }

}
