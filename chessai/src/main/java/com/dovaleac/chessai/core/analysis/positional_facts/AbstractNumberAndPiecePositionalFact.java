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

  public class NumberOfSquaresQueenCanReach extends AbstractNumberAndPiecePositionalFact {

    public NumberOfSquaresQueenCanReach(int number, Piece piece) {
      super(PositionalFactType.NUMBER_OF_SQUARES_A_QUEEN_CAN_MOVE, number, piece);
    }
  }
  public class NumberOfSquaresRookCanReach extends AbstractNumberAndPiecePositionalFact {

    public NumberOfSquaresRookCanReach(int number, Piece piece) {
      super(PositionalFactType.NUMBER_OF_SQUARES_A_ROOK_CAN_MOVE, number, piece);
    }
  }
  public class NumberOfSquaresBishopCanReach extends AbstractNumberAndPiecePositionalFact {

    public NumberOfSquaresBishopCanReach(int number, Piece piece) {
      super(PositionalFactType.NUMBER_OF_SQUARES_A_BISHOP_CAN_MOVE, number, piece);
    }
  }
  public class NumberOfSquaresKnightCanReach extends AbstractNumberAndPiecePositionalFact {

    public NumberOfSquaresKnightCanReach(int number, Piece piece) {
      super(PositionalFactType.NUMBER_OF_SQUARES_A_KNIGHT_CAN_MOVE, number, piece);
    }
  }
}
