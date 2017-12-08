package com.dovaleac.chessai.core.game;

import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.moves.Move;

import java.util.Timer;

public interface Player {

  Move chooseMove(Position position);
  Timer getTimer();
}
