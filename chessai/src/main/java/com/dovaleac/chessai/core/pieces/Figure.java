package com.dovaleac.chessai.core.pieces;

import com.dovaleac.chessai.core.moves.MoveCalculator;

public enum Figure {
  QUEEN (9.0, MoveCalculator.queen(), 'Q'),
  ROOK (5.0, MoveCalculator.rook(), 'R'),
  BISHOP (3.0, MoveCalculator.bishop(), 'B'),
  KNIGHT (3.0, MoveCalculator.knight(), 'N'),
  PAWN (1.0, MoveCalculator.pawn(), null),
  KING (0.0, MoveCalculator.king(), 'K');

  private final double baseValue;
  private final MoveCalculator moveCalculator;
  private final Character representation;

  Figure(double baseValue, MoveCalculator moveCalculator, Character representation) {
    this.baseValue = baseValue;
    this.moveCalculator = moveCalculator;
    this.representation = representation;
  }

  public double getBaseValue() {
    return baseValue;
  }

  public MoveCalculator getMoveCalculator() {
    return moveCalculator;
  }

  public static Figure [] queeningTransformations() {
    return new Figure[]{QUEEN, ROOK, BISHOP, KNIGHT};
  }

  public static Figure [] rangeFigures() {
    return new Figure[]{QUEEN, ROOK, BISHOP};
  }


  public static Figure byRepresentation(char c) {
    switch (c) {
      case 'K': return KING;
      case 'N': return KNIGHT;
      case 'B': return BISHOP;
      case 'R': return ROOK;
      case 'Q': return QUEEN;
      default: return PAWN;
    }
  }

  public Character getRepresentation() {
    return representation;
  }
}
