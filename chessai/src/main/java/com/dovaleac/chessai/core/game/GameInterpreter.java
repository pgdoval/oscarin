package com.dovaleac.chessai.core.game;

import com.dovaleac.chessai.core.ConsolidatedPosition;

public class GameInterpreter {
  private final Player whitePlayer;
  private final Player blackPlayer;
  private ConsolidatedPosition position;

  public GameInterpreter(Player whitePlayer, Player blackPlayer, ConsolidatedPosition position) {
    this.whitePlayer = whitePlayer;
    this.blackPlayer = blackPlayer;
    this.position = position;
  }
}
