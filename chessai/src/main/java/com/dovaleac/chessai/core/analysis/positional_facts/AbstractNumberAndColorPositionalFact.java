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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AbstractNumberAndColorPositionalFact that = (AbstractNumberAndColorPositionalFact) o;

    if (number != that.number) {
      return false;
    }
    if (type != that.type) {
      return false;
    }
    return color == that.color;
  }

  @Override
  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + number;
    result = 31 * result + (color != null ? color.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("AbstractNumberAndColorPositionalFact{");
    sb.append("type=").append(type);
    sb.append(", number=").append(number);
    sb.append(", color=").append(color);
    sb.append('}');
    return sb.toString();
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
