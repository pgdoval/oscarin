package com.dovaleac.chessai.core.game;

import com.dovaleac.chessai.core.Position;

public class GameInterpreter {
  private final Player whitePlayer;
  private final Player blackPlayer;
  private Position position;

  public GameInterpreter(Player whitePlayer, Player blackPlayer, Position position) {
    this.whitePlayer = whitePlayer;
    this.blackPlayer = blackPlayer;
    this.position = position;
  }
}
