package com.dovaleac.chessai.core.analysis;

import com.dovaleac.chessai.core.Position;
import com.dovaleac.chessai.core.analysis.prioritizers.NoKingPresentException;

public abstract class LastMoveAnalyzer<RT> implements PositionAnalyzer<RT> {

  @Override
  public RT analyze(Position position) throws NoKingPresentException {
    return analyze(position, position.getLastMove());
  }

  protected abstract RT analyze(Position position, Position.PositionRewritingInfo lastMove) throws NoKingPresentException;
}
