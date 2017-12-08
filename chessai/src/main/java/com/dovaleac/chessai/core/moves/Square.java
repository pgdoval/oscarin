package com.dovaleac.chessai.core.moves;

public class Square {
  public static final int BOARD_SIZE = 8;
  private final int column;
  private final int row;

  Square(int column, int row) {
    this.column = column;
    this.row = row;
  }

  public int getColumn() {
    return column;
  }

  public int getRow() {
    return row;
  }

  public static Square fromString(String key) {
    char[] charArray = key.toCharArray();
    return new Square(charArray[0]-'a', charArray[1] - '1');
  }

  @Override
  public String toString() {
    String sb = String.valueOf('a' + column) +
        ('1' + row);
    return sb;
  }

  public boolean validateLimits() {
    return (
        (column >= 0) &&
            (column < BOARD_SIZE) &&
            (row >= 0) &&
            (row < BOARD_SIZE)
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Square square = (Square) o;
    return column == square.column &&
        row == square.row;
  }

  @Override
  public int hashCode() {
    return com.google.common.base.Objects.hashCode(column, row);
  }
}
