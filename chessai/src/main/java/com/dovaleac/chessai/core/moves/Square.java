package com.dovaleac.chessai.core.moves;

import java.util.stream.IntStream;

public class Square {

  public static final int BOARD_SIZE;
  private static Square [][] squares;
  private final int column;
  private final int row;

  static {
    BOARD_SIZE = 8;
  }

  private Square(int column, int row) {
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
    return of(charArray[0]-'a', charArray[1] - '1');
  }

  public static Square of(int column, int row) {
    if (!validateLimits(column, row)) {
      return null;
    }
    if (squares == null) {
      squares =
          IntStream.range(0, BOARD_SIZE)
              .boxed().map(c -> IntStream.range(0, BOARD_SIZE)
              .boxed().map(r-> new Square(c, r)).toArray(Square[]::new)).toArray(Square[][]::new);
    }
    return squares[column][row];
  }

  @Override
  public String toString() {
    String sb = String.valueOf((char)('a' + column)) +
        ((char)('1' + row));
    return sb;
  }

  public static boolean validateLimits(int column, int row) {
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

}
