package com.dovaleac.chessai.core;

public enum Color {
  WHITE(true),
  BLACK(false);

  public boolean isInner() {
    return inner;
  }

  private boolean inner;

  Color(boolean b) {
    inner=b;
  }

  public Color flip() {
    if (this == WHITE) {
      return BLACK;
    }
    return WHITE;
  }
}
