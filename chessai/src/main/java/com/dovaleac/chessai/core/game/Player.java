package com.dovaleac.chessai.core.game;

import com.dovaleac.chessai.core.ConsolidatedPosition;
import com.dovaleac.chessai.core.moves.Move;

import java.util.Timer;

public interface Player {

  Move chooseMove(ConsolidatedPosition position);
  Timer getTimer();
}
