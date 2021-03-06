package com.dovaleac.chessai.core.analysis.positional_facts;

public enum PositionalFactType {
  //king safety
  IS_KING_IN_MIDDLE_COLUMNS (1),
  IS_KING_IN_MIDDLE_ROWS (2),
  ARE_PROTECTING_PAWNS_IN_LINE (3),
  IS_THERE_A_GOOD_FIANCHETTO (4), //study in the future
  ARE_THERE_OPEN_LINES_TOWARDS_THE_KING (5), //study in the future
  OWN_PIECE_NEAR_KING (6),
  ENEMY_PIECE_NEAR_KING (7),

  //the pawn structure will be removed from here
  NUMBER_OF_ISLANDS (101),
  DOUBLED_PAWNS (102),
  LATE_PAWN (103),
  ISOLATED_PAWN (104),
  PASSED_PAWN (105),
  PASSED_PROTECTED_PAWN (106),
  PASSED_PAWN_COUPLE (107),

  //piece quality
  NUMBER_OF_SQUARES_A_QUEEN_CAN_MOVE (201),
  NUMBER_OF_SQUARES_A_ROOK_CAN_MOVE (202),
  NUMBER_OF_SQUARES_A_BISHOP_CAN_MOVE (203),
  NUMBER_OF_SQUARES_A_KNIGHT_CAN_MOVE (204),
  KNIGHT_IS_IN_OUTPOST (205),
  BISHOP_IS_IN_OUTPOST (206),
  ARE_THERE_WEAKNESSES_OF_BISHOPS_COLOR_NEAR_RIVALS_KING (207),
  RIVAL_BISHOP_OF_SAME_COLOR_IS_DEAD (208),
  ROOK_IN_SEVENTH_OR_EIGHTH (209),
  OVERLOADED_PIECE (210),
  SKEWERED_PIECE (211),
  BISHOP_COUPLE (212),

  //open lines
  ROOK_CONTROLS_OPEN_COLUMN (301),
  OPEN_COLUMN (302);

  //4xx - center type

  private int id;
  PositionalFactType(int i) {
    id = i;
  }

  public int getId() {
      return id;
    }



}
