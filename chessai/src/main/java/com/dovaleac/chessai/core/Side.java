package com.dovaleac.chessai.core;

public enum Side {
  KINGSIDE (true),
  QUEENSIDE (false);

  public boolean isInner() {
    return inner;
  }

  private boolean inner;

  Side(boolean b) {
    inner=b;
  }
}
