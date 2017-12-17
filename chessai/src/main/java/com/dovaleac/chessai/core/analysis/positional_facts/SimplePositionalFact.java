package com.dovaleac.chessai.core.analysis.positional_facts;

public enum SimplePositionalFact implements PositionalFact {
  IS_KING_IN_MIDDLE_COLUMNS (PositionalFactType.IS_KING_IN_MIDDLE_COLUMNS),
  IS_KING_IN_MIDDLE_ROWS (PositionalFactType.IS_KING_IN_MIDDLE_ROWS),
  ARE_PROTECTING_PAWNS_IN_LINE (PositionalFactType.ARE_PROTECTING_PAWNS_IN_LINE),
  IS_THERE_A_GOOD_FIANCHETTO (PositionalFactType.IS_THERE_A_GOOD_FIANCHETTO),
  ARE_THERE_OPEN_LINES_TOWARDS_THE_KING (PositionalFactType.ARE_THERE_OPEN_LINES_TOWARDS_THE_KING),
  KNIGHT_IS_GUARDING_KING (PositionalFactType.KNIGHT_IS_GUARDING_KING),
  QUEEN_IS_NEAR_KING (PositionalFactType.QUEEN_IS_NEAR_KING),

  BISHOP_COUPLE (PositionalFactType.BISHOP_COUPLE);

  private PositionalFactType type;
  SimplePositionalFact(PositionalFactType type) {
    this.type = type;
  }

  @Override
    public PositionalFactType getFactType() {
      return type;
    }



}
