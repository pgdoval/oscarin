package com.dovaleac.chessai.core.analysis.positional_facts;

import com.dovaleac.chessai.core.Color;

public class AbstractColorPositionalFact implements PositionalFact {

  private final PositionalFactType type;
  private final Color color;

  public AbstractColorPositionalFact(PositionalFactType type, Color color) {
    this.type = type;
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

    AbstractColorPositionalFact that = (AbstractColorPositionalFact) o;

    if (type != that.type) {
      return false;
    }
    return color == that.color;
  }

  @Override
  public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (color != null ? color.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("AbstractColorPositionalFact{");
    sb.append("type=").append(type);
    sb.append(", color=").append(color);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public PositionalFactType getFactType() {
    return type;
  }

  public Color getColor() {
    return color;
  }

  public static class BishopCouple extends AbstractColorPositionalFact {

    public BishopCouple(Color color) {
      super(PositionalFactType.BISHOP_COUPLE, color);
    }
  }
}
