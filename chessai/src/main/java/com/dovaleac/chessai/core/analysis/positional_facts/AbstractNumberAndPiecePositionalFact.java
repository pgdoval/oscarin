package com.dovaleac.chessai.core.analysis.positional_facts;

import com.dovaleac.chessai.core.pieces.Piece;

public class AbstractNumberAndPiecePositionalFact implements PositionalFact {

  private final PositionalFactType type;
  private final int number;
  private final Piece piece;

  public AbstractNumberAndPiecePositionalFact(PositionalFactType type, int number, Piece piece) {
    this.type = type;
    this.number = number;
    this.piece = piece;
  }

  @Override
  public PositionalFactType getFactType() {
    return type;
  }

  public int getNumber() {
    return number;
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

    AbstractNumberAndPiecePositionalFact that = (AbstractNumberAndPiecePositionalFact) o;

    if (number != that.number) {
      return false;
    }
    if (type != that.type) {
      return false;
    }
    return piece != null ? piece.equals(that.piece) : that.piece == null;
  }

  @Override
  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + number;
    result = 31 * result + (piece != null ? piece.hashCode() : 0);
    return result;
  }

  public static class NumberOfSquaresQueenCanReach extends AbstractNumberAndPiecePositionalFact {

    public NumberOfSquaresQueenCanReach(int number, Piece piece) {
      super(PositionalFactType.NUMBER_OF_SQUARES_A_QUEEN_CAN_MOVE, number, piece);
    }
  }
  public static class NumberOfSquaresRookCanReach extends AbstractNumberAndPiecePositionalFact {

    public NumberOfSquaresRookCanReach(int number, Piece piece) {
      super(PositionalFactType.NUMBER_OF_SQUARES_A_ROOK_CAN_MOVE, number, piece);
    }
  }
  public static class NumberOfSquaresBishopCanReach extends AbstractNumberAndPiecePositionalFact {

    public NumberOfSquaresBishopCanReach(int number, Piece piece) {
      super(PositionalFactType.NUMBER_OF_SQUARES_A_BISHOP_CAN_MOVE, number, piece);
    }
  }
  public static class NumberOfSquaresKnightCanReach extends AbstractNumberAndPiecePositionalFact {

    public NumberOfSquaresKnightCanReach(int number, Piece piece) {
      super(PositionalFactType.NUMBER_OF_SQUARES_A_KNIGHT_CAN_MOVE, number, piece);
    }
  }
}
