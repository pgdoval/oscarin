package com.dovaleac.chessai.core.analysis.positional_facts;

public class AbstractNumberPositionalFact implements PositionalFact {

  private final PositionalFactType type;
  private final int number;

  public AbstractNumberPositionalFact(PositionalFactType type, int number) {
    this.type = type;
    this.number = number;
  }

  @Override
  public PositionalFactType getFactType() {
    return type;
  }

  public int getNumber() {
    return number;
  }

  public class NumberOfSquaresQueenCanReach extends AbstractNumberPositionalFact {

    public NumberOfSquaresQueenCanReach(int number) {
      super(PositionalFactType.NUMBER_OF_SQUARES_A_QUEEN_CAN_MOVE, number);
    }
  }
  public class NumberOfSquaresRookCanReach extends AbstractNumberPositionalFact {

    public NumberOfSquaresRookCanReach(int number) {
      super(PositionalFactType.NUMBER_OF_SQUARES_A_ROOK_CAN_MOVE, number);
    }
  }
  public class NumberOfSquaresBishopCanReach extends AbstractNumberPositionalFact {

    public NumberOfSquaresBishopCanReach(int number) {
      super(PositionalFactType.NUMBER_OF_SQUARES_A_BISHOP_CAN_MOVE, number);
    }
  }
  public class NumberOfSquaresKnightCanReach extends AbstractNumberPositionalFact {

    public NumberOfSquaresKnightCanReach(int number) {
      super(PositionalFactType.NUMBER_OF_SQUARES_A_KNIGHT_CAN_MOVE, number);
    }
  }
  public class NumberOfIslands extends AbstractNumberPositionalFact {

    public NumberOfIslands(int number) {
      super(PositionalFactType.NUMBER_OF_ISLANDS, number);
    }
  }
}
