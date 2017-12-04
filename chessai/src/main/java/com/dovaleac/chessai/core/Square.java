package com.dovaleac.chessai.core;

public class Square {
  private final int column;
  private final int row;

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
    return new Square(charArray[0]-'a', charArray[1] - '1');
  }
}
