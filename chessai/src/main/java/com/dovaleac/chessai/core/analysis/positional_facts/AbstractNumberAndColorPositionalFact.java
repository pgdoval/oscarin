package com.dovaleac.chessai.core.analysis.positional_facts;

import com.dovaleac.chessai.core.Color;

public class AbstractNumberAndColorPositionalFact implements PositionalFact {

  private final PositionalFactType type;
  private final int number;
  private final Color color;

  public AbstractNumberAndColorPositionalFact(PositionalFactType type, int number, Color color) {
    this.type = type;
    this.number = number;
    this.color = color;
  }

  @Override
  public PositionalFactType getFactType() {
    return type;
  }

  public int getNumber() {
    return number;
  }

  public Color getColor() {
    return color;
  }

  public static class OpenOrSemiopenColumn extends AbstractNumberAndColorPositionalFact {

    public OpenOrSemiopenColumn(int number, Color color) {
      super(PositionalFactType.OPEN_COLUMN, number, color);
    }
  }
  public static class NumberOfIslands extends AbstractNumberAndColorPositionalFact {

    public NumberOfIslands(int number, Color color) {
      super(PositionalFactType.NUMBER_OF_ISLANDS, number, color);
    }
  }
}
